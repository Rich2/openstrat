/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import math.Pi

/** Extension class for Double. This is created as a separate class to keep down the size of the package object. */
class DoubleImplicit(val thisDouble: Double) extends AnyVal
{ /** Method to be removed, prefer kMetres. */
  def km: Length = new Length(thisDouble * 1000)

  /** Returns this [[Double]] value in [[Length]]. */
  @inline def metres: Length = new Length(thisDouble)

  /** Extension methods multiplies this scalar [[Double]] by the operand in metres */
  @inline def * (operator: Length): Length = new Length(thisDouble * operator.metresNum)

  /** Returns this [[Int]] value in [[Length]] kilometres. */
  @inline def kMetres: Length = Length(thisDouble * 1000)

  /** Returns this [[Double]] value in [[Length]] or millions of kilometres. */
  @inline def gMetres: Length = new Length(thisDouble * 1000000000)

  /** Returns this [[Double]] value in [[Length]]. */
  @inline def miles: Length = new Length(thisDouble * 1609.344)

  /** Returns this [[Double]] value of millions of miles in [[Length]]. */
  @inline def mMiles: Length = new Length(thisDouble * 1609344000)

  /** Alternative modulo or remainder operator that gives a positive modulus remainders for negative numbers. So -1 %% 3 == 2. -7 %% 4 == 1. */
  def %%(divisor: Double): Double =
  { val r = thisDouble % divisor
    ife(r < 0, divisor + r, r)
  }

  /** newV = ((v - l) %% (2 * l)) + l. Alternative modulo or remainder operation that performs a modulus with a divisor twice the limit value but
   *  where values of between one limit value and 2 limit values are expressed as negatives. */
  def %+-(limit: Double): Double =
  { val r = thisDouble % (limit * 2)
    r match {
      case r if r > limit => r - 2 * limit
      case r if r <= - limit => r + 2 * limit
      case r => r
    }
  }

  def precisionDefault = 1e-12
  def =~(that: Double, precision: Double = 1e-12): Boolean = (thisDouble - that).abs <= precision
  def !=~(that: Double, precision: Double = 1e-12): Boolean = (thisDouble - that).abs > precision

  /** Returns the square of this Double, raises it to the power 2. */
  def squared: Double = thisDouble * thisDouble

  /** Returns the cube of this Double, raises it to the power 3. */
  def cubed: Double = thisDouble * thisDouble * thisDouble
  
  /** Returns the square root of this Double. */
  def sqrt: Double = math.sqrt(thisDouble)

  /** String representation for Double that drops the decimal point and zero for integer values. */
  /*def str: String = thisDouble.toLong match
  { case l if l == thisDouble => l.toString
    case _ => thisDouble.toString
  }
*/
  def str0: String = f"$thisDouble%1.0f"
  def str1: String = f"$thisDouble%1.1f"
  def str2: String = f"$thisDouble%1.2f"
  def str3: String = f"$thisDouble%1.3f"
  def commaedStr1s(others: Double*): String = others.foldLeft(str1)(_ + ", " + _.str1)
  def commaedStr2s(others: Double*): String = others.foldLeft(str2)(_ + ", " + _.str2)
   
  def fFromTo[A](toValue: Double, step: Double, f: Double => A): List[A] =
  { var count = thisDouble
    var acc: List[A] = Nil
    while (count <= toValue)
    { acc ::= f(count)
      count += step
    }
    acc.reverse
  }
  
  /** If this between plus and minus the operand */
  def >< (operand: Double): Boolean =
  { if (operand < 0) throw new Exception("Operand must be positive for >< outside of operator")
    thisDouble > operand || thisDouble < -operand
  }
  
  /** if this outside the range minus to plus operand */
  def <> (operand: Double): Boolean = thisDouble > -operand && thisDouble < operand
  def toRoundInt: Int = ife(thisDouble > 0, (thisDouble + 0.5).toInt, (thisDouble - 0.5).toInt)

  /** Takes this Double as a value in arc degrees and converts it to a value of radians. */
  @inline def degsToRadians: Double = thisDouble * Pi / 180.0

  /** Takes this Double as a value in arc degrees and converts it to a value of arc seconds. */
  @inline def degsToSecs: Double = thisDouble * 3600

  /** Takes this Double as a value in arc degrees and converts it to a value of arc seconds. */
  @inline def degsToMilliSecs: Double = thisDouble * 3600000

  /** Takes this Double as a value in radians and converts it to a value of arc degrees. */
  @inline def radiansToDegs: Double = thisDouble * 180 / Pi

  /** Takes this Double as a value in radians and converts it to a value of arc seconds. */
  @inline def radiansToSecs: Double = thisDouble * 3600 * 180.0 / Pi

  /** Takes this Double as a value in radians and converts it to a value of arc seconds. */
  @inline def radiansToMilliSecs: Double = thisDouble * 3600000 * 180.0 / Pi

  /** Takes this Double as a value in arc seconds and converts it to a value of radians. */
  @inline def secsToRadians = thisDouble * Pi / (180.0 * 3600)

  /** Takes this Double as a value in thousands of an arc second and converts it to a value expressed in radians. */
  @inline def milliSecsToRadians = thisDouble * Pi / (180.0 * 3600000)

  /** Takes this Double as a value in arc seconds and converts it to a value of arc degrees. */
  @inline def secsToDegs = thisDouble / 3600

  /** Takes this Double as a value in arc seconds and converts it to a value of arc degrees. */
  @inline def milliSecsToDegs = thisDouble / 3600000

  /** Probably good to get rid of this. */
  @inline def toWholeDegsStr: String = math.round(radiansToDegs).toString
  def to2Ints: (Int, Int) =
  { val lg = java.lang.Double.doubleToRawLongBits(thisDouble)
    ((lg >>> 32).toInt, (lg & 0xFFFFFFFFL).toInt)
  }

  def toDegsMins: (Int, Int) =
  { val sx: Int = (radiansToDegs * 60).toInt
    ((sx / 60), sx % 60)
  }

  /** The sine of this Double expressed in radians. */
  @inline def sine: Double = math.sin(thisDouble)

  /** The cosine of this Double expressed in radians. */
  @inline def cos: Double = math.cos(thisDouble)

  def scaledStr(pairs: (Double, String)*): String = {
    var res = ""
    var i = 0
    val ps: Seq[(Double, String)] = pairs.sortWith(_._1 > _._1)
    while(res == "" & i < ps.length) if (thisDouble >= ps(i)._1) res = ps(i)._2 else i += 1
    res
  }
}