package com.pokedex.http.exception_handlers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{ Directives, ExceptionHandler }
import com.pokedex.model.ApiJsonFormatters
import com.pokedex.model.api.ApiFailure

/**
  */
trait GenericExceptionHandler {
  self: Directives with SprayJsonSupport with ApiJsonFormatters ⇒

  def genericHandler: ExceptionHandler = ExceptionHandler {
    case e: IllegalArgumentException ⇒
      extractUri { uri ⇒
        println(s"Request to $uri could not be handled normally, $e")
        complete(StatusCodes.BadRequest, ApiFailure(e.getMessage))
      }
    case e: NullPointerException ⇒
      extractUri { uri ⇒
        e.printStackTrace()
        println(s"Request to $uri could not be handled normally, $e")
        complete(StatusCodes.InternalServerError, ApiFailure("NullpointerException"))
      }
    case e: NoSuchElementException ⇒
      extractUri { uri ⇒
        e.printStackTrace()
        println(s"Request to $uri could not be handled normally, $e")
        complete(StatusCodes.NoContent, ApiFailure("NoSuchElement"))
      }
    case e: Exception ⇒
      extractUri { uri ⇒
        println(s"Request to $uri could not be handled normally, $e")
        complete(StatusCodes.InternalServerError, ApiFailure(e.getMessage))
      }
  }
}
