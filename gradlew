#!/usr/bin/env sh
SAVED="`pwd`"
cd "`dirname \"$0\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null
APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'
die () { echo; echo "$*"; echo; exit 1; }
JAVACMD="java"
which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set"
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
exec "$JAVACMD" $DEFAULT_JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
