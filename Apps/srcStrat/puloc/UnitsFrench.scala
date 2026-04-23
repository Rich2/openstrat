/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom.*, pStrat.*, pglobe.*, pEarth.*, pEurope.*

abstract class FraNumberedCorps(val startDate: TimeMin, val endDate: Option[TimeMin], val corpsNum: Int) extends LunitLocHist
{ override def idStr: String = corpsNum.frenchOrdAbbr(true)
  override def levelName: TimeMinSeries[String] = TimeMinSeries("Corps")
  override val polity: TimeMinSeries[Polity] = TimeMinSeries(France)
  override def uniLevel: TimeMinSeries[LuUniLevel] = TimeMinSeries(Corps)
}

/** 1st French Corps WW2. */
object FraCp1 extends FraNumberedCorps(TimeMin(1939, 8, 27), Some(TimeMin(1945, 5, 8)), 1)
{ override val locPosns: TimeMinSeries[LatLong] = TimeMinSeries(FranceNorth.calais, (TimeMin(1939, 11, 15), FranceNorth.calais))
}

/** 2nd French Corps WW2. */
object FraCp2 extends FraNumberedCorps(TimeMin(1939, 8, 23), Some(TimeMin(1940, 5, 26)), 2)
{ override val locPosns: TimeMinSeries[LatLong] = TimeMinSeries(FranceNorth.calais)
}