/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import reflect.ClassTag

/** Optional layer for Square tile grids. */
class SqCenOptStepLayer[A](val arrayInt: Array[Int], val arrayA: Array[A])(implicit val ct: ClassTag[A], val gridSys: SqGridSys)
{
  def numCens: Int = arrayA.length
  def step(hc: SqCen): SqStepLike = SqStepLike.fromInt(arrayInt(gridSys.layerArrayIndex(hc)))
  def index(hc: SqCen): Int = gridSys.layerArrayIndex(hc)

  def setSome(hc: SqCen, step: SqStepLike, value: A): Unit =
  { arrayInt(index(hc)) = step.int1
    arrayA(index(hc)) = value
  }

  /*def mapAcc: SqCenAccLayer[A] =
  {
    val acc = SqCenAccLayer[A]()
    gridSys.foreach{origin =>
      val index = gridSys.layerArrayIndex(origin)
      val optA = arrayA(index)
      if (optA != null)
      { val optTarget: Option[SqCen] = gridSys.stepOptEndFind(origin, step(origin))
        optTarget.foreach{ target => acc.append(target, origin, arrayA(index)) }
      }
    }
    acc
  }*/
}

object SqCenOptStepLayer
{
  def apply[A](gSys: SqGridSys)(implicit ct: ClassTag[A]): SqCenOptStepLayer[A] = new SqCenOptStepLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles))(ct, gSys)
  def apply[A]()(implicit ct: ClassTag[A], gSys: SqGridSys): SqCenOptStepLayer[A] = new SqCenOptStepLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles))
}
