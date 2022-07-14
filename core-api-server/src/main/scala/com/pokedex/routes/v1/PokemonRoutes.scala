package com.pokedex.routes.v1

import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ HttpResponse, StatusCodes }
import akka.http.scaladsl.server.{ Directives, Route }
import com.pokedex.Dependencies
import com.pokedex.model.ApiJsonFormatters
import com.pokedex.model.api.{ ApiResponse, Pokemon, PokemonCompanion }
import spray.json._
import akka.util.Timeout
import com.pokedex.libs.AHttp.getBody
import com.pokedex.libs.PokeApiCo
import com.pokedex.model.pokeapi.gql.{ PokeApiGQLResponse, PokemonCore }

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.{ Failure, Success }

trait PokemonRoutes {
  self: Dependencies with Directives with SprayJsonSupport with ApiJsonFormatters ⇒

  def pokemonRoutesV1: Route =
    path("pokemon") {
      get {
        parameter("limit".as[Int]?, "offset".as[Int]?) { (limit, offset) ⇒
          implicit lazy val t: Timeout = 10.seconds
          log.info(s"Received request to get gql pokemon limit=${limit} and offset=${offset}.")
          // Construct GQL Query
          val request: Future[HttpResponse] = Http().singleRequest(PokeApiCo.GQL.getPokemon(limit.getOrElse(20), offset.getOrElse(0)))
          // Execute GQL Query
          val f = request.flatMap(r ⇒ {
            //TODO: Wrap this with a try to catch api failures
            getBody[PokeApiGQLResponse[JsObject]](r)
          })
          // Process / Serve response
          onComplete(f) {
            case Success(response) ⇒
              // Slight inefficiency here as we map over the collection twice, once to parse the json and another to remap the object
              val finalResponse = response.data.fields("pokemon_v2_pokemon").convertTo[List[PokemonCore]].map(PokemonCompanion.fromCore)
              complete(StatusCodes.OK, ApiResponse[List[Pokemon], Unit](finalResponse, None))
            case Failure(e) ⇒
              log.info(s"Route failure GET gql/pokemon")
              throw e
          }
        }
      }
    } ~
      path("pokemon" / "search") {
        get {
          parameter("name".?, "type".?, "limit".as[Int]?, "offset".as[Int]?) { (name, _type, limit, offset) ⇒
            implicit lazy val t: Timeout = 10.seconds
            // Construct GQL Query (this endpoint is overloaded as an exclusive OR)
            val request: Future[HttpResponse] = (name, _type) match {
              case (Some(_), Some(_)) ⇒
                Future.failed(new IllegalArgumentException("Name or type parameter is an exclusive OR search by."))
              case (Some(name), _) ⇒
                log.info(s"Received request to search pokemon for name=$name by limit=${limit} and offset=${offset}.")
                Http().singleRequest(PokeApiCo.GQL.searchPokemonByName(name, limit.getOrElse(20), offset.getOrElse(0)))
              case (_, Some(_type)) ⇒
                log.info(s"Received request to search pokemon for type=${_type} by limit=${limit} and offset=${offset}.")
                Http().singleRequest(PokeApiCo.GQL.searchPokemonByType(_type, limit.getOrElse(20), offset.getOrElse(0)))
              case _ ⇒
                Future.failed(new IllegalArgumentException("Missing name or type parameter to search by."))
            }
            // Execute GQL Query
            val f = request.flatMap(r ⇒ {
              //TODO: Wrap this with a try to catch api failures
              getBody[PokeApiGQLResponse[JsObject]](r)
            })
            // Process / Serve response
            onComplete(f) {
              case Success(response) ⇒
                // Slight inefficiency here as we map over the collection twice, once to parse the json and another to remap the object
                val finalResponse = response.data.fields("pokemon_v2_pokemon").convertTo[List[PokemonCore]].map(PokemonCompanion.fromCore)
                complete(StatusCodes.OK, ApiResponse[List[Pokemon], Unit](finalResponse, None))
              case Failure(e) ⇒
                log.info(s"Route failure GET pokemon / search")
                throw e
            }
          }
        }
      }
}