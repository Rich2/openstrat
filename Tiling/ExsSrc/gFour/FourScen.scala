/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gFour
import prid._, phex._, Colour._

sealed trait Terr extends Coloured
object Water extends Terr { def colour = DarkBlue }
object Woods extends Terr { def colour = Green }
object Plain extends Terr { def colour = Wheat }

case class Hold(turns: Int)
//type Command = HStep | Hold
case class Team(name: String, colour: Colour) extends Coloured
object TeamA extends Team("TeamA" , Red)
object TeamB extends Team("TeamB" , Violet)

class Lunit(val team: Team, val cmds: HDirnArr = HDirnArr()) extends Coloured
{ def colour = team.colour
  override def toString: String = team.toString
}

object Lunit
{ def apply(team: Team, cmds: HDirn *): Lunit = new Lunit(team, cmds.toArr)
  def apply(team: Team, cmds: HDirnArr): Lunit = new Lunit(team, cmds)
}

/** Example Game four scenario trait. */
abstract class FourScen(val turn: Int) extends HGridScen
{ /** tile terrain. */
  def terrs: HCenDGrid[Terr]
  def units: HCenOptLayer[Lunit]

  /** Resolves turn. Takes a list [[Arr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real location. */
  def endTurn(orderList: Arr[(Lunit, HDirnArr)]): FourScen =
  {
    val playersKey: Map[Lunit, HCen] = units.keyMap

    /** A mutable grid of data. The tile data is an Array buffer of [[HDirn]]s, the HStep pointing back to the origin [[HCen]] of the player. */
    val targets: HCenBuffDGrid[HDirn] = gridSys.newHCenArrOfBuff

    orderList.foreach { case (player, steps) =>  steps.ifHead { step =>
      val hc1 = playersKey(player)
      val optTarget: Option[HCen] = hc1.stepOpt(step)
      optTarget.foreach { target => targets.appendAt(target, step.reverse) }
      }
    }

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within the method. */
    val oPlayersNew: HCenOptLayer[Lunit] = units.clone
    targets.foreach{ (hc2, buff) => buff.foreachLen1(backStep => if (units.tileNone(hc2)) oPlayersNew.unsafeMove(hc2.unsafeStep(backStep), hc2)) }

    FourScen(turn + 1, gridSys, terrs, oPlayersNew)
  }
}

object FourScen
{
  def apply(turnNum: Int, gridIn: HGrid, terrsIn: HCenDGrid[Terr], unitsIn: HCenOptLayer[Lunit]): FourScen = new FourScen(turnNum) {
    /** tile terrain. */
    override def terrs: HCenDGrid[Terr] = terrsIn

    override def units: HCenOptLayer[Lunit] = unitsIn

    /** This gives the structure of the hex grid. It contains no data about the elements of the grid. But it allows the scenario to create and operate
     * on flat arrays of data. */
    override implicit val gridSys: HGrid = gridIn
  }
}