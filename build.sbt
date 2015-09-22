name := "codinggame"

version := "1.0"

val commonSettings = Seq(
  scalaVersion  := "2.11.7",
  libraryDependencies +=
    "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)


libraryDependencies in ThisBuild ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.3.9",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

lazy val codinggame = project in file(".") aggregate(apu2, skynetVirus, bourse, conway) settings(commonSettings: _*)

lazy val apu2 = project settings(commonSettings: _*)

lazy val skynetVirus = project settings(commonSettings: _*)

lazy val bourse = project settings(commonSettings: _*)

lazy val conway = project settings(commonSettings: _*)

lazy val numeros = project settings(commonSettings: _*)

lazy val cgx = project settings(commonSettings: _*)

lazy val gattaca = project settings(commonSettings: _*)

lazy val superCalculateur = project settings(commonSettings: _*)
