package de.tum.in.test.api;

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
 * This annotation can be applied to a class or method and tells the
 * {@link IOTester} (or an alternative implementation provided by
 * {@link IOManager}), whether to pipe the output to the original standard
 * output as well (mirroring everything received). It does not affect the test
 * result (unless the standard output throws an exception).
 * <p>
 * A {@link MirrorOutput} annotation on a method always overrides the one on the
 * class level.
 * <p>
 * <code>maxCharCount</code> is used to restrict the number of characters that
 * are stored and printed to the original standard output.
 *
 * @author Christian Femers
 * @version 1.2.1
 * @see IOManager
 * @since 0.1.0
 */
@API(status = Status.STABLE)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, ANNOTATION_TYPE})
public @interface MirrorOutput {

    long DEFAULT_MAX_STD_OUT = 100_000_000L;

    /**
     * If the output should be mirrored and printed to the original standard output
     * in addition to being recorded.
     */
    MirrorOutputPolicy value() default MirrorOutputPolicy.ENABLED;

    /**
     * This is used to restrict the number of characters that are stored and printed
     * to the original standard output.
     * <p>
     * Default value is <code>100_000_000</code>
     */
    long maxCharCount() default DEFAULT_MAX_STD_OUT;

    enum MirrorOutputPolicy {
        DISABLED,
        ENABLED;

        public boolean isEnabled() {
            return this == ENABLED;
        }
    }
}
