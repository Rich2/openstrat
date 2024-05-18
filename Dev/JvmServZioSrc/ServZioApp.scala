/* Copyright 2024 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pszio
import zio._, Console._, http._, ostrat._, geom._, prid.phex._

object ServZioApp extends ZIOAppDefault
{
  def hPage(str: String) = handler(Response.text(str).addHeader(Header.ContentType(MediaType.text.html)))
  val handHome = hPage(pDev.IndexPage.out)
  val css = handler(Response.text(CssOpenstrat()).addHeader(Header.ContentType(MediaType.text.css)))

  val routes: Routes[Any, Response] = Routes(
    Method.GET / "" -> handHome,
    Method.GET / "index.html" -> handHome,
    Method.GET / "index.htm" -> handHome,
    Method.GET / "index" -> handHome,
    Method.GET / "Documentation/documentation.css" -> css,
    Method.GET / "Documentation/util.html" -> hPage(UtilPage.out),
    Method.GET / "Documentation/geom.html" -> hPage(geom.GeomPage.out),
    Method.GET / "Documentation/tiling.html" -> hPage(prid.TilingPage.out),
  )
  val app: HttpApp[Any] = routes.toHttpApp

  override val run = Server.serve(app).provide(Server.default)
}