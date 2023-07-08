/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pEarth._, pEurope._

abstract class FraNumberedCorps(startDate: MTime, endDate: MTime, val corpsNum: Int) extends Lunit(startDate, endDate)
{ override def desig: String = corpsNum.frenchOrdAbbr(true)
  override def levelName: String = "Corps"
  override val polity: MTimeSeries[Polity] = MTimeSeries(France)
}

/** 1st French Corps WW2. */
object FraCp1 extends FraNumberedCorps(MTime(1939, 8, 27), MTime(1945, 5, 8), 1)
{ override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Frankia.calais, (MTime(1939, 11, 15), Frankia.calais))
}

/** 2nd French Corps WW2. */
object FraCp2 extends FraNumberedCorps(MTime(1939, 8, 23), MTime(1940, 5, 26), 2)
{ override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Frankia.calais)
}