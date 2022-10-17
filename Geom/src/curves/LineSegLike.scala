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

  def map[VB, LB <: LineSegLike[VB]](f: VT => VB)(implicit build: LineSegLikeBuilder[VB, LB]) = build.newSeg(f(startPt), f(endPt))
}

trait LineSegLikeArr[A <: LineSeg] extends Any with Arr[A]

trait LineSegLikeBuilder[VT, ST <: LineSegLike[VT]]
{
  def newSeg(vStart: VT, vEnd: VT): ST
}

/** A [[LineSegLike]] object paired with an object of type A2.]] */
trait LineSegLikePair[VT, A1 <: LineSegLike[VT], A2] extends ElemPair[A1, A2]

/** An [[Arr]] of [[LineSegLikePair]]s stored efficiently allowing maping between different [[LineSegLike]] types while keeping the A2 values unchanged. */
trait LineSegLikePairArr[VT, A1 <: LineSegLike[VT], ArrA1 <: Arr[A1], A2, A <: LineSegLikePair[VT, A1, A2]] extends PairArr[A1, ArrA1, A2, A]
{
  /** Maps this to a new [LineSegLikePairArr]] by mapping [[LineSegLike]]s to new [[LineSegLike]]s of type B1 leaving the second parts of the pairs
   * unchanged. */
  def lineSegMapToPair[B1V <: ElemValueN, B1 <: LineSegLike[B1V], ArrB1 <: Arr[B1], B <: LineSegLikePair[B1V, B1, A2],
    ArrB <: LineSegLikePairArr[B1V, B1, ArrB1, A2, B]](f: VT => B1V)(implicit build: LineSegLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]): ArrB =
  { val lineSegs = a1Arr.map(p => p.map[B1V, B1](f)(build.b1Builder))(build.b1ArrBuilder)
    build.pairArrBuilder(lineSegs, a2Array)
  }
}

trait LineSegLikePairArrBuilder[B1V, B1 <: LineSegLike[B1V], ArrB1 <: Arr[B1], B2, B <: LineSegLikePair[B1V, B1, B2],
  ArrB <: LineSegLikePairArr[B1V, B1, ArrB1, B2, B]] extends PairArrBuilder[B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1, in this case a [[LineSegLike]]. The return type has been narrowed as it is needed for the
 * polygonMapPair method on [[LineSegLikePairArr]]. */
  def b1Builder: LineSegLikeBuilder[B1V, B1]
}

trait LineSegDblsPairArr[VT <: ElemDblN, A1 <: LineSegLike[VT], ArrA1 <: Arr[A1], A2, A <: LineSegLikePair[VT, A1, A2]] extends LineSegLikePairArr[VT, A1, ArrA1, A2, A]
{ type ThisT <: LineSegDblsPairArr[VT, A1, ArrA1, A2, A]
  def a1ArrayDbl: Array[Double]
  def fromArrays(a1Arr: Array[Double], a2Arr: Array[A2]): ThisT
  override final def unsafeSameSize(length: Int): ThisT = fromArrays(new Array[Double](a1ArrayDbl.length), a2Array)
}

//trait LinSeDblsPairArrBuilder[]