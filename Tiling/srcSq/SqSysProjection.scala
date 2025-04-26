/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, pgui._

trait SqSysProjection extends TSysProjection
{ type SysT <: SqGridSys
  var gChild: SqGridSys
  def foreach(f: SqCen => Unit): Unit = gChild.foreach(f)

  def sidesForeach(f: SqSep => Unit): Unit = gChild.sidesForeach(f)

  def sidesMap[B, ArrB <: Arr[B]](f: SqSep => B)(implicit build: BuilderMapArr[B, ArrB]) =
  { val buff = build.newBuff()
    sidesForeach{ss => buff.grow(f(ss))}
    build.buffToSeqLike(buff)
  }

  /** only use for projection's known [[SqCoord]]s. */
  def transCoord(sc: SqCoord): Pt2
  def transOptCoord(sc: SqCoord): Option[Pt2] = ???
  def transOptLineSeg(seg: LineSegSC): Option[LSeg] = ???

  /** Set the perspective, The position of the view. the rotation and the scale. */
  def setView(view: Any): Unit
}

case class SqSysProjectionFlat(parent: SqGridSys, panel: Panel) extends SqSysProjection with TSysProjectionFlat
{ type SysT = SqGridSys
  pixelsPerC = parent.fullDisplayScale(panel.width, panel.height)
  override def pixelsPerTile: Double = pixelsPerC * 2
  override def pixelsPerR: Double = pixelsPerC
  var focus: Vec2 = parent.defaultView(pixelsPerC).vec
  override def ifTileScale(minScale: Double, elems: => GraphicElems): GraphicElems = ife(pixelsPerTile >= minScale, elems, RArr())

  var gChild: SqGridSys = getGChild
  def getGChild: SqGridSys = parent
  def setGChild: Unit = gChild = getGChild
  override def transCoord(sc: SqCoord): Pt2 = (parent.flatSqCoordToPt2(sc) - focus).scale(pixelsPerC)
  override def transOptCoord(sc: SqCoord): Option[Pt2] = Some(parent.flatSqCoordToPt2(sc).slate(-focus).scale(pixelsPerC))
  override def tilePolygons: PolygonGenArr = gChild.map(_.sqVertPolygon.map(parent.flatSqCoordToPt2(_)).slate(-focus).scale(pixelsPerC))

  override def tileActives: RArr[PolygonActive] =
    gChild.map(hc => hc.sqVertPolygon.map(parent.flatSqCoordToPt2(_)).slate(-focus).scale(pixelsPerC).active(hc))

  /** The visible hex sides. */
  override def sideLines: LineSegArr = gChild.sideLines.slate(-focus).scale(pixelsPerC)//LineSegArr()
    //gChild.sideLineSegSqCs.map(_.map(gridSys.hCoordToPt2(_))).slate(-focus).scale(pixCScale)

  /** The visible inner hex sides. */
  override def innerSideLines: LineSegArr = LineSegArr()

  /** The visible outer hex sides. */
  override def outerSideLines: LineSegArr = LineSegArr()

  override def transOptLineSeg(seg: LineSegSC): Option[LSeg] = Option.map2(transOptCoord(seg.startPt), transOptCoord(seg.endPt)) { (p1, p2) => LSeg(p1, p2) }

  override def setView(view: Any): Unit = view match {
    case hv: SGView => {
      pixelsPerC = hv.pixelsPerC
      focus = hv.vec
      gChild = getGChild
    }
    case d: Double => pixelsPerC = d
    case _ =>
  }
}