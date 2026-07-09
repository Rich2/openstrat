/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import jakarta.*, servlet.annotation.WebServlet, servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}

/** BasicServlet that only requires the scala-library-3.8.4.jar in Base/lib directory. See richstrat.com/tomcat.html for setup. */
@WebServlet(urlPatterns = Array("/")) class HelloLet extends HttpServlet
{ var numReqs: Int = 0
  
  def html(str: String): String = s"""<body>
  |<h1>Hello World Servlet for Scala</h1>
  |<p>Very basic servlet that only requires the Scala library from version 3.8. Here's some info.</p>
  |$str
  |<p>This is request number $numReqs.</p>
  |</body>
  |""".stripMargin

  override def doGet(req: HSReq, resp: HSResp): Unit =
  { val str1 = "Method = " + req.getMethod + "<br>"
    numReqs += 1
    val cookies = req.getCookies
    val str2 = if (cookies == null) "cookies = null<br>\n" else s"First cookie: \nName = ${cookies(0).getName}\nValue = ${cookies(0).getValue}<br>\n"
    val cont = req.getContextPath
    val str3: String = "Context = " + (if(cont == null) "null" else cont) + "<br>\n"
    val pathInfo = req.getPathInfo()
    val str4: String = "PathInfo = " + (if (pathInfo == null) "null" else pathInfo) + "<br>\n"
    val pathTrans = req.getPathTranslated()
    val str5: String = "PathTranslated = " + (if (pathTrans == null) "null" else pathTrans) + "<br>\n"
    val uri = req.getRequestURI()
    val str6: String = "URI = " + (if (uri == null) "null" else uri) + "<br>\n"
    val url = req.getRequestURL()
    val str7: String = "URL = " + (if (url == null) "null" else url) + "<br>\n"
    val servPath = req.getServletPath()
    val str8: String = "Serv Path = " + (if (servPath == null) "null" else servPath) + "<br>\n"

    resp.getWriter().println(html(str1 + str2 + str3 + str4 + str5 + str6 + str7 + str8))

    val c = new Cookie("Visit", "1")
    resp.addCookie(c)
  }
}