/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import zio._, Console._, zio.http._, zio.http.html._

object HelloWorld extends ZIOAppDefault
{
  val app: App[Any] =
    Http.collect[Request] {
      case Method.GET -> Root => Response.html("This is the home page")
    }

  override val run =
    Server.serve(app).provide(Server.default)
}