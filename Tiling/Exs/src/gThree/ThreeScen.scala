/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import prid._, Colour._

sealed trait Terr extends Coloured
object Water extends Terr { def colour = DarkBlue }
object Woods extends Terr { def colour = Green }
object Plain extends Terr { def colour = Wheat }

case class Hold(turns: Int)
//type Command = HStep | Hold
case class Team(name: String, colour: Colour) extends Coloured
object TeamA extends Team("TeamA" , Red)
object TeamB extends Team("TeamB" , Violet)

class Lunit(val team: Team, val cmds: Arr[HStep] = Arr()) extends Coloured
{ def colour = team.colour
}

object Lunit
{
  def apply(team: Team, cmds: HStep *): Lunit = new Lunit(team, cmds.toArr)
  def apply(team: Team, cmds: Arr[HStep]): Lunit = new Lunit(team, cmds)
}

/** Example Game three scenario trait. */
trait ThreeScen extends HexGridScen
{ /** tile terrain. */
  def terrs: HCenArr[Terr]
  def units: HCenArrOpt[Lunit]

  /** Resolves turn. Takes a list [[Arr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real location. */
  def endTurn(orderList: Arr[(Lunit, HSteps)]): ThreeScen =
  {
    val playersKey: Map[Lunit, HCen] = units.keyMap

    /** A mutable grid of data. The tile data is an Array buffer of [[HStep]]s, the HStep pointing back to the origin [[HCen]] of the player. */
    val targets: HCenArrOfBuff[HStep] = grid.newHCenArrOfBuff

    orderList.foreach { (player, steps) => ??? }
      /*val hc1 = playersKey(player)
      val optTarget: Option[HCen] = hc1.step(step)
      optTarget.foreach { target => targets.appendAt(target, step.reverse) }
    }*/

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within the method. */
    val oPlayersNew: HCenArrOpt[Lunit] = units.clone
    targets.foreach{ (hc2, buff) => buff.foreachLen1(backStep => if (units.tileNone(hc2)) oPlayersNew.unsafeMove(hc2.stepOld(backStep), hc2)) }

    ThreeScen(turn + 1, grid, terrs, oPlayersNew)
  }
}

object ThreeScen
{
  def apply(turnNumIn: Int, gridIn: HGrid, terrsIn: HCenArr[Terr], unitsIn: HCenArrOpt[Lunit]): ThreeScen = new ThreeScen {
    /** tile terrain. */
    override def terrs: HCenArr[Terr] = terrsIn

    override def units: HCenArrOpt[Lunit] = unitsIn

    /** This gives the structure of the hex grid. It contains no data about the elements of the grid. But it allows the scenario to create and operate
     * on flat arrays of data. */
    override implicit val grid: HGrid = gridIn

    /** The turn number. This will normally start at 0. The player will then give their instructions for turn 1. The scenario will take these orders /
     * instructions and return the new game state at turn 1. */
    override def turn: Int = turnNumIn
  }
}

/** Example Game three opening scenario trait. */
trait ThreeScenStart extends ThreeScen
{ override def turn: Int = 0
}