#!/bin/sh
./gradlew build -x test
cp build/classes/java/main/META-INF/swagger/registration-0.0.yml src/main/resources/swagger/registration-0.0.yml
cp build/classes/java/main/META-INF/swagger/registration-0.0.yml build/classes/java/main/META-INF/swagger/registration-0.0.yml
