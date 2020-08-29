/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._

object WebPage1 extends App
{
  val title = "Web Page Example 1"
  
  val body =
    """<p>This is just some text for creating a web page. So I'm just going to go on and on for a bit, just in an attempt to drag this out into
      |multiple lines. So i really don't know what I'm going to say. Its awful really when you've just got to write stuff to take up lines.<p>
      |
      |<p>And here's a second paragraph. And again and again, what on earth am I going to say, to drag this out beyond a single line, especially when
      |there's no formatting in the output.</p>
      |
      |<p>And a third.</p>
      |""".stripMargin
  
  val r = homeWrite("Web1.html", HtmlPage(title, body).out)
  deb(r.toString)
}
