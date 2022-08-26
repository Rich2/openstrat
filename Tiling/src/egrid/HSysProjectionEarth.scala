/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._, pglobe._, pgui._

case class HSysProjectionEarth(gridSys: EGridSys, panel: Panel) extends HSysProjection
{
  override type GridT = EGridSys
  var focus: LatLong = 0 ll 0
  var scale: Length = 4.km
  def gScale: Double = gridSys.cScale / scale

  override def setView(view: Any): Unit = view match {
    case hv: HGView => {
      scale = gridSys.cScale / hv.cPScale
      focus = gridSys.hCoordLL(hv.hCoord)
    }
    //case d: Double => cPScale = d
    case _ =>
  }

  var gChild: HGridSys = ???

  def zoomIn: PolygonCompound = clickButton("+") { _ =>
    scale *= 1.1
    //panel.repaint(frame)
    //statusText = tilePScaleStr
    //thisTop()
  }

  def zoomOut: PolygonCompound = clickButton("-") { _ =>
    scale /= 1.1
    //panel.repaint(frame)
    //statusText = tilePScaleStr
    //thisTop()
  }
  override val buttons: Arr[PolygonCompound] = Arr(zoomIn, zoomOut)
//  val sides0 = sTerrs.truesMap(_.lineSegHC.map(gridSys.hCoordLL(_)))
//
//  def sides1: LineSegM3Arr = sides0.map {
//    _.map(_.toMetres3)
//  }
//
//  def sides2: LineSegM3Arr = sides1.map {
//    _.map(_.fromLatLongFocus(focus))
//  }
//
//  def sides3: LineSegM3Arr = sides2.filter(_.zsPos)
//
//  def sides4: LineSegArr = sides3.map {
//    _.map(_.xy / scale)
//  }

  //def sides: GraphicElems = sides4.map { ls => Rectangle.fromAxisRatio(ls, 0.3).fill(Red) }
  override def tiles: PolygonArr = ???

  override def tileActives: Arr[PolygonActive] = ???

  override def sides: LineSegArr = transLineSegM3Arr(gridSys.sideLineM3s)
  override def innerSides: LineSegArr = transLineSegM3Arr(gridSys.innerSideLineM3s)
  override def outerSides: LineSegArr = transLineSegM3Arr(gridSys.outerSideLineM3s)

  override def transHSides(inp: HSideArr): LineSegArr =
  { val lls: LineSegLLArr = inp.map(_.lineSegHC.map(gridSys.hCoordLL(_)))
    val m3s = lls.map(_.map(_.toMetres3))
    transLineSegM3Arr(m3s)
  }

  override def transLineSeg(seg: LineSegHC): Option[LineSeg] = ???


  def transLineSegM3Arr(inp: LineSegM3Arr): LineSegArr =
  { val rotated = inp.fromLatLongFocus(focus)
    val visible = rotated.filter(_.zsPos)
    visible.map(_.xyLineSeg(scale))
  }

  override def transCoord(hc: HCoord): Option[Pt2] =
  { val m3 = gridSys.hCoordLL(hc).toMetres3
    val rotated = m3.fromLatLongFocus(focus)
    val opt = ife(rotated.zPos, Some(rotated.xy), None)
    opt.map(_ / scale)
  }

  override def transTile(hc: HCen): Option[Polygon] = {
    val p1 = hc.hVertPolygon.map(gridSys.hCoordLL(_)).toMetres3.fromLatLongFocus(focus)
    val opt = ife(p1.vert(0).zPos, Some(p1.map(_.xy)), None)
    opt.map(_.map(_ / scale))
  }

  override def hCoordOptStr(hc: HCoord): Option[String] = Some(gridSys.hCoordLL(hc).degStr)
}