/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, pgui._

final case class HProjectionFlat(gridSys: HGridSys, panel: Panel) extends HSysProjection
{ type GridT = HGridSys

  var cPScale: Double = 10
  var focus: Vec2 = Vec2(0, 0)
  override def sides: LineSegArr = gridSys.sideLines.slate(-focus).scale(cPScale)
  override def innerSides: LineSegArr = gridSys.innerSideLines.slate(-focus).scale(cPScale)
  override def outerSides: LineSegArr = gridSys.outerSideLines.slate(-focus).scale(cPScale)


  def frame: GraphicElems = ???
  def zoomIn: PolygonCompound = clickButton("+") { _ =>
    cPScale *= 1.1
    panel.repaint(frame)
    //statusText = tilePScaleStr
    //thisTop()
  }

  def zoomOut: PolygonCompound = clickButton("-") { _ =>
    cPScale /= 1.1
    panel.repaint(frame)
    //statusText = tilePScaleStr
    //thisTop()
  }
}