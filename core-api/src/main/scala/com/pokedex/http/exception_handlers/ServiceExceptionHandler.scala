package com.pokedex.http.exception_handlers

import akka.http.scaladsl.server.ExceptionHandler

/**
  */
trait ServiceExceptionHandler {
  def genericHandler: ExceptionHandler
}
