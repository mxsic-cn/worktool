#!/bin/sh

# scrpit : this sh will start the main


echo 'customer ui starting...'

cd /Users/siqishangshu/workspace/code
echo 'cd to the code dir'

cd HNA-logistics-cus-ui

echo 'customer ui starting...'
forever start app.js
echo 'customer ui is ready'
