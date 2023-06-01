/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

class HCenIntNArrLayer[A <: IntNElem, ArrA <: IntNArr[A]](val outerArrayUnsafe: Array[Array[Int]], val gridSys: HGridSys)(
implicit val arrBuilder: IntNArrMapBuilder[A, ArrA]) extends HCenArrLayer[A, ArrA]
{
  override def foreach(f: ArrA => Unit): Unit =
  { var i = 0
    outerArrayUnsafe.foreach { array =>
      f(arrBuilder.fromIntArray(array))
      i += 1
    }
  }

  override def iForeach(f: (Int, ArrA) => Unit): Unit =
  { var i = 0
    outerArrayUnsafe.foreach { array =>
      f(i, arrBuilder.fromIntArray(array))
      i += 1
    }
  }
}