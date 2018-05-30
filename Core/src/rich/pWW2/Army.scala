/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pWW2
import geom._
import pGrid._

class Army(val polity: Polity, var cood: Cood)
{
   val colour = polity.colour
   override def toString = "Army" - (polity.toString).enParenth
   override def equals(other: Any): Boolean = other match
   {
      case that: Army => polity == that.polity
      case _ => false
   }
}

object Army
{
   def apply(polity: Polity, cood: Cood = Cood00): Army = new Army(polity, cood) 
}