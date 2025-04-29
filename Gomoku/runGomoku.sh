#!/bin/bash

echo "Compiling..."

javac -d target/classes \
src/main/java/model/*.java \
src/main/java/util/*.java \
src/main/java/ai/*.java \
src/main/java/save/*.java \
src/main/java/app/*.java

if [ $? -ne 0 ]; then
  echo "Compilation failed."
  read -p "Press enter to exit..."
  exit 1
fi

echo
echo "Running Gomoku..."
echo

java -cp target/classes main.java.app.Gomoku

read -p "Press enter to exit..."
