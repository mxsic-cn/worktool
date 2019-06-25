#!/bin/sh

# scrpit : this sh will start the main


echo 'main ui starting...'

echo 'php-fpm starting...'
sudo php-fpm -c /usr/local/php/etc/php.ini -y /usr/local/php/etc/php-fpm.conf
echo 'nginx starting...'
nginx 
echo 'main ui ready'
