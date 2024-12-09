package de.tum.cit.ase.javafx;

import de.tum.in.test.api.*;
import de.tum.in.test.api.jupiter.Public;
import org.hamcrest.TypeSafeMatcher;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// Whitelisted paths for macOS
@WhitelistPath(value = "/Users/[^/]+/\\.gradle(/.*)?", type = PathType.REGEX_ABSOLUTE)
@WhitelistPath(value = "/Users/[^/]+/\\.openjfx(/.*)?", type = PathType.REGEX_ABSOLUTE)
@WhitelistPath("/System/Library/Fonts")
@WhitelistPath(value = "/Users/[^/]+/Library/Java/Extensions/libglass.dylib", type = PathType.REGEX_ABSOLUTE)
@WhitelistPath(value = "/Users/[^/]+/Library/Java/JavaVirtualMachines(/.*)?", type = PathType.REGEX_ABSOLUTE)

@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
public @interface TestFXMacAnnotations {
}
