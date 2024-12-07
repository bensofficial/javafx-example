package de.tum.in.test.api.internal.sanitization;

import de.tum.in.test.api.util.UnexpectedExceptionError;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.util.List;

@API(status = Status.INTERNAL)
public final class ThrowableSanitizer {

    private static final List<de.tum.in.test.api.internal.sanitization.SpecificThrowableSanitizer> SANITIZERS = List.of(de.tum.in.test.api.internal.sanitization.SafeTypeThrowableSanitizer.INSTANCE,
            de.tum.in.test.api.internal.sanitization.AssertionFailedErrorSanitizer.INSTANCE, de.tum.in.test.api.internal.sanitization.PrivilegedExceptionSanitizer.INSTANCE,
            de.tum.in.test.api.internal.sanitization.MultipleFailuresErrorSanitizer.INSTANCE, MultipleAssertionsErrorSanitizer.INSTANCE,
            de.tum.in.test.api.internal.sanitization.ExceptionInInitializerErrorSanitizer.INSTANCE, de.tum.in.test.api.internal.sanitization.SoftAssertionErrorSanitizer.INSTANCE);

    static {
        de.tum.in.test.api.internal.sanitization.ThrowableSets.init();
    }

    private ThrowableSanitizer() {
        // static methods only
    }

    public static Throwable sanitize(final Throwable t) {
        return sanitize(t, MessageTransformer.IDENTITY);
    }

    public static Throwable sanitize(final Throwable t, MessageTransformer messageTransformer) {
        if (t == null)
            return null;
        return de.tum.in.test.api.internal.sanitization.SanitizationUtils.sanitizeWithinScopeOf(t, () -> {
            if (UnexpectedExceptionError.class.equals(t.getClass()))
                return t;
            var firstPossibleSan = SANITIZERS.stream().filter(s -> s.canSanitize(t)).findFirst();
            return firstPossibleSan.orElse(de.tum.in.test.api.internal.sanitization.ArbitraryThrowableSanitizer.INSTANCE).sanitize(t, messageTransformer);
        });
    }
}
