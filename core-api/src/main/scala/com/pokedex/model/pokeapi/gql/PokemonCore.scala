package com.pokedex.model.pokeapi.gql

/** get /pokemon response result object
  * @param id Id of the pokemon
  * @param name Name of the pokemon
  */
case class PokemonCore(id: Int, name: String)