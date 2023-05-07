/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag

/** Optional [[HStepLike]] pair data layer  for Hex grid systems. */
class HCenOptStepLayer[A](val arrayInt: Array[Int], val arrayA: Array[A])(implicit val ct: ClassTag[A], val gridSys: HGridSys)
{
  def numCens: Int = arrayA.length
  def step(hc: HCen): HStepLike = HStepLike.fromInt(arrayInt(gridSys.layerArrayIndex(hc)))
  def index(hc: HCen): Int = gridSys.layerArrayIndex(hc)

  def setSome(hc: HCen, step: HStepLike, value: A): Unit =
  { arrayInt(index(hc)) = step.int1
    arrayA(index(hc)) = value
  }

  def mapAcc: HCenAccLayer[A] =
  {
    val acc = HCenAccLayer[A]()
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

object HCenOptStepLayer
{ /** Factory apply method for [[HCenOptStepLayer]]. */
  def apply[A](gSys: HGridSys)(implicit ct: ClassTag[A]): HCenOptStepLayer[A] = new HCenOptStepLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles))(ct, gSys)

  /** Factory apply method for [[HCenOptStepLayer]]. */
  def apply[A]()(implicit ct: ClassTag[A], gSys: HGridSys): HCenOptStepLayer[A] = new HCenOptStepLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles))
}