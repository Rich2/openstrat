/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** Produces an HTML file documentation for the Geom module. */
object XHmlDocPage extends HtmlPage
{ override def head: HeadHtml = HeadHtml.titleCss("Geom Module", "documentation")
  override def body: BodyHtml = BodyHtml(HtmlH1("Geom Module"), central)
  def central: HtmlDiv = HtmlDiv.classAtt("central", HtmlP(intro))
  def intro = """The pWeb package contains classes to output Xml, Html and CSS.""".stripMargin
}