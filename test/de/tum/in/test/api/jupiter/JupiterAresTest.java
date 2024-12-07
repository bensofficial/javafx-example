package de.tum.in.test.api.jupiter;

import de.tum.in.test.api.context.TestType;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This is only for internal use, to reduce redundancy.
 *
 * @author Christian Femers
 */
@API(status = Status.INTERNAL)
@Inherited
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
@ExtendWith(JupiterIOExtension.class)
@ExtendWith(JupiterTestGuard.class)
@ExtendWith(JupiterSecurityExtension.class)
@ExtendWith(JupiterStrictTimeoutExtension.class)
public @interface JupiterAresTest {

    TestType value();
}
