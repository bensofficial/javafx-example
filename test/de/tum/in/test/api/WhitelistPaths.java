package de.tum.in.test.api;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.*;
import java.nio.file.Path;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Container annotation for {@linkplain Repeatable} {@link WhitelistPath}.
 *
 * @author Christian Femers
 * @version 1.1.0
 * @see WhitelistPath
 * @see Path
 * @since 0.2.0
 */
@API(status = Status.STABLE)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({METHOD, TYPE, ANNOTATION_TYPE})
public @interface WhitelistPaths {
    WhitelistPath[] value();
}
