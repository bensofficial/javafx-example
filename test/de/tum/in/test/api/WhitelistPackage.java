package de.tum.in.test.api;

import de.tum.in.test.api.security.ArtemisSecurityManager;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Allows to whitelist a package, possibly including all subpackages. Only
 * useful in combination with {@link de.tum.in.test.api.BlacklistPackage}. The
 * {@link ArtemisSecurityManager} will allow access for <b>non-whitelisted
 * callers</b> to the set of whitelisted packages. This annotation is
 * {@linkplain Repeatable}, and can be placed additively on the test class and
 * test method.
 * <p>
 * You can use <code>*</code> and <code>**</code> in the same way as in GLOB
 * patterns just applied to packages where the delimiter is <code>"."</code>.
 * <p>
 * Use e.g. <code>@BlacklistPackage("java.util**")</code> and then
 * <code>@WhitelistPackage("java.util.function")</code> to allow access to all
 * classes in the package <code>java.util.function</code> but not any other
 * classes in <code>java.util</code> or any of its subclasses.
 * <p>
 * <b>This annotation overpowers any {@link BlacklistPackage} annotations.</b>
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
@Repeatable(WhitelistPackages.class)
public @interface WhitelistPackage {

    String[] value();
}
