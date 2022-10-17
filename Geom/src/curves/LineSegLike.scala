/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A class that is like a LineSeg, includes [[LineSeg]] and [[LineSegM]]. The trait takes the type parameter of the vertex. */
trait LineSegLike[VT]
{
  /** The start point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegM]]. */
  def startPt: VT

  /** The end point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegM]]. */
  def endPt: VT

  def map[VB, LB <: LineSegLike[VB]](f: VT => VB)(implicit build: LineSegBuilder[VB, LB]) = build.newSeg(f(startPt), f(endPt))
}

trait LineSegLikeArr[A <: LineSeg] extends Any with Arr[A]

trait LineSegBuilder[VT, ST <: LineSegLike[VT]]
{
  def newSeg(vStart: VT, vEnd: VT): ST
}

trait LineSegLikePair[VT, A1 <: LineSegLike[VT], A2] extends ElemPair[A1, A2]

trait LineSegLikePairArr[VT, A1 <: LineSegLike[VT], ArrA1 <: Arr[A1], A2, A <: LineSegLikePair[VT, A1, A2]] extends PairArr[A1, ArrA1, A2, A]

trait LineSegDblsPairArr[VT <: ElemDblN, A1 <: LineSegLike[VT], ArrA1 <: Arr[A1], A2, A <: LineSegLikePair[VT, A1, A2]] extends LineSegLikePairArr[VT, A1, ArrA1, A2, A]
{ type ThisT <: LineSegDblsPairArr[VT, A1, ArrA1, A2, A]
  def a1ArrayDbl: Array[Double]
  def fromArrays(a1Arr: Array[Double], a2Arr: Array[A2]): ThisT
}

//trait LinSeDblsPairArrBuilder[]