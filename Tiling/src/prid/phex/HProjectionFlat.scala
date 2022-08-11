/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, pgui._

final case class HProjectionFlat(gridSys: HGridSys) extends HSysProjection
{ type GridT = HGridSys

  var cPScale: Double = 10
  var focus: Vec2 = Vec2(0, 0)
  override def sides: LineSegArr = gridSys.sideLines.slate(-focus).scale(cPScale)
  def sidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = sides.draw(lineWidth, colour)

  def zoomIn: PolygonCompound = clickButton("+") { _ =>
    cPScale *= 1.1
    //repaint()
    //statusText = tilePScaleStr
    //thisTop()
  }

  def zoomOut: PolygonCompound = clickButton("-") { _ =>
    cPScale /= 1.1
    //repaint()
    //statusText = tilePScaleStr
    //thisTop()
  }
}