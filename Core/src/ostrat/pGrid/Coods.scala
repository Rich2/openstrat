/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

class Coods(val arr: Array[Int]) extends AnyVal with IntProduct2s[Cood]//(length) 
{
   override def newElem(i1: Int, i2: Int): Cood = Cood.apply(i1, i2)
}

object Coods extends Int2Maker[Cood, Coods]
{
   implicit val factory: Int => Coods = i => new Coods(new Array[Int](i * 2))
   @inline def xy(inp: Int*): Coods = ints(inp:_ *)
}