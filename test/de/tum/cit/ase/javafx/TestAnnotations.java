package de.tum.cit.ase.javafx;

import de.tum.in.test.api.*;
import de.tum.in.test.api.jupiter.Public;
import org.hamcrest.TypeSafeMatcher;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


// Whitelisted paths for build
@WhitelistPath("build/classes/java/main")
@WhitelistPath("build/resources/main")

// Whitelisted paths for screenshots
@WhitelistPath(value = "screenshots", level = PathActionLevel.WRITE)
@WhitelistPath(value = "screenshots", level = PathActionLevel.DELETE)
@WhitelistPath(value = "screenshots", level = PathActionLevel.READ)

// Whitelisted paths for Linux and when running in Docker
@WhitelistPath("/root/.gradle")
@WhitelistPath("/root/.openjfx")
@WhitelistPath("/usr/share/fonts")
@WhitelistPath("/opt/java/openjdk/lib")
@WhitelistPath("/usr/java/packages/lib")

// Whitelisted paths for macOS
@WhitelistPath(value = "/Users/[^/]+/\\.gradle(/.*)?", type = PathType.REGEX_ABSOLUTE)
@WhitelistPath(value = "/Users/[^/]+/\\.openjfx(/.*)?", type = PathType.REGEX_ABSOLUTE)
@WhitelistPath("/System/Library/Fonts")
@WhitelistPath(value = "/Users/[^/]+/Library/Java/Extensions/libglass.dylib", type = PathType.REGEX_ABSOLUTE)
@WhitelistPath(value = "/Users/[^/]+/Library/Java/JavaVirtualMachines(/.*)?", type = PathType.REGEX_ABSOLUTE)

// Whitelisted packages
@AddTrustedPackages({
        @AddTrustedPackage("com.sun.prism.**"),
        @AddTrustedPackage("com.sun.glass.ui.**"),
        @AddTrustedPackage("com.sun.javafx.**"),
        @AddTrustedPackage("javafx.**"),
        @AddTrustedPackage("java.awt.**"),
        @AddTrustedPackage("sun.awt.**"),
        @AddTrustedPackage("org.testfx.**"),
})
@WhitelistClass(TypeSafeMatcher.class)

// Allow all threads and trust all threads
@TrustedThreads(value = TrustedThreads.TrustScope.ALL_THREADS)
@AllowThreads

// Whitelisted test classes
@WhitelistClass(ExampleAppTest.class)
@WhitelistClass(HelperClass.class)

// General test annotations
@StrictTimeout(10)
@MirrorOutput
@Public
@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
public @interface TestAnnotations {
}
