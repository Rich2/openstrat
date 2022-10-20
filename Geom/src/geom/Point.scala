/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._, collection.mutable.ArrayBuffer, Colour.Black

/** A point in a space. So [[Pt2]]s are points in 2D space. [[Pt3]]s are points in 3D space. */
trait Point extends Any

trait PointPair[A1 <: Point, A2] extends ElemPair[A1, A2]

trait PointPairArr[A1 <: Point, ArrA1 <: Arr[A1], A2, A <: PointPair[A1, A2]] extends PairArr[A1, ArrA1, A2, A]

trait PointDblN extends Any with Point with ElemDblN

trait PointDblNPair[A1 <: PointDblN, A2] extends PointPair[A1, A2] with ElemDblNPair[A1, A2]

trait PointDblNPairArr[A1 <: PointDblN, ArrA1 <: DblNArr[A1], A2, A <: PointDblNPair[A1, A2]] extends PointPairArr[A1, ArrA1, A2, A] with DblNPairArr[A1, ArrA1, A2, A]


trait PointDbl2 extends Any with PointDblN with ElemDbl2

trait PointDbl2Pair[A1 <: PointDbl2, A2] extends PointDblNPair[A1, A2]
{ def a1Dbl1: Double
  def a1Dbl2: Double
}

trait PointDbl2PairArr[A1 <: PointDbl2, ArrA1 <: Dbl2Arr[A1], A2, A <: PointDbl2Pair[A1, A2]] extends PointDblNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: PointDbl2PairArr[A1, ArrA1, A2, A]
  def newPair(dbl1: Double, dbl2: Double, a2: A2): A
  override final def apply(index: Int): A = newPair(a1ArrayDbl(index * 2), a1ArrayDbl(index * 2 + 1), a2Array(index))

  override final def unsafeSetElem(i: Int, value: A): Unit = { a1ArrayDbl(i * 2) = value.a1Dbl1; a1ArrayDbl(i * 2 + 1) = value.a1Dbl2
    a2Array(i) = value.a2 }
}