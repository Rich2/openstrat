/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag, collection.mutable.ArrayBuffer

class HCenOptHStepLayer[A](val arrayInt: Array[Int], val arrayA: Array[A])
{
  def numCens: Int = arrayA.length
  def step(hc: HCen)(implicit gSys: HGridSys): HStepOpt = HStepOpt.fromInt(arrayInt(gSys.layerArrayIndex(hc)))

  def mapAcc(implicit gSys: HGridSys): Unit =
  {
    val origBuff = new Array[ArrayBuffer[Int]](numCens * 2)
    val actionBuff = new Array[ArrayBuffer[A]](numCens)
    gSys.foreach{hc =>
      val optA = arrayA(gSys.layerArrayIndex(hc))
      if (optA != null) {
        val oEnd: Option[HCen] = gSys.findOptStepEnd(hc, step(hc))
      }
    }
  }
}

class HCenAccLayer[A](val origins: Array[ArrayBuffer[Int]], val actions: Array[ArrayBuffer[A]], gSysIn: HGridSys)
{
  implicit val gSys: HGridSys = gSysIn
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