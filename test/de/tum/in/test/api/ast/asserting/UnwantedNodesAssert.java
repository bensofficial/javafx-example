package de.tum.in.test.api.ast.asserting;

import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.StaticJavaParser;
import de.tum.in.test.api.AresConfiguration;
import de.tum.in.test.api.ast.model.UnwantedNode;
import de.tum.in.test.api.ast.type.*;
import de.tum.in.test.api.util.ProjectSourcesFinder;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;
import org.assertj.core.api.AbstractAssert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import static de.tum.in.test.api.localization.Messages.localized;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.fail;

/**
 * Checks whole Java files for unwanted nodes
 *
 * @author Markus Paulsen
 * @version 1.0.0
 * @since 1.12.0
 */
@API(status = Status.MAINTAINED)
public class UnwantedNodesAssert extends AbstractAssert<UnwantedNodesAssert, Path> {

    /**
     * The language level for the Java parser
     */
    private final LanguageLevel level;

    private UnwantedNodesAssert(Path path, LanguageLevel level) {
        super(requireNonNull(path), UnwantedNodesAssert.class);
        this.level = level;
        if (!Files.isDirectory(path)) {
            fail("The source directory %s does not exist", path); //$NON-NLS-1$
        }
    }

    /**
     * Creates an unwanted node assertion object for all project source files.
     * <p>
     * The project source directory gets extracted from the build configuration, and
     * a <code>pom.xml</code> or <code>build.gradle</code> in the execution path is
     * the default build configuration location. The configuration here is the same
     * as the one in the structural tests and uses {@link AresConfiguration}.
     *
     * @return An unwanted node assertion object (for chaining)
     */
    public static UnwantedNodesAssert assertThatProjectSources() {
        var path = ProjectSourcesFinder.findProjectSourcesPath().orElseThrow(() -> //$NON-NLS-1$
 new AssertionError("Could not find project sources folder." //$NON-NLS-1$
                + " Make sure the build file is configured correctly." //$NON-NLS-1$
                + " If it is not located in the execution folder directly," //$NON-NLS-1$
                + " set the location using AresConfiguration methods.")); //$NON-NLS-1$
        return new UnwantedNodesAssert(path, null);
    }

    /**
     * Creates an unwanted node assertion object for all source files at and below
     * the given directory path.
     *
     * @param directory Path to a directory under which all files are considered
     * @return An unwanted node assertion object (for chaining)
     */
    public static UnwantedNodesAssert assertThatSourcesIn(Path directory) {
        Objects.requireNonNull(directory, "The given source path must not be null."); //$NON-NLS-1$
        return new UnwantedNodesAssert(directory, null);
    }

    /**
     * Creates an unwanted node assertion object for all source files in the given
     * package, including all of its sub-packages.
     *
     * @param packageName Java package name in the form of, e.g.,
     *                    <code>de.tum.in.test.api</code>, which is resolved
     *                    relative to the path of this UnwantedNodesAssert.
     * @return An unwanted node assertion object (for chaining)
     * @implNote The package is split at "." with the resulting segments being
     * interpreted as directory structure. So
     * <code>assertThatSourcesIn(Path.of("src/main/java")).withinPackage("net.example.test")</code>
     * will yield an assert for all source files located at and below the
     * relative path <code>src/main/java/net/example/test</code>
     */
    public UnwantedNodesAssert withinPackage(String packageName) {
        Objects.requireNonNull(packageName, "The package name must not be null."); //$NON-NLS-1$
        var newPath = actual.resolve(Path.of("", packageName.split("\\."))); //$NON-NLS-1$ //$NON-NLS-2$
        return new UnwantedNodesAssert(newPath, level);
    }

    /**
     * Configures the language level used by the Java parser
     *
     * @param level The language level for the Java parser
     * @return An unwanted node assertion object (for chaining)
     */
    public UnwantedNodesAssert withLanguageLevel(LanguageLevel level) {
        return new UnwantedNodesAssert(actual, level);
    }

    /**
     * Verifies that the selected Java files do not contain any syntax tree nodes
     * (statements, expressions, ...) of the given type.
     *
     * @param type Unwanted statements
     * @return This unwanted node assertion object (for chaining)
     * @see ClassType
     * @see ConditionalType
     * @see ExceptionHandlingType
     * @see LoopType
     */
    public UnwantedNodesAssert hasNo(Type type) {
        if (level == null) {
            failWithMessage("The 'level' is not set. Please use UnwantedNodesAssert.withLanguageLevel(LanguageLevel)."); //$NON-NLS-1$
        }
        StaticJavaParser.getParserConfiguration().setLanguageLevel(level);
        Optional<String> errorMessage = UnwantedNode.getMessageForUnwantedNodesForAllFilesBelow(actual,
                type.getNodeNameNodeMap());
        errorMessage.ifPresent(unwantedNodeMessageForAllJavaFiles -> failWithMessage(
                localized("ast.method.has_no") + System.lineSeparator() + unwantedNodeMessageForAllJavaFiles)); //$NON-NLS-1$
        return this;
    }
}
