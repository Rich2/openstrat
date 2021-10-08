/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package ostrat
package geom
package pExs

/** App for writing pages, may convert this into an Sbt app. */
object LessonPagesApp extends App
{ val r = opensettHtmlWrite("lessonA1", "Hello")
  deb(r.toString)
}