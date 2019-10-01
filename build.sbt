lazy val commonSettings = Seq(
  organization := "net.kgtkr",
  name := "twitter-tools",
  version := "0.1",
  scalaVersion := "2.13.1",
  libraryDependencies := Seq(
    "org.typelevel" %% "cats-core" % "2.0.0"
  ),
  scalacOptions := Seq(
    "-language:higherKinds"
  )
)

lazy val app = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    mainClass in assembly := Some("net.kgtkr.twitter_tools.App"),
    assemblyJarName in assembly := "app.jar"
  )
