/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._

trait Nation
object Germany extends Nation
object France extends Nation

case class Date(year: Int, month: Int = 1, day: Int = 1)
case class LocEnd(location: LatLong, endDate: Date)
case class LocPeriod(location: LatLong, stDate: Date, endDate: Date)

trait Lunit
{
  def nation: Nation

  def startDate: Date
}

object DeuCp1 extends Lunit
{
  override val nation: Nation = Germany

  override val startDate: Date = Date(1934, 10)
}

object DeuCp2 extends Lunit
{
  override def nation: Nation = Germany
  override val startDate: Date = Date(1935, 4)
}
