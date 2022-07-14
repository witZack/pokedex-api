package com.pokedex.routes

import akka.http.scaladsl.server.Route

/**
  */
trait ServiceRoutes {
  def opsRoutesV1: Route
  def pokemonRoutesV1: Route
}
