/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pEarth._

class LocStart(val startDate: Date, val oLocation: Option[LatLong] = None)

object LocStart{
  def data(startDate: Date): LocStart = new LocStart(startDate)

  def apply(latLong: LatLong, year: Int, month: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0): LocStart =
  { val date = Date(year, month, day: Int, hour: Int, minute: Int, second: Int)
    new LocStart(date, Some(latLong))
  }

  def date(year: Int, month: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0): LocStart ={
    val date = Date(year, month, day: Int, hour: Int, minute: Int, second: Int)
    new LocStart(date)
  }
}
case class LocPeriod(location: LatLong, stDate: Date, endDate: Date)

trait Lunit extends Coloured
{ /** The nation / state to which this unit belongs.  */
  def nation: Nation

  /** The end date for this incarnation of this Unit identity. */
  def endDate: Date

  def locStarts: RArr[LocStart]

  override def colour: Colour = nation.colour

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
  override val locStarts: RArr[LocStart] = RArr(LocStart(pEurope.Baltland.konigsberg, 1934, 10), LocStart.date(1939, 9, 2))
  override val endDate: Date = Date(1945, 5, 8)
}

object DeuCp2 extends CorpsNumbered
{ override val nation: Nation = Germany
  override val corpsNum: Int = 2
  override val locStarts: RArr[LocStart] = RArr(LocStart.date(1935, 4))
  override val endDate: Date = Date(1945, 5, 8)
}

object FraCp1 extends CorpsNumbered
{ override val nation: Nation = France
  override val corpsNum: Int = 1
  override val locStarts: RArr[LocStart] = RArr(LocStart(pEurope.Frankia.calais, 1939, 8, 27), LocStart.date(1939, 11, 15))
  override val endDate: Date = Date(1945, 5, 8)
}

object FraCp2 extends CorpsNumbered
{ override val nation: Nation = France
  override val corpsNum: Int = 2
  override val locStarts: RArr[LocStart] = RArr(LocStart.date(1939, 8, 23))
  override val endDate: Date = Date(1940, 5, 26)
}