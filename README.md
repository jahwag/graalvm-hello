# GraalVM Native Image Hello World
This is an example demonstrating the use of GraalVM's Native Image functionality.

## Prerequisites
Before proceeding, make sure to have the following software installed:
1. GraalVM >= 19
2. Native Image component JAR: https://www.graalvm.org/docs/reference-manual/native-image/#install-native-image
3. UPX (optional)
4. Maven

## Building
To build, execute the following command:
1. Run command 'mvn package'

## Shrinking
To reduce the size of the final executable, run UPX compression
1. Run command 'mvn exec:exec'

As a reference, on MacOS the original executable is compressed from 8 -> 2.6 MB.

## Running
To run, execute the following command:
1. './target/HelloWorld'

If you used shrinking, run the following:
1. './target/HelloWorld_upx'