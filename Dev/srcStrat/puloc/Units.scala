/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pEarth._

class LocStart(val startDate: MTime, val oLocation: Option[LatLong] = None)

object LocStart{
  def data(startDate: MTime): LocStart = new LocStart(startDate)

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

trait Lunit extends Coloured
{ /** The nation / state to which this unit belongs.  */
  def nation: Polity

  /** The end date for this incarnation of this Unit identity. */
  def endDate: MTime

  def locStarts: RArr[LocStart]

  override def colour: Colour = nation.colour

  def startDate: MTime = locStarts(0).startDate

  def locationFind(date: MTime): Option[(Lunit, LatLong)] = if (date < startDate | date > endDate ) None else
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

object PruCp1 extends CorpsNumbered
{ override def corpsNum: Int = 1
  override def nation: Polity = Prussia
  override def endDate: MTime = MTime(1871, 1, 18)
  override def locStarts: RArr[LocStart] = RArr(LocStart.date(1820))
}

object DeuCp1 extends CorpsNumbered
{ override val nation: Polity = Germany
  override val corpsNum: Int = 1
  override val locStarts: RArr[LocStart] = RArr(LocStart(pEurope.Baltland.konigsberg, 1934, 10), LocStart.date(1939, 9, 2))
  override val endDate: MTime = MTime(1945, 5, 8)
}

object DeuCp2 extends CorpsNumbered
{ override val nation: Polity = Germany
  override val corpsNum: Int = 2
  override val locStarts: RArr[LocStart] = RArr(LocStart.date(1935, 4))
  override val endDate: MTime = MTime(1945, 5, 8)
}

object FraCp1 extends CorpsNumbered
{ override val nation: Polity = France
  override val corpsNum: Int = 1
  override val locStarts: RArr[LocStart] = RArr(LocStart(pEurope.Frankia.calais, 1939, 8, 27), LocStart.date(1939, 11, 15))
  override val endDate: MTime = MTime(1945, 5, 8)
}

object FraCp2 extends CorpsNumbered
{ override val nation: Polity = France
  override val corpsNum: Int = 2
  override val locStarts: RArr[LocStart] = RArr(LocStart.date(1939, 8, 23))
  override val endDate: MTime = MTime(1940, 5, 26)
}