/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pEarth._, pEurope._

object FraCp1 extends Lunit(MTime(1939, 8, 27), MTime(1945, 5, 8)) with CorpsNumbered
{ override val corpsNum: Int = 1
  //override val startDate: MTime =
  //override val endDate: MTime =
  override val polity: MTimeSeries[Polity] = MTimeSeries(France)
  override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Frankia.calais, (MTime(1939, 11, 15), Frankia.calais))
}

object FraCp2 extends Lunit(MTime(1939, 8, 23), MTime(1940, 5, 26)) with CorpsNumbered
{ override val corpsNum: Int = 2
  //override val startDate =
 // override val endDate: MTime =
  override val polity: MTimeSeries[Polity] = MTimeSeries(France)
  override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Frankia.calais)
}