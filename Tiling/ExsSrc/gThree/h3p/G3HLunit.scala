/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree; package h3p
import prid._, phex._, Colour._, gPlay._

/** Class may not be needed. A class identifying a [[Counter]] and an [[HCen]] hex coordinate position. */
case class HCounter(hc: HCen, value: Counter) extends HexMemShow[Counter]
{ override def typeStr: String = "HCounter"
  override def name2: String = "counter"
  override implicit def showT2: ShowT[Counter] = Counter.showTEv
  override def syntaxDepth: Int = 2
}


case class Hold(turns: Int)
//type Command = HStep | Hold
case class Team(char: Char, colour: Colour) extends Coloured
{ def name: String = "Team" + char
  override def toString: String = name
  def charStr: String = char.toString

}
object TeamA extends Team('A' , Red)
object TeamB extends Team('B' , Violet)
object TeamC extends Team('C', Turquoise)
object TeamD extends Team('D', Salmon)
object TeamE extends Team('E', Orange)

class Lunit(val team: Team, val num: Int) extends Coloured
{ override def colour: Colour = team.colour
  override def toString: String = team.toString -- num.adjective
}

object Lunit
{ def apply(team: Team, num: Int): Lunit = new Lunit(team, num)
}

class LunitState(val lunit: Lunit, val intentions: HStepArr = HStepArr()) extends Coloured
{ def colour: Colour = lunit.colour
  def team: Team = lunit.team
  override def toString: String = lunit.toString
  def intensionsTail: LunitState = new LunitState(lunit, intentions.tail)
}

object LunitState
{ def apply(lunit: Lunit, intentions: HStep *): LunitState = new LunitState(lunit, intentions.toArr)
  def apply(lunit: Lunit, inentions: HStepArr): LunitState = new LunitState(lunit, inentions)
  def apply(team: Team, unitNum: Int, cmds: HStep *): LunitState = new LunitState(Lunit(team, unitNum), cmds.toArr)
}
