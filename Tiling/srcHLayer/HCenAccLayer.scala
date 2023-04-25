/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag, collection.mutable.ArrayBuffer

class HCenOptHStepLayer[A](val arrayInt: Array[Int], val arrayA: Array[A])
{
  def numCens: Int = arrayA.length
  def step(hc: HCen)(implicit gSys: HGridSys): HStepOpt = HStepOpt.fromInt(arrayInt(gSys.layerArrayIndex(hc)))

  def mapAcc(implicit gSys: HGridSys): Unit =
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
  }
}

class HCenAccLayer[A](val origins: Array[ArrayBuffer[Int]], val actions: Array[ArrayBuffer[A]], gSysIn: HGridSys)
{
  implicit val gSys: HGridSys = gSysIn
  def index(hc: HCen): Int = gSys.layerArrayIndex(hc)

  def append(target: HCen, origin: HCen, action: A): Unit =
  { origins(index(target)).append(origin.r)
    origins(index(target)).append(origin.c)
    actions(index(target)).append(action)
  }
}

object HCenAccLayer
{
  def apply[A]()(implicit gSys: HGridSys): HCenAccLayer[A] =
  {
    val numCens: Int = gSys.numTiles
    val origBuff = new Array[ArrayBuffer[Int]](numCens)
    val actionBuff = new Array[ArrayBuffer[A]](numCens)
    iUntilForeach(numCens){ i =>
      origBuff(i) = new ArrayBuffer[Int](0)
      actionBuff(i) = new ArrayBuffer[A](0)
    }
    new HCenAccLayer[A](origBuff, actionBuff, gSys)
  }
}