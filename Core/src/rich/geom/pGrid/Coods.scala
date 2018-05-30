/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pGrid

class Coods(length: Int) extends IntProduct2s[Cood](length) 
{
   override def newElem(i1: Int, i2: Int): Cood = Cood.apply(i1, i2)
}

object Coods extends Int2Maker[Cood, Coods]
{
   implicit val factory: Int => Coods = new Coods(_)
   @inline def xy(inp: Int*): Coods = ints(inp:_ *)
}