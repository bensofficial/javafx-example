# Secure testing of JavaFX applications in context of Computer Science Education

This repository contains an example of how a JavaFX application can be tested securely using TestFX and Ares.

## Running the application

```
gradle clean run
```

## Running the tests

You can run the tests locally (e.g. on a system with a graphical interface) using: `./gradlew testLocally`

To start the tests in the headless mode, use: `./gradlew test`

If you want to run the tests in headless mode inside a Docker container (to replicate the behaviour of the Continuous Integration System), use: `docker run -it --rm -v ./:/app -w /app ls1tum/artemis-maven-template:java17-20 /bin/bash -c "apt-get update && apt-get install -y libpango1.0-0 && ./gradlew clean test"`

## Writing tests

As a testing framework for testing the JavaFX application, we use [TestFX](https://github.com/TestFX/TestFX).

The main features of TestFX are as stated on the website:

TestFX provides a simple-to-use API for setting up the test robot that will interact with the application.
Additionally, it provides a set of matchers to verify the state of the app right.

This integration provides a readable language to define such GUI tests.

This example of how it might look like is taken from _Best Practices for Efficient Development of JavaFX Applications_ (Kruk et al., 2018):

```java
rightClickOn("#desktop").moveTo("New").clickOn("Text Document");
write("myTextfile.txt").push(ENTER);

drag(".file").dropTo("#trash-can");

verifyThat("#desktop", hasChildren(0, ".file"));
```

You can also capture screenshots using:
```java
captureAndSaveScreenshot("testInput/1");
```

## Securing the tests

In general, we use [Ares](https://github.com/ls1intum/Ares) to secure our test execution.

To make Ares compatible with our JavaFX application, we had to adapt a few things.
Thus, this repo contains a hard fork of Ares.
The following things have been adapted compared to the original version:

- Removed Jqwik
  - `de.tum.in.test.api.jqwik` package
  - `@AddLifecycleHook(JqwikLocaleExtension.class)` in `UseLocale.java`
  - The reference in the JavaDoc of `StrictTimeout.java and `IOManager.java`
  - `ThrowableSets.java`
- Allow additional testing threads by replacing the `checkThreadGroup()` method in the `ArtemisSecurityManager`
```java
	private Thread[] checkThreadGroup() {
        // Allow all threads
		return new Thread[testThreadGroup.activeCount()];
	}
```
