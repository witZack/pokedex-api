package com.pokedex.model.api

import com.pokedex.model.pokeapi.gql.PokemonCore

case class Pokemon(pokemonID: Int, name: String, photoUrl: String)

object PokemonCompanion {
  /** Method that wraps the core in a new object with different field names and constructs the image URL
    * We construct the image url right now, due to a bug in the GQL Api not providing this data.
    * We could pull this dynamically through the http api, but that's an inefficiency and this will work
    * until the bug is fixed.
    * @param o PokemonCore to remap
    */
  def fromCore(o: PokemonCore): Pokemon = {
    Pokemon(o.id, o.name, photoUrl = s"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${o.id}.png")
  }
}
