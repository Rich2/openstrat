/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom.*, pStrat.*, pglobe.*, pEarth.*, pEurope.*

abstract class PrussianKorpsNumbered(val startDate: TimeMin, val endDate: Option[TimeMin], val korpsNum: Int) extends LunitLocHist//(startDate, endDate)
{ override val polity: TimeMinSeries[Polity] = TimeMinSeries(Prussia,(TimeMin(1871, 1, 18), Deutch))
  override def idStr: String = korpsNum.ordAbbr
  override def levelName: TimeMinSeries[String] = TimeMinSeries("Korps")
  override def uniLevel: TimeMinSeries[LuUniLevel] = TimeMinSeries(Corps)
}

/** 1st Prussian later 1st German Corps. */
object PruCp1 extends PrussianKorpsNumbered( TimeMin(1820), Some(TimeMin(1919, 10, 1)), 1)
{ override def locPosns: TimeMinSeries[LatLong] = TimeMinSeries(Baltland.konigsberg)
}

/** 2nd Prussian later 2nd German Corps. */
object PruCp2 extends PrussianKorpsNumbered(TimeMin(1820, 4, 3), Some(TimeMin(1919, 10, 1)), 2)
{ override def locPosns: TimeMinSeries[LatLong] = TimeMinSeries(Germania.berlin.latLong)
}