/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, pgui._

trait SqSysProjection extends TSysProjection
{ type GridT <: SqGridSys
}

case class SqSysProjectionFlat(gridSys: SqGridSys, panel: Panel) extends SqSysProjection with TSysProjectionFlat
{
  type GridT = SqGridSys
  var pixCScale: Double = gridSys.fullDisplayScale(panel.width, panel.height)
  override def pixTileScale: Double = pixCScale * 2
  override def pixRScale: Double = pixCScale
  var focus: Vec2 = gridSys.defaultView(pixCScale).vec
  override def ifGScale(minScale: Double, elems: => GraphicElems): GraphicElems = ???

  var gChild: SqGridSys = getGChild

  def getGChild: SqGridSys = gridSys

  def setGChild: Unit = gChild = getGChild

  override def tiles: PolygonArr = PolygonArr()

  override def tileActives: Arr[PolygonActive] = Arr()

  /** The visible hex sides. */
  override def sides: LineSegArr = LineSegArr()

  /** The visible inner hex sides. */
  override def innerSides: LineSegArr = LineSegArr()

  /** The visible outer hex sides. */
  override def outerSides: LineSegArr = LineSegArr()
}
