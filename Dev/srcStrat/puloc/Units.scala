/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pEarth._

case class LocStart(oLocation: Option[LatLong], startDate: Date)
case class LocPeriod(location: LatLong, stDate: Date, endDate: Date)

trait Lunit
{
  def nation: Nation

  def endDate: Date

  def locStarts: RArr[LocStart]

  def startDate: Date = locStarts(0).startDate

  def locationFind(date: Date): Option[(Lunit, LatLong)] = if (date < startDate | date > endDate ) None else
  {
    def loop(i : Int): Option[(Lunit, LatLong)] = i match
    { case i if i == locStarts.length - 1 => locStarts(i).oLocation.map(ll => (this, ll))
      case i if date < locStarts(i + 1).startDate => locStarts(i).oLocation.map(ll => (this, ll))
      case i => loop(i + 1)
    }
    loop(0)
  }
}

trait CorpsNumbered extends Lunit
{
  def corpsNum: Int

  override def toString: String = nation.name -- corpsNum.adjective -- "Corps"
}

object DeuCp1 extends CorpsNumbered
{ override val nation: Nation = Germany
  override val corpsNum: Int = 1
  override val locStarts: RArr[LocStart] = RArr(LocStart(Some(pEurope.Baltland.konigsberg), Date(1934, 10)), LocStart(None, Date(1939, 9, 2)))
  override val endDate: Date = Date(1945, 5, 8)
}

object DeuCp2 extends CorpsNumbered
{ override val nation: Nation = Germany
  override val corpsNum: Int = 2
  override val locStarts: RArr[LocStart] = RArr(LocStart(None, Date(1935, 4)))
  override val endDate: Date = Date(1945, 5, 8)
}

object FraCp1 extends CorpsNumbered
{ override val nation: Nation = France
  override val corpsNum: Int = 1
  override val locStarts: RArr[LocStart] = RArr(LocStart(Some(pEurope.Frankia.calais), Date(1939, 8, 27)), LocStart(None, Date(1939, 11, 15)))
  override val endDate: Date = Date(1945, 5, 8)
}