#!/bin/bash

green_text() {
    echo -e "\e[32m$1\e[0m"
}

clear

echo -n deleting old posts...
rm ./_posts/*.md 2> /dev/null
rm ./_movies/*.md 2> /dev/null
green_text done!

echo -n cleaning posts...
rm ./_posts/*.md 2> /dev/null
green_text done!

#echo -n cleaning assets and ...
#rm ./_includes/media 2> /dev/null
#rm ./assets/media 2> /dev/null
#green_text done!

echo -n creating assets and includes files...
cp -ru ./_data/media ./assets/
cp -ru ./_data/media ./_includes/
green_text done!

#echo -n creating assets and includes symlinks...
#ln -s ../_data/media ./_includes/media
#ln -s ../_data/media ./assets/media
#green_text done!

echo creating posts...
groovy ./_scripts/create_posts.groovy
echo "end of run.sh successfully!"

