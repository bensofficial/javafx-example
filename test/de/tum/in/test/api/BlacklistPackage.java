package de.tum.in.test.api;

import de.tum.in.test.api.security.ArtemisSecurityManager;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Allows to blacklist a package, possibly including all subpackages. The
 * {@link ArtemisSecurityManager} will disallow any access for
 * <b>non-whitelisted callers</b> outside the set of whitelisted packages. This
 * annotation is {@linkplain Repeatable}, and can be placed additively on the
 * test class and test method.
 * <p>
 * You can use <code>*</code> and <code>**</code> in the same way as in GLOB
 * patterns just applied to packages where the delimiter is <code>"."</code>.
 * <p>
 * Use e.g. <code>@BlacklistPackage("java.util")</code> to disallow access to
 * all classes in the package <code>java.util</code> and
 * <code>@BlacklistPackage("java.util**")</code> to disallow access to classes
 * in <code>java.util</code> and all its subpackages.
 * <code>@BlacklistPackage("**")</code> will disallow any use of classes outside
 * <code>java.lang</code>.
 * <p>
 * The access to <code>java.lang</code> is always allowed and by default, all
 * packages can be used, apart from java.lang.reflect, and internal packages of
 * Ares.
 * <p>
 * <b>This annotation will be overpowered any {@link WhitelistPackage}
 * annotations.</b>
 *
 * @author Christian Femers
 * @version 1.2.0
 * @since 0.5.1
 */
@API(status = Status.MAINTAINED)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({METHOD, TYPE, ANNOTATION_TYPE})
@Repeatable(BlacklistPackages.class)
public @interface BlacklistPackage {

    String[] value();
}
