/* Copyright 2024 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pszio
import zio.*, http.*, pWeb.*

object ServZioApp extends ZIOAppDefault
{
  def hPage(str: String): Handler[Any, Nothing, Any, Response] = handler(Response.text(str).addHeader(Header.ContentType(MediaType.text.html)))
  val handHome: Handler[Any, Nothing, Any, Response] = hPage(pDev.IndexPage.out)
  def cssHan(spec: CssSpec): Handler[Any, Nothing, Any, Response] = handler(Response.text(spec()).addHeader(Header.ContentType(MediaType.text.css)))
  val dirStr: ZIO[ZIOAppArgs, Nothing, String] = getArgs.map(strs => strs.headOrElse("/assets"))
  def getJs(fileName: String): ZIO[ZIOAppArgs, Nothing, String] = dirStr.map(dir => scala.io.Source.fromFile(dir / fileName).getLines.mkString)
  //def dljs: Handler[Any, Nothing, Any, Response] = handler(Response.text(getJs("dicelessapp").).addHeader(Header.ContentType(MediaType.text.html)))

  val routes = Routes(
    Method.GET / "" -> handHome,
    Method.GET / "index.html" -> handHome,
    Method.GET / "index.htm" -> handHome,
    Method.GET / "index" -> handHome,
    Method.GET / "Documentation/documentation.css" -> cssHan(CssDocmentation),
    Method.GET / "only.css" -> cssHan(OnlyCss),
    Method.GET / "Documentation/util.html" -> hPage(UtilPage.out),
    Method.GET / "Documentation/geom.html" -> hPage(geom.GeomPage.out),
    Method.GET / "Documentation/tiling.html" -> hPage(prid.TilingPage.out),
    Method.GET / "Documentation/egrid.html" -> hPage(egrid.EGridPage.out),
    Method.GET / "Documentation/apps.html" -> hPage(pDev.AppsPage.out),
    Method.GET / "Documentation/dev.html" -> hPage(pDev.DevPage.out),
    Method.GET / "Documentation/newdevs.html" -> hPage(pDev.NewDevsPage.out),
    Method.GET / "earthgames/dicelessapp.html" -> hPage(pDev.AppPage("DicelessApp", "", "DiceLess").out),
    //Method.GET / "earthgames/dicelessapp.js" ->
  )

  def run: ZIO[Any, Throwable, Nothing] = Server.serve(routes).provide(Server.default)
}