lazy val commonSettings = Seq(
  organization := "net.kgtkr",
  scalaVersion := "2.13.1",
  scalacOptions ++= Seq(
    "-language:higherKinds"
  ),
  wartremoverErrors in (Compile, compile) ++= Warts
    .allBut(Wart.Any, Wart.DefaultArguments)
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
      "org.scalatest" %% "scalatest" % "3.0.8" % "test",
      "dev.zio" %% "zio" % "1.0.0-RC14",
      "dev.zio" %% "zio-streams" % "1.0.0-RC14",
      "org.typelevel" %% "cats-tagless-macros" % "0.10",
      "org.typelevel" %% "cats-mtl-core" % "0.7.0",
      "org.typelevel" %% "cats-effect" % "2.0.0"
    ),
    mainClass in assembly := Some("net.kgtkr.twitter_tools.App"),
    assemblyJarName in assembly := "app.jar"
  )
