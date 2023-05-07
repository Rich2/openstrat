/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree; package h3p
import prid._, phex._, Colour._

case class Hold(turns: Int)
//type Command = HStep | Hold
case class Team(name: String, colour: Colour) extends Coloured
{ override def toString: String = name
}
object TeamA extends Team("TeamA" , Red)
object TeamB extends Team("TeamB" , Violet)

class Lunit(val team: Team, val num: Int) extends Coloured
{ override def colour: Colour = team.colour
  override def toString: String = team.toString -- num.adjective
}

object Lunit
{ def apply(team: Team, num: Int): Lunit = new Lunit(team, num)
}

class LunitState(val lunit: Lunit, val cmds: HStepArr = HStepArr()) extends Coloured
{ def colour: Colour = lunit.colour
  def team: Team = lunit.team
  override def toString: String = lunit.toString
  def cmdsTail: LunitState = new LunitState(lunit, cmds.tail)
}

object LunitState
{ def apply(lunit: Lunit, cmds: HStep *): LunitState = new LunitState(lunit, cmds.toArr)
  def apply(lunit: Lunit, cmds: HStepArr): LunitState = new LunitState(lunit, cmds)
  def apply(team: Team, unitNum: Int, cmds: HStep *): LunitState = new LunitState(Lunit(team, unitNum), cmds.toArr)
}
