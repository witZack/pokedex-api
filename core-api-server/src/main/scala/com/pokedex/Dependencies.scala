package com.pokedex

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.typesafe.config.{ Config, ConfigFactory }
import org.slf4j.{ Logger, LoggerFactory }

import scala.concurrent.ExecutionContext

/** Service Dependencies such as actors, clients, and interface implementations
  */
trait Dependencies {

  implicit val system: ActorSystem
  implicit val materializer: Materializer
  implicit val ec: ExecutionContext

  //Logger
  lazy val log: Logger = LoggerFactory.getLogger("main")
  //Configs
  lazy val config: Config = ConfigFactory.load()
}
