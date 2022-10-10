/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

trait PolygonLikePair[VT, PT <: PolygonLike[VT], A2] extends SeqDefPair[A2]
{
  def polygon: PT
}

trait PolygonLikePairArr[VT, PT <: PolygonLike[VT], A2, PPT <: PolygonLikePair[VT, PT, A2]] extends SeqDefPairArr[A2, PPT]
{
  def polygonArr: SeqImut[PT]

  def polygonMapPair[VB, PB <: PolygonLike[VB], A2, PPB <: PolygonLikePair[VB, PB, A2], ArrB <: PolygonLikePairArr[VB, PB, A2, PPB]](f: VT => VB)(
    implicit build: PolygonLikePairArrBuilder[VB, PB, A2, PPB, ArrB]): ArrB = {

    //polygonArr.map(f)
    ???
  }
}

trait PolygonLikePairArrBuilder[VB, PB <: PolygonLike[VB], A2, PPB <: PolygonLikePair[VB, PB, A2], ArrB <: PolygonLikePairArr[VB, PB, A2, PPB]]
{
  def newArr(newPolygonArr: Arr[PB], a2Arr: Arr[A2]): ArrB
  //def polygonBuilder: PolygonBuilder[VB, PB]
}

trait PolygonDblsPair[VT <: ElemDblN, PT <: PolygonLike[VT], A2] extends PolygonLikePair[VT, PT, A2]
{
  def unsafeArray: Array[Double]
}

trait PolygonDblsLikePairArr[VT <: ElemDblN, PT <: PolygonLike[VT], A2, PPT <: PolygonDblsPair[VT, PT, A2]] extends PolygonLikePairArr[VT, PT, A2, PPT]
{
  def arrayArrayDbl: Array[Array[Double]]
}

trait PolygonDblsLikePairArrBuilder[VB <: ElemDblN, PB <: PolygonLike[VB], A2, PPB <: PolygonDblsPair[VB, PB, A2],
  ArrB <: PolygonDblsLikePairArr[VB, PB, A2, PPB]] extends PolygonLikePairArrBuilder[VB, PB, A2, PPB, ArrB]
{
  override def newArr(newPolygonArr: Arr[PB], a2Arr: Arr[A2]): ArrB = ???// fromArrayArrayDbl(newPolygonArr.arrayArrayDbl, a2Arr)
  def fromArrayArrayDbl(arrayArrayDbl: Array[Array[Double]], a2Arr: Arr[A2]): ArrB
}