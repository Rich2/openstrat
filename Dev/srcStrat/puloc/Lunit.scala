/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._

trait Lunit extends Coloured
{ /** The nation / state to which this unit belongs. */
  def polity: Polity

  /** The date this unt comes into existence. */
  val startDate: MTime

  /** The end date for this incarnation of this Unit identity. */
  val endDate: MTime

  /** An implicit value for the start and end of the unit to be used in building time series.  */
  implicit def startEnd: MTime2 = new MTime2(startDate.int1, endDate.int1)

  override def colour: Colour = polity.colour

  /** Optional location of the unit throughout its existence. */
  def locPosns: MTimeSeries[LatLongOpt]

  /** Finds all the [[Lunit]]s that have a defined location at the given time. */
  def locationFind(date: MTime): Option[(Lunit, LatLong)] = locPosns.find(date).flatMap(_.map(ll => (this, ll)))

  def dateFind(date: MTime): Option[UnitState] = locPosns.find(date).map(oll => UnitState(polity, oll))
}

case class UnitState( polity: Polity, oLoc: LatLongOpt) extends Coloured
{
  override def colour: Colour = polity.colour
}

trait CorpsNumbered extends Lunit
{ /** The number of the Corps 1st, 2nd 3rd, etc. */
  def corpsNum: Int

  override def toString: String = polity.name -- corpsNum.adjective -- "Corps"
}