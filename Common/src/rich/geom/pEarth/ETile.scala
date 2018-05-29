/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pEarth
import pGrid._

//sealed trait TerrMaker extends Any
trait ETile extends Tile with WithColour// with TerrMaker// with PersistSingle// with AsType[ETile]
{
   def terr: Terrain
   def colour: Colour = terr.colour
//
//   def colourContrast: Colour = colour.blackOrWhite
   //override def colourContrast: Colour = colour.colourContrast2(TerrSea.colour)
   //def apply(num: Int): TerrMulti = TerrMulti(this, num)
   def str: String = terr.str
}



