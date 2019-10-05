lazy val commonSettings = Seq(
  organization := "net.kgtkr",
  scalaVersion := "2.13.1",
  scalacOptions ++= Seq(
    "-language:higherKinds"
  ),
  wartremoverErrors ++= Warts.all
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "twitter-tools",
    version := "0.1",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.0.0",
      "io.circe" %% "circe-yaml" % "0.11.0-M1",
      "io.circe" %% "circe-core" % "0.12.1",
      "io.circe" %% "circe-generic" % "0.12.1",
      "io.circe" %% "circe-parser" % "0.12.1",
      "org.scalactic" %% "scalactic" % "3.0.8",
      "org.scalatest" %% "scalatest" % "3.0.8" % "test"
    ),
    mainClass in assembly := Some("net.kgtkr.twitter_tools.App"),
    assemblyJarName in assembly := "app.jar"
  )
