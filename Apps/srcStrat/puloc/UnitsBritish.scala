/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom.*, pStrat.*, pglobe.*, pEarth.*, pEurope.*

abstract class BritArmy(val startDate: TimeMin, val endDate: Option[TimeMin], val armyNum: Int) extends LunitLocHist
{ override val polity: TimeMinSeries[Polity] = TimeMinSeries(Deutch)
  override def uniLevel: TimeMinSeries[LuUniLevel] = TimeMinSeries(FieldArmy)
  override def levelName: TimeMinSeries[String] = TimeMinSeries("Army")
  override def idStr: String = armyNum.ordAbbr
}

object BritArmy2 extends BritArmy(TimeMin(1944, 6), None, 2)
{ override def locPosns: TimeMinSeries[LatLong] = TimeMinSeries(FranceNorth.caen.latLong)
  //override val subUnits: MTimeSeries[RArr[LunitLocHist]] = MTimeSeries(RArr(DeuKorps26))
}