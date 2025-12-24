/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** HTML documentation page for Earth module. */
object EarthPage extends OSDocumentationPage
{ override val titleStr: String = "Earth Module"
  override def fileNameStem: String = "earth"

  override def body: HtmlBody = HtmlBody(HtmlH1("Tiling Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", list)

  def list: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("The Earth module contains"), latLong, lines, trans, terrs)

  def latLong: HtmlLi = HtmlLi("The LatLong class. Specifies a point in terms of latitude and longitude.")

  def lines: HtmlLi = HtmlLi("Classes for collections, lines and polygons specified in LatLongs.")

  def trans: HtmlLi = HtmlLi("Transformation of these classes into their equivalents in 3D distance space, 2D distance space and 2D scalar" --
    "geometric spaces.")

  def terrs: HtmlLi = HtmlLi("Some basic terrain polygons for the Earth, intended as a development aid rather for polished professional looking" --
    "applications.")
}