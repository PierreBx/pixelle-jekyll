#!/bin/bash

clear

echo -n deleting old posts...
rm ./_movies/*.md 2> /dev/null
echo done!

echo -n cleaning posts...
rm ./_movies/*.md 2> /dev/null
echo done!

echo -n cleaning assets and includes symlinks
rm ./_includes/media 2> /dev/null
rm ./assets/media 2> /dev/null
echo done!

echo -n creating asets and includes symlinks
ln -s ./_data/media ./_includes/media
ln -s ./_data/media ./assets/media
echo done!

echo -n creating posts...
groovy ./_scripts/create_posts.groovy
echo done!

