import pokedex.{Build,Dependencies}
import xerial.sbt.pack.PackPlugin.autoImport.packExtraClasspath
val workaround: Unit = sys.props("packaging.type") = "jar"

lazy val root = Project(
  id = "pokedex-backend-app",
  base = file(".")
)
  .enablePlugins(ScalaUnidocPlugin)

lazy val coreServer = Seq(
  fork := true,
  packMain := Map("application" -> "com.pokedex.Bootstrap"),
  packJvmOpts := Map("application" ->
    List("-XX:+UnlockExperimentalVMOptions",
      "-XX:+UseCGroupMemoryLimitForHeap",
      "-XX:+UnlockExperimentalVMOptions")),
  packExtraClasspath := Map("application" -> Seq("/deploy/resources"))

)

lazy val api = project("core-api")
  .settings(Dependencies.core)
  .settings(Dependencies.api)
  .settings(Dependencies.coreTest)

lazy val apiServer = project("core-api-server")
  .dependsOn(api)
  .settings(Dependencies.apiServer)
  .settings(coreServer)

def project(name: String) =
  Project(id = name, base = file(name))
    .settings(Build.buildSettings)
    .settings(Build.defaultSettings)
