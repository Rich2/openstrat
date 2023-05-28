/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

class HCenIntNArrLayer[A <: IntNElem, ArrT <: IntNArr[A]](val arrayArrayInt: Array[Array[Int]], val gridSys: HGridSys)(
  implicit intNArrMapBuilder: IntNArrMapBuilder[A, ArrT]) extends HCenArrLayer[A, ArrT]
{

}