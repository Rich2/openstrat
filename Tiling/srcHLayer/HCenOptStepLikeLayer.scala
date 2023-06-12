/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Optional [[HStepLike]] data layer  for Hex grid systems. */
class HCenOptStepLikeLayer(val arrayInt: Array[Int])(implicit val gridSys: HGridSys)
{
  def numCens: Int = arrayInt.length
  def step(hc: HCen): HStepLike = HStepLike.fromInt(arrayInt(gridSys.layerArrayIndex(hc)))
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

object HCenOptStepLikeLayer
{ /** Factory apply method for [[HCenOptStepLikePairLayer]]. */
  def apply(gSys: HGridSys): HCenOptStepLikeLayer = new HCenOptStepLikeLayer(new Array[Int](gSys.numTiles))(gSys)

  /** Factory apply method for [[HCenOptStepLikePairLayer]]. */
//  def apply()(implicit gSys: HGridSys): HCenOptStepLikeLayer = new HCenOptStepLikeLayer(new Array[Int](gSys.numTiles))
}
