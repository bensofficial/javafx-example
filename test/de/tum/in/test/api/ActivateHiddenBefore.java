package de.tum.in.test.api;

import de.tum.in.test.api.jupiter.HiddenTest;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This is the counterpart to {@link Deadline}. It specifies a date before which
 * {@link HiddenTest}s will always be executed. The format of the String has to
 * be the same as the format for <code>Deadline</code>.
 * <p>
 * <b>This annotation overrides the {@link Deadline} annotation and deactivates
 * it up to the given date.</b>
 *
 * @author Christian Femers
 * @version 1.2.0
 * @see Deadline
 * @since 0.1.2
 */
@API(status = Status.STABLE)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, ANNOTATION_TYPE})
public @interface ActivateHiddenBefore {
    String value();
}
