package de.tum.in.test.api;

import de.tum.in.test.api.io.AresIOContext;
import de.tum.in.test.api.io.IOManager;
import de.tum.in.test.api.io.IOTester;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Allows to overwrite the default IO test implementation of Ares with is using
 * {@link IOTester}.
 * <p>
 * A custom {@link IOManager} class must have a constructor that takes no
 * arguments and be accessible to Ares. All classes used for that purpose should
 * be trusted/whitelisted.
 *
 * @author Christian Femers
 * @version 1.0.0
 * @see IOManager
 * @since 1.9.1
 */
@API(status = Status.EXPERIMENTAL)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, ANNOTATION_TYPE})
public @interface WithIOManager {

    /**
     * The {@link IOManager} implementation to use for testing in the annotated
     * element.
     */
    Class<? extends IOManager<?>> value();

    /**
     * Effectively no {@link IOManager}. {@link System#out}, {@link System#err} and
     * {@link System#in} are unchanged. Not recommended. Consider a custom but
     * functional {@link IOManager} implementation first.
     */
    final class None implements IOManager<Void> {

        @Override
        public void beforeTestExecution(AresIOContext context) {
            // do nothing
        }

        @Override
        public void afterTestExecution(AresIOContext context) {
            // do nothing
        }

        @Override
        public Void getControllerInstance(AresIOContext context) {
            return null;
        }

        @Override
        public Class<Void> getControllerClass() {
            return null;
        }
    }
}
