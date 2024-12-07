package de.tum.in.test.api.localization;

import de.tum.in.test.api.jupiter.JupiterLocaleExtension;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Locale;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Used to specify a specific locale for a test class like <code>de</code> or
 * <code>en_US</code>.
 *
 * @author Christian Femers
 * @version 1.1.0
 * @see Locale#Locale(String)
 * @since 0.1.0
 */
@Documented
@Inherited
@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
@API(status = Status.STABLE)
@ExtendWith(JupiterLocaleExtension.class)
public @interface UseLocale {
    String value();
}
