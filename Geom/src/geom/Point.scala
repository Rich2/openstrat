/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A point in a space. So [[Pt2]]s are points in 2D space. [[Pt3]]s are points in 3D space. LatLongs are points in a 2D curved space. */
trait Point extends Any
{ /** The type of this point for the purposes of LineSegT. May not be the final type. */
  type ThisT <: Point

  /** The type of [[LineSegLike]] that this [[Point]] can start or ends. */
  type LineSegT <: LineSegLike[ThisT]

  /** [[LineSegLike]] from this point to the parameter point. */
  def lineSegTo(endPt: ThisT): LineSegT

  /** [[LinSegLike]] from the parameter point to this point. */
  def lineSegFrom(startPt: ThisT): LineSegT
}

trait PointSeqLike[PT <: Point] extends SeqLike[PT]

trait PointPair[A1 <: Point, A2] extends ElemPair[A1, A2]

trait PointPairArr[A1 <: Point, ArrA1 <: Arr[A1], A2, A <: PointPair[A1, A2]] extends PairArr[A1, ArrA1, A2, A]

trait PointDblN extends Any with Point with ElemDblN

trait PointDblNSeqLike[PT <: PointDblN] extends PointSeqLike[PT]

trait PointDblNPair[A1 <: PointDblN, A2] extends PointPair[A1, A2] with DblNPairElem[A1, A2]

trait PointDblNPairArr[A1 <: PointDblN, ArrA1 <: DblNArr[A1], A2, A <: PointDblNPair[A1, A2]] extends PointPairArr[A1, ArrA1, A2, A] with DblNPairArr[A1, ArrA1, A2, A]

trait PointDbl2 extends Any with PointDblN with ElemDbl2

trait PointDbl2SeqLike[PT <: PointDbl2] extends PointDblNSeqLike[PT]

trait PointDbl2Pair[A1 <: PointDbl2, A2] extends PointDblNPair[A1, A2] with Dbl2PairElem[A1, A2]

trait PointDbl2PairArr[A1 <: PointDbl2, ArrA1 <: Dbl2Arr[A1], A2, A <: PointDbl2Pair[A1, A2]] extends PointDblNPairArr[A1, ArrA1, A2, A] with
  Dbl2PairArr[A1, ArrA1, A2, A]
{ type ThisT <: PointDbl2PairArr[A1, ArrA1, A2, A]
}

trait PointDbl3 extends Any with PointDblN with ElemDbl3

trait PointDbl3Pair[A1 <: PointDbl3, A2] extends PointDblNPair[A1, A2] with Dbl3PairElem[A1, A2]

trait PointDbl3PairArr[A1 <: PointDbl3, ArrA1 <: Dbl3Arr[A1], A2, A <: PointDbl3Pair[A1, A2]] extends PointDblNPairArr[A1, ArrA1, A2, A] with
  Dbl3PairArr[A1, ArrA1, A2, A]
{ type ThisT <: PointDbl3PairArr[A1, ArrA1, A2, A]
}