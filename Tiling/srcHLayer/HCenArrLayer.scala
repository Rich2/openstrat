/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** A data layer for an [[HGridSys]] where each tile's data is an [[Arr]] of the specified type. */
trait HCenArrLayer[A, ArrA <: Arr[A]]
{
  def gridSys: HGridSys
  def numTiles: Int = gridSys.numTiles
}

trait HCenArrLayerBuilder[A, ArrT <: Arr[A], LayerT <: HCenArrLayer[A, ArrT]]
{
  def uninitialised(gridSys: HGridSys): LayerT
  def iSet(layer: LayerT, i: Int, arr: ArrT): Unit
}

object HCenArrLayer extends HCenArrLayerLowPrioity
{
  implicit def intNEv[A <: IntNElem, ArrT <: IntNArr[A]](implicit intNArrMapBuilder: IntNArrMapBuilder[A, ArrT]): HCenArrLayerBuilder[A, ArrT, HCenIntNArrLayer[A, ArrT]] =
    new HCenArrLayerBuilder[A, ArrT, HCenIntNArrLayer[A, ArrT]]
    { val mapBuilder: IntNArrMapBuilder[A, ArrT] = intNArrMapBuilder
      override def uninitialised(gridSys: HGridSys): HCenIntNArrLayer[A, ArrT] =
        new HCenIntNArrLayer[A, ArrT](new Array[Array[Int]](gridSys.numTiles), gridSys)
      override def iSet(layer: HCenIntNArrLayer[A, ArrT], i: Int, arr: ArrT): Unit = layer.arrayArrayInt(i) = arr.unsafeArray
    }
}

trait HCenArrLayerLowPrioity
{
  implicit def RArrEv[A]: HCenArrLayerBuilder[A, RArr[A], HCenRArrLayer[A]] = new HCenArrLayerBuilder[A, RArr[A], HCenRArrLayer[A]]{
    override def uninitialised(gridSys: HGridSys): HCenRArrLayer[A] = ???

    override def iSet(layer: HCenRArrLayer[A], i: Int, arr: RArr[A]): Unit = ???
  }
}