/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package ostrat
package geom
package pExs
import pWeb._

object PageA1 extends LessonPage
{ val head = HtmlHead(Arr(HtmlTitle("Lesson A1")))
  val bodyStr =
    """<h1>Lesson A1</h1>
      |<p>Covers Circles and Ellipses.</p>""".stripMargin
  override val page: HtmlPage = HtmlPage(head, HtmlBody(bodyStr))    
}
