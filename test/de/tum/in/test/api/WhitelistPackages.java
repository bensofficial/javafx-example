package de.tum.in.test.api;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Container annotation for {@linkplain Repeatable} {@link WhitelistPackage}.
 *
 * @author Christian Femers
 * @version 1.1.0
 * @see WhitelistPackage
 * @since 0.5.1
 */
@API(status = Status.MAINTAINED)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({METHOD, TYPE, ANNOTATION_TYPE})
public @interface WhitelistPackages {
    WhitelistPackage[] value();
}
