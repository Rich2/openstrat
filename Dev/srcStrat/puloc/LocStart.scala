/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._

class LocStart(val startDate: MTime, val oLocation: Option[LatLong] = None)

object LocStart
{
  def apply(latLong: LatLong, year: Int, month: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0): LocStart =
  { val date = MTime(year, month, day: Int, hour: Int, minute: Int, second: Int)
    new LocStart(date, Some(latLong))
  }

  def date(year: Int, month: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0): LocStart ={
    val date = MTime(year, month, day: Int, hour: Int, minute: Int, second: Int)
    new LocStart(date)
  }
}

case class LocPeriod(location: LatLong, stDate: MTime, endDate: MTime)