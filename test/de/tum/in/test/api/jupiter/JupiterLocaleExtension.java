package de.tum.in.test.api.jupiter;

import de.tum.in.test.api.localization.UseLocale;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Locale;

@API(status = Status.INTERNAL)
public class JupiterLocaleExtension implements BeforeAllCallback, AfterAllCallback {

    private static final String OLD_LOCALE_KEY = "old-locale"; //$NON-NLS-1$

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        var annot = AnnotationSupport.findAnnotation(context.getElement(), UseLocale.class);
        if (annot.isEmpty())
            return;
        getStore(context).put(OLD_LOCALE_KEY, Locale.getDefault());
        var newLocale = new Locale(annot.get().value());
        Locale.setDefault(newLocale);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        var oldLocale = getStore(context).remove(OLD_LOCALE_KEY, Locale.class);
        if (oldLocale != null)
            Locale.setDefault(oldLocale);
    }

    private Store getStore(ExtensionContext context) {
        return context.getStore(Namespace.create(getClass(), context.getElement()));
    }
}
