/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._

object WebPage1 extends App
{
  val title = "Web Page Example 1"
  
  val body =
    """This is just some text for creating a web page.
      |
      |And here's a second paragraph.
      |And a third.
      |""".stripMargin
  
  val r = homeWrite("temp.txt", HtmlPage(body).out)
  deb(r.toString)
}
