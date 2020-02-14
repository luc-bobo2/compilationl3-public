#!/bin/sh

for file in test/input/*.l ; do
  echo "Auto compiling $file"
  java -cp out/production/compilationl3-public/ Compiler "$file"
done
