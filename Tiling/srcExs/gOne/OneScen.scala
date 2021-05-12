/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import prid._, pCanv._

/** A scenario turn or state for Game One. Consists of just a turn number and a tile Grid. Each tile can contain a single player or can be empty. */
trait OneScen extends HexGridScen
{ /** An optional player can occupy each tile. This is the only tile data in the game. */
  def oPlayers: HCenArrOpt[Player]

  /** Resolves turn. Takes a set of commands / orders, resolves them and returns the new game state scenario. */
  def endTurn(orderList: Arr[HexAndStep]): OneScen =
  { /** A mutable grid of data. The tile data is an Array buffer of [[HexAndStep]]s. */
    val resolve: HCenArrBuff[HexAndStep] = grid.newHCenArrBuff
    orderList.foreach{ hts => resolve.appendAt(hts.hc2, hts) }
    val resValue: HCenArrOpt[Player] = oPlayers.clone
    resolve.foreach { (hc2, buff) => buff.forLen1(head => resValue.mutMove(head.hc1, hc2)) }
    OneScen(turn + 1, grid, resValue)
  }
}

/** Companion object for OneScen trait, contains factory apply method. */
object OneScen
{ /** Factory apply method for OneScen trait. */
  def apply(turnIn: Int, gridIn: HGrid, opIn: HCenArrOpt[Player]): OneScen = new OneScen
  { override val turn = turnIn
    override implicit val grid: HGrid = gridIn
    override def oPlayers: HCenArrOpt[Player] = opIn
  }
}

/** This trait just puts the value 0 in for the turn. */
trait OneScenStart extends OneScen
{ override val turn: Int = 0
}

object OneLaunch extends GuiLaunchStd
{
  override def settingStr: String = "gOne"
  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) = s2 match {
    case 1 => (gOne.GOneGui(_, gOne.OneScen1), "JavaFx Game One")
    case 2 => (gOne.GOneGui(_, gOne.OneScen2), "JavaFx Game One")
    case _ => (gOne.GOneGui(_, gOne.OneScen1), "JavaFx Game One")
  }
}

/** 1st example Turn 0 scenario state for Game One. */
object OneScen1 extends OneScenStart
{ implicit val grid: HGridReg = HGridReg(2, 6, 2, 10)
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.setSome(4, 4, PlayerA)
  oPlayers.setSomes((4, 8, PlayerB), (6, 10, PlayerC))
}

/** 2nd example Turn 0 scenario state for Game One. */
object OneScen2 extends OneScenStart
{ implicit val grid: HGridReg = HGridReg(2, 10, 4, 8)
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.setSome(4, 4, PlayerA)
}