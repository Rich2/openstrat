/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pEarth._

trait Lunit extends Coloured
{ /** The nation / state to which this unit belongs.  */
  def polity: Polity

  val startDate: MTime

  /** The end date for this incarnation of this Unit identity. */
  val endDate: MTime

  implicit def startEnd: MTime2 = new MTime2(startDate.int1, endDate.int1)

  def locStarts: RArr[LocStart]

  override def colour: Colour = polity.colour

  def locationFind(date: MTime): Option[(Lunit, LatLong)] = if (date < startDate | date > endDate ) None else
  {
    def loop(i : Int): Option[(Lunit, LatLong)] = i match
    { case i if i == locStarts.length - 1 => locStarts(i).oLocation.map(ll => (this, ll))
      case i if date < locStarts(i + 1).startDate => locStarts(i).oLocation.map(ll => (this, ll))
      case i => loop(i + 1)
    }
    loop(0)
  }

  def locPosns: MTimeSeries[LatLongOpt]
}

trait CorpsNumbered extends Lunit
{
  def corpsNum: Int

  override def toString: String = polity.name -- corpsNum.adjective -- "Corps"
}