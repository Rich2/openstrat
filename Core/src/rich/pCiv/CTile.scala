/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pCiv
import geom._
import pEarth._
import pGrid._

case class CTile(x: Int, y: Int, terr: Terrain) extends Tile
{
   def colour = terr.colour
   var settlement: Boolean = false
   var lunits: List[Warrior] = Nil
}

object CTile
{
   implicit val tileMaker: (Int, Int, Terrain) => CTile = apply
   implicit object CTileIsType extends IsType[CTile]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[CTile]
      override def asType(obj: AnyRef): CTile = obj.asInstanceOf[CTile]
   }
}
