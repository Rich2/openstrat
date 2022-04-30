/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._, pEarth._, pgui._

/** A Civ scenario turn state. */
trait CivScen  extends HFlatTurnScen
{
  /** tile terrain. */
  def terrs: HCenDGrid[Terrain]
  val lunits: HCenArrDGrid[Warrior]
}

/** A Civ scenario state at turn 0. */
trait CivScenStart extends CivScen
{ override def turn: Int = 0
}

object CivLaunch extends GuiLaunchStd
{
  override def settingStr: String = "civ"

  override def default: (CanvasPlatform => Any, String) = (CivGui(_, Civ1), "JavaFx Civ")

  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) = s2 match
  { case 1 => (CivGui(_, Civ1), "JavaFx Civ")
    case 2 => (CivGui(_, Civ2), "JavaFx Civ")
    case _ => (CivGui(_, Civ1), "JavaFx Civ")
  }
}

/** Civ scenario 1. */
object Civ1 extends CivScenStart
{
  override implicit val gridSys: HGrid = HGridReg(2, 14, 4, 40)
  val terrs: HCenDGrid[Terrain] = gridSys.newHCenDGrid[Terrain](Plains)
  terrs.completeRow(12, 20, Hilly, Mountains * 2, Plains * 3)
  terrs.completeRow(4, 4, Hilly * 3, Plains * 7)
  val lunits: HCenArrDGrid[Warrior] = gridSys.newHCenArrDGrid[Warrior]
  lunits.set(10, 18, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))
}

/** Civ scenario 2. */
object Civ2 extends CivScenStart
{
  override implicit val gridSys: HGrid = HGridReg(2, 8, 4, 20)
  val terrs: HCenDGrid[Terrain] = gridSys.newHCenDGrid[Terrain](Plains)
  terrs.completeRow(4, 4, Mountains * 3, Plains * 2)
  val lunits: HCenArrDGrid[Warrior] = gridSys.newHCenArrDGrid[Warrior]
  lunits.set(8, 16, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))
}