/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag

/** Optional [[HStepLike]] pair data layer for Hex grid systems. */
class HCenOptStepLikePairLayer[A](val arrayInt: Array[Int], val arrayA: Array[A])(implicit val ct: ClassTag[A], val gridSys: HGridSys)
{
  def numCens: Int = arrayA.length
  def step(hc: HCen): HStepLike = HStepLike.fromInt(arrayInt(gridSys.layerArrayIndex(hc)))
  def index(hc: HCen): Int = gridSys.layerArrayIndex(hc)

  def setSome(hc: HCen, step: HStepLike, value: A): Unit =
  { arrayInt(index(hc)) = step.int1
    arrayA(index(hc)) = value
  }

  /** Needs changing. */
  def mapAcc: HCenAccPairLayer[A] =
  {
    val acc = HCenAccPairLayer[A]()
    gridSys.foreach{origin =>
      val index = gridSys.layerArrayIndex(origin)
      val optA = arrayA(index)
      if (optA != null)
      { val optTarget: Option[HCen] = gridSys.stepLikeEndFind(origin, step(origin))
        optTarget.foreach{ target => acc.append(target, origin, arrayA(index)) }
      }
    }
    acc
  }
}

object HCenOptStepLikePairLayer
{ /** Factory apply method for [[HCenOptStepLikePairLayer]], an optional [[HStepLike]] pair data layer for Hex grid systems. */
  def apply[A](gSys: HGridSys)(implicit ct: ClassTag[A]): HCenOptStepLikePairLayer[A] = new HCenOptStepLikePairLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles))(ct, gSys)

  /** Factory apply method for [[HCenOptStepLikePairLayer]]. */
  def apply[A]()(implicit ct: ClassTag[A], gSys: HGridSys): HCenOptStepLikePairLayer[A] = new HCenOptStepLikePairLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles))
}