/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** Looks very incomplete. */
object LessonsPage extends HtmlPageFile
{ override val titleStr: String = "Geom Lessons"
  override val fileNameStem: String =  "lessons"
  override def head: HtmlHead = headCss("documentation")
  override def body: HtmlBody = HtmlBody(HtmlH1("Geom Lessons"), central)
  def central: HtmlDiv = HtmlDiv.classAtt("central", "Some text", HtmlCanvas(), "Some more text")
}