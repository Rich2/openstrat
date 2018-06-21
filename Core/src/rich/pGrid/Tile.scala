/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pGrid

trait Tile extends AnyRef
{
   val x, y: Int   
   def cood: Cood = Cood(x, y)
}

trait HexTile extends Tile


