 #!/bin/bash

for file in ./*
do 
 if test -d $file
 then 
     echo $file ;
     cd $file;
     git branch;
     git status ;
    cd ..;
 fi
done

