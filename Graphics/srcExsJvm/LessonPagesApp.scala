/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package ostrat
package geom
package pExs
import pWeb._

trait LessonPage
{ def page: HtmlPage
}

object LessonPagesApp extends App
{
  val r = opensettHtmlWrite("lessonA1", "Hello")
  deb(r.toString)
}
