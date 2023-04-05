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

//trait LandTerr extends Coloured
//{ def colour: Colour
//}

/*trait LandLike extends VTile
{ def terr: LandTerr
  override def colour: Colour = terr.colour
}*/

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