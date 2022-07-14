package com.pokedex.model.api

/**
  */
case class ApiResponse[T, K](
    response: T,
    request:  Option[K]       = None,
    meta:     Option[ApiMeta] = None)
