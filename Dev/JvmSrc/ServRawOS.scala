/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb.*, utiljvm.*

object ServRawOS extends ServRaw
{
  var resDirStr: String = "~/OpenStratResources"
  
  override val port = 8081

  override def responses(req: ThrowMon[HttpReq]): Option[HttpResp] = req match
  {
    case Succ(hrg: HttpReqGet) =>
    { val resp: HttpResp = hrg.uri match
      { case "/" | "" | "/index.html" | "index.html" | "/index.htm" | "index.htm" => IndexPage.httpResp(httpNow, "localhost")
        case AppPage.AllHtmlExtractor(page) => page.httpResp(httpNow, "localhost")
        case JsPathNameStr(pathName) =>
        {
          val resPath = resDirStr / pathName
          deb(resPath)
          loadTextFile(resPath) match
          { case Succ(str) =>
            { deb("Js found Length = " + str.length.toString)
              HttpFound(httpNow, "localhost", HttpConTypeJs, str)
            }
            case _ => { deb("Js not found"); HtmlPageNotFoundstd(pathName).httpResp(httpNow, "localhost")}
          }
        }

        case "/test" => TestPage.httpResp(httpNow, "localhost")
        case "/Documentation/util.html" => UtilPage.httpResp(httpNow, "localhost")
        case "/Documentation/geom.html" => geom.GeomPage.httpResp(httpNow, "localhost")
        case "/Documentation/tiling.html" => prid.TilingPage.httpResp(httpNow, "localhost")
        case "/Documentation/egrid.html" => EGridPage.httpResp(httpNow, "localhost")
        case "/Documentation/apps.html" => AppsPage.httpResp(httpNow, "localhost")
        case "/Documentation/documentation.css" => CssDocumentation.httpResp(httpNow, "localhost")
        case "/only.css" => OnlyCss.httpResp(httpNow, "localhost")
        case "/favicon.ico" => HttpFound(httpNow, "localhost", HttpConTypeSvg, Favicon1())
        case id =>{
          deb("Page not found.")
          HtmlPageNotFoundstd(id).httpResp(httpNow, "localhost")
        }
      }

      Some(resp)
    }
    case _ => {deb("Other match"); None }
  }

  def main(args: Array[String]): Unit =
  {
    val oLoc = args.headOption
    val locStr: String = oLoc.fld("None", str => str)
    deb(locStr)
    oLoc.foreach{a1 => resDirStr = a1}
    run()
  }
}