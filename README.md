# GraalVM Native Image Hello World
This is an example demonstrating the use of GraalVM's Native Image functionality.

## Prerequisites
Before proceeding, make sure to have the following software installed:
1. GraalVM >= 19
2. Native Image component JAR:
https://www.graalvm.org/docs/reference-manual/native-image/#install-native-image
3. Maven

## Building
To build, execute the following command:
1. Run command 'mvn package'

## Running
To run, execute the following command:
1. './target/HelloWorld'
2. You should see output 'Hello, world!'