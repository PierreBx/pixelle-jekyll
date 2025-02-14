#!/bin/bash

clear
echo deleting old posts
rm ./_posts/*.md 2> /dev/null

echo creating posts...
groovy ./_scripts/create_posts.groovy

echo done!

