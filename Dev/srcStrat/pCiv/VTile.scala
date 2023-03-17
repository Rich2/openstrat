/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid.phex._, Colour._

trait VTile extends Coloured

object Sea extends VTile
{ override def colour: Colour = Blue
}

trait LandTerr extends Coloured
{
  def colour: Colour
}

trait LandLike extends VTile
{ def terr: LandTerr
  override def colour: Colour = terr.colour
}

case class Land(terr: LandTerr = Plain) extends LandLike

case class Island(terr: LandTerr = Plain) extends LandLike with HInner6

case class Head1Land(outSideNum: Int, terr: LandTerr = Plain) extends LandLike with HInner5

object Plain extends LandTerr
{ override def colour: Colour = MintCream
}

object Hill extends LandTerr
{ override def colour: Colour = Brown
}

case object Mountain extends LandTerr
{ def str = "Mountain"
  override def colour = Gray
}