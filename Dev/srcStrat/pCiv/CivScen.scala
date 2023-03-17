/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._, pgui._

/** A Civ scenario turn state. */
trait CivScen  extends HSysTurnScen
{ override def title: String = "Civ Scenario"

  /** tile terrain. */
  def terrs: HCenLayer[VTile]
  val corners: HCornerLayer
  val lunits: HCenArrLayer[Warrior]
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
  val terrs: HCenLayer[VTile] = gridSys.newHCenLayer[VTile](Land())
  terrs.setRowEnd(12, 20, Land(Hill), Land(Mountain) * 2, Land() * 3)
  terrs.setRowEnd(4, 4, Land(Hill) * 3, Land(Plain) * 7)
  val lunits: HCenArrLayer[Warrior] = gridSys.newHCenArrLayer[Warrior]
  lunits.set(10, 18, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))

  override val corners: HCornerLayer = gridSys.newHVertOffsetLayer
}

/** Civ scenario 2. */
object Civ2 extends CivScenStart
{
  override implicit val gridSys: HGrid = HGridReg(2, 8, 4, 20)
  val terrs: HCenLayer[VTile] = gridSys.newHCenLayer[VTile](Land())
  terrs.setRowEnd(4, 4, Land(Mountain) * 3, Land(Plain) * 2)
  val lunits: HCenArrLayer[Warrior] = gridSys.newHCenArrLayer[Warrior]
  lunits.set(8, 16, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))

  override val corners: HCornerLayer = gridSys.newHVertOffsetLayer
}