/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package mainlet
import ostrat.*, pweb.*, jakarta.servlet.*, annotation.WebServlet, http.{HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}

/** This is intended as the main and perhaps only Servlet for richstrat.com. For now, it's just a test bed. */
@WebServlet(urlPatterns = Array("/membership.html", "/register.html")) class MainLet extends HttpServlet
{
  override def doGet(req: HSReq, resp: HSResp): Unit =
  { 
    val memPage =  new HtmlPage
    { override def head: HeadHtml = HeadHtml.title("Membership Page")
      override def body: BodyHtml = BodyHtml(
        "Just to test things out for the moment.",
        DivHtml("To Register", FormHtml.link("register.html", "Register"))
      )
    }
    resp.getWriter().println(memPage.out)
  }
}