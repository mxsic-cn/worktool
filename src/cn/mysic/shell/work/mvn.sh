#!/bin/sh
# name:pull.sh
# discription:this script will pull all the new version git file from origin with it's branch or the branch has been given
echo 'mvnning...'

DIR=`pwd`
echo $DIR
cd $DIR
echo 'mvn clean'
mvn clean;
echo 'mvn compile'
mvn compile;
echo 'mvn install'
mvn install
echo 'mvn done'
