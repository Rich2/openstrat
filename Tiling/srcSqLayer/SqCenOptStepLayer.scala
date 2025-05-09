/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import reflect.ClassTag

/** Optional [[SqStep]] layer for Square tile grids. */
class SqCenOptStepLayer[A](val arrayInt: Array[Int], val arrayA: Array[A], gridSysIn: SqGridSys)(using val ct: ClassTag[A])
{ given gridSys: SqGridSys = gridSysIn
  def numCens: Int = arrayA.length
  def step(hc: SqCen): SqStepLike = SqStepLike.fromInt(arrayInt(gridSys.layerArrayIndex(hc)))
  def index(hc: SqCen): Int = gridSys.layerArrayIndex(hc)

  def setSome(hc: SqCen, step: SqStepLike, value: A): Unit =
  { arrayInt(index(hc)) = step.int1
    arrayA(index(hc)) = value
  }

  /** Not full sure what to do with this method. */
  def mapAcc: SqCenAccLayer[SqStep] =
  {
    val acc = SqCenAccLayer[SqStep]()
    gridSys.foreach{origin =>
      val index = gridSys.layerArrayIndex(origin)
      val optA = arrayA(index)
      if (optA != null)
      { val optTarget: Option[SqCen] = gridSys.stepLikeEndFind(origin, step(origin))
        optTarget.foreach{ target => acc.append(target, origin, SqStep.fromInt(arrayInt(index))) }
      }
    }
    acc
  }
}

/** Companion object for [[SqCenOptStepLayer]] trait contains factory apply methods. */
object SqCenOptStepLayer
{ def apply[A](gSys: SqGridSys)(using ctA: ClassTag[A]): SqCenOptStepLayer[A] =
    new SqCenOptStepLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles), gSys)

  def apply[A]()(implicit ct: ClassTag[A], gSys: SqGridSys): SqCenOptStepLayer[A] =
    new SqCenOptStepLayer[A](new Array[Int](gSys.numTiles), new Array[A](gSys.numTiles), gSys)
}