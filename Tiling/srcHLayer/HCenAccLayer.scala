/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import collection.mutable.ArrayBuffer

/** This is a helper class for turn / segment resolution. It accumulates all actions along with the origin of the action, upon a hex tile. */
class HCenAccLayer(val originsBuffer: Array[ArrayBuffer[Int]], gSysIn: HGridSys)
{
  implicit val gSys: HGridSys = gSysIn
  def index(hc: HCen): Int = gSys.layerArrayIndex(hc)

  /** Appends an origin-action pair to this given target hex's accumulator. */
  def append(target: HCen, origin: HCen): Unit =
  { originsBuffer(index(target)).append(origin.r)
    originsBuffer(index(target)).append(origin.c)
  }

  /** This produces the collection of origin-action pairs upon the given hex tile. */
  def originActions(target: HCen): HCenArr =
  { val i = index(target)
    val array = originsBuffer(i).toArray
    new HCenArr(array)
  }

  /** Side effecting function for initialising new game state layers. */
  def foreach(f: (HCen, HCenArr) => Unit): Unit = gSys.foreach{hc => f(hc, originActions(hc)) }
}

object HCenAccLayer
{
  def apply(gSys: HGridSys): HCenAccLayer =
  { val numCens: Int = gSys.numTiles
    val origBuff = new Array[ArrayBuffer[Int]](numCens)
    iUntilForeach(numCens){ i =>
      origBuff(i) = new ArrayBuffer[Int](0)
    }
    new HCenAccLayer(origBuff, gSys)
  }
}
