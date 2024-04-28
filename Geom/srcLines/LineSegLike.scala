/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A class that is like a LineSeg, includes [[LineSeg]] and [[LineSegM2]]. The trait takes the type parameter of the vertex. */
trait LineSegLike[VT] extends ValueNElem
{
  /** The start point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegM2]]. */
  def startPt: VT

  /** The end point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegM2]]. */
  def endPt: VT

  /** Transforms this [[LineSegLike]] into a [[LineSegLike]] of type LB, by mapping the vertices to vertices of type VB. */
  def map[VB, LB <: LineSegLike[VB]](f: VT => VB)(implicit build: LineSegLikeBuilderMap[VB, LB]): LB = build.newSeg(f(startPt), f(endPt))

  /** Optionally Transforms this [[LineSegLike]] into a [[LineSegLike]] of type LB, by mapping the vertices to vertices of type VB, as long as both
   * vertices map to a [[Some]] result. */
  def mapOpt[VB, LB <: LineSegLike[VB]](f: VT => Option[VB])(implicit build: LineSegLikeBuilderMap[VB, LB]): Option[LB] =
    f(startPt).flatMap{ p1 => f(endPt).map(p2 =>build.newSeg(p1, p2)) }
}

trait LineSegLikeArr[VT, A <: LineSegLike[VT]] extends Any with Arr[A]

trait LineSegLikeBuff[VT, B <: LineSegLike[VT]] extends Any

/** Builder for [[LineSegLike]] map operations. Note this is a builder for [[LineSegLike]] not a [[LineSegLikeArr]] so unlike most builders it does
 * not inherit from [[BuilderSeqLike]]. */
trait LineSegLikeBuilderMap[VT, ST <: LineSegLike[VT]]
{ /** Utility method to construct the new [[LineSegLike]] for the new [[Point]] type. */
  def newSeg(vStart: VT, vEnd: VT): ST
}

/** A line segment where the start and end points are defined in [[DblNElem]] vertices. */
trait LineSegLikeDblN[VT <: DblNElem] extends LineSegLike[VT] with DblNElem

trait LineSegLikeDblNArr[VT <: DblNElem, A <: LineSegLikeDblN[VT]] extends Any with LineSegLikeArr[VT, A] with ArrDblN[A]

/** A line segment where the start and end points are defined in [[Dbl2Elem]] vertices. Theis will be the case for the classic 2D space line segment
 * a 2D line segment specified in metres and a line segment specified in latitude and longitude. */
trait LineSegLikeDbl4[VT <: Dbl2Elem] extends LineSegLikeDblN[VT] with Dbl4Elem

trait LineSegLikeDbl4Arr[VT <: Dbl2Elem, A <: LineSegLikeDbl4[VT]] extends Any with LineSegLikeDblNArr[VT, A] with Dbl4Arr[A]

/** A line segment where the start and end points are defined in [[Dbl3Elem]] vertices. Theis will be the case for 3D space line segment and 3D line
 *  segment specified in metres. */
trait LineSegLikeDbl6[VT <: Dbl3Elem] extends LineSegLikeDblN[VT] with Dbl6Elem

trait LineSegLikeDbl6Arr[VT <: Dbl3Elem, A <: LineSegLikeDbl6[VT]] extends Any with LineSegLikeDblNArr[VT, A] with ArrDbl6[A]

trait LineSegLikeIntN[VT <: IntNElem] extends LineSegLike[VT] with IntNElem

trait LineSegLikeInt4[VT <: Int2Elem] extends LineSegLikeIntN[VT] with Int4Elem

trait LineSegLikeInt6[VT <: Int3Elem] extends LineSegLikeIntN[VT] with Int6Elem