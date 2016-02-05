#!/bin/bash
cat << EOF | sbt
set name := "SbtTest"
set version := "1.0"
set scalaVersion := "$SCALA_VERSION"
session save
compile
exit
EOF

sbt about