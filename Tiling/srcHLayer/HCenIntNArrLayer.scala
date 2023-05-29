/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

class HCenIntNArrLayer[A <: IntNElem, ArrA <: IntNArr[A]](val arrayArrayInt: Array[Array[Int]], val gridSys: HGridSys)(
implicit val arrBuilder: IntNArrMapBuilder[A, ArrA]) extends HCenArrLayer[A, ArrA]
{
  override def map[B, ArrB <: Arr[B], LayerT <: HCenArrLayer[B, ArrB]](f: ArrA => ArrB)(implicit builder: HCenArrLayerBuilder[B, ArrB, LayerT]): LayerT = {
    val res = builder.uninitialised(gridSys)
    var i = 0
    while (i < numTiles)
    { builder.iSet(res, i, f(arrBuilder.fromIntArray(arrayArrayInt(i))))
      i += 1
    }
    res
  }
}