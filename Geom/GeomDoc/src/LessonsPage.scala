/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** Looks very incomplete. */
object LessonsPage extends PageFile
{ override val titleStr: String = "Geom Lessons"
  override val fileNameStem: String =  "lessons"
  override def head: HeadHtml = headCss("documentation")
  override def body: BodyHtml = BodyHtml.h1(titleStr, central)
  def central: DivHtml = DivHtml.classAtt("central", "Some text", CanvasHtml(), "Some more text")
}