package de.tum.in.test.api.dynamic;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.tum.in.test.api.localization.Messages.localized;
import static de.tum.in.test.api.localization.Messages.localizedFailure;

@API(status = Status.MAINTAINED)
public class DynamicField<T> implements Checkable {

    private final de.tum.in.test.api.dynamic.DynamicClass<?> owner;
    private final List<String> name;
    private final de.tum.in.test.api.dynamic.DynamicClass<T> type;
    private final boolean ignoreCase;
    private Field field;

    public DynamicField(de.tum.in.test.api.dynamic.DynamicClass<?> dClass, Class<T> fType, boolean ignoreCase, String... possibleNames) {
        this(dClass, de.tum.in.test.api.dynamic.DynamicClass.toDynamic(fType), ignoreCase, possibleNames);
    }

    public DynamicField(de.tum.in.test.api.dynamic.DynamicClass<?> dClass, DynamicClass<T> fType, boolean ignoreCase, String... possibleNames) {
        this.owner = Objects.requireNonNull(dClass);
        if (ignoreCase)
            this.name = Stream.of(possibleNames).map(String::toLowerCase).collect(Collectors.toUnmodifiableList());
        else
            this.name = List.of(possibleNames);
        this.type = Objects.requireNonNull(fType);
        this.ignoreCase = ignoreCase;
    }

    public Field toField() {
        if (field == null) {
            var of = findField(owner.toClass());
            if (!of.isPresent()) {
                throw localizedFailure("dynamics.field.not_found", name); //$NON-NLS-1$
            }
            field = of.get();
            field.trySetAccessible();
        }
        return field;
    }

    @Override
    public boolean exists() {
        if (field == null) {
            var of = findField(owner.toClass());
            if (!of.isPresent()) {
                return false;
            }
            field = of.get();
            field.trySetAccessible();
        }
        return true;
    }

    public T getOf(Object o) {
        try {
            return type.cast(toField().get(o));
        } catch (IllegalAccessException e) {
            throw localizedFailure(e, "dynamics.field.access", name, owner); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            throw localizedFailure(e, "dynamics.field.target", name, owner); //$NON-NLS-1$
        } catch (ClassCastException e) {
            throw localizedFailure(e, "dynamics.field.cast", name, owner, type.getName()); //$NON-NLS-1$
        }
    }

    public T getStatic() {
        try {
            return getOf(null);
        } catch (NullPointerException e) {
            throw localizedFailure(e, "dynamics.field.static", name, owner); //$NON-NLS-1$
        }
    }

    public void setStatic(T newValue) {
        try {
            setOf(null, newValue);
        } catch (NullPointerException e) {
            throw localizedFailure(e, "dynamics.field.static", name, owner); //$NON-NLS-1$
        }
    }

    public void setOf(Object o, T newValue) {
        if (Modifier.isFinal(toField().getModifiers()))
            throw localizedFailure("dynamics.field.final", name, owner); //$NON-NLS-1$
        try {
            toField().set(o, type.cast(newValue));
        } catch (IllegalAccessException e) {
            throw localizedFailure(e, "dynamics.field.access", name, owner); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            throw localizedFailure(e, "dynamics.field.target", name, owner); //$NON-NLS-1$
        } catch (ClassCastException e) {
            throw localizedFailure(e, "dynamics.field.cast_set", name, owner, type.getName(), //$NON-NLS-1$
                    newValue == null ? "null" : newValue.getClass()); //$NON-NLS-1$
        }
    }

    private Optional<Field> findField(Class<?> c) {
        return fieldsOf(c).stream().filter(f -> name.contains(ignoreCase ? f.getName().toLowerCase() : f.getName()))
                .findFirst();
    }

    private List<Field> fieldsOf(Class<?> c) {
        ArrayList<Field> al = new ArrayList<>();
        Class<?> current = c;
        while (current != Object.class) {
            for (Field ff : current.getDeclaredFields())
                if (type.toClass().isAssignableFrom(ff.getType()))
                    al.add(ff);
            current = current.getSuperclass();
        }
        return al;
    }

    @Override
    public String toString() {
        return owner.toString() + "." + name; //$NON-NLS-1$
    }

    @Override
    public void check(Check... checks) {
        int modifiers = toField().getModifiers();
        for (Check check : checks)
            check.checkModifiers(modifiers, () -> localized("dynamics.field.name", this)); //$NON-NLS-1$
    }
}
