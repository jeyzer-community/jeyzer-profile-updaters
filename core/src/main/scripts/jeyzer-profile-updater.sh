#!/bin/sh

# -------------------------------------
# 
# Jeyzer Profile Updater
# 
# -------------------------------------

if [ ! -d "$JAVA_HOME" ]; then
	echo "JAVA_HOME not set"
	echo "JAVA_HOME must point to JDK/JRE 1.7+ installation"
	exit 1
fi

# -----------------------------------------------------------
# Jeyzer Profile Updater parameters
# -----------------------------------------------------------

BASE_DIR=`pwd`

JEYZER_PROFILE_SOURCE_PATH="$BASE_DIR/test/src/demo-features_profile.xml"
export JEYZER_PROFILE_SOURCE_PATH

JEYZER_PROFILE_TARGET_PATH="$BASE_DIR/test/target/demo-features_profile.xml"
export JEYZER_PROFILE_TARGET_PATH

JEYZER_NEW_PROFILE_ENTRIES_PATH="$BASE_DIR/test/patterns/new_profile_entries.xml"
export JEYZER_NEW_PROFILE_ENTRIES_PATH


# -----------------------------------------------------------
# Internals - do not edit
# -----------------------------------------------------------

# reset JAVA_OPTS
JAVA_OPTS=

JEYZER_PROFILE_UPDATER_PARAMS="-Djeyzer.profile.source=$JEYZER_PROFILE_SOURCE_PATH -Djeyzer.profile.target=$JEYZER_PROFILE_TARGET_PATH -Djeyzer.new.profile.entries=$JEYZER_NEW_PROFILE_ENTRIES_PATH"

# JVM options
#JAVA_OPTS="$JAVA_OPTS -Xmn15m -Xms20m -Xmx20m"

# Jeyzer profile updater library
CLASSPATH="$CLASSPATH:lib/profile-updater.jar:lib/guava-${com.google.guava.guava.version}.jar"

# Java debug options
# JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5000"

echo Starting Jeyzer Profile Updater v${pom.version}...
$JAVA_HOME/bin/java $JEYZER_PROFILE_UPDATER_PARAMS $JAVA_OPTS -cp $CLASSPATH org.jeyzer.profile.JeyzerProfileUpdater
