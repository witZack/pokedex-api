package pokedex

import scala.language.postfixOps
import sbt._, Keys._

/**
  * For projects that are not published.
  */
object NoPublish extends AutoPlugin {
  override def requires = plugins.JvmPlugin

  override def projectSettings = Seq(
    publishArtifact := false,
    publish := {},
    publishLocal := {}
  )

}

/**
  * For publishing projects
  */
object Publish extends AutoPlugin {

  override def projectSettings = Seq(
    // put settings here to do publishing
  )
}