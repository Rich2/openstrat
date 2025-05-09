/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Optional [[HStep]] data layer  for Hex grid systems. */
class HCenOptStepLayer(val gridSys: HGridSys, val arrayInt: Array[Int])
{ def numCens: Int = arrayInt.length
  def step(hc: HCen): HStep = HStep.fromInt(arrayInt(gridSys.layerArrayIndex(hc)))
  def index(hc: HCen): Int = gridSys.layerArrayIndex(hc)

  def setSome(hc: HCen, step: HStepLike): Unit = arrayInt(index(hc)) = step.int1

  /** Needs changing. */
  def mapAcc: HCenAccLayer =
  {
    val acc = HCenAccLayer(gridSys)
    gridSys.foreach{origin =>
      val index = gridSys.layerArrayIndex(origin)
      val optA = arrayInt(index)
      if (optA != 0)
      { val optTarget: Option[HCen] = gridSys.stepLikeEndFind(origin, step(origin))
        optTarget.foreach{ target => acc.append(target, origin) }
      }
    }
    acc
  }
}

object HCenOptStepLayer
{ /** Factory apply method for [[HCenOptStepLikePairLayer]]. */
  def apply()(using gSys: HGridSys): HCenOptStepLayer = new HCenOptStepLayer(gSys, new Array[Int](gSys.numTiles))
}
