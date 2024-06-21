/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb.*, utiljvm.*

object ServRawOS extends ServRaw
{
  override def responses(req: EMon[HttpReq]): Option[HttpResp] = req match
  {
    case Good(hrg: HttpReqGet) => {
      val resp: HttpResp = hrg.uri match {
        case "/" | "" | "/index.html" | "index.html" | "/index.htm" | "index.htm" => IndexPage.httpResp(httpNow, "localhost")
        case "/Documentation/util.html" => UtilPage.httpResp(httpNow, "localhost")
        case "/Documentation/documentation.css" => CssDocmentation.httpResp(httpNow, "localhost")
        case "/favicon.ico" => HttpFound(httpNow, "localhost", HttpConTypeSvg, Favicon1())
        case id => HtmlPageNotFoundstd(id).httpResp(httpNow, "localhost")
      }

      Some(resp)
    }
    case _ => {deb("Other match"); None }
  }

  run()
}