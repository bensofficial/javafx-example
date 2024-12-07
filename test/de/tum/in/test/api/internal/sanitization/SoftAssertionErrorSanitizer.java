package de.tum.in.test.api.internal.sanitization;

import org.assertj.core.api.SoftAssertionError;

import java.util.List;
import java.util.Set;

import static de.tum.in.test.api.internal.BlacklistedInvoker.invoke;

enum SoftAssertionErrorSanitizer implements de.tum.in.test.api.internal.sanitization.SpecificThrowableSanitizer {
    INSTANCE;

    private final Set<Class<? extends Throwable>> types = Set.of(SoftAssertionError.class);

    @Override
    public boolean canSanitize(Throwable t) {
        return types.contains(t.getClass());
    }

    @Override
    public Throwable sanitize(Throwable t, MessageTransformer messageTransformer) {
        SoftAssertionError sae = (SoftAssertionError) t;
        SoftAssertionError newSae = new SoftAssertionError(invoke(() -> List.copyOf(sae.getErrors())));
        de.tum.in.test.api.internal.sanitization.SanitizationUtils.copyThrowableInfoSafe(sae, newSae);
        return newSae;
    }
}
