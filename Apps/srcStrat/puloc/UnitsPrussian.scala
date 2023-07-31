/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pStrat._, pglobe._, pEarth._, pEurope._

abstract class PrussianKorpsNumbered(startDate: MTime, endDate: MTime, val korpsNum: Int) extends Lunit(startDate, endDate)
{ override val polity: MTimeSeries[Polity] = MTimeSeries(Prussia,(MTime(1871, 1, 18), Deutch))
  override def desig: String = korpsNum.ordAbbr
  override def levelName: String = "Korps"
  override def level: LunitLevel = Corps
}

/** 1st Prussian later 1st German Corps. */
object PruCp1 extends PrussianKorpsNumbered( MTime(1820),MTime(1919, 10, 1), 1)
{ override def locPosns: MTimeSeries[LatLong] = MTimeSeries(Baltland.konigsberg)
}

/** 2nd Prussian later 2nd German Corps. */
object PruCp2 extends PrussianKorpsNumbered(MTime(1820, 4, 3), MTime(1919, 10, 1), 2)
{ override def locPosns: MTimeSeries[LatLong] = MTimeSeries(Germania.berlin.latLong)
}