/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import Colour._

trait VTileHelper

trait VTile extends VTileHelper with Coloured

trait Water extends VTile with VSideSome

object Sea extends Water
{ override def colour: Colour = DarkBlue
}

object Lake extends Water
{ override def colour: Colour = Colour.SeaGreen
}

trait Land extends VTile


object Plain extends Land
{ override def colour: Colour = LightGreen
}

object Hill extends Land
{ override def colour: Colour = Brown
}

case object Mountain extends Land
{ def str = "Mountain"
  override def colour = Gray
}