/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

trait HCenArrLayerBase[A, ArrA <: Arr[A]]

trait HCenArrBuilder[A, ArrT <: Arr[A], LayerT <: HCenArrLayerBase[A, ArrT]]
{
  def uninitialised(length: Int): LayerT
}

object HCenArrLayerBase{
  implicit def intNEv[A <: IntNElem, ArrT <: IntNArr[A]](implicit intNArrMapBuilder: IntNArrMapBuilder[A, ArrT]): HCenArrBuilder[A, ArrT, HCenIntNArrLayer[A, ArrT]] =
    new HCenArrBuilder[A, ArrT, HCenIntNArrLayer[A, ArrT]]
  {
    override def uninitialised(length: Int): HCenIntNArrLayer[A, ArrT] = new HCenIntNArrLayer[A, ArrT](new Array[Array[Int]](length))
  }
}

class HCenIntNArrLayer[A <: IntNElem, ArrT <: IntNArr[A]](val arrayArrayInt: Array[Array[Int]])(implicit intNArrMapBuilder: IntNArrMapBuilder[A, ArrT])
extends HCenArrLayerBase[A, ArrT]
{

}

