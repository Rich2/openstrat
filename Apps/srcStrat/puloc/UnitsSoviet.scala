/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pStrat._, pglobe._, pEarth._, pEurope._

abstract class SovArmiya(val startDate: MTime, val endDate: MTime, val armeeNum: Int) extends LunitLocHist//(startDate, endDate)
{ override val polity: MTimeSeries[Polity] = MTimeSeries(Soviets)
  override def levelName: String = "Armiya"
  override def desig: String = armeeNum.ordAbbr
  override def uniLevel: LuUniLevel = FieldArmy
}

object SovArmy3 extends SovArmiya(MTime(1939, 9, 15), MTime(1939, 11, 5), 3)
{ override def locPosns: MTimeSeries[LatLong] = MTimeSeries(Baltland.slutsk)
}