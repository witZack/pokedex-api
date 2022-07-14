package pokedex

import sbt._
import sbt.Keys._
import scala.language.implicitConversions

object Dependencies {

  val junitVersion = "4.12"
  val h2specVersion = "1.5.0"

  lazy val akkaVersion = settingKey[String]("The version of Akka to use.")
  lazy val akkaHttpVersion = settingKey[String]("The version of Akka-Http to use.")
  lazy val scalaTestVersion = settingKey[String]("The version of ScalaTest to use.")
  lazy val specs2Version = settingKey[String]("The version of Specs2 to use")
  lazy val scalaStmVersion = settingKey[String]("The version of ScalaSTM to use.")
  lazy val scalaCheckVersion = settingKey[String]("The version of ScalaCheck to use.")
  lazy val java8CompatVersion = settingKey[String]("The version of scala-java8-compat to use.")
  lazy val enumeratumVersion = settingKey[String]("The version of scala-java8-compat to use.")

  val Versions = Seq(
    akkaVersion := "2.6.19",
    akkaHttpVersion := "10.2.9",
    crossScalaVersions := Seq("2.12.7"),
    scalaVersion := crossScalaVersions.value.head,
    scalaCheckVersion := sys.props.get("akka.build.scalaCheckVersion").getOrElse("1.13.4"),
    scalaTestVersion := "3.0.0",
    specs2Version := "3.8.6",
    java8CompatVersion := "0.8.0",
    enumeratumVersion := "1.5.10"
  )

  import Versions._

  object Compile {
    // Compile

    // For akka http cause we want to use akka http
    val akkaHttp         = Def.setting { "com.typesafe.akka"             %% "akka-http"                    % akkaHttpVersion.value }
    val akkaSJson        = Def.setting { "com.typesafe.akka"             %  "akka-http-spray-json_2.12"    % akkaHttpVersion.value }
    val akkaHttpCore     = Def.setting { "com.typesafe.akka"             %% "akka-http-core"               % akkaHttpVersion.value }
    val akkaStream       = Def.setting { "com.typesafe.akka"             %% "akka-stream"                  % akkaVersion.value }
    val akkaCaching      = Def.setting { "com.typesafe.akka"             %  "akka-http-caching_2.12"       % akkaHttpVersion.value }
    val akkaLogSLF4j     = Def.setting { "com.typesafe.akka"             %% "akka-slf4j"                   % akkaVersion.value }
    val sprayCaching     = "io.spray"                                    %  "spray-caching_2.11"           % "1.3.4"
    val scalaXML         = "org.scala-lang.modules"                      %  "scala-xml_2.12"               % "1.0.6"
    val scalaz           = "org.scalaz"                                  %% "scalaz-core"                  % "7.2.30"

    // For enumeration cause gotta have those types
    val enumeration     = Def.setting { "com.beachape"    %% "enumeratum"                   % enumeratumVersion.value }
    // Joda DateTime for... DateTime stuff
    val jodaTime        = "joda-time"                     %  "joda-time"                    % "2.9.9"
    // Logging Library
    val logbackClassic  = "ch.qos.logback"                % "logback-classic"               % "1.3.0-alpha4"

    object Test { // All our testing dependencies
      val junit        = "junit"                            % "junit"                        % junitVersion             % "test" // Common Public License 1.0
      val logback      = "ch.qos.logback"                   % "logback-classic"              % "1.3.0-alpha4"                  % "test" // EPL 1.0 / LGPL 2.1
      val mockito      = "org.mockito"                      % "mockito-all"                  % "1.10.19"                % "test" // MIT
      val scalatest    = Def.setting { "org.scalatest"      %% "scalatest"                   % scalaTestVersion.value   % "test" }  // ApacheV2
      val specs2       = Def.setting { "org.specs2"         %% "specs2-core"                 % specs2Version.value      % "test" }  // MIT
      val scalacheck   = Def.setting { "org.scalacheck"     %% "scalacheck"                  % scalaCheckVersion.value  % "test" }  // New BSD
      val akkaTestKit  = Def.setting { "com.typesafe.akka"  %% "akka-testkit"                % akkaVersion.value }
    }

  }

  import Compile._

  lazy val l = libraryDependencies

  lazy val core = l ++= Seq(
    akkaHttp.value,
    akkaLogSLF4j.value,
    akkaSJson.value,
    akkaStream.value,
    enumeration.value,
    logbackClassic,
    jodaTime,
  )

  lazy val api = l ++= Seq(
    scalaz,
  )

  lazy val apiServer = l ++= Seq()

  lazy val coreTest = l ++= Seq(
    Test.junit,
    Test.scalatest.value,
    Test.specs2.value,
    Test.akkaTestKit.value
  )

}
