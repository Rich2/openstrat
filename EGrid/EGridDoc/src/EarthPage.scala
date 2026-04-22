/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, HtmlStrExts.*

/** HTML documentation page for Earth module. */
object EarthPage extends OSDocumentationPage
{ override val titleStr: String = "Earth Module"
  override def fileNameStemStr: String = "earth"
  override def body: BodyHtml = BodyHtml(titleStr.h1, central)

  def central: DivHtml = DivHtml.classAtt("central", list)

  def list: OlSection = OlSection(H2Html("The Earth module contains"), latLong, lines, trans, terrs)

  def latLong: LiHtml = LiHtml("The LatLong class. Specifies a point in terms of latitude and longitude.")

  def lines: LiHtml = LiHtml("Classes for collections, lines and polygons specified in LatLongs.")

  def trans: LiHtml = LiHtml("Transformation of these classes into their equivalents in 3D distance space, 2D distance space and 2D scalar" --
    "geometric spaces.")

  def terrs: LiHtml = LiHtml("Some basic terrain polygons for the Earth, intended as a development aid rather for polished professional looking" --
    "applications.")
}