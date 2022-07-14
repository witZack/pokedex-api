resolvers += Classpaths.sbtPluginReleases
resolvers += Classpaths.typesafeReleases
resolvers += Resolver.sonatypeRepo("releases") // to more quickly obtain paradox right after release
resolvers += ("spray repo" at "https://repo.spray.io").withAllowInsecureProtocol(true)

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.4.1")
//addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.0") // for advanced PR validation features
addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.10.1")