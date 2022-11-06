/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, pgui._

trait SqSysProjection extends TSysProjection
{ type GridT <: SqGridSys
  var gChild: SqGridSys
  def foreach(f: SqCen => Unit): Unit = gChild.foreach(f)

  def sidesForeach(f: SqSide => Unit): Unit = gChild.sidesForeach(f)

  def sidesMap[B, ArrB <: Arr[B]](f: SqSide => B)(implicit build: ArrMapBuilder[B, ArrB]) =
  { val buff = build.newBuff()
    sidesForeach{ss => buff.grow(f(ss))}
    build.buffToBB(buff)
  }

  /** only use for projection's known [[SqCoord]]s. */
  def transCoord(sc: SqCoord): Pt2
  def transOptCoord(sc: SqCoord): Option[Pt2] = ???
  def transOptLineSeg(seg: LineSegSC): Option[LineSeg] = ???
}

case class SqSysProjectionFlat(parent: SqGridSys, panel: Panel) extends SqSysProjection with TSysProjectionFlat
{
  type GridT = SqGridSys
  var pixCScale: Double = parent.fullDisplayScale(panel.width, panel.height)
  override def pixTileScale: Double = pixCScale * 2
  override def pixRScale: Double = pixCScale
  var focus: Vec2 = parent.defaultView(pixCScale).vec
  override def ifGScale(minScale: Double, elems: => GraphicElems): GraphicElems = ???

  var gChild: SqGridSys = getGChild
  def getGChild: SqGridSys = parent
  def setGChild: Unit = gChild = getGChild
  override def transCoord(sc: SqCoord): Pt2 = (parent.flatSqCoordToPt2(sc) - focus).scale(pixCScale)
  override def transOptCoord(sc: SqCoord): Option[Pt2] = Some(parent.flatSqCoordToPt2(sc).slate(-focus).scale(pixCScale))
  override def tilePolygons: PolygonArr = gChild.map(_.sqVertPolygon.map(parent.flatSqCoordToPt2(_)).slate(-focus).scale(pixCScale))

  override def tileActives: RArr[PolygonActive] =
    gChild.map(hc => hc.sqVertPolygon.map(parent.flatSqCoordToPt2(_)).slate(-focus).scale(pixCScale).active(hc))

  /** The visible hex sides. */
  override def sideLines: LineSegArr = gChild.sideLines.slate(-focus).scale(pixCScale)//LineSegArr()
    //gChild.sideLineSegSqCs.map(_.map(gridSys.hCoordToPt2(_))).slate(-focus).scale(pixCScale)

  /** The visible inner hex sides. */
  override def innerSideLines: LineSegArr = LineSegArr()

  /** The visible outer hex sides. */
  override def outerSideLines: LineSegArr = LineSegArr()

  override def transOptLineSeg(seg: LineSegSC): Option[LineSeg] =
    transOptCoord(seg.startPt).map2(transOptCoord(seg.endPt)) { (p1, p2) => LineSeg(p1, p2) }
}