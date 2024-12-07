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
 * Extends a {@link de.tum.in.test.api.Deadline} for a {@link HiddenTest}, is optional. This can be
 * applied both to the test class and the test method. If the method does not
 * specify a {@link de.tum.in.test.api.Deadline}, both two {@link ExtendedDeadline}s may be
 * combined. A test annotated with {@link ExtendedDeadline} must be hidden.
 * <p>
 * The format has to be like (spaces in between required)
 * <ul>
 * <li><code>2d</code></li>
 * <li><code>12h 30m</code></li>
 * <li><code>1d 5h</code></li>
 * <li><code>1d 5h 8m</code></li>
 * </ul>
 *
 * @author Christian Femers
 * @version 1.1.0
 * @see Deadline
 * @since 0.1.0
 */
@API(status = Status.STABLE)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, ANNOTATION_TYPE})
public @interface ExtendedDeadline {
    String value();
}
