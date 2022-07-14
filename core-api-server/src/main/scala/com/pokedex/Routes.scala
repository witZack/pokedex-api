package com.pokedex

import akka.event.Logging
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.directives.DebuggingDirectives
import akka.http.scaladsl.server.{ Directives, ExceptionHandler, Route }
import akka.util.Timeout
import com.pokedex.http.exception_handlers.ServiceExceptionHandler
import com.pokedex.model.ApiJsonFormatters
import com.pokedex.routes.ServiceRoutes

import scala.concurrent.duration._

/** Route definition of the services api
  */
trait Routes extends Directives
  with SprayJsonSupport {
  self: Dependencies with ApiJsonFormatters with ServiceRoutes with ServiceExceptionHandler ⇒

  implicit lazy val t: Timeout = 5.seconds

  implicit def myExceptionHandler: ExceptionHandler = genericHandler
  val env = config.getString("environment")

  val route: Route =
    opsRoutesV1 ~
      DebuggingDirectives.logRequest("REQUESTS", Logging.InfoLevel)(
        handleExceptions(myExceptionHandler) {
          pokemonRoutesV1
        }
      )
}
