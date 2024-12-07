package de.tum.in.test.api;

import de.tum.in.test.api.jupiter.HiddenTest;
import de.tum.in.test.api.jupiter.PublicTest;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Set a Deadline for {@link HiddenTest}, must be present. After this deadline,
 * hidden tests will be executed if there are no relevant
 * {@link ExtendedDeadline} annotations present. The annotation can also be
 * placed on a single method, which will <b>overwrite</b> any annotations on the
 * class level. This annotation is optional if all tests of the test class are
 * {@link PublicTest}s.
 * <p>
 * The deadline date (and time) is the date set by this annotation <b>plus</b>
 * the {@link ExtendedDeadline} annotation of the test class if present and
 * <b>plus</b> the {@link ExtendedDeadline} annotation of the test method, if
 * present. Hidden tests are executed, when {@link ZonedDateTime#now()} is
 * <b>past this date</b>.
 * <p>
 * The format has to be
 *
 * <pre>
 * {@linkplain DateTimeFormatter#ISO_LOCAL_DATE ISO_LOCAL_DATE}(T| ){@linkplain DateTimeFormatter#ISO_LOCAL_TIME ISO_LOCAL_TIME}( {@link ZoneId
 * ZONE_ID})?
 * </pre>
 * <p>
 * While the {@link ZoneId} can be left out, this is highly discouraged because
 * build agents might not have the same/correct time zone set. <br>
 * So this can be e.g.
 * <ul>
 * <li><code>2019-09-09 06:00 Europe/Berlin</code></li>
 * <li><code>2019-09-09T06:00 UTC</code></li>
 * <li><code>2019-09-09T06:00:01 America/Los_Angeles</code></li>
 * </ul>
 *
 * @author Christian Femers
 * @version 1.2.0
 * @since 0.1.0
 */
@API(status = Status.STABLE)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, ANNOTATION_TYPE})
public @interface Deadline {
    String value();
}
