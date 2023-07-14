/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pWeb._

object EarthPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Earth Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Tiling Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", list, centralStr.xCon)

  def list: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("The Earth module contains"), latLong, lines)

  def latLong: HtmlLi = HtmlLi.str("The LatLong class. Specifies a point in terms of latitude and longitude.")

  def lines: HtmlLi = HtmlLi.str("Classes for collections, lines and polygons specified in LatLongs.")

  def centralStr: String ="""
      |<h2>Contents</h2>
      |<ul>
      |
      |      <li>ostrat.pEarth depends on geom, pCanv and pGrid
      |        <ul>
      |          <li>Earth and sphere geometry.</li>
      |          <li>Grids of Earth terrain.</li>
      |        </ul>
      |      </li>
      |
      |      <li>ostrat.pEarth.pPts large irregular Earth terrain areas. This is mainly a development aid.</li>
      |
      |    </ul>
      |	This package and module is for Earth maps. In particular the tiling of the whole world in Hex grids, defining the changes
      |   over the course of history. This will be a data orientated module. It will also include terrain types to model terrain, both real and imagined for
      |   local maps and higher scales right up to 0.5 metres per tile However it won't generally include the data for these. The data for the real world
      |   will be organised according to a number of levels, which are likely to change over increasingly shorter historical time frames.
      |""".stripMargin
}