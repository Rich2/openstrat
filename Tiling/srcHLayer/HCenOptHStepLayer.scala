/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag

class HCenOptHStepLayer[A](val arrayInt: Array[Int], val arrayA: Array[A])(implicit val ct: ClassTag[A], val gSys: HGridSys)
{
  def numCens: Int = arrayA.length
  def step(hc: HCen)(implicit gSys: HGridSys): HStepOpt = HStepOpt.fromInt(arrayInt(gSys.layerArrayIndex(hc)))
  def index(hc: HCen): Int = gSys.layerArrayIndex(hc)

  def setSome(hc: HCen, step: HStepOpt, value: A): Unit =
  { arrayInt(index(hc)) = step.int1
    arrayA(index(hc)) = value
  }

  def mapAcc: HCenAccLayer[A] =
  {
    val acc = HCenAccLayer[A]()
    gSys.foreach{origin =>
      val index = gSys.layerArrayIndex(origin)
      val optA = arrayA(index)
      if (optA != null)
      { val optTarget: Option[HCen] = gSys.findOptStepEnd(origin, step(origin))
        optTarget.foreach{ target => acc.append(target, origin, arrayA(index)) }
      }
    }
    acc
  }
}

object HCenOptHStepLayer
{
  def apply[A](gSys: HGridSys)(implicit ct: ClassTag[A]): HCenOptHStepLayer[A] = new HCenOptHStepLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles))(ct, gSys)
  def apply[A]()(implicit ct: ClassTag[A], gSys: HGridSys): HCenOptHStepLayer[A] = new HCenOptHStepLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles))
}