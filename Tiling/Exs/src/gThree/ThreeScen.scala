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
}

/** Example Game three opening scenario trait. */
trait ThreeScenStart extends ThreeScen
{ override def turn: Int = 0
}