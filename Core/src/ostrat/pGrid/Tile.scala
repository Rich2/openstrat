/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

trait GridMem extends AnyRef
{
   val x, y: Int   
   def cood: Cood = Cood(x, y)
}

trait Tile extends GridMem

trait HexTile extends Tile

trait Side extends GridMem


