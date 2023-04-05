/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid.phex._, Colour._

trait VTile extends Coloured

object Sea extends VTile with VSideSome
{ override def colour: Colour = DarkBlue
}

object Lake extends VTile
{ override def colour: Colour = Colour.SeaGreen
}

trait LandTerr extends Coloured
{ def colour: Colour
}

trait LandLike extends VTile
{ def terr: LandTerr
  override def colour: Colour = terr.colour
}

case class Land(terr: LandTerr = Plain) extends LandLike

trait LandInner extends LandLike
{
  def sideTerrs: VSideSome
}

case class Island(terr: LandTerr = Plain, sideTerrs: VSideSome = Sea) extends LandInner with HIndent6

case class Head4Land(indentStartIndex: Int, terr: LandTerr = Plain, sideTerrs: VSideSome = Sea) extends LandInner with HIndent4
case class Head3Land(indentStartIndex: Int, terr: LandTerr = Plain, sideTerrs: VSideSome = Sea) extends LandInner with HIndent3
case class Head2Land(indentStartIndex: Int, terr: LandTerr = Plain, sideTerrs: VSideSome = Sea) extends LandInner with HIndent2
case class Head1Land(indentStartIndex: Int, terr: LandTerr = Plain, sideTerrs: VSideSome = Sea) extends LandInner with HIndent1

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