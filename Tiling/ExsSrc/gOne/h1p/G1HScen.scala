/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package h1p
import prid._, phex._, gPlay._

/** A scenario turn or state for Game One. Consists of just a turn number and a tile Grid. Each tile can contain a single player or can be empty. */
trait G1HScen extends HSysTurnScen
{ override def title: String = "Game 1 hex Scen"

  /** An optional counter can occupy each tile. This is the only tile data in the game. */
  def counters: HCenOptLayer[Counter]

  def counterCens: HCenPairArr[Counter] = counters.somePairs

  /** The [[Counter]]s in the scenario. The data is folded into an [[Arr]]. */
  def counterSet: RArr[Counter] = counters.somesMap(c => c)

    /** Contains the resolution logic. The intentions are presumed to be correct. Combining and checking of intentions should be done before calling
     *  this method. */
  def resolve(intensions: HCenOptStepLayer): HCenOptLayer[Counter] =
  { val playersNew: HCenOptLayer[Counter] = counters.copy
    val acc: HCenAccLayer = intensions.mapAcc
    acc.foreach { (target, arr) => if (arr.length == 1 & counters.emptyTile(target)) playersNew.moveUnsafe(arr.head, target) }
    playersNew
  }
}

/** Companion object for [[G1HScen]], contains factory apply method. */
object G1HScen
{ /** Factory apply method for [[G1HScen]] trait. */
  def apply(turnIn: Int, gridIn: HGridSys, opIn: HCenOptLayer[Counter]): G1HScen = new G1HScen
  { override val turn = turnIn
    override implicit val gridSys: HGridSys = gridIn
    override def counters: HCenOptLayer[Counter] = opIn
  }
}