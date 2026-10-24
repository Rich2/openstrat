/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package mainlet
import ostrat.*, utiljvm.*, pweb.*, jakarta.servlet.*, annotation.WebServlet, http.{ HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp, *}
import java.time.*, java.util.Calendar

/** This is intended as the main and perhaps only Servlet for richstrat.com. For now, it's just a test bed. */
@WebServlet(urlPatterns = Array("/membership.html", "/register.html")) class MainLet extends HttpServlet
{ var visitors: Int = 0
  val sTime: Instant = Instant.now
  val stStr: String = sTime.y2mdhm

  override def doGet(req: HSReq, resp: HSResp): Unit = req.getRequestURI match
  {
    case "/membership.html" =>
    { visitors += 1
      val cookieStr: String = stStr + visitors.str
      val head: HeadHtml = HeadHtml.title("Membership Page")
      val body: BodyHtml = BodyHtml(
        "Just to test things out for the moment.",
        DivHtml(s"cookie value = $cookieStr"),
        DivHtml ("To Register", FormPost.link ("register.html", "Register"))
      )      
      val memPage = HtmlPage(head, body)
      resp.getWriter().println(memPage.out)
      
      val c = new Cookie("Visitor", cookieStr)
      resp.addCookie(c)
    }
    case context => resp.getWriter().println(s"Context: $context")
    
  }

  override def doPost(req: HSReq, resp: HSResp): Unit = {
    resp.getWriter.println("Post Response.")
  }
}