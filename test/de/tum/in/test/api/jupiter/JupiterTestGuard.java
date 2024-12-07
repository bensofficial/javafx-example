package de.tum.in.test.api.jupiter;

import de.tum.in.test.api.Deadline;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.util.Optional;

import static de.tum.in.test.api.internal.ReportingUtils.doProceedAndPostProcess;
import static de.tum.in.test.api.internal.TestGuardUtils.checkForHidden;

/**
 * This class' main purpose is to guard the {@link HiddenTest}s execution and
 * evaluate the {@link Deadline}.
 *
 * @author Christian Femers
 */
@API(status = Status.INTERNAL)
public final class JupiterTestGuard implements UnifiedInvocationInterceptor {

    @Override
    public <T> T interceptGenericInvocation(Invocation<T> invocation, ExtensionContext extensionContext,
                                            Optional<ReflectiveInvocationContext<?>> invocationContext) throws Throwable {
        de.tum.in.test.api.jupiter.JupiterContext jupiterContext = JupiterContext.of(extensionContext);
        checkForHidden(jupiterContext);
        return doProceedAndPostProcess(invocation, jupiterContext);
    }
}
