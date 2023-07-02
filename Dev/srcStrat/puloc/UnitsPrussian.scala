/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pEarth._, pEurope._

/** 1st Prussian later 1st German Corps. */
object PruCp1 extends CorpsNumbered
{ override val corpsNum: Int = 1
  override val startDate: MTime = MTime(1820)
  override val endDate: MTime = MTime(1919, 10, 1)
  override val polity: MTimeSeries[Polity] = MTimeSeries(Prussia,(MTime(1871, 1, 18), Germany))
  override def locPosns: MTimeSeries[LatLong] = MTimeSeries(Baltland.konigsberg)
}

/** 2nd Prussian later 2nd German Corps. */
object PruCp2 extends CorpsNumbered
{ override val corpsNum: Int = 2
  override val startDate: MTime = MTime(1820, 4, 3)
  override val endDate: MTime = MTime(1919, 10, 1)
  override val polity: MTimeSeries[Polity] = MTimeSeries(Prussia,(MTime(1871, 1, 18), Germany))
  override def locPosns: MTimeSeries[LatLong] = MTimeSeries(Germania.berlin.latLong)
}