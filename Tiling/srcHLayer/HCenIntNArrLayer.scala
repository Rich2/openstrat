/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

trait HCenArrLayer[A, ArrA <: Arr[A]]

trait HCenArrLayerBuilder[A, ArrT <: Arr[A], LayerT <: HCenArrLayer[A, ArrT]]
{
  def uninitialised(gridSys: HGridSys): LayerT
  def iSet(layer: LayerT, i: Int, arr: ArrT): Unit
}

object HCenArrLayer
{
  implicit def intNEv[A <: IntNElem, ArrT <: IntNArr[A]](implicit intNArrMapBuilder: IntNArrMapBuilder[A, ArrT]): HCenArrLayerBuilder[A, ArrT, HCenIntNArrLayer[A, ArrT]] =
    new HCenArrLayerBuilder[A, ArrT, HCenIntNArrLayer[A, ArrT]]
    { val mapBuilder: IntNArrMapBuilder[A, ArrT] = intNArrMapBuilder
      override def uninitialised(gridSys: HGridSys): HCenIntNArrLayer[A, ArrT] =
        new HCenIntNArrLayer[A, ArrT](new Array[Array[Int]](gridSys.numTiles), gridSys)
      override def iSet(layer: HCenIntNArrLayer[A, ArrT], i: Int, arr: ArrT): Unit = layer.arrayArrayInt(i) = arr.unsafeArray
    }
}

class HCenIntNArrLayer[A <: IntNElem, ArrT <: IntNArr[A]](val arrayArrayInt: Array[Array[Int]], val gridSys: HGridSys)(
  implicit intNArrMapBuilder: IntNArrMapBuilder[A, ArrT]) extends HCenArrLayer[A, ArrT]
{

}