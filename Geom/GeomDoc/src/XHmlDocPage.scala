/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*

/** Produces an HTML file documentation for the Geom module. */
object XHmlDocPage extends PageHtml
{ override def head: HeadHtml = HeadHtml.titleCss("Geom Module", "documentation")
  override def body: BodyHtml = BodyHtml(H1Html("Geom Module"), central)
  def central: DivHtml = DivHtml.classAtt("central", PHtml(intro))
  def intro = """The pWeb package contains classes to output Xml, Html and CSS.""".stripMargin
}