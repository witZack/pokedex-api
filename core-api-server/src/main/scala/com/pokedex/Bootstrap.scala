package com.pokedex

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.pokedex.http.exception_handlers.{ GenericExceptionHandler, ServiceExceptionHandler }
import com.pokedex.model.ApiJsonFormatters
import com.pokedex.routes.ServiceRoutes
import com.pokedex.routes.v1.PokemonRoutes
import org.slf4j.LoggerFactory
import pokedex.api.routes.v1.OpsRoutes

import scala.concurrent.ExecutionContext

/** App
  */
object Bootstrap extends App
  with Dependencies
  with Routes
  with ServiceRoutes
  with OpsRoutes
  with PokemonRoutes
  with ServiceExceptionHandler
  with GenericExceptionHandler
  with ApiJsonFormatters {

  // Initialization
  implicit val system: ActorSystem = ActorSystem("api-server")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher

  val LOG = LoggerFactory.getLogger(this.getClass)

  Http().bindAndHandle(route, "0.0.0.0", 3010)

  LOG.info(s"Server online")
}
