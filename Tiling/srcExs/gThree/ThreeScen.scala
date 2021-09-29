/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import prid._, Colour._

sealed trait Terr extends WithColour
object Water extends Terr { def colour = DarkBlue }
object Woods extends Terr { def colour = Green }
object Plain extends Terr { def colour = Wheat }

/** Example Game three scenario trait. */
trait ThreeScen extends HexGridScen
{ /** tile terrain. */
  def terrs: HCenArr[Terr]
  def oPlayers: HCenArrOpt[Player]
}

/** Example Game three opening scenario trait. */
trait ThreeScenStart extends ThreeScen
{ override def turn: Int = 0
}