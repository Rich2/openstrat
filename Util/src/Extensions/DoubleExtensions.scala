/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import math.Pi

/** Extension class for Double. This is created as a seperate class to keep down the size of the package object. */
class DoubleImplicit(val thisDouble: Double) extends AnyVal
{
  /** Alternative remainder operator that gives positive remainders for negative numbers. So -1 %% 3 == 2. -7 %% 4 == 1. */
  def %%(divisor: Double): Double =
  { val r = thisDouble % divisor
    ife(r < 0, divisor + r, r)
  }
  
  def precision = 1e12
  def =~ (other: Double): Boolean =  ((thisDouble - other).abs/(thisDouble.abs.max(other.abs).max(1))) * precision  < 1
  def squared: Double = thisDouble * thisDouble
  def cubed: Double = thisDouble * thisDouble * thisDouble

  def str: String = thisDouble.toLong match
  { case l if l == thisDouble => l.toString
    case _ => thisDouble.toString
  }

  def str1: String = f"$thisDouble%1.1f"
  def str2: String = f"$thisDouble%1.2f"
  def str3: String = f"$thisDouble%1.3f"
  def commaedStr1s(others: Double*): String = others.foldLeft(str1)(_ + ", " + _.str1)
  def commaedStr2s(others: Double*): String = others.foldLeft(str2)(_ + ", " + _.str2)
  //def fromToOld(toValue: Double, step: Double): List[Double] = doubleFromToOld(thisDouble, toValue, step)
   
  def fFromTo[A](toValue: Double, step: Double, f: Double => A): List[A] =
  { var count = thisDouble
    var acc: List[A] = Nil
    while (count <= toValue)
    { acc ::= f(count)
      count += step
    }
    acc.reverse
  }
   
  def subMin(operand: Double, minValue: Double): Double = (thisDouble - operand).max(minValue)
  def ifNeg[A](vNeg: => A, vPos: => A): A = if (thisDouble < 0) vNeg else vPos
  
  /** If this between plus and minus the operand */
  def >< (operand: Double): Boolean =
  { if (operand < 0) throw new Exception("Operand must be positive for >< outside of operator")
    thisDouble > operand || thisDouble < -operand
  }
  
  /** if this outside the range minus to plus operand */
  def <> (operand: Double): Boolean = thisDouble > -operand && thisDouble < operand
  def toRoundInt: Int = ife(thisDouble > 0, (thisDouble + 0.5).toInt, (thisDouble - 0.5).toInt)

  @inline def radiansToDegrees: Double = thisDouble * 180.0 / Pi
  @inline def degreesToRadians: Double = thisDouble * Pi / 180.0
  @inline def radiansToSecs: Double = thisDouble * 3600 * 180 / Pi
  @inline def secsToRadians = thisDouble * Pi / 180.0 / 3600.0

  @inline def toWholeDegsStr: String = math.round(radiansToDegrees).toString
  def to2Ints: (Int, Int) =
  { val lg = java.lang.Double.doubleToRawLongBits(thisDouble)   
    ((lg >>> 32).toInt, (lg & 0xFFFFFFFFL).toInt)
  }
   
  def toDegsMins: (Int, Int) =
  { val sx: Int = (radiansToDegrees * 60).toInt
    ((sx / 60), sx % 60)
  }
  
  @inline def sin: Double = math.sin(thisDouble)
  @inline def cos: Double = math.cos(thisDouble)
}
