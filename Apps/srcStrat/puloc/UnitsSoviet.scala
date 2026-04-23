/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pStrat._, pglobe._, pEarth._, pEurope._

abstract class SovArmiya(val startDate: TimeMin, val endDate: Option[TimeMin], val armeeNum: Int) extends LunitLocHist
{ override val polity: TimeMinSeries[Polity] = TimeMinSeries(Soviets)
  override def levelName: TimeMinSeries[String] = TimeMinSeries("Armiya")
  override def idStr: String = armeeNum.ordAbbr
  override def uniLevel: TimeMinSeries[LuUniLevel] = TimeMinSeries(FieldArmy)
}

object SovArmy3 extends SovArmiya(TimeMin(1939, 9, 15), Some(TimeMin(1939, 11, 5)), 3)
{ override def locPosns: TimeMinSeries[LatLong] = TimeMinSeries(Baltland.slutsk)
}