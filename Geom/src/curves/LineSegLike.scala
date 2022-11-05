/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A class that is like a LineSeg, includes [[LineSeg]] and [[LineSegM2]]. The trait takes the type parameter of the vertex. */
trait LineSegLike[VT] extends ElemValueN
{
  /** The start point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegM2]]. */
  def startPt: VT

  /** The end point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegM2]]. */
  def endPt: VT

  def map[VB, LB <: LineSegLike[VB]](f: VT => VB)(implicit build: LineSegLikeMapBuilder[VB, LB]) = build.newSeg(f(startPt), f(endPt))
}

trait LineSegLikeArr[VT, A <: LineSegLike[VT]] extends Any with Arr[A]

trait LineSegLikeBuff[VT, B <: LineSegLike[VT]] extends Any

/** Builder for [[LineSegLike]] map operations. Note this is a builder for [[LineSegLike]] not a [[LineSegLikeArr]] so unlike most builders it does
 * not inherit from [[SeqLikeCommonBuilder]]. */
trait LineSegLikeMapBuilder[VT, ST <: LineSegLike[VT]]
{
  def newSeg(vStart: VT, vEnd: VT): ST
}

/** A line segment where the start and end points are defined in [[ElemDblN]] vertices. */
trait LineSegLikeDblN[VT <: ElemDblN] extends LineSegLike[VT] with ElemDblN

trait LineSegLikeDblNArr[VT <: ElemDblN, A <: LineSegLikeDblN[VT]] extends Any with LineSegLikeArr[VT, A] with DblNArr[A]

trait LineSegLikeDblNBuff[VT <: ElemDblN, A <: LineSegLikeDblN[VT]] extends Any with LineSegLikeBuff[VT, A] with DblNBuff[A]

/** A line segment where the start and end points are defined in [[ElemDbl2]] vertices. Theis will be the case for the classic 2D space line segment
 * a 2D line segment specified in metres and a line segment specified in latitude and longitude. */
trait LineSegLikeDbl4[VT <: ElemDbl2] extends LineSegLikeDblN[VT] with ElemDbl4

trait LineSegLikeDbl4Arr[VT <: ElemDbl2, A <: LineSegLikeDbl4[VT]] extends Any with LineSegLikeDblNArr[VT, A] with Dbl4Arr[A]

//trait LineSegLikeDbl4Buff[VT <: ElemDbl2, A <: LineSegLikeDbl4[VT]] extends Any with LineSegLikeDblNBuff[VT, A] with Dbl4Buff[A]

/** A line segment where the start and end points are defined in [[ElemDbl3]] vertices. Theis will be the case for 3D space line segment and 3D line
 *  segment specified in metres. */
trait LineSegLikeDbl6[VT <: ElemDbl3] extends LineSegLikeDblN[VT] with ElemDbl6

trait LineSegLikeDbl6Arr[VT <: ElemDbl3, A <: LineSegLikeDbl6[VT]] extends Any with LineSegLikeDblNArr[VT, A] with Dbl6Arr[A]

trait LineSegLikeIntN[VT <: ElemIntN] extends LineSegLike[VT] with ElemIntN

trait LineSegLikeInt4[VT <: ElemInt2] extends LineSegLikeIntN[VT] with ElemInt4