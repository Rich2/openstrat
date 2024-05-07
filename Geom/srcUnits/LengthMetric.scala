/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pParse._

/** Metric units of measurement. Many convenient operators are provided for metric units. Operations that mix units from different measurment system are gnerally
 * provided with named operators, to highlight the programmer is doing this. */
trait MetricUnits extends Any

/** A metric measurement of [[Length]] such as [[Metres]] or the [[Kilometres]] */
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
final class Metres(val metresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Metres"
  override def unitsDbl: Double = metresNum
  override def endingStr: String = "m"
  override def +(operand: Length): Metres = Metres(metresNum + operand.metresNum)
  override def -(operand: Length): Metres = Metres(metresNum - operand.metresNum)
  override def unary_- : Metres = Metres(-metresNum)
  override def *(operand: Double): Metres = Metres(metresNum * operand)
  override def mulByLength(operand: Length): MetresSq = MetresSq(metresNum * operand.metresNum)
  override def /(operand: Double): Metres = Metres(metresNum / operand)
  override def divByLength(operand: Length): Double = metresNum / operand.metresNum
  @inline def yardsNum: Double = metresNum * 1.09361
  @inline def milesNum: Double = metresNum / 1609.34

  override def max(operand: LengthMetric): Metres = Metres(metresNum.max(operand.metresNum))
  override def min(operand: LengthMetric): Metres = ife(metresNum < operand.metresNum, this, operand.toMetres)

  def kmStr2 = (metresNum / 1000).str2 + "km"

  override def compare(that: Length): Int = metresNum.compare(that.metresNum)

  def pos: Boolean = metresNum >= 0
  def neg: Boolean = metresNum < 0

  @inline override def kilometresNum: Double = metresNum / 1000
  @inline override def megametresNum: Double = metresNum / 1000000
  @inline override def gigametresNum: Double = metresNum / 1000000000
}

/** Companion object for the [[Metres]] class. */
object Metres
{ /** Factory apply method for [[Metres]]. */
  def apply(metres: Double): Metres = new Metres(metres)

  implicit class LengthMetricExtensions(thisLength: Metres)
  { /** Extension operator method to produce [[MetresSq]], multiplying this [[Length]] by an operand [[Length]]. */
    def *(operand: LengthMetric): MetresSq = thisLength.mulByLength(operand)
  }
}

/** Measurement of [[Length]] in Kilometres. can be negative. */
final class Kilometres(val kilometresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Kilometres"
  override def unitsDbl: Double = kilometresNum
  override def endingStr: String = "km"
  override def compare(that: Length): Int = kilometresNum.compare(that.kilometresNum)
  override def metresNum: Double = kilometresNum * 1000
  override def megametresNum: Double = kilometresNum / 1000
  override def gigametresNum: Double = kilometresNum / 1000000
  override def +(operand: Length): Kilometres = Kilometres(kilometresNum = operand.kilometresNum)
  override def -(operand: Length): Kilometres = Kilometres(kilometresNum - operand.kilometresNum)
  override def unary_- : Kilometres = Kilometres(-kilometresNum)
  override def *(operand: Double): Kilometres = Kilometres(kilometresNum * operand)
  override def mulByLength(operand: Length): KilometresSq = KilometresSq(kilometresNum * operand.kilometresNum)
  override def /(operand: Double): Kilometres = Kilometres(kilometresNum / operand)
  override def divByLength(operand: Length): Double = kilometresNum / operand.kilometresNum
  override def max(operand: LengthMetric): Kilometres = Kilometres(kilometresNum.max(operand.kilometresNum))
  override def min(operand: LengthMetric): Kilometres = Kilometres(kilometresNum.min(operand.kilometresNum))

  def pos: Boolean = kilometresNum >= 0
  def neg: Boolean = kilometresNum < 0
}

object Kilometres
{ /** Factory apply method for kilometres. */
  def apply(kilometresNum: Double): Kilometres = new Kilometres(kilometresNum)

  implicit class LengthMetricExtensions(thisLength: Kilometres)
  { /** Extension operator method to produce [[KilometresSq]], multiplying this [[Kilometres]] by an operand [[Length]]. */
    def *(operand: Length): KilometresSq = thisLength.mulByLength(operand)
  }

  implicit val unshow: Unshow[Kilometres] = new Unshow[Kilometres]
  { override def typeStr: String = "Kilometres"

    override def fromExpr(expr: Expr): EMon[Kilometres] = expr match
    { case dh: DigitHeadAlphaToken if dh.alphaStr == "km" => Good(Kilometres(dh.num))
      case _ => bad1(expr, "Kilometre not found")
    }
  }
}

/** Measurement of [[Length]] in Megametres. can be negative. */
final class Megametres(val megametresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Megametres"
  override def unitsDbl: Double = megametresNum
  override def endingStr: String = "Mm"
  override def compare(that: Length): Int = megametresNum.compare(that.megametresNum)
  override def metresNum: Double = megametresNum * 1000000
  override def kilometresNum: Double = megametresNum * 1000
  override def gigametresNum: Double = megametresNum / 1000
  override def +(operand: Length): Megametres = Megametres(megametresNum = operand.megametresNum)
  override def -(operand: Length): Megametres = Megametres(megametresNum - operand.megametresNum)
  override def unary_- : Megametres = Megametres(-megametresNum)
  override def *(operand: Double): Megametres = Megametres(megametresNum * operand)
  override def mulByLength(operand: Length): KilometresSq = KilometresSq(kilometresNum * operand.kilometresNum)
  override def /(operand: Double): Megametres = Megametres(megametresNum / operand)
  override def divByLength(operand: Length): Double = megametresNum / operand.megametresNum
  override def max(operand: LengthMetric): Megametres = Megametres(megametresNum.max(operand.megametresNum))
  override def min(operand: LengthMetric): LengthMetric = Megametres(megametresNum.min(operand.megametresNum))
}

object Megametres
{ /** Factory apply method for [[Megametres]]. */
  def apply(megametresNum: Double): Megametres = new Megametres(megametresNum)
}

/** Measurement of [[Length]] in Gigametres. can be negative. */
final class Gigametres(val gigametresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Gigametres"
  override def unitsDbl: Double = gigametresNum
  override def endingStr: String = "Gm"
  override def compare(that: Length): Int = gigametresNum.compare(that.gigametresNum)

  override def metresNum: Double = gigametresNum * 1000000000
  override def kilometresNum: Double = gigametresNum * 1000000
  override def megametresNum: Double = gigametresNum * 1000
  override def +(operand: Length): Gigametres = Gigametres(gigametresNum = operand.gigametresNum)
  override def -(operand: Length): Gigametres = Gigametres(gigametresNum - operand.gigametresNum)
  override def unary_- : Gigametres = Gigametres(-gigametresNum)
  override def *(operand: Double): Gigametres = Gigametres(gigametresNum * operand)
  override def mulByLength(operand: Length): KilometresSq = KilometresSq(kilometresNum * operand.kilometresNum)
  override def /(operand: Double): Gigametres = Gigametres(gigametresNum / operand)
  override def divByLength(operand: Length): Double = megametresNum / operand.megametresNum
  override def max(operand: LengthMetric): Gigametres = Gigametres(gigametresNum.max(operand.gigametresNum))
  override def min(operand: LengthMetric): LengthMetric = Gigametres(gigametresNum.min(operand.gigametresNum))
}

object Gigametres
{ /** Factory apply method for [[Gigametres]]. */
  def apply(gigametresNum: Double): Gigametres = new Gigametres(gigametresNum)
}