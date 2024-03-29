/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pStrat._, pglobe._, pEarth._, pEurope._

abstract class SovArmiya(val startDate: MTime, val endDate: Option[MTime], val armeeNum: Int) extends LunitLocHist
{ override val polity: MTimeSeries[Polity] = MTimeSeries(Soviets)
  override def levelName: MTimeSeries[String] = MTimeSeries("Armiya")
  override def idStr: String = armeeNum.ordAbbr
  override def uniLevel: MTimeSeries[LuUniLevel] = MTimeSeries(FieldArmy)
}

object SovArmy3 extends SovArmiya(MTime(1939, 9, 15), Some(MTime(1939, 11, 5)), 3)
{ override def locPosns: MTimeSeries[LatLong] = MTimeSeries(Baltland.slutsk)
}