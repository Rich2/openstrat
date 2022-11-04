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

  def map[VB, LB <: LineSegLike[VB]](f: VT => VB)(implicit build: LineSegLikeBuilder[VB, LB]) = build.newSeg(f(startPt), f(endPt))
}

trait LineSegLikeArr[A <: LineSeg] extends Any with Arr[A]

trait LineSegLikeBuilder[VT, ST <: LineSegLike[VT]]
{
  def newSeg(vStart: VT, vEnd: VT): ST
}

/** A line segment where the start and end points are defined in [[ElemDblN]] vertices. */
trait LineSegLikeDblN[VT <: ElemDblN] extends LineSegLike[VT] with ElemDblN

/** A line segment where the start and end points are defined in [[ElemDbl2]] vertices. Theis will be the case for the classic 2D space line segment
 * a 2D line segment specified in metres and a line segment specified in latitude and longitude. */
trait LineSegLikeDbl4[VT <: ElemDbl2] extends LineSegLikeDblN[VT] with ElemDbl4

/** A line segment where the start and end points are defined in [[ElemDbl3]] vertices. Theis will be the case for 3D space line segment and 3D line
 *  segment specified in metres. */
trait LineSegLikeDbl6[VT <: ElemDbl3] extends LineSegLikeDblN[VT] with ElemDbl6

trait LineSegLikeIntN[VT <: ElemIntN] extends LineSegLike[VT] with ElemIntN

trait LineSegLikeInt4[VT <: ElemInt2] extends LineSegLikeIntN[VT] with ElemInt4