/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._

trait Lunit extends Coloured
{ /** The nation / state to which this unit belongs. */
  def polity: Polity

  val startDate: MTime

  /** The end date for this incarnation of this Unit identity. */
  val endDate: MTime

  implicit def startEnd: MTime2 = new MTime2(startDate.int1, endDate.int1)

  override def colour: Colour = polity.colour

  def locPosns: MTimeSeries[LatLongOpt]

  /** Finds all the [[Lunit]]s that have a defined location at the given time. */
  def locationFind(date: MTime): Option[(Lunit, LatLong)] = locPosns.find(date).flatMap(_.map(ll => (this, ll)))
}

trait CorpsNumbered extends Lunit
{ /** The umber of the Corps 1st, 2nd 3rd, etc. */
  def corpsNum: Int

  override def toString: String = polity.name -- corpsNum.adjective -- "Corps"
}