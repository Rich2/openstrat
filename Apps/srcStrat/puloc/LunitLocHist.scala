/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pgui._, pStrat._, pglobe._

/** A military land unit. The unit can change nationality, position, composition and leadership, but if it changes name it is consdered to be a new
 *  unit. */
trait LunitLocHist
{ val startDate: MTime
  val endDate: Option[MTime]
  /** The nation / state to which this unit belongs. */
  def polity: MTimeSeries[Polity]

  /** An implicit value for the start and end of the unit to be used in building time series.  */
  implicit def startEnd: MTime2Opt =
  { val endInt = endDate match
    {  case Some(t) => t.int1
      case None => 0
    }
    new MTime2Opt(startDate.int1, endDate.nonEmpty,  endInt)
  }

  /** Locations of the unit throughout its existence. */
  def locPosns: MTimeSeries[LatLong]

  def dateFind(date: MTime): Option[LunitState] = locPosns.find(date).map(ll => LunitState(polity.get(date), timeDesig(date), uniLevel.get(date), ll))

  /** The name of the level of the unit such as Army, Corps or Division. */
  def levelName: String

  def desig: String

  def timeDesig(date: MTime): String = desig

  def uniLevel: MTimeSeries[LuUniLevel]

  def supUnit: MTimeSeries[JustOrName[LunitLocHist]] = MTimeSeries(Unknown)

  def subUnits: MTimeSeries[RArr[LunitLocHist]] = MTimeSeries(RArr[LunitLocHist]())
}

/** A [[LunitLocHist]], a military land unit's state at a particular moment in time.  */
case class LunitState(polity: Polity, desig: String, level: LuUniLevel, loc: LatLong) extends Selectable with Coloured
{ override def colour: Colour = polity.colour
  def levelName = level.toString
  override def selectStr: String = s"$polity $desig $levelName"
}

trait ArmyNumbered extends LunitLocHist
{ /** The number of the Army 1st, 2nd 3rd, etc. */
  def armyNum: Int

  override def desig: String = armyNum.ordAbbr

  override def levelName: String = "Army"
}