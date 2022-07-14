package com.pokedex.libs

import akka.http.scaladsl.model.HttpResponse
import akka.stream.Materializer
import akka.util.{ ByteString, Timeout }

import spray.json._

import scala.concurrent.{ ExecutionContext, Future }

object AHttp {
  /** Extracts type T from a json body of an HttpResponse payload using akka streaming api
    */
  def getBody[T](response: HttpResponse)(implicit timeout: Timeout, m: Materializer, ec: ExecutionContext, jf: JsonFormat[T]): Future[T] =
    response.entity.toStrict(timeout.duration) flatMap { e ⇒
      e.dataBytes.runFold(ByteString(""))(_ ++ _) map { body ⇒
        //DEBUG JSON failures: System.out.println(body.utf8String)
        body.utf8String.parseJson.convertTo[T]
      }
    }
}
