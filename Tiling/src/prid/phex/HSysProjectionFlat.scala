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
  override def transLineSeg(seg: LineSegHC): Option[LineSeg] = transCoord(seg.startPt).map2(transCoord(seg.endPt)){(p1, p2) => LineSeg(p1, p2) }

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

  def focusAdj(uniStr: String)(f: (Vec2, Double) => Vec2): PolygonCompound = clickButton(uniStr) { butt =>
    val delta = butt(1, 10, 100, 0)
    focus = f(focus, cPScale * delta / 40)
    panel.repaint(getFrame())
    setStatusText(focus.strSemi(2, 2))
  }

  def focusLeft: PolygonCompound = focusAdj("\u2190") { (v, d) =>
    val newX: Double = (v.x - d).max(gridSys.left)
    Vec2(newX, v.y)
  }

  def focusRight: PolygonCompound = focusAdj("\u2192") { (v, d) =>
    val newX: Double = (v.x + d).min(gridSys.right)
    Vec2(newX, v.y)
  }

  def focusUp: PolygonCompound = focusAdj("\u2191") { (v, d) =>
    val newY: Double = (v.y + d).min(gridSys.top)
    Vec2(v.x, newY)
  }

  def focusDown: PolygonCompound = focusAdj("\u2193") { (v, d) =>
    val newY: Double = (v.y - d).max(gridSys.bottom)
    Vec2(v.x, newY)
  }

  val buttons: Arr[PolygonCompound] = Arr(zoomIn, zoomOut, focusLeft, focusRight, focusUp, focusDown)
  //override val buttons: Arr[PolygonCompound] = Arr(zoomIn, zoomOut)
}