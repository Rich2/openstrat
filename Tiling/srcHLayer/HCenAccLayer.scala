/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag, collection.mutable.ArrayBuffer


class HCenAccLayer[A](val originsBuffer: Array[ArrayBuffer[Int]], val actionsBuffer: Array[ArrayBuffer[A]], gSysIn: HGridSys)(implicit val ct: ClassTag[A])
{
  implicit val gSys: HGridSys = gSysIn
  def index(hc: HCen): Int = gSys.layerArrayIndex(hc)

  def append(target: HCen, origin: HCen, action: A): Unit =
  { originsBuffer(index(target)).append(origin.r)
    originsBuffer(index(target)).append(origin.c)
    actionsBuffer(index(target)).append(action)
  }

  def originActions(target: HCen): HCenPairArr[A] =
  { val i = index(target)
    val hCens = originsBuffer(i).toArray
    val actions: Array[A] = actionsBuffer(i).toArray
    new HCenPairArr[A](hCens, actions)
  }

  def foreach(f: (HCen, HCenPairArr[A]) => Unit): Unit = gSys.foreach{hc => f(hc, originActions(hc)) }
}

object HCenAccLayer
{
  def apply[A]()(implicit ct: ClassTag[A], gSys: HGridSys): HCenAccLayer[A] = apply[A](gSys)(ct)

  def apply[A](gSys: HGridSys)(implicit ct: ClassTag[A]): HCenAccLayer[A] =
  { val numCens: Int = gSys.numTiles
    val origBuff = new Array[ArrayBuffer[Int]](numCens)
    val actionBuff = new Array[ArrayBuffer[A]](numCens)
    iUntilForeach(numCens){ i =>
      origBuff(i) = new ArrayBuffer[Int](0)
      actionBuff(i) = new ArrayBuffer[A](0)
    }
    new HCenAccLayer[A](origBuff, actionBuff, gSys)
  }
}