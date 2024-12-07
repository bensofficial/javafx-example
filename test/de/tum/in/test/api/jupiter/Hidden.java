package de.tum.in.test.api.jupiter;

import de.tum.in.test.api.Deadline;
import de.tum.in.test.api.context.TestType;
import de.tum.in.test.api.io.IOTester;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks a HIDDEN test case, can declare {@link IOTester} as parameter. This
 * annotation requires a {@link Deadline} annotation to be set either on the
 * test class or the test method. See {@link Deadline} for more information.
 * <p>
 * <b>This annotation must be accompanied by some JUnit 5 test annotation, it
 * will not cause test execution by itself!</b> This makes it usable with
 * different JUnit 5 runners.
 * <p>
 * Can be used on class level in addition to method level to affect test class
 * initialization, {@link BeforeAll}, {@link AfterAll}, {@link BeforeEach},
 * {@link AfterEach}. This will also cause all members of the class (e.g. test
 * methods) to inherit this test case type, unless they declare another one.
 *
 * @author Christian Femers
 * @version 1.2.0
 * @see Deadline
 * @since 0.2.0
 */
@API(status = Status.STABLE)
@Inherited
@Documented
@Retention(RUNTIME)
@Target({METHOD, ANNOTATION_TYPE, TYPE})
@JupiterAresTest(TestType.HIDDEN)
public @interface Hidden {
    // marker only
}