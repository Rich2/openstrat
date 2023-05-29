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
  implicit def intNBuilderEv[B <: IntNElem, ArrB <: IntNArr[B]](implicit intNArrMapBuilder: IntNArrMapBuilder[B, ArrB]):
  HCenArrLayerBuilder[B, ArrB, HCenIntNArrLayer[B, ArrB]] = new HCenArrLayerBuilder[B, ArrB, HCenIntNArrLayer[B, ArrB]]
  { val arrBuilder: IntNArrMapBuilder[B, ArrB] = intNArrMapBuilder
    override def uninitialised(gridSys: HGridSys): HCenIntNArrLayer[B, ArrB] =
      new HCenIntNArrLayer[B, ArrB](new Array[Array[Int]](gridSys.numTiles), gridSys)
    override def iSet(layer: HCenIntNArrLayer[B, ArrB], i: Int, arr: ArrB): Unit = layer.arrayArrayInt(i) = arr.unsafeArray
  }
}

trait HCenArrLayerLowPrioity
{
  implicit def RArrBuilderEv[B]: HCenArrLayerBuilder[B, RArr[B], HCenRArrLayer[B]] = new HCenArrLayerBuilder[B, RArr[B], HCenRArrLayer[B]]{
    override def uninitialised(gridSys: HGridSys): HCenRArrLayer[B] = ???

    override def iSet(layer: HCenRArrLayer[B], i: Int, arr: RArr[B]): Unit = ???
  }
}