/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pgui._, pStrat._, pglobe._

/** A military land unit. The unit can change nationality, position, composition and leadership, but if it changes name it is consdered to be a new
 *  unit. */
abstract class Lunit(val startDate: MTime, val endDate: MTime)
{ /** The nation / state to which this unit belongs. */
  def polity: MTimeSeries[Polity]

  /** An implicit value for the start and end of the unit to be used in building time series.  */
  implicit def startEnd: MTime2 = new MTime2(startDate.int1, endDate.int1)

  /** Locations of the unit throughout its existence. */
  def locPosns: MTimeSeries[LatLong]

  def dateFind(date: MTime): Option[LunitState] = locPosns.find(date).map(ll => LunitState(polity.find(date).get, timeDesig(date), level, ll))

  /** The name of the level of the unit such as Army, Corps or Division. */
  def levelName: String

  def desig: String

  def timeDesig(date: MTime): String = desig

  def level: LunitLevel

  def supUnit: MTimeSeries[JustOrName[Lunit]] = MTimeSeries(Unknown)

  def subUnits: MTimeSeries[RArr[Lunit]] = MTimeSeries(RArr[Lunit]())
}

/** A [[Lunit]], a military land unit's state at a particular moment in time.  */
case class LunitState(polity: Polity, desig: String, level: LunitLevel, loc: LatLong) extends Selectable with Coloured
{ override def colour: Colour = polity.colour
  def levelName = level.toString
  override def selectStr: String = s"$polity $desig $levelName"
}



trait ArmyNumbered extends Lunit
{ /** The number of the Army 1st, 2nd 3rd, etc. */
  def armyNum: Int

  override def desig: String = armyNum.ordAbbr

  override def levelName: String = "Army"
}