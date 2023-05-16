/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package hp1
import prid._, phex._, gPlay._

/** A scenario turn or state for Game One. Consists of just a turn number and a tile Grid. Each tile can contain a single player or can be empty. */
trait G1HScen extends HSysTurnScen
{ override def title: String = "Game 1 hex Scen"

  /** An optional player can occupy each tile. This is the only tile data in the game. */
  def players: HCenOptLayer[Counter]

  def playerSet: RArr[Counter] = players.somesMap(p => p)

    /** Contains the resolution logic. The actions are presumed to be correct. Combining and checking of actions should be done before calling this
   *  method. */
  def resolve(actions: HCenOptStepLayer[Counter]): HCenOptLayer[Counter] =
  { val playersNew: HCenOptLayer[Counter] = players.copy
    val acc: HCenAccLayer[Counter] = actions.mapAcc
    acc.foreach{ (target, arr) => if(arr.length == 1 & players.emptyTile(target)) playersNew.moveUnsafe(arr.headHCen, target) }
    playersNew
  }
}

/** Companion object for OneScen trait, contains factory apply method. */
object G1HScen
{ /** Factory apply method for OneScen trait. */
  def apply(turnIn: Int, gridIn: HGridSys, opIn: HCenOptLayer[Counter]): G1HScen = new G1HScen
  { override val turn = turnIn
    override implicit val gridSys: HGridSys = gridIn
    override def players: HCenOptLayer[Counter] = opIn
  }
}