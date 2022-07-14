package pokedex

import sbt._
import sbt.Keys._

/**
  * Generate version.conf files based on the version setting.
  */
object Version {

  def versionSettings: Seq[Setting[_]] = inConfig(Compile)(Seq(
    resourceGenerators += generateVersion(resourceManaged, _ / "example-version.conf",
      """|example.version = "%s"
         |""").taskValue
  ))

  def generateVersion(dir: SettingKey[File], locate: File => File, template: String) = Def.task[Seq[File]] {
    val file = locate(dir.value)
    val content = template.stripMargin.format(version.value)
    if (!file.exists || IO.read(file) != content) IO.write(file, content)
    Seq(file)
  }

}