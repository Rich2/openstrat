/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A class that is like a LineSeg, includes [[LSeg2]] and [[LSegM2]]. The trait takes the type parameter of the vertex. */
trait LSegBase[+VT] extends ValueNElem
{
  /** The start point of the line segment The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LSeg2]] a [[PtM2]] for a
   * [[LSegM2]]. */
  def startPt: VT

  /** The end point of the line segment. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LSeg2]] a [[PtM2]] for a
   * [[LSegM2]]. */
  def endPt: VT

  /** Transforms this [[LSegBase]] into a [[LSegBase]] of type LB, by mapping the vertices to vertices of type VB. */
  def map[VB, LB <: LSegBase[VB]](f: VT => VB)(implicit build: BuilderMapLSegBase[VB, LB]): LB = build.newSeg(f(startPt), f(endPt))

  /** Optionally Transforms this [[LSegBase]] into a [[LSegBase]] of type LB, by mapping the vertices to vertices of type VB, as long as both vertices
   * map to a [[Some]] result. */
  def mapOpt[VB, LB <: LSegBase[VB]](f: VT => Option[VB])(implicit build: BuilderMapLSegBase[VB, LB]): Option[LB] =
    f(startPt).flatMap{ p1 => f(endPt).map(p2 =>build.newSeg(p1, p2)) }
}

/** Base trait for [[Arr]]s of line segments in all geometries. */
trait LSegArrBase[VT, A <: LSegBase[VT]] extends Any, Arr[A]

/** Base trait for buffer classes for line segments in all geometries. */
trait LSegBuffBase[VT, B <: LSegBase[VT]] extends Any

/** Base trait for builders of line segments of all geometris via the map method. Note this is a builder for [[LSegBase]] not a [[LSegArrBase]] so unlike most
 * builders it does not inherit from [[BuilderSeqLike]]. */
trait BuilderMapLSegBase[VT, ST <: LSegBase[VT]]
{ /** Utility method to construct the new [[LSegBase]] for the new [[Point]] type. */
  def newSeg(vStart: VT, vEnd: VT): ST
}

/** A line segment where the start and end points are defined in [[DblNElem]] vertices. */
trait LSegDblN[VT <: DblNElem] extends LSegBase[VT] with DblNElem

/** Specialist [[Arr]] trait for line segments whose vertices are [[DblNElem]]s. */
trait ArrLSegDblN[VT <: DblNElem, A <: LSegDblN[VT]] extends Any with LSegArrBase[VT, A] with ArrDblN[A]

/** A line segment where the start and end points are defined in [[Dbl2Elem]] vertices. Theis will be the case for the classic 2D space line segment a 2D line
 * segment specified in metres and a line segment specified in latitude and longitude. */
trait LSegDbl4[VT <: Dbl2Elem] extends LSegDblN[VT] with Dbl4Elem

/** Specialist [[Arr]] trait for line segments whose start and end points are defined in [[Dbl2Elem]] vertices. */
trait ArrLSegDbl4[VT <: Dbl2Elem, A <: LSegDbl4[VT]] extends Any with ArrLSegDblN[VT, A] with ArrDbl4[A]

/** A line segment where the start and end points are defined in [[Dbl3Elem]] vertices. Theis will be the case for 3-dimensional scalar line segments and
 * 3-dimensional line segments specified in [[Length]] units. */
trait LSegDbl6[VT <: Dbl3Elem] extends LSegDblN[VT] with Dbl6Elem

/** Specialist [[Arr]] trait for line segments with [[Dbl3Elem]] vertices. */
trait ArrLSegDbl6[VT <: Dbl3Elem, A <: LSegDbl6[VT]] extends Any with ArrLSegDblN[VT, A] with ArrDbl6[A]

/** Line segments with vertices that are [[IntNElem]]s. Used in the Tiling module. */
trait LSegIntN[VT <: IntNElem] extends LSegBase[VT] with IntNElem

/** Line segments with vertices that are [[Int4Elem]]s. */
trait LSegInt4[VT <: Int2Elem] extends LSegIntN[VT] with Int4Elem

/** Line segments with vertices that are [[Int6Elem]]s. */
trait LSegInt6[VT <: Int3Elem] extends LSegIntN[VT] with Int6Elem