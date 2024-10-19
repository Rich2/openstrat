/* Copyright 2024 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pszio
import zio.*, http.*, pWeb.*, utiljvm.*

object ServZioApp extends ZIOAppDefault
{
  def hPage(str: String): Handler[Any, Nothing, Any, Response] = handler(Response.text(str).addHeader(Header.ContentType(MediaType.text.html)))
  val handHome: Handler[Any, Nothing, Any, Response] = hPage(pDev.IndexPage.out)
  def cssHan(css: CssRules): Handler[Any, Nothing, Any, Response] = handler(Response.text(css()).addHeader(Header.ContentType(MediaType.text.css)))
  val dirStr: ZIO[ZIOAppArgs, Nothing, String] = getArgs.map(strs => strs.headOrElse("/assets"))
  val args: ZIO[ZIOAppArgs, Nothing, Chunk[String]] = getArgs
  val str1: ZIO[ZIOAppArgs, Nothing, String] = args.map{ strs => if (strs.isEmpty) "~" else strs(0) }

  

  val routes: Routes[Any, Nothing] = Routes(
    Method.GET / "" -> handHome,
    Method.GET / "index.html" -> handHome,
    Method.GET / "index.htm" -> handHome,
    Method.GET / "index" -> handHome,
    Method.GET / "Documentation/documentation.css" -> cssHan(CssDocumentation),
    Method.GET / "only.css" -> cssHan(OnlyCss),
    Method.GET / "Documentation/util.html" -> hPage(UtilPage.out),
    Method.GET / "Documentation/geom.html" -> hPage(geom.GeomPage.out),
    Method.GET / "Documentation/tiling.html" -> hPage(prid.TilingPage.out),
    Method.GET / "Documentation/egrid.html" -> hPage(pDev.EGridPage.out),
    Method.GET / "Documentation/apps.html" -> hPage(pDev.AppsPage.out),
    Method.GET / "Documentation/dev.html" -> hPage(pDev.DevPage.out),
    Method.GET / "Documentation/newdevs.html" -> hPage(pDev.NewDevsPage.out),
    Method.GET / "earthgames/dicelessapp.html" -> hPage(pDev.AppPage("DicelessApp", "", "DiceLess").out),
    Method.GET / "earthgames/dicelessapp.js" -> {handler(Response.text(loadTextFile("/CommonSsd/ServerOS/earthgames/dicelessapp.js").get).addHeader(Header.ContentType(MediaType.text.javascript))) },
    //Method.GET / "earthgames/dicelessapp.js" -> str1.flatMap{str0 => handler(Response.text(loadTextFile("/CommonSsd/ServerOS/earthgames/dicelessapp.js").get).addHeader(Header.ContentType(MediaType.text.javascript))) }
  )

  def run: ZIO[Any, Any, Nothing] = Server.serve(routes).provide(Server.default)
}