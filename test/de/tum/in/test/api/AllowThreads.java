package de.tum.in.test.api;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Allows starting and modifying own Threads.
 *
 * @author Christian Femers
 * @version 1.0.0
 * @since 0.4.0
 */
@API(status = Status.STABLE)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, ANNOTATION_TYPE})
public @interface AllowThreads {
    /**
     * The maximum number of own Threads that are allowed to be active at the same
     * time.
     * <p>
     * Can be used to prevent crashing the test process
     * <p>
     * The default value is <code>1000</code>.
     */
    int maxActiveCount() default 1000;
}
