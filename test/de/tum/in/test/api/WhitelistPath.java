package de.tum.in.test.api;

import de.tum.in.test.api.security.ArtemisSecurityManager;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.*;
import java.nio.file.Path;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Allows to whitelist a {@link Path}, including all subpaths. The
 * {@link ArtemisSecurityManager} will disallow any fileaccess for
 * <b>non-whitelisted callers</b> outside the set of whitelisted paths. This
 * annotation is {@linkplain Repeatable}, and can be placed additively on the
 * test class and test method. Different types can be set to gain more control
 * over the mtaching paths.<br>
 * Use e.g. <code>@WhitelistPath("")</code> to allow access to all files in the
 * execution directory.
 * <p>
 * <b>If this annotation is not present, no access to any paths is granted.</b>
 *
 * @author Christian Femers
 * @version 1.2.0
 * @see Path
 * @since 0.2.0
 */
@API(status = Status.STABLE)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({METHOD, TYPE, ANNOTATION_TYPE})
@Repeatable(WhitelistPaths.class)
public @interface WhitelistPath {

    String[] value();

    de.tum.in.test.api.PathType type() default PathType.STARTS_WITH;

    de.tum.in.test.api.PathActionLevel level() default PathActionLevel.READ;
}
