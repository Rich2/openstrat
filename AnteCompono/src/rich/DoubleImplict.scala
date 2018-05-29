/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

class DoubleImplicit(thisDouble: Double)// extends PersistSimple[Double
{
   def %%(divisor: Double): Double =
   {
      val r = thisDouble % divisor
      ife(r < 0, divisor + r, r)
   }
   //override def persistStr: String = thisDouble.toString
   val precision = 1e12
   def =~ (other: Double): Boolean =  ((thisDouble - other).abs/(thisDouble.abs.max(other.abs).max(1))) * precision  < 1
   def squared: Double = thisDouble * thisDouble
   def cubed: Double = thisDouble * thisDouble * thisDouble
   def str1: String = f"$thisDouble%1.1f"
   def str2: String = f"$thisDouble%1.2f"
   def str3: String = f"$thisDouble%1.3f"
   def commaedStr1s(others: Double*): String = others.foldLeft(str1)(_ - ", " - _.str1)
   def commaedStr2s(others: Double*): String = others.foldLeft(str2)(_ - ", " - _.str2) 
   def subMin(operand: Double, minValue: Double): Double = (thisDouble - operand).max(minValue)
   def ifNeg[A](vNeg: => A, vPos: => A): A = if (thisDouble < 0) vNeg else vPos
   //def fIfNeg[A](fIfNeg: Double => A, fElse: Double => A): A = if (thisDouble < 0) fIfNeg(-thisDouble) else fElse(thisDouble)
   /** If this between plus and minus the operand */
   def >< (operand: Double): Boolean =
   {
      if (operand < 0) throw new Exception("Operand must be positive for >< outside of operator")
      thisDouble > operand || thisDouble < -operand
   }
   /** if this outside the range minus to plus operand */
   def <> (operand: Double): Boolean = thisDouble > -operand && thisDouble < operand
   def toRoundInt: Int = ife(thisDouble > 0, (thisDouble + 0.5).toInt, (thisDouble - 0.5).toInt)
   import math._
   @inline def radiansToDegrees: Double = thisDouble * 180.0 / Pi
   @inline def degreesToRadians: Double = thisDouble * Pi / 180.0   
   @inline def toWholeDegsStr: String = round(radiansToDegrees).toString
   
   def toDegsMins: (Int, Int) =
   {
      val sx: Int = (radiansToDegrees * 60).toInt
      ((sx / 60), sx % 60)
   }
   def toDegsMinsStr: String =
   {
      val (degs, mins) = toDegsMins
      degs.toString - "°" - mins.ifZero("", mins.toString)
   }
   @inline def sin: Double = math.sin(thisDouble)
   @inline def cos: Double = math.cos(thisDouble)
}   