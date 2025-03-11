#!/bin/bash

echo this script will create vocabulary.yml from italiano-dictionary-complete.csv
echo and then check that the ouput file is well formed
# Input and output file paths
input_file="italiano-dictionary-complete.csv"
output_preprocessed_file="/tmp/vocabulary_preprocessed.csv"
output_file="vocabulary.yml"


# Function to convert date format
convert_date() {
  IFS='/' read -r day month year <<< "$1"
  echo "$year-$month-$day"
}
# preprocess input file to replace ; within double-quoted strings" by ,
# Preprocess the CSV file to handle quoted strings with semicolons
 sed 's/"\([^"]*\);\([^"]*\)"/"\1,\2"/g'  "$input_file" > "$output_preprocessed_file"

# Read the CSV file and convert it to YAML format
{
  echo "dictionary:"
  IFS=';'
  while read -r N Article Italien Francais Type TypeComp Date Cat1 Cat2 Cat3 Locutions Expressions Proverbi Conjugaison emoji Nota; do
    if [[ "$N" != "N" ]]; then
      Francais=${Francais//:/=}
      Italien=${Italien//:/=}
      if [ -n "$Cat1" ]; then
        Cat1=" $Cat1"
      fi
      if [ -n "$Cat2" ]; then
        Cat2=" $Cat2"
      fi
      if [ -n "$Cat3" ]; then
        Cat3=" $Cat3"
      fi
      if [ -n "$Conjugaison" ]; then
        Conjugaison=" $Conjugaison"
      fi
      if [ -n "$Locutions" ]; then
        Locutions=" $Locutions"
      fi
      if [ -n "$Expressions" ]; then
        Expressions=" $Expressions"
      fi
      if [ -n "$Proverbi" ]; then
        Proverbi=" $Proverbi"
      fi
      echo "  - word:"
      echo "      french:"
      echo "        word: $Francais"
      echo "      italian:"
      echo "        itword: $Italien"
      echo "        type: $Type"
      if [[ "$TypeComp" == "nm" ]]; then
      echo "        genre: masc."
      elif [[ "$TypeComp" == "nf" ]]; then
      echo "        genre: fém."
      fi
      echo "        category:"
      echo "          cat1:$Cat1"
      echo "          cat2:$Cat2"
      echo "          cat3:$Cat3"
      echo "        date: $(convert_date $Date)"

      if [[ "$Type" == "verbe" ]]; then
      echo "        conjugation:$Conjugaison"
      fi
      echo "        locution:$Locutions"
      echo "        expression:$Expressions"
      echo "        proverb:$Proverbi"
    fi
  done
} < "$output_preprocessed_file" > "$output_file"

sed -i 's/[[:space:]]*$//' "$output_file"

echo checking resulting file...
yamllint --no-warnings -d "{extends: default, rules: {line-length: {max: 180}}}" "$output_file"

echo "YAML file created successfully!"
