/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom.*, pStrat.*, pglobe.*

/** A military land unit. The unit can change nationality, position, composition and leadership, but if it changes name it is considered to be a new unit. */
trait LunitLocHist
{ val startDate: TimeMin
  val endDate: Option[TimeMin]
  /** The nation / state to which this unit belongs. */
  def polity: TimeMinSeries[Polity]

  /** An implicit value for the start and end of the unit to be used in building time series.  */
  implicit def startEnd: MTime2Opt =
  { val endInt = endDate match
    {  case Some(t) => t.long1
      case None => 0
    }
    new MTime2Opt(startDate.long1, endDate.nonEmpty, endInt)
  }

  /** Locations of the unit throughout its existence. */
  def locPosns: TimeMinSeries[LatLong]

  def dateFind(date: TimeMin): Option[LunitState] = locPosns.find(date).map(ll => LunitState(polity.get(date), timeDesig(date), uniLevel.get(date), ll))

  /** The name of the level of the unit such as Army, Corps or Division. */
  def levelName: TimeMinSeries[String]

  /** The designated identification, such as "8th" in "8th Army" or "Wodrig" in "Korps Wodrig". */
  def idStr: String

  def timeDesig(date: TimeMin): String = idStr

  def uniLevel: TimeMinSeries[LuUniLevel]

  def supUnit: TimeMinSeries[JustOrName[LunitLocHist]] = TimeMinSeries(Unknown)

  def subUnits: TimeMinSeries[RArr[LunitLocHist]] = TimeMinSeries(RArr[LunitLocHist]())
}

/** A [[LunitLocHist]], a military land unit's state at a particular moment in time.  */
case class LunitState(polity: Polity, desig: String, level: LuUniLevel, loc: LatLong) extends Coloured
{ override def colour: Colour = polity.colour
  def levelName = level.toString  
}

trait ArmyNumbered extends LunitLocHist
{ /** The number of the Army 1st, 2nd 3rd, etc. */
  def armyNum: Int

  override def idStr: String = armyNum.ordAbbr

  override def levelName: TimeMinSeries[String] = TimeMinSeries("Army")
}