/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom.*, pStrat.*, pglobe.*, pEarth.*, pEurope.*

abstract class BritArmy(val startDate: MTime, val endDate: Option[MTime], val armyNum: Int) extends LunitLocHist
{ override val polity: MTimeSeries[Polity] = MTimeSeries(Deutch)
  override def uniLevel: MTimeSeries[LuUniLevel] = MTimeSeries(FieldArmy)
  override def levelName: MTimeSeries[String] = MTimeSeries("Army")
  override def idStr: String = armyNum.ordAbbr
}

object BritArmy2 extends BritArmy(MTime(1944, 6), None, 2)
{ override def locPosns: MTimeSeries[LatLong] = MTimeSeries(FranceNorth.caen.latLong)
  //override val subUnits: MTimeSeries[RArr[LunitLocHist]] = MTimeSeries(RArr(DeuKorps26))
}