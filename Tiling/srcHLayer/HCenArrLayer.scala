/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag

/** A data layer for an [[HGridSys]] where each tile's data is an [[Arr]] of the specified type. */
trait HCenArrLayer[A, ArrA <: Arr[A]]
{
  def gridSys: HGridSys
  def numTiles: Int = gridSys.numTiles

  /** Maps from this [[HCenArrLayer]] to an [[HCenArrLayer]] type ArrB. */
  def map[B, ArrB <: Arr[B], LayerT <: HCenArrLayer[B, ArrB]](f: ArrA => ArrB)(implicit builder: HCenArrLayerBuilder[B, ArrB,LayerT]): LayerT
}


object HCenArrLayer extends HCenArrLayerLowPrioity
{
  implicit def intNBuilderEv[B <: IntNElem, ArrB <: IntNArr[B]](implicit intNArrMapBuilder: IntNArrMapBuilder[B, ArrB]):
  HCenArrLayerBuilder[B, ArrB, HCenIntNArrLayer[B, ArrB]] = new HCenArrLayerBuilder[B, ArrB, HCenIntNArrLayer[B, ArrB]]
  { override val arrBBuild: IntNArrMapBuilder[B, ArrB] = intNArrMapBuilder
    override def uninitialised(gridSys: HGridSys): HCenIntNArrLayer[B, ArrB] =
      new HCenIntNArrLayer[B, ArrB](new Array[Array[Int]](gridSys.numTiles), gridSys)
    override def iSet(layer: HCenIntNArrLayer[B, ArrB], i: Int, arr: ArrB): Unit = layer.arrayArrayInt(i) = arr.unsafeArray
  }
}

trait HCenArrLayerLowPrioity
{
  implicit def RArrBuilderEv[B](implicit ct: ClassTag[B]): HCenArrLayerBuilder[B, RArr[B], HCenRArrLayer[B]] = new HCenArrLayerBuilder[B, RArr[B], HCenRArrLayer[B]]
  { override val arrBBuild: ArrMapBuilder[B, RArr[B]] = ArrMapBuilder.rMapImplicit
    override def uninitialised(gridSys: HGridSys): HCenRArrLayer[B] = ???
    override def iSet(layer: HCenRArrLayer[B], i: Int, arr: RArr[B]): Unit = ???
  }
}

trait HCenArrLayerBuilder[B, ArrB <: Arr[B], LayerB <: HCenArrLayer[B, ArrB]]
{ def arrBBuild: ArrMapBuilder[B, ArrB]
  def uninitialised(gridSys: HGridSys): LayerB
  def iSet(layer: LayerB, i: Int, arr: ArrB): Unit
}