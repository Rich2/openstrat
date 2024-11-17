/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pParse._

/** Metric units of measurement. Many convenient operators are provided for metric units. Operations that mix units from different measurment system are gnerally
 * provided with named operators, to highlight the programmer is doing this. */
trait MetricUnits extends Any

/** A metric measurement of [[Length]] such as [[Metre]] or the [[Kilometre]] */
trait LengthMetric extends Any with Length with MetricUnits with TellDblBased
{ override def +(operand: Length): LengthMetric
  override def -(operand: Length): LengthMetric
  override def mulByLength(operand: Length): AreaMetric
  /** Returns the max length of this and the operand length in the units of this object. */
  def max(operand: LengthMetric): LengthMetric

  /** Returns the max length of this and the operand length in the units of this object. */
  def min(operand: LengthMetric): LengthMetric

  override def unary_- : LengthMetric
  override def *(operand: Double): LengthMetric
  override def /(operand: Double): LengthMetric
}

object LengthMetric
{
  implicit class LengthMetricExtensions(thisLength: LengthMetric)
  { def * (operand: LengthMetric): AreaMetric = thisLength.mulByLength(operand)
  }
}

/** Length can be negative. The underlying data is stored in metres. */
final class Metre(val metresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Metres"
  override def unitsDbl: Double = metresNum
  override def endingStr: String = "m"
  override def +(operand: Length): Metre = Metre(metresNum + operand.metresNum)
  override def -(operand: Length): Metre = Metre(metresNum - operand.metresNum)
  override def unary_- : Metre = Metre(-metresNum)
  override def *(operand: Double): Metre = Metre(metresNum * operand)
  override def mulByLength(operand: Length): Metrare = Metrare(metresNum * operand.metresNum)
  override def /(operand: Double): Metre = Metre(metresNum / operand)
  override def divByLength(operand: Length): Double = metresNum / operand.metresNum
  @inline def yardsNum: Double = metresNum * 1.09361
  @inline def milesNum: Double = metresNum / 1609.34
  override def max(operand: LengthMetric): Metre = Metre(metresNum.max(operand.metresNum))
  override def min(operand: LengthMetric): Metre = ife(metresNum < operand.metresNum, this, operand.toMetres)
  override def compare(that: Length): Int = metresNum.compare(that.metresNum)
  override def nonNeg: Boolean = metresNum >= 0
  override def pos: Boolean = metresNum > 0
  override def neg: Boolean = metresNum < 0
  @inline override def kilometreNum: Double = metresNum / 1000
  @inline override def megametreNum: Double = metresNum / 1000000
  @inline override def gigametreNum: Double = metresNum / 1000000000
}

/** Companion object for the [[Metre]] class. */
object Metre
{ /** Factory apply method for [[Metre]]. */
  def apply(metres: Double): Metre = new Metre(metres)

  implicit class LengthMetricExtensions(thisLength: Metre)
  { /** Extension operator method to produce [[Metrare]], multiplying this [[Length]] by an operand [[Length]]. */
    def *(operand: LengthMetric): Metrare = thisLength.mulByLength(operand)
  }
}

/** Measurement of [[Length]] in Kilometres. can be negative. */
final class Kilometre(val kilometreNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Kilometres"
  override def unitsDbl: Double = kilometreNum
  override def endingStr: String = "km"
  override def compare(that: Length): Int = kilometreNum.compare(that.kilometreNum)
  override def metresNum: Double = kilometreNum * 1000
  override def megametreNum: Double = kilometreNum / 1000
  override def gigametreNum: Double = kilometreNum / 1000000
  override def +(operand: Length): Kilometre = Kilometre(kilometresNum = operand.kilometreNum)
  override def -(operand: Length): Kilometre = Kilometre(kilometreNum - operand.kilometreNum)
  override def unary_- : Kilometre = Kilometre(-kilometreNum)
  override def *(operand: Double): Kilometre = Kilometre(kilometreNum * operand)
  override def mulByLength(operand: Length): Kilare = Kilare(kilometreNum * operand.kilometreNum)
  override def /(operand: Double): Kilometre = Kilometre(kilometreNum / operand)
  override def divByLength(operand: Length): Double = kilometreNum / operand.kilometreNum
  override def max(operand: LengthMetric): Kilometre = Kilometre(kilometreNum.max(operand.kilometreNum))
  override def min(operand: LengthMetric): Kilometre = Kilometre(kilometreNum.min(operand.kilometreNum))
  override def nonNeg: Boolean = kilometreNum >= 0
  override def pos: Boolean = kilometreNum > 0
  override def neg: Boolean = kilometreNum < 0
}

