#!/bin/bash
# this script install and build 
# all the java project in this path
cd common/idl;
./gradlew  clean;
./gradlew  assemble;
./gradlew  build;
./gradlew  install;

cd ../../
cd ui ;
gulp compile ;
cd ../
cd apps/apps/module ;
gradle clean;
gradle ideaModule;
gradle install;
cd ../../../

cd apps/apps/server;
gradle clean;
gradle ideaModule;
gradle install;

cd ../../../
cd core/lib;
gradle clean;
gradle ideaModule;
gradle install;

cd ../../
cd learning/lib ;
gradle clean;
gradle ideaModule;
gradle install;
cd ../../
