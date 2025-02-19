/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._

/** A generalisation of a line path where the type of the vertices is not restricted to [[Pt2]]. */
trait LinePathLike[VT] extends Any with VertBased[VT]
{ type ThisT <: LinePathLike[VT]
  type PolygonT <: PolygonLike[VT]
  type LineSegT <: LineSegLike[VT]
  type LineSegArrT <: Arr[LineSegT]

  /** maps to a [[LinePathLike]]. This map operates on a single [[LinePathLike]] its not to be confused with a map on Arr of [[LinePathLike]]s. */
  def map[B <: ValueNElem, BB <: LinePathLike[B]](f: VT => B)(implicit build: LinePathBuilder[B, BB]): BB =
  { val res = build.uninitialised(numElems)
    iForeach((i, p) => res.setElemUnsafe(i, f(p)))
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

  /** Appends the tail (without its first point) of the operand [[LinePathLike]] of this type. The ++ indicates to append a sequence. The trailing indicates to
   * drop the first point of the operand. */
  @targetName("appendTail") def +-+(operand: ThisT): ThisT

  /** Appends a single vertex of type VT. Returns a new extended [[LinePathLike]]. */
  @targetName("appendPt") def +%(operandPt: VT): ThisT

  /** Prepends a single vertex of type VT. Returns a new extended [[LinePathLike]]. */
  @targetName("prepend") def %:(operand: VT): ThisT

  /** Prepends a single vertex of type VT. Returns a new extended [[LinePathLike]]. */
  @targetName("prependReverse") def %<:(operand: VT): ThisT

  /** Appends the reverse vertex order of another [[LinePathLike]] of this type. Returns a new extended [[LinePathLike]]. The < character after the ++ indicates
   * that is the operand that is being reversed. */
  @targetName("appendReverse") def ++<(operand: ThisT): ThisT

  /** Appends the operand point and closes the path into a [[PolygonLike]] of the matching type. +% indicates to append a point. The enclosing '|' characters
   * indicate to close the line path into a polygon. */
  @targetName("appendPtToPolygon") def |+%|(operandPt: VT): PolygonT

  /** Appends the operand [[LinePathLike]] of this type and closes the path into a [[PolygonLike]] of the matching type. ++ indicates to append a sequence. The
   * enclosing '|' characters indicate to close the line path into a polygon. */
  @targetName("appendToPolygon") def |++|(operand: ThisT): PolygonT

  /** Appends the tail (without its first point) of the operand [[LinePathLike]] of this type, closing the path to a [[PolygonLike]] of the matching type. ++
   *  indicates append a sequence. The - between the + characters indicates to drop the first point of the operand. The enclosing '|' characters indicate to
   *  close the line path into a polygon. */
  @targetName("appendTailToPolygon") def |+-+|(operand: ThisT): PolygonT

  /** Appends the init of another [[LinePathLike]] of this type to the init of this [[LinePathLike]], closing the path to return a [[PolygonLike]] of the
   * matching type. The - before the ++ indicates to drop the last point of this line path. The - after the ++ indicates to drop the end point of the
   * operand. */
  @targetName("initAppendInitToPolygon") def |-++-|(operand: ThisT): PolygonT

  /** Appends a single vertex of type A. Returns a  [[PolygonLike]]. */
  @targetName("appendVertToPolygon") def |+|[AA >: VT](op: VT): PolygonT

  /** Appends the reverse vertex order of another [[LinePathLike]] of this type. Returns a new extended closed [[PolygonLike]]. The < after the ++ indicates it
   * is the operand to be reversed. */
  @targetName("appendReverseToPolygon") def |++<|(operand: ThisT): PolygonT

  /** Reverses this line path and then appends the operand. The < character between the + characters indicates that it this line path that is reversed. */
  @targetName("reverseAppend") def +<+(operand: ThisT): ThisT

  /** Reverses this line path and then appends the operand. The < character between the + characters indicates that it this line path that is reversed. */
  @targetName("reverseAppendReverse") def +<+<(operand: ThisT): ThisT

  /** Reverses this line path, appends the operand line path and then closes to a polygon. The < character between the + characters indicates
   * that this line is reversed. The | characters at the begining and the end indicate to close to a polygon. */
  @targetName("reverseAppendToPolygon") def |+<+|(operand: ThisT): PolygonT

  /** Closes this [[LinePathLike]] into a [[PolygonLike]] by adding a [[LineSegLike]] from the last vertex to the first. */
  def toPolygon: PolygonT

  final def numVerts: Int = numElems
}