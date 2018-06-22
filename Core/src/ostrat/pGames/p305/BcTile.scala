/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package p305
import pGrid._
import pEarth._

case class BCTile(x: Int, y: Int, terr: Terrain) extends ETile
{
   var lunits: List[Legion] = Nil
}

object BCTile
{  
   implicit object NTileIsType extends IsType[BCTile]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[BCTile]
      override def asType(obj: AnyRef): BCTile = obj.asInstanceOf[BCTile]
   }
}

class Legion(val polity: Polity, var cood: Cood)
{
   val colour = polity.colour
   override def toString = "Legions" - (polity.toString).enParenth
   override def equals(other: Any): Boolean = other match
   {
      case that: Legion => polity == that.polity
      case _ => false
   }
}

object Legion
{
   def apply(polity: Polity, cood: Cood = Cood00): Legion = new Legion(polity, cood) 
}