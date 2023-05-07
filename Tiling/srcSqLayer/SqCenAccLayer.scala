/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import reflect.ClassTag, collection.mutable.ArrayBuffer

/** This is a helper class for turn / segment resolution. It accumulates all actions along with the origin of the action, upon a hex tile. */
class SqCenAccLayer[A](val originsBuffer: Array[ArrayBuffer[Int]], val actionsBuffer: Array[ArrayBuffer[A]], gSysIn: SqGridSys)(implicit
  val ct: ClassTag[A])
{
  implicit val gSys: SqGridSys = gSysIn
  def index(hc: SqCen): Int = gSys.layerArrayIndex(hc)

  /** Appends an origin-action pair to this given target hex's accumulator. */
  def append(target: SqCen, origin: SqCen, action: A): Unit =
  { originsBuffer(index(target)).append(origin.r)
    originsBuffer(index(target)).append(origin.c)
    actionsBuffer(index(target)).append(action)
  }

  /** This produces the collection of origin-action pairs upon the given hex tile. */
  def originActions(target: SqCen): SqCenPairArr[A] =
  { val i = index(target)
    val SqCens = originsBuffer(i).toArray
    val actions: Array[A] = actionsBuffer(i).toArray
    new SqCenPairArr[A](SqCens, actions)
  }

  /** Side effecting function for initalising new game state layers. */
  def foreach(f: (SqCen, SqCenPairArr[A]) => Unit): Unit = gSys.foreach{hc => f(hc, originActions(hc)) }
}

object SqCenAccLayer
{
  def apply[A]()(implicit ct: ClassTag[A], gSys: SqGridSys): SqCenAccLayer[A] = apply[A](gSys)(ct)

  def apply[A](gSys: SqGridSys)(implicit ct: ClassTag[A]): SqCenAccLayer[A] =
  { val numCens: Int = gSys.numTiles
    val origBuff = new Array[ArrayBuffer[Int]](numCens)
    val actionBuff = new Array[ArrayBuffer[A]](numCens)
    iUntilForeach(numCens){ i =>
      origBuff(i) = new ArrayBuffer[Int](0)
      actionBuff(i) = new ArrayBuffer[A](0)
    }
    new SqCenAccLayer[A](origBuff, actionBuff, gSys)
  }
}
