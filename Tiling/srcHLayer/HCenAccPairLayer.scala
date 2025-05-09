/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag, collection.mutable.ArrayBuffer

/** This is a helper class for turn / segment resolution. It accumulates all actions along with the origin of the action, upon a hex tile. */
class HCenAccPairLayer[A](val originsBuffer: Array[ArrayBuffer[Int]], val actionsBuffer: Array[ArrayBuffer[A]], gSysIn: HGridSys)(using
  val ctA: ClassTag[A])
{
  implicit val gSys: HGridSys = gSysIn
  def index(hc: HCen): Int = gSys.layerArrayIndex(hc)

  /** Appends an origin-action pair to this given target hex's accumulator. */
  def append(target: HCen, origin: HCen, action: A): Unit =
  { originsBuffer(index(target)).append(origin.r)
    originsBuffer(index(target)).append(origin.c)
    actionsBuffer(index(target)).append(action)
  }

  /** This produces the collection of origin-action pairs upon the given hex tile. */
  def originActions(target: HCen): HCenPairArr[A] =
  { val i = index(target)
    val hCens = originsBuffer(i).toArray
    val actions: Array[A] = actionsBuffer(i).toArray
    new HCenPairArr[A](hCens, actions)
  }

  /** Side effecting function for initialising new game state layers. */
  def foreach(f: (HCen, HCenPairArr[A]) => Unit): Unit = gSys.foreach{hc => f(hc, originActions(hc)) }
}

object HCenAccPairLayer
{
  def apply[A]()(using ctA: ClassTag[A], gSys: HGridSys): HCenAccPairLayer[A] = apply[A](gSys)

  def apply[A](gSys: HGridSys)(using ctA: ClassTag[A]): HCenAccPairLayer[A] =
  { val numCens: Int = gSys.numTiles
    val origBuff = new Array[ArrayBuffer[Int]](numCens)
    val actionBuff = new Array[ArrayBuffer[A]](numCens)
    iUntilForeach(numCens){ i =>
      origBuff(i) = new ArrayBuffer[Int](0)
      actionBuff(i) = new ArrayBuffer[A](0)
    }
    new HCenAccPairLayer[A](origBuff, actionBuff, gSys)
  }
}