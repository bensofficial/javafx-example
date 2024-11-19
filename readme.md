# Execute application

```
gradle clean run
```

# Run tests

```
docker run -it --rm -v ./:/app -w /app ls1tum/artemis-maven-template:java17-20 /bin/bash -c "apt-get update && apt-get install -y libpango1.0-0 && ./gradlew clean test"
```

# Run tests for debugging

This has the advantage that you can run the tests without reinstalling the dependencies every time.

```
docker run -it --rm -v ./:/app -w /app ls1tum/artemis-maven-template:java17-20 /bin/bash
```
Then, run `apt-get update && apt-get install -y libpango1.0-0` inside the container. Now you can run `./gradlew clean test` inside the container to test the code after each change.
