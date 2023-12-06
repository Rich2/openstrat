/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnorm
import prid.phex._, Colour._

trait Tile extends Coloured

object Sea extends Tile
{ override def colour: Colour = Blue
}

trait LandTerr extends Coloured
{
  def colour: Colour
}

trait LandLike extends Tile
{ def terr: LandTerr
  override def colour: Colour = terr.colour
}

case class Land(terr: LandTerr = Plain) extends LandLike

case class Island(terr: LandTerr = Plain) extends LandLike with HcIndent6

//case class Head1Land(indentStartIndex: Int, terr: LandTerr = Plain) extends LandLike with HIndent4

object Plain extends LandTerr
{ override def colour: Colour = LightGreen
}

object Hill extends LandTerr
{ override def colour: Colour = Brown
}