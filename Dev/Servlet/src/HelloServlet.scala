/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import jakarta.servlet.http.{Cookie, HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp}

class HelloServlet extends HttpServlet
{
  var numReqs: Int = 0
  def html(str: String): String =
    s"""<body>
       |<h1>This is the start!</h1>
       |<p>This is some info. $str</p>
       |<p>This is request number $numReqs.</p>
       |</body>
       |""".stripMargin

  override def doGet(req: HSReq, resp: HSResp): Unit =
  { val str = req.getMethod
    numReqs += 1
    val cookies = req.getCookies
    val str2 = if (cookies == null) "null" else "Yeah " + cookies(0).toString + "<br>\n"//cookies.toString
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

    resp.getWriter().println(html(str + str2 + str3 + str4 + str5 + str6 + str7 + str8))

    val c = new Cookie("Visit", "1")
    resp.addCookie(c)
  }
}