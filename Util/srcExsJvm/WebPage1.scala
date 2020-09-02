/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._

object WebPage1 extends App
{
  val title = "Web Page Example 1"
  
  val bodyStr =
    """<p>This is just some text for creating a web page, using ultra simple code. So I'm just going to go on and on for a bit, just in an attempt to
      | drag this out into multiple lines. So I really don't know what I'm going to say. Its awful really when you've just got to write stuff to take
      | up lines.<p>
      |
      |<p>And here's a second paragraph. And again and again, what on earth am I going to say, to drag this out beyond a single line, especially when
      |there's no formatting in the output. Anyway the SVG below demonstrates the 300 by 150 default viewbox dimensions.</p>
      |
      |<svg>
      |<circle cx=75 cy=75 r=75 fill=orange /><circle cx=225 cy=75 r=75 fill=violet /><circle cx=300 cy=75 r=25 fill=red />
      |</svg>
      |
      |<p>And a third.</p>""".stripMargin
  
  val r = homeHtmlWrite("Web1", bodyStr)
  deb(r.toString)
}
