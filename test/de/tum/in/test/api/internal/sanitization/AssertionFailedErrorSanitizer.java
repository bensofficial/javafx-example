package de.tum.in.test.api.internal.sanitization;

import org.opentest4j.AssertionFailedError;
import org.opentest4j.ValueWrapper;

import java.util.Set;

import static de.tum.in.test.api.internal.BlacklistedInvoker.invoke;

enum AssertionFailedErrorSanitizer implements de.tum.in.test.api.internal.sanitization.SpecificThrowableSanitizer {
    INSTANCE;

    private final Set<Class<? extends Throwable>> types = Set.of(AssertionFailedError.class);

    private static Object sanitizeValue(ValueWrapper vw) {
        if (vw == null)
            return null;
        return de.tum.in.test.api.internal.sanitization.SanitizationUtils.sanitizeWithinScopeOf(vw.getType(), () -> invoke(vw::getStringRepresentation));
    }

    @Override
    public boolean canSanitize(Throwable t) {
        return types.contains(t.getClass());
    }

    @Override
    public Throwable sanitize(Throwable t, MessageTransformer messageTransformer) {
        AssertionFailedError afe = (AssertionFailedError) t;
        ValueWrapper expected = afe.getExpected();
        ValueWrapper actual = afe.getActual();
        de.tum.in.test.api.internal.sanitization.ThrowableInfo info = ThrowableInfo.getEssentialInfosSafeFrom(t).sanitize();
        String newMessage = messageTransformer.apply(info);
        AssertionFailedError newAfe;
        // If both expected and actual are null, this cannot arise from assertEquals,
        // and thus afe was constructed only by providing a message (and cause)
        if (expected == null && actual == null)
            newAfe = new AssertionFailedError(newMessage);
        else
            newAfe = new AssertionFailedError(newMessage, sanitizeValue(expected), sanitizeValue(actual));
        de.tum.in.test.api.internal.sanitization.SanitizationUtils.copyThrowableInfoSafe(info, newAfe);
        return newAfe;
    }
}
