/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import math._

final class Dist(val metres: Double) extends AnyVal with Ordered[Dist]
{
   def +(operand: Dist) = Dist(metres + operand.metres)
   def -(operand: Dist) = Dist(metres - operand.metres)
   def unary_- : Dist = Dist(-metres)
   def *(operand: Double): Dist = Dist(metres * operand)
   def /(operand: Double): Dist = Dist(metres / operand)
   def max(operand: Dist): Dist = ife(metres > operand.metres, this, operand)
   def min(operand: Dist): Dist = ife(metres < operand.metres, this, operand)
   def kmStr2 = (metres / 1000).str2 - "km"
   override def toString = kmStr2
   override def compare(that: Dist): Int = Unit match 
   {
      case _ if metres == that.metres => 0
      case _ if metres > that.metres => 1
      case _ => -1
   }
   def pos: Boolean = metres >= 0
   def neg: Boolean = metres < 0
}

object Dist
{
   def apply(metres: Double): Dist = new Dist(metres)
   //implicit object DistPersist extends Persist
}




