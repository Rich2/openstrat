/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gFour; package hp4
import prid._, phex._, Colour._

sealed trait Terr extends Coloured
object Water extends Terr { def colour = DarkBlue }
object Woods extends Terr { def colour = Green }
object Plain extends Terr { def colour = Wheat }

case class Hold(turns: Int)
//type Command = HStep | Hold
case class Team(name: String, colour: Colour) extends Coloured
{
  override def toString: String = name
}
object TeamA extends Team("TeamA" , Red)
object TeamB extends Team("TeamB" , Violet)

class Lunit(val team: Team, val num: Int) extends Coloured
{ override def colour: Colour = team.colour
  override def toString: String = team.toString -- num.ordAbbr
}

object Lunit
{ def apply(team: Team, num: Int): Lunit = new Lunit(team, num)
}

class LunitState(lunit: Lunit, val cmds: HStepArr = HStepArr()) extends Coloured
{ def colour: Colour = lunit.colour
  def team: Team = lunit.team
  override def toString: String = lunit.toString
}

object LunitState
{ def apply(lunit: Lunit, cmds: HStep *): LunitState = new LunitState(lunit, cmds.toArr)
  def apply(lunit: Lunit, cmds: HStepArr): LunitState = new LunitState(lunit, cmds)
}
