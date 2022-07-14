package pokedex.api.routes.v1

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{ Directives, Route }
import com.pokedex.Dependencies
import com.pokedex.model.ApiJsonFormatters

trait OpsRoutes {
  self: Dependencies with Directives with SprayJsonSupport with ApiJsonFormatters ⇒

  def opsRoutesV1: Route =
    (pathEndOrSingleSlash | pathPrefix("ops" / "health_check")) {
      get {
        complete(StatusCodes.OK)
      }
    }

}
