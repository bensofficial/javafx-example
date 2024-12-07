package de.tum.in.test.api;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Container annotation for {@linkplain Repeatable} {@link de.tum.in.test.api.BlacklistPackage}.
 *
 * @author Christian Femers
 * @version 1.1.0
 * @see de.tum.in.test.api.BlacklistPackage
 * @since 0.5.1
 */
@API(status = Status.MAINTAINED)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({METHOD, TYPE, ANNOTATION_TYPE})
public @interface BlacklistPackages {
    BlacklistPackage[] value();
}
