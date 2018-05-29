/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package p1783
import geom._
import pGrid._
import pEarth._

case class NTile(x: Int, y: Int, terr: Terrain) extends ETile
{
   var lunits: List[Corps] = Nil
}

object NTile
{  
   implicit object NTileIsType extends IsType[NTile]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[NTile]
      override def asType(obj: AnyRef): NTile = obj.asInstanceOf[NTile]
   }
}

class Corps(val polity: Polity, var cood: Cood)
{
   val colour = polity.colour
   override def toString = "Corps" - (polity.toString).enParenth
   override def equals(other: Any): Boolean = other match
   {
      case that: Corps => polity == that.polity
      case _ => false
   }
}

object Corps
{
   def apply(polity: Polity, cood: Cood = Cood00): Corps = new Corps(polity, cood) 
}