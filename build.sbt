name := "codinggame"

version := "1.0"

val commonSettings = Seq(
  scalaVersion  := "2.11.6"
)


libraryDependencies in ThisBuild ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.3.9",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

lazy val codinggame = project in file(".") aggregate(apu2, skynetVirus, bourse) settings(commonSettings: _*)

lazy val apu2 = project settings(commonSettings: _*)

lazy val skynetVirus = project settings(commonSettings: _*)

lazy val bourse = project settings(commonSettings: _*)

