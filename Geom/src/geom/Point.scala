/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._, collection.mutable.ArrayBuffer, Colour.Black

/** A point in a space. So [[Pt2]]s are points in 2D space. [[Pt3]]s are points in 3D space. */
trait Point extends Any

trait PointPair[A1 <: Point, A2] extends ElemPair[A1, A2]

trait PointPairArr[A1 <: Point, ArrA1 <: Arr[A1], A2, A <: PointPair[A1, A2]] extends PairArr[A1, ArrA1, A2, A]

trait PointDblN extends Any with Point with ElemDblN

trait PointDblNPair[A1 <: PointDblN, A2] extends PointPair[A1, A2]

trait PointDblNPairArr[A1 <: PointDblN, ArrA1 <: Arr[A1], A2, A <: PointDblNPair[A1, A2]] extends PointPairArr[A1, ArrA1, A2, A]

trait PointDbl2 extends Any with PointDblN with ElemDbl2

trait PointDbl2Pair[A1 <: PointDbl2, A2] extends PointPair[A1, A2]

trait PointDbl2PairArr[A1 <: PointDbl2, ArrA1 <: Arr[A1], A2, A <: PointDbl2Pair[A1, A2]] extends PointPairArr[A1, ArrA1, A2, A]