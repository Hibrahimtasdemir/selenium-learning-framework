@echo off
set MAVEN_CMD=mvn
%MAVEN_CMD% -N io.takari:maven:wrapper %*
