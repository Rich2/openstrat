/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, pgui._

final case class HSysProjectionFlat(gridSys: HGridSys, panel: Panel) extends HSysProjection
{ type GridT = HGridSys

  /** The number of pixels per column unit. */
  var cPScale: Double = 40

  /** The number of pixels per tile from side to opposite side. */
  def tileScale: Double = cPScale * 4

  def tilePScaleStr = s"scale = ${tileScale.str2} pixels per tile"
  var focus: Vec2 = Vec2(0, 0)
  override def sides: LineSegArr = gridSys.sideLines.slate(-focus).scale(cPScale)
  override def innerSides: LineSegArr = gridSys.innerSideLines.slate(-focus).scale(cPScale)
  override def outerSides: LineSegArr = gridSys.outerSideLines.slate(-focus).scale(cPScale)

  override def transCoord(hc: HCoord): Option[Pt2] = Some(gridSys.hCoordToPt2(hc).slate(-focus).scale(cPScale))

  override def transTile(hc: HCen): Option[Polygon] = ???

  override def transHSides(inp: HSideArr): LineSegArr = ???//.slate(-focus).scale(cPScale)

  override def setView(view: Any): Unit = view match
  {
    case hv: HGView => {
      cPScale = hv.pxScale
      focus = hv.vec
    }
    case d: Double => cPScale = d
    case _ =>
  }

  def zoomIn: PolygonCompound = clickButton("+") { _ =>
    cPScale *= 1.1
    panel.repaint(getFrame())
    setStatusText(tilePScaleStr)
  }

  def zoomOut: PolygonCompound = clickButton("-") { _ =>
    cPScale /= 1.1
    panel.repaint(getFrame())
    setStatusText(tilePScaleStr)
  }

  override val buttons: Arr[PolygonCompound] = Arr(zoomIn, zoomOut)
}