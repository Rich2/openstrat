/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._

object WebPage1 extends App
{
  val title = "Web Page Example 1"
  
  val bodyStr =
    s"""<p>This is just some text for creating a web page. Using fairly simple code. Below are some svg circles encoded by hand.</p>     
      |
      |<svg>
      |  <circle cx=75 cy=75 r=75 fill=orange /><circle cx=225 cy=75 r=75 fill=violet /><circle cx=300 cy=75 r=25 fill=red />
      |</svg>""".stripMargin
  
  val r = opensettHtmlWrite("Web1", bodyStr)
  deb(r.toString)
}