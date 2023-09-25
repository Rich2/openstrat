/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import jakarta.servlet.http._, pWeb._, java.io._

class PeriServe extends HttpServlet
{
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit =
  { val path = req.getServletPath()
    path match
    {
      case "/" =>
      { val head = HtmlHead.title("Periculo")
        val p1 = HtmlP("This is the first paragraph, using pWeb classes.")
        val body = HtmlBody(HtmlCanvas.id("scanv"), HtmlScript.jsSrc("peri2.js"), HtmlScript.main("Peri2JsApp"))
        val page = HtmlPage(head, body)
        resp.getWriter().println(page.out)
      }

      case "/peri2.js" =>
      { val file = File(getServletContext().getRealPath(File.separator) + "peri2.js")
        resp.setContentType("text/javascript")
        val inpStream = new FileInputStream(file)
        val outStream = resp.getOutputStream()
        inpStream.transferTo(outStream)
      }

      case _ => resp.getWriter().println("Your path was " + path + "<br>\nServletContext: " + getServletContext().getRealPath(File.separator))
    }
  }

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit =
  { val path = req.getServletPath()
    path match
    { case "/" =>
      { resp.setContentType("text/plain")
        resp.getWriter().println("42")
      }
    }
  }
}