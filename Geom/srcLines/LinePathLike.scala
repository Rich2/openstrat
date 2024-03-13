/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._

/** A generalisation of a line path where the type of the vertices is not restricted to [[Pt2]]. */
trait LinePathLike[VT] extends Any with SeqSpec[VT]
{ type ThisT <: LinePathLike[VT]
  type PolygonT <: PolygonLike[VT]
  type LineSegT <: LineSegLike[VT]
  type LineSegArrT <: Arr[LineSegT]

  /** maps to a [[LinePathLike]]. This map operates on a single [[LinePathLike]] its not to be confused with a map on Arr of [[LinePathLike]]s. */
  def map[B <: ValueNElem, BB <: LinePathLike[B]](f: VT => B)(implicit build: LinePathBuilder[B, BB]): BB =
  { val res = build.uninitialised(ssLength)
    ssIForeach((i, p) => res.setElemUnsafe(i, f(p)))
    res
  }

  /** This line path with the first vertex removed. This can be useful for borders where the end points may show up in multiple line paths and therefore
   *  sometimes need to be excluded when appending. */
  def tail: ThisT

  /** This line path with the last vertex removed. This can be useful for borders where the end points may show up in multiple line paths and therefore
   *  sometimes need to be excluded when appending. */
  def init: ThisT

  /** This line path with the first and last vertex's removed. This can be useful for borders where the end points may show up in multiple line paths* and
   *  therefore sometimes need to be excluded when appending. */
  def inner: ThisT

  /** Appends another [[LinePathLike]] of this type. Returns a new extended [[LinePathLike]]. */
  @targetName("append") def ++ (operand: ThisT): ThisT

  /** Appends the init of another [[LinePathLike]] of this type. Returns a new extended [[LinePathLike]]. The ++ indicates to append a sequence. the trailing -
   * indicates to drop the last point. */
  @targetName("appendInit") def ++- (operand: ThisT): ThisT

  /** Appends a single vertex of type VT. Returns a new extended [[LinePathLike]]. */
  @targetName("appendVert") def +%[AA >: VT](op: VT): ThisT

  /** Prepends a single vertex of type VT. Returns a new extended [[LinePathLike]]. */
  @targetName("prependVert") def %:(operand: VT): ThisT

  /** Appends the reverse vertex order of another [[LinePathLike]] of this type. Returns a new extended [[LinePathLike]]. */
  @targetName("appendReverse") def ++<(operand: ThisT): ThisT

  /** Appends another [[LinePathLike]] of this type. Returns a [[PolygonLike]]. ++ indicates append a sequence. The enclosing lines indicate to close the
   *  polygon. */
  @targetName("appendToPolygon") def |++|(operand: ThisT): PolygonT

  /** Appends the init of another [[LinePathLike]] of this type. Returns a [[PolygonLike]]. */
  @targetName("appendInitToPolygon") def |++-|(operand: ThisT): PolygonT

  /** Appends a single vertex of type A. Returns a  [[PolygonLike]]. */
  @targetName("appendVertToPolygon") def |+|[AA >: VT](op: VT): PolygonT

  /** Appends the reverse vertex order of another [[LinePathLike]] of this type. Returns a new extended closed [[PolygonLike]]. */
  @targetName("appendReverseToPolygon") def |++<|(operand: ThisT): PolygonT

  /** Closes this [[LinePathLike]] into a [[PolygonLike]] by adding a [[LineSegLike]] from the last vertex to the first. */
  def toPolygon: PolygonT

  final def numVerts: Int = ssLength
}