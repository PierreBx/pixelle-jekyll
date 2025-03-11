#!/bin/bash

PIXL_LOCAL_URL=http://127.0.0.1:4000/pixelle02/

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

echo -n creating assets and includes files...
cp -ru ./_data/media ./assets/
cp -ru ./_data/media ./_includes/
green_text done!

echo creating posts...
groovy ./_scripts/create_posts.groovy

echo creating vocabulary entries...
groovy ./_scripts/create-voc-posts.groovy


echo "end of run.sh successfully!"

echo "waiting 1 sec before launching site"
sleep 1 && google-chrome --new-tab $PIXL_LOCAL_URL

