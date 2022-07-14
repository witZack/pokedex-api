package com.pokedex.libs

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, HttpMethods, HttpRequest }
import akka.http.scaladsl.model.headers.RawHeader
import com.pokedex.http.gql.GQLQuery
import com.pokedex.model.ApiJsonFormatters
import spray.json._

object PokeApiCo {

  /** Get /pokemon request to PokeAPI (Testing function)
    * @param limit number of items to attempt to retrieve
    * @param offset offset of pagination
    * @return
    */
  def getPokemon(limit: Int, offset: Int): HttpRequest = {
    val m = HttpMethods.GET
    val p = s"/pokemon/" +
      s"?offset=${offset}" +
      s"&limit=${limit}"

    HttpRequest(m, "https://pokeapi.co/api/v2" + p, List(
      RawHeader("accept", "application/json")))
  }

  object GQL extends ApiJsonFormatters {
    /** GQL Query to retrieve paginated list of Pokemon
      * @param limit number of items to attempt to retrieve
      * @param offset offset of pagination
      * @return
      */
    def getPokemon(limit: Int, offset: Int): HttpRequest = {
      // GQL Query Setup
      val query = "query GetPokemon ($offset: Int, $limit: Int) {  pokemon_v2_pokemon(offset: $offset, limit: $limit) {name id}}"
      val operationName = "GetPokemon"
      val variables = JsObject(Map[String, JsValue]("offset" -> JsNumber(offset), "limit" -> JsNumber(limit)))

      // Http Request Construction
      val m = HttpMethods.POST
      val entity = GQLQuery(query, operationName, variables)

      HttpRequest(m, "https://beta.pokeapi.co/graphql/v1beta", List(
        RawHeader("accept", "application/json")),
                  entity = HttpEntity(ContentTypes.`application/json`, entity.toJson.compactPrint))
    }

    /** GQL Query to search Pokemon by name
      * @param name Name of the search term
      * @param limit number of items to attempt to retrieve
      * @param offset offset of pagination
      * @return
      */
    def searchPokemonByName(name: String, limit: Int, offset: Int): HttpRequest = {
      // GQL Query Setup
      val likeName = "%" + name + "%"
      val query = "query SearchPokemonByName ($name: String, $offset: Int, $limit: Int) { pokemon_v2_pokemon(where: {name: {_like: $name}}, offset: $offset, limit: $limit) {name id}}"
      val operationName = "SearchPokemonByName"
      val variables = JsObject(Map[String, JsValue]("name" -> JsString(likeName), "offset" -> JsNumber(offset), "limit" -> JsNumber(limit)))

      // Http Request Construction
      val m = HttpMethods.POST
      val entity = GQLQuery(query, operationName, variables)

      HttpRequest(m, "https://beta.pokeapi.co/graphql/v1beta", List(
        RawHeader("accept", "application/json")),
                  entity = HttpEntity(ContentTypes.`application/json`, entity.toJson.compactPrint))
    }
    /** GQL Query to search Pokemon by name
      * @param _type Type of the pokemon to search
      * @param limit number of items to attempt to retrieve
      * @param offset offset of pagination
      * @return
      */
    def searchPokemonByType(_type: String, limit: Int, offset: Int): HttpRequest = {
      // GQL Query Setup
      val query = "query SearchPokemonByType($type: String, $offset: Int, $limit: Int) { pokemon_v2_pokemon(offset: $offset, limit: $limit, where: {pokemon_v2_pokemontypes: {pokemon_v2_type: {name: {_eq: $type}}}}) {name id}}"
      val operationName = "SearchPokemonByType"
      val variables = JsObject(Map[String, JsValue]("type" -> JsString(_type), "offset" -> JsNumber(offset), "limit" -> JsNumber(limit)))

      // Http Request Construction
      val m = HttpMethods.POST
      val entity = GQLQuery(query, operationName, variables)

      HttpRequest(m, "https://beta.pokeapi.co/graphql/v1beta", List(
        RawHeader("accept", "application/json")),
                  entity = HttpEntity(ContentTypes.`application/json`, entity.toJson.compactPrint))
    }
  }
}
