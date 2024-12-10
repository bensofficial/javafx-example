package de.tum.cit.ase.javafx.exercise;

import de.tum.in.test.api.*;
import de.tum.in.test.api.jupiter.Public;
import org.hamcrest.TypeSafeMatcher;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


// Whitelisted test classes
@WhitelistClass(ExampleAppTest.class)

// General test annotations
@StrictTimeout(10)
@MirrorOutput
@Public
@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
public @interface CustomAnnotations {
}