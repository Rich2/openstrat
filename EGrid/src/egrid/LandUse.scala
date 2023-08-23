/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid

/** The local climate. */
trait LandUse {

}

object CivMix extends LandUse
object LandFree extends LandUse

/** forest that is not taiga or rain forest. */
case object Forest extends LandUse
{ def str = "Forest"
  def colour: Colour = Colour.ForestGreen
}
