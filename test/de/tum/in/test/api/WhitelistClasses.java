package de.tum.in.test.api;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Container annotation for {@linkplain Repeatable} {@link WhitelistClass}.
 *
 * @author Christian Femers
 * @version 1.1.0
 * @see WhitelistClass
 * @since 0.4.4
 */
@API(status = Status.STABLE)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, ANNOTATION_TYPE})
public @interface WhitelistClasses {
    WhitelistClass[] value();
}
