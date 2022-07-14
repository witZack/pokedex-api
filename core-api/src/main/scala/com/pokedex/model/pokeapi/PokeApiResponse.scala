package com.pokedex.model.pokeapi

/** PokeApi Generic Response (coded against /pokemon to start)
  * @param count Count of all items to be paginated over
  * @param next Link to next page of items
  * @param previous Link to previous page of items
  * @param results Result of one or more entities
  * @tparam T Type expected
  */
case class PokeApiResponse[T](count: Option[Int], next: Option[String], previous: Option[String], results: T)