object Kilometre
{ /** Factory apply method for kilometres. */
  def apply(kilometresNum: Double): Kilometre = new Kilometre(kilometresNum)

  implicit class LengthMetricExtensions(thisLength: Kilometre)
  { /** Extension operator method to produce [[Kilare]], multiplying this [[Kilometre]] by an operand [[Length]]. */
    def *(operand: Length): Kilare = thisLength.mulByLength(operand)
  }

  implicit val unshow: Unshow[Kilometre] = new Unshow[Kilometre]
  { override def typeStr: String = "Kilometres"

    override def fromExpr(expr: Expr) = expr match
    { case dh: DigitHeadAlphaToken if dh.alphaStr == "km" => Succ(Kilometre(dh.num))
      case _ => expr.failExc("Kilometre not found")
    }
  }
}

/** Measurement of [[Length]] in Megametres. can be negative. */
final class Megametre(val megametreNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Megametres"
  override def unitsDbl: Double = megametreNum
  override def endingStr: String = "Mm"
  override def compare(that: Length): Int = megametreNum.compare(that.megametreNum)
  override def metresNum: Double = megametreNum * 1000000
  override def kilometreNum: Double = megametreNum * 1000
  override def gigametreNum: Double = megametreNum / 1000
  override def +(operand: Length): Megametre = Megametre(megametresNum = operand.megametreNum)
  override def -(operand: Length): Megametre = Megametre(megametreNum - operand.megametreNum)
  override def unary_- : Megametre = Megametre(-megametreNum)
  override def *(operand: Double): Megametre = Megametre(megametreNum * operand)
  override def mulByLength(operand: Length): Kilare = Kilare(kilometreNum * operand.kilometreNum)
  override def /(operand: Double): Megametre = Megametre(megametreNum / operand)
  override def divByLength(operand: Length): Double = megametreNum / operand.megametreNum
  override def max(operand: LengthMetric): Megametre = Megametre(megametreNum.max(operand.megametreNum))
  override def min(operand: LengthMetric): Megametre = Megametre(megametreNum.min(operand.megametreNum))
  override def nonNeg: Boolean = megametreNum >= 0
  override def pos: Boolean = megametreNum > 0
  override def neg: Boolean = megametreNum < 0
}

object Megametre
{ /** Factory apply method for [[Megametre]]. */
  def apply(megametresNum: Double): Megametre = new Megametre(megametresNum)
}

/** Measurement of [[Length]] in Gigametres. can be negative. */
final class Gigametre(val gigametreNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Gigametres"
  override def unitsDbl: Double = gigametreNum
  override def endingStr: String = "Gm"
  override def compare(that: Length): Int = gigametreNum.compare(that.gigametreNum)

  override def metresNum: Double = gigametreNum * 1000000000
  override def kilometreNum: Double = gigametreNum * 1000000
  override def megametreNum: Double = gigametreNum * 1000
  override def +(operand: Length): Gigametre = Gigametre(gigametresNum = operand.gigametreNum)
  override def -(operand: Length): Gigametre = Gigametre(gigametreNum - operand.gigametreNum)
  override def unary_- : Gigametre = Gigametre(-gigametreNum)
  override def *(operand: Double): Gigametre = Gigametre(gigametreNum * operand)
  override def mulByLength(operand: Length): Kilare = Kilare(kilometreNum * operand.kilometreNum)
  override def /(operand: Double): Gigametre = Gigametre(gigametreNum / operand)
  override def divByLength(operand: Length): Double = megametreNum / operand.megametreNum
  override def max(operand: LengthMetric): Gigametre = Gigametre(gigametreNum.max(operand.gigametreNum))
  override def min(operand: LengthMetric): Gigametre = Gigametre(gigametreNum.min(operand.gigametreNum))
  override def nonNeg: Boolean = gigametreNum >= 0
  override def pos: Boolean = gigametreNum > 0
  override def neg: Boolean = gigametreNum < 0
}

object Gigametre
{ /** Factory apply method for [[Gigametre]]. */
  def apply(gigametresNum: Double): Gigametre = new Gigametre(gigametresNum)
}