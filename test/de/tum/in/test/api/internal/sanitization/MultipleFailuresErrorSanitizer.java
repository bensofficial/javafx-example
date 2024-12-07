package de.tum.in.test.api.internal.sanitization;

import org.assertj.core.error.AssertJMultipleFailuresError;
import org.opentest4j.MultipleFailuresError;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

enum MultipleFailuresErrorSanitizer implements de.tum.in.test.api.internal.sanitization.SpecificThrowableSanitizer {
    INSTANCE;

    private final Set<Class<? extends Throwable>> types = Set.of(MultipleFailuresError.class,
            AssertJMultipleFailuresError.class);

    private static MultipleFailuresError createNewInstance(Throwable t, String heading, List<Throwable> failures) {
        if (t.getClass().equals(MultipleFailuresError.class))
            return new MultipleFailuresError(heading, failures);
        return new AssertJMultipleFailuresError(heading, failures);
    }

    @Override
    public boolean canSanitize(Throwable t) {
        return types.contains(t.getClass());
    }

    @Override
    public Throwable sanitize(Throwable t, MessageTransformer messageTransformer) {
        MultipleFailuresError mfe = (MultipleFailuresError) t;
        de.tum.in.test.api.internal.sanitization.ThrowableInfo info = ThrowableInfo.getEssentialInfosSafeFrom(mfe).sanitize();
        // list is safe here because of defensive copying in MultipleFailuresError
        List<Throwable> failures = mfe.getFailures().stream().map(ThrowableSanitizer::sanitize)
                .collect(Collectors.toUnmodifiableList());
        /*
         * Message already contains the failures and separating both is more difficult.
         * So we just pass the message constructed be the old object, as the new object
         * will in absence of failures only return the heading.
         */
        MultipleFailuresError newMfe = createNewInstance(mfe, messageTransformer.apply(info), List.of());
        de.tum.in.test.api.internal.sanitization.SanitizationUtils.copyThrowableInfoSafe(info, newMfe);
        // Retain failure information in the suppressed Throwables
        for (Throwable failure : failures)
            newMfe.addSuppressed(failure);
        return newMfe;
    }
}
