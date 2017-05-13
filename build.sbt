name := """JacobBotServer"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"

libraryDependencies += filters
libraryDependencies += "uk.co.panaxiom" %% "play-jongo" % "2.0.0-jongo1.3"
libraryDependencies += javaWs