package com.pokedex.model

import com.pokedex.http.gql.GQLQuery
import com.pokedex.model.api.{ ApiFailure, ApiMeta, ApiResponse, Pokemon }
import com.pokedex.model.pokeapi.gql.{ PokeApiGQLResponse, PokemonCore }
import com.pokedex.model.pokeapi.PokeApiResponse
import spray.json._

/** Json formatters for all service models and related things
  */
trait ApiJsonFormatters extends DefaultJsonProtocol {

  /** We use this method to sanitize data that could contain nulls,
    * Json parsers will return nulls if present.
    * We're coding in scala, we don't believe in nulls.
    * @param js JsObject to sanitize
    * @return Sanitized JsObject
    */
  def removeJsNulls(js: JsObject): JsObject = {
    val fields: scala.collection.mutable.Map[String, JsValue] = scala.collection.mutable.Map.empty
    js.fields.foreach(p ⇒ p._2 match {
      case JsNull                           ⇒
      case null                             ⇒
      case s: JsString if s.value == "null" ⇒
      case o: JsObject ⇒
        val cleaned = removeJsNulls(o)
        if (cleaned.fields.nonEmpty)
          fields += (p._1 -> cleaned)
      case _ ⇒ fields += (p._1 -> p._2)
    })
    JsObject(fields.toMap)
  }

  // Api
  implicit val apiMetaJF: RootJsonFormat[ApiMeta] = jsonFormat2(ApiMeta)
  implicit val pokemonJF: RootJsonFormat[Pokemon] = jsonFormat3(Pokemon)

  // Api Responses
  implicit def apiResponseJF[T: JsonFormat, K: JsonFormat] = new RootJsonFormat[ApiResponse[T, K]] {
    def write(t: ApiResponse[T, K]): JsValue = {
      val fields: scala.collection.mutable.Map[String, JsValue] = scala.collection.mutable.Map.empty[String, JsValue]
      fields.put("response", t.response.toJson)
      t.meta.map(m ⇒ fields.put("meta", m.toJson))
      t.request.map(m ⇒ fields.put("request", m.toJson))
      JsObject(fields.toMap)
    }
    def read(json: JsValue): ApiResponse[T, K] = {
      json.asJsObject.fields.get("response") match {
        case Some(v) ⇒ ApiResponse[T, K](
          v.convertTo[T],
          json.asJsObject.fields.get("request").map(_.convertTo[K]),
          json.asJsObject.fields.get("meta").map(_.convertTo[ApiMeta]))
        case None ⇒ throw DeserializationException(s"Improperly formatted ApiResponse")
      }
    }
  }

  implicit val apiErrorJF: RootJsonFormat[ApiFailure] = jsonFormat1(ApiFailure)

  // TODO: Abstract this to pokeapi specific formatters
  implicit def pokeApiResponseJF[T: JsonFormat] = new RootJsonFormat[PokeApiResponse[T]] {
    def write(t: PokeApiResponse[T]): JsValue = {
      val fields: scala.collection.mutable.Map[String, JsValue] = scala.collection.mutable.Map.empty[String, JsValue]
      fields.put("results", t.results.toJson)
      t.count.map(m ⇒ fields.put("count", m.toJson))
      t.next.map(m ⇒ fields.put("next", m.toJson))
      t.previous.map(m ⇒ fields.put("previous", m.toJson))
      JsObject(fields.toMap)
    }
    def read(json: JsValue): PokeApiResponse[T] = {
      // We have to sanitize results with removeJSNulls due to api supporting null values
      val jsFieldsWithoutNulls = removeJsNulls(json.asJsObject).asJsObject().fields
      json.asJsObject.fields.get("results") match {
        case Some(v) ⇒ PokeApiResponse[T](
          jsFieldsWithoutNulls.get("count").map(_.convertTo[Int]),
          jsFieldsWithoutNulls.get("next").map(_.convertTo[String]),
          jsFieldsWithoutNulls.get("previous").map(_.convertTo[String]),
          v.convertTo[T])
        case None ⇒ throw DeserializationException(s"Improperly formatted PokeApiResponse")
      }
    }
  }

  // TODO: Abstract this to pokeapi specific formatters
  implicit def pokeApiGQLResponseJF[T: JsonFormat] = new RootJsonFormat[PokeApiGQLResponse[T]] {
    def write(t: PokeApiGQLResponse[T]): JsValue = {
      val fields: scala.collection.mutable.Map[String, JsValue] = scala.collection.mutable.Map.empty[String, JsValue]
      fields.put("data", t.data.toJson)
      JsObject(fields.toMap)
    }
    def read(json: JsValue): PokeApiGQLResponse[T] = {
      // We have to sanitize results with removeJSNulls due to api supporting null values
      json.asJsObject.fields.get("data") match {
        case Some(v) ⇒ PokeApiGQLResponse[T](v.convertTo[T])
        case None    ⇒ throw DeserializationException(s"Improperly formatted PokeApiResponse")
      }
    }
  }

  implicit val gqlQueryJF: RootJsonFormat[GQLQuery] = jsonFormat3(GQLQuery)
  implicit val gqlPokemonJF: RootJsonFormat[PokemonCore] = jsonFormat2(PokemonCore)
}
