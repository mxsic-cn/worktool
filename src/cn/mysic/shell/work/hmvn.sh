#!/bin/sh
# name:pull.sh
# discription:this script will pull all the new version git file from origin with it's branch or the branch has been given
echo 'mvnning...'

cd /Users/siqishangshu/workspace/code
echo 'cd to the code dir'

DIR=$(cd `dirname $0`; pwd)
echo $DIR

for file in ./*
do
    if test -d $file
    then
	if test -f $file/pom.xml
	then
            echo $file 
	    cd $file
	    echo 'mvn clean'
	    mvn clean;
	    echo 'mvn compile'
	    mvn compile;
	    echo 'mvn package'
	    mvn package;
	    cd $DIR
	fi
    fi
done
echo 'mvn done'
