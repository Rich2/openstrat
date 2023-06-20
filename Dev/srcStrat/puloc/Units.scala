/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pEarth._

trait Nation
object Germany extends Nation
object France extends Nation

case class Date(year: Int, month: Int = 1, day: Int = 1)
case class LocStart(oLocation: Option[LatLong], startDate: Date)
case class LocPeriod(location: LatLong, stDate: Date, endDate: Date)

trait Lunit
{
  def nation: Nation

  def endDate: Date// = Date(1945, 5, 8)

  def locStarts: RArr[LocStart]

  def startDate: Date = locStarts(0).startDate
}

object DeuCp1 extends Lunit
{
  override val nation: Nation = Germany

  override def locStarts: RArr[LocStart] = RArr(LocStart(Some(pEurope.Baltland.konigsberg), Date(1934, 10)), LocStart(None, Date(1939, 9)))

  override def endDate: Date = Date(1945, 5, 8)
}

object DeuCp2 extends Lunit
{
  override def nation: Nation = Germany
  override def locStarts: RArr[LocStart] = RArr(LocStart(None, Date(1935, 4)))
  override def endDate: Date = Date(1945, 5, 8)
}

object FraCp1 extends Lunit
{
  override def nation: Nation = France

  override def locStarts: RArr[LocStart] = RArr(LocStart(Some(pEurope.Frankia.calais), Date(1939, 8, 27)), LocStart(None, Date(1939, 11, 15)))
  override def endDate: Date = Date(1945, 5, 8)
}