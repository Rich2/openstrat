/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import Colour._

trait VTileHelper

trait VTile extends VTileHelper, Coloured, TellSimple
{
  override def typeStr: String = "VTile"
  def isWater: Boolean
}

trait Water extends VTile with VSepSome
{ override def isWater: Boolean = true
}

object Sea extends Water
{ override val colour: Colour = DarkBlue
  override val str: String = "Sea"
}

object Lake extends Water
{ override def colour: Colour = Colour.SeaGreen
  override val str: String = "Lake"
}

trait Land extends VTile
{ override def isWater: Boolean = false
}

object Plain extends Land
{ override def colour: Colour = LightGreen
  override val str: String = "Plain"
}

object Hill extends Land
{ override def colour: Colour = Brown
  override val str: String = "Hill"
}

case object Mountain extends Land
{ override val str = "Mountain"
  override def colour = Gray
}