/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pGrid._

trait ETileAncient extends ColouredTileAncient
{ def terr: WTile
  def colour: Colour = terr.colour
  def str: String = terr.str
}



