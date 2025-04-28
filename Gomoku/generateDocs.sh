#!/bin/bash

echo "Generating Javadoc with private methods..."

javadoc -d doc -private \
  src/main/java/model/*.java \
  src/main/java/util/*.java \
  src/main/java/ai/*.java \
  src/main/java/save/*.java \
  src/main/java/app/*.java

if [ $? -ne 0 ]; then
  echo "Failed to generate Javadoc."
  read -p "Press enter to exit..."
  exit 1
fi

echo "Javadoc generated in the 'doc' folder."

if command -v xdg-open &> /dev/null; then
  xdg-open doc/index.html
else
  echo "Open doc/index.html manually in your browser."
fi

read -p "Press enter to exit..."
