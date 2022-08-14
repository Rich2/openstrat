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
      scale = gridSys.cScale / hv.pxScale
      focus = gridSys.hCoordLL(hv.hCoord)
    }
    //case d: Double => cPScale = d
    case _ =>
  }
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

  override def sides: LineSegArr = transLineSegM3Arr(gridSys.sideLineM3s)
  override def innerSides: LineSegArr = transLineSegM3Arr(gridSys.innerSideLineM3s)
  def outerSides: LineSegArr = transLineSegM3Arr(gridSys.outerSideLineM3s)

  def transHSides(inp: HSideArr): LineSegArr = {
    val lls: LineSegLLArr = inp.map(_.lineSegHC.map(gridSys.hCoordLL(_)))
    val m3s = lls.map(_.map(_.toMetres3))
    transLineSegM3Arr(m3s)
  }

  def transLineSegM3Arr(inp: LineSegM3Arr): LineSegArr ={
    val rotated = inp.fromLatLongFocus(focus)
    val visible = rotated.filter(_.zsPos)
    visible.map(_.xyLineSeg(scale))
  }
}
