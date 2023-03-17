/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid.phex._, Colour._

trait VTile extends Coloured

object Sea extends VTile
{ override def colour: Colour = DarkBlue
}

object Lake extends VTile
{ override def colour: Colour = Colour.SeaGreen
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
case class Head2Land(outSideNum: Int, terr: LandTerr = Plain) extends LandLike with HInner4
case class Head3Land(outSideNum: Int, terr: LandTerr = Plain) extends LandLike with HInner3
case class Head4Land(outSideNum: Int, terr: LandTerr = Plain) extends LandLike with HInner2

object Plain extends LandTerr
{ override def colour: Colour = LightGreen
}

object Hill extends LandTerr
{ override def colour: Colour = Brown
}

case object Mountain extends LandTerr
{ def str = "Mountain"
  override def colour = Gray
}