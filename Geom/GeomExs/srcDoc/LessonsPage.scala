/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import geom.*, pWeb.*, Colour.*

object LessonsPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Geom Lessons", "documentation")
  override def body: HtmlBody = HtmlBody(HtmlH1("Geom Lessons"), central)
  def central: HtmlDiv = HtmlDiv.classAtt("central", "Some text", HtmlCanvas(), "Some more text")
}