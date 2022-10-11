/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

trait PolygonLikePair[VT, A1 <: PolygonLike[VT], A2] extends SeqDefPair[A1, A2]
{
  def polygon: A1
}

trait PolygonLikePairArr[A1V, A1 <: PolygonLike[A1V], A2, A <: PolygonLikePair[A1V, A1, A2]] extends SeqDefPairArr[A1, A2, A]
{
  def polygonArr: SeqImut[A1]

  def polygonMapPair[B1V <: ElemValueN, B1 <: PolygonLike[B1V], ArrB1 <: SeqImut[B1], B <: PolygonLikePair[B1V, B1, A2],
    ArrB <: PolygonLikePairArr[B1V, B1, A2, B]](f: A1V => B1V)(implicit build: PolygonLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]): ArrB =
  {
    val polygons = polygonArr.map(p => p.map[B1V, B1](f)(build.polygonBuilder))(build.singleBuilder)
    build.pairBuilder(polygons, a2Array)
  }
}

trait PolygonLikePairArrBuilder[B1V, B1 <: PolygonLike[B1V], ArrB1 <: SeqImut[B1], A2, B <: PolygonLikePair[B1V, B1, A2],
  ArrB <: PolygonLikePairArr[B1V, B1, A2, B]] extends ArrBuilder[B, ArrB]
{
  def polygonBuilder: PolygonBuilder[B1V, B1]
  def singleBuilder: ArrBuilder[B1, ArrB1]
  def pairBuilder(polygonArr: ArrB1, a2s: Array[A2]): ArrB
  //def newArr(newPolygonArr: Arr[PB], a2Arr: Arr[A2]): ArrB
  //def polygonBuilder: PolygonBuilder[VB, PB]
}

trait PolygonDblsPair[A1V <: ElemDblN, A1 <: PolygonLike[A1V], A2] extends PolygonLikePair[A1V, A1, A2]
{
  def unsafeArray: Array[Double]
}

trait PolygonDblsLikePairArr[A1V <: ElemDblN, A1 <: PolygonLike[A1V], A2, A <: PolygonDblsPair[A1V, A1, A2]] extends PolygonLikePairArr[A1V, A1, A2, A]
{
  def arrayArrayDbl: Array[Array[Double]]
}

trait PolygonDblsLikePairArrBuilder[VB <: ElemDblN, B1 <: PolygonLike[VB], ArrC <: SeqImut[B1], A2, PPB <: PolygonDblsPair[VB, B1, A2],
  ArrB <: PolygonDblsLikePairArr[VB, B1, A2, PPB]] extends PolygonLikePairArrBuilder[VB, B1, ArrC, A2, PPB, ArrB]
{
 // override def newArr(newPolygonArr: Arr[PB], a2Arr: Arr[A2]): ArrB = ???// fromArrayArrayDbl(newPolygonArr.arrayArrayDbl, a2Arr)
  def fromArrayArrayDbl(arrayArrayDbl: Array[Array[Double]], a2Arr: Arr[A2]): ArrB
}