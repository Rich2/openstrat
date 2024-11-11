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
  override def pos: Boolean = metresNum >= 0
  override def neg: Boolean = metresNum < 0
  @inline override def kilometresNum: Double = metresNum / 1000
  @inline override def megametresNum: Double = metresNum / 1000000
  @inline override def gigametresNum: Double = metresNum / 1000000000
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
final class Kilometre(val kilometresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Kilometres"
  override def unitsDbl: Double = kilometresNum
  override def endingStr: String = "km"
  override def compare(that: Length): Int = kilometresNum.compare(that.kilometresNum)
  override def metresNum: Double = kilometresNum * 1000
  override def megametresNum: Double = kilometresNum / 1000
  override def gigametresNum: Double = kilometresNum / 1000000
  override def +(operand: Length): Kilometre = Kilometre(kilometresNum = operand.kilometresNum)
  override def -(operand: Length): Kilometre = Kilometre(kilometresNum - operand.kilometresNum)
  override def unary_- : Kilometre = Kilometre(-kilometresNum)
  override def *(operand: Double): Kilometre = Kilometre(kilometresNum * operand)
  override def mulByLength(operand: Length): Kilare = Kilare(kilometresNum * operand.kilometresNum)
  override def /(operand: Double): Kilometre = Kilometre(kilometresNum / operand)
  override def divByLength(operand: Length): Double = kilometresNum / operand.kilometresNum
  override def max(operand: LengthMetric): Kilometre = Kilometre(kilometresNum.max(operand.kilometresNum))
  override def min(operand: LengthMetric): Kilometre = Kilometre(kilometresNum.min(operand.kilometresNum))
  override def pos: Boolean = kilometresNum >= 0
  override def neg: Boolean = kilometresNum < 0
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
final class Megametre(val megametresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Megametres"
  override def unitsDbl: Double = megametresNum
  override def endingStr: String = "Mm"
  override def compare(that: Length): Int = megametresNum.compare(that.megametresNum)
  override def metresNum: Double = megametresNum * 1000000
  override def kilometresNum: Double = megametresNum * 1000
  override def gigametresNum: Double = megametresNum / 1000
  override def +(operand: Length): Megametre = Megametre(megametresNum = operand.megametresNum)
  override def -(operand: Length): Megametre = Megametre(megametresNum - operand.megametresNum)
  override def unary_- : Megametre = Megametre(-megametresNum)
  override def *(operand: Double): Megametre = Megametre(megametresNum * operand)
  override def mulByLength(operand: Length): Kilare = Kilare(kilometresNum * operand.kilometresNum)
  override def /(operand: Double): Megametre = Megametre(megametresNum / operand)
  override def divByLength(operand: Length): Double = megametresNum / operand.megametresNum
  override def max(operand: LengthMetric): Megametre = Megametre(megametresNum.max(operand.megametresNum))
  override def min(operand: LengthMetric): Megametre = Megametre(megametresNum.min(operand.megametresNum))
  override def pos: Boolean = megametresNum >= 0
  override def neg: Boolean = megametresNum < 0
}

object Megametre
{ /** Factory apply method for [[Megametre]]. */
  def apply(megametresNum: Double): Megametre = new Megametre(megametresNum)
}

/** Measurement of [[Length]] in Gigametres. can be negative. */
final class Gigametre(val gigametresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Gigametres"
  override def unitsDbl: Double = gigametresNum
  override def endingStr: String = "Gm"
  override def compare(that: Length): Int = gigametresNum.compare(that.gigametresNum)

  override def metresNum: Double = gigametresNum * 1000000000
  override def kilometresNum: Double = gigametresNum * 1000000
  override def megametresNum: Double = gigametresNum * 1000
  override def +(operand: Length): Gigametre = Gigametre(gigametresNum = operand.gigametresNum)
  override def -(operand: Length): Gigametre = Gigametre(gigametresNum - operand.gigametresNum)
  override def unary_- : Gigametre = Gigametre(-gigametresNum)
  override def *(operand: Double): Gigametre = Gigametre(gigametresNum * operand)
  override def mulByLength(operand: Length): Kilare = Kilare(kilometresNum * operand.kilometresNum)
  override def /(operand: Double): Gigametre = Gigametre(gigametresNum / operand)
  override def divByLength(operand: Length): Double = megametresNum / operand.megametresNum
  override def max(operand: LengthMetric): Gigametre = Gigametre(gigametresNum.max(operand.gigametresNum))
  override def min(operand: LengthMetric): Gigametre = Gigametre(gigametresNum.min(operand.gigametresNum))
  override def pos: Boolean = gigametresNum >= 0
  override def neg: Boolean = gigametresNum < 0
}

object Gigametre
{ /** Factory apply method for [[Gigametre]]. */
  def apply(gigametresNum: Double): Gigametre = new Gigametre(gigametresNum)
}