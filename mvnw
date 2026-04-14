#!/usr/bin/env sh
MAVEN_PROJECTBASEDIR=`dirname "$0"`
MVNW_REPOURL="https://repo.maven.apache.org/maven2/"
if [ -z "$MAVEN_HOME" ]; then
  MVN_EXEC="mvn"
else
  MVN_EXEC="$MAVEN_HOME/bin/mvn"
fi
exec "$MVN_EXEC" -N io.takari:maven:wrapper "$@"
