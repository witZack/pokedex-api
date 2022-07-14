package com.pokedex.http.gql

import spray.json.JsObject

case class GQLQuery(query: String, operationName: String, variables: JsObject)
