/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A point in a space. So [[Pt2]]s are points in 2D space. [[Pt3]]s are points in 3D space. LatLongs are points in a 2D curved space. */
trait Point extends Any
{ /** The type of this point for the purposes of LineSegT. May not be the final type. */
  type ThisT <: Point

  /** The type of [[LSegBase]] that this [[Point]] can start or ends. */
  type LineSegT <: LSegBase[ThisT]
}

trait PointSeqLike[PT <: Point] extends Any with SeqLike[PT]

trait PointPair[A1 <: Point, A2] extends PairFinalA1Elem[A1, A2]

trait PointPairArr[A1 <: Point, ArrA1 <: Arr[A1], A2, A <: PointPair[A1, A2]] extends ArrPairFinalA1[A1, ArrA1, A2, A]

trait PointDblN extends Any with Point with DblNElem

trait PointDblNSeqLike[PT <: PointDblN] extends Any with PointSeqLike[PT]

trait PointDblNPair[A1 <: PointDblN, A2] extends PointPair[A1, A2] with PairDblNElem[A1, A2]

trait PointDblNPairArr[A1 <: PointDblN, ArrA1 <: ArrDblN[A1], A2, A <: PointDblNPair[A1, A2]] extends PointPairArr[A1, ArrA1, A2, A] with ArrPairDblN[A1, ArrA1, A2, A]

trait PointDbl2 extends Any with PointDblN with Dbl2Elem

trait PointDbl2SeqLike[PT <: PointDbl2] extends Any with PointDblNSeqLike[PT]

trait PointDbl2Pair[A1 <: PointDbl2, A2] extends PointDblNPair[A1, A2] with PairDbl2Elem[A1, A2]

trait PointDbl2PairArr[A1 <: PointDbl2, ArrA1 <: ArrDbl2[A1], A2, A <: PointDbl2Pair[A1, A2]] extends PointDblNPairArr[A1, ArrA1, A2, A] with
  ArrPairDbl2[A1, ArrA1, A2, A]
{ type ThisT <: PointDbl2PairArr[A1, ArrA1, A2, A]
}

trait PointDbl3 extends Any with PointDblN with Dbl3Elem

trait PointDbl3Pair[A1 <: PointDbl3, A2] extends PointDblNPair[A1, A2] with PairDbl3Elem[A1, A2]

trait PointDbl3PairArr[A1 <: PointDbl3, ArrA1 <: ArrDbl3[A1], A2, A <: PointDbl3Pair[A1, A2]] extends PointDblNPairArr[A1, ArrA1, A2, A],
  ArrPairDbl3[A1, ArrA1, A2, A]
{ type ThisT <: PointDbl3PairArr[A1, ArrA1, A2, A]
}