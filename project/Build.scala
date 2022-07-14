package pokedex

import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import sbt.Keys._
import sbt._
/**
  *
  */
object Build {

  lazy val defaultSettings: Seq[Def.Setting[_]] = Def.settings(
    organization := "com.osinc.pokedex",
    organizationName := "com.osinc.pokedex",
    startYear := Some(2022),
    scalacOptions ++= Seq(
      //"-deprecation",
      "-encoding", "UTF-8",
      //"-unchecked",
      //"-Xlint",
      //"-Ywarn-dead-code"
    ),
    javacOptions ++= Seq(
      "-encoding", "UTF-8"
    ),
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v"),
    Dependencies.Versions,
    Formatting.formatSettings,
    Compile / ScalariformKeys.autoformat := true,
    Compile / SbtScalariform.autoImport.scalariformAutoformat := true,
    shellPrompt := { s => Project.extract(s).currentProject.id + " > " },
    resolvers += Classpaths.typesafeReleases,
    resolvers += Resolver.jcenterRepo,
    resolvers += Resolver.sonatypeRepo("releases"),
    resolvers += "jcenter-snapshot" at "https://oss.jfrog.org/artifactory/libs-release/",
    resolvers += Classpaths.sbtPluginReleases
  )

  lazy val buildSettings: Seq[Def.Setting[_]] = Dependencies.Versions ++ Seq(
    organization := "com.osinc.pokedex",
    version := "0.0.0")
}