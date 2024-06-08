/* Copyright 2018-21 Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pExs
import pWeb._

/** App for writing pages, may convert this into an Sbt app. */
object LessonPagesApp extends App
{ deb("LessonPagesApp")
  val r = opensettHtmlWrite("lessonA1", "Hello")
  deb(r.toString)
}