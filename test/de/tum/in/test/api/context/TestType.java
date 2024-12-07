package de.tum.in.test.api.context;

import de.tum.in.test.api.jupiter.Hidden;
import de.tum.in.test.api.jupiter.HiddenTest;
import de.tum.in.test.api.jupiter.Public;
import de.tum.in.test.api.jupiter.PublicTest;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Type of an Ares test case
 *
 * @author Christian Femers
 * @version 1.1.0
 * @see Hidden
 * @see Public
 * @see HiddenTest
 * @see PublicTest
 * @since 0.2.0
 */
@API(status = Status.MAINTAINED)
public enum TestType {
    PUBLIC,
    HIDDEN
}
