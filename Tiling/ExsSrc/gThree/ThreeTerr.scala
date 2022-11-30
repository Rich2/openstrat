/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
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