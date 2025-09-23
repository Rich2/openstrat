
/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import geom.*, pWeb.*, Colour.*

/** Produces an HTML file documentation for the Geom module. */
object XHmlPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Geom Module", "documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Geom Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", HtmlP(intro))

  def intro =
    """The pWeb package contains classes to output Xml, Html and CSS.""".stripMargin
}