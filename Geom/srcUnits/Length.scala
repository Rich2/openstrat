/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pParse._

trait Length extends Any with Ordered[Length]
{ /** The number of metres in this [[Length]]. */
  def metresNum: Double

  /** The number of kilometres in this [[Length]]. */
  def kilometresNum: Double

  /** The number of Megametres in this [[Length]]. */
  def megametresNum: Double

  /** The number of Gigametres in this [[Length]]. */
  def gigametresNum: Double

  /** The negative of this [[Length]] */
  def unary_- : Length

  /** Add a [[Length]] defined in [[Length]] units. Use addLength method if you wish to mix units from different unit measurement systems. */
  def +(operand: Length): Length

  /** Subtract a [[Length]] defined in [[MetricLength]] units. Use subLength method if you wish to mix units from different unit measurement systems. */
  def -(operand: Length): Length

  /** Multiply by the given scalar. */
  def *(operand: Double): Length

  /** Divide by the given scalar. */
  def /(operand: Double): Length

  /** Converts this [[Length]] to [[Metres]]. */
  def toMetres: Metres = Metres(metresNum)

  /** Converts this [[Length]] to [[Metres]]. */
  def toKiloMetres: Kilometres = Kilometres(kilometresNum)
}

/** Metric units of measurement. Many convenient operators are provided for metric units. Operations that mix units from different measurment system are gnerally
 * provided with named operators, to highlight the programmer is doing this. */
trait MetricUnits extends Any

/** A metric measurement of [[Length]] such as [[Metres]] or the [[Kilometres]] */
trait MetricLength extends Any with Length with MetricUnits
{ override def +(operand: Length): MetricLength
  override def -(operand: Length): MetricLength

  /** Returns the max length of this and the operand length in the units of this object. */
  def max(operand: MetricLength): MetricLength

  /** Returns the max length of this and the operand length in the units of this object. */
  def min(operand: MetricLength): MetricLength

  override def unary_- : MetricLength
  override def *(operand: Double): MetricLength
  override def /(operand: Double): MetricLength
}

object MetricLength
{
  implicit class MetreExtensions(thisMetres: MetricLength)
  { def * (operand: MetricLength): MetresSq = new MetresSq(thisMetres.metresNum * operand.metresNum)
    def / (operand: MetricLength): Double = thisMetres.metresNum / operand.metresNum
  }
}

/** Length can be negative. The underlying data is stored in metres. */
final class Metres(val metresNum: Double) extends AnyVal with MetricLength
{ def typeStr: String = "Metres"
  override def toString: String = metresNum.str + "m"
  def str: String = "Length".appendParenth(metresNum.toString)
  override def +(operand: Length): Metres = Metres(metresNum + operand.metresNum)
  override def -(operand: Length): Metres = Metres(metresNum - operand.metresNum)
  override def unary_- : Metres = Metres(-metresNum)
  override def *(operand: Double): Metres = Metres(metresNum * operand)
  override def /(operand: Double): Metres = Metres(metresNum / operand)
  @inline def yardsNum: Double = metresNum * 1.09361
  @inline def milesNum: Double = metresNum / 1609.34

  override def max(operand: MetricLength): Metres = Metres(metresNum.max(operand.metresNum))
  override def min(operand: MetricLength): Metres = ife(metresNum < operand.metresNum, this, operand.toMetres)

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
}

/** Measurement of [[Length]] in Kilometres. can be negative. */
final class Kilometres(val kilometresNum: Double) extends AnyVal with MetricLength
{ def typeStr: String = "Kilometres"
  override def compare(that: Length): Int = kilometresNum.compare(that.kilometresNum)
  override def metresNum: Double = kilometresNum * 1000
  override def megametresNum: Double = kilometresNum / 1000
  override def gigametresNum: Double = kilometresNum / 1000000
  override def +(operand: Length): Kilometres = Kilometres(kilometresNum = operand.kilometresNum)
  override def -(operand: Length): Kilometres = Kilometres(kilometresNum - operand.kilometresNum)
  override def unary_- : Kilometres = Kilometres(-kilometresNum)
  override def *(operand: Double): Kilometres = Kilometres(kilometresNum * operand)
  override def /(operand: Double): Kilometres = Kilometres(kilometresNum / operand)
  override def max(operand: MetricLength): Kilometres = Kilometres(kilometresNum.max(operand.kilometresNum))
  override def min(operand: MetricLength): Kilometres = Kilometres(kilometresNum.min(operand.kilometresNum))
}

object Kilometres
{ /** Factory apply method for kilometres. */
  def apply(kilometresNum: Double): Kilometres = new Kilometres(kilometresNum)

  val unshow: Unshow[Kilometres] = new Unshow[Kilometres]
  { override def typeStr: String = "Kilometres"

    override def fromExpr(expr: Expr): EMon[Kilometres] = expr match
    { case dh: DigitHeadAlphaToken if dh.alphaStr == "km" => Good(Kilometres(dh.num))
      case _ => bad1(expr, "Kilometre not found")
    }
  }
}

/** Measurement of [[Length]] in Megametres. can be negative. */
final class Megametres(val megametresNum: Double) extends AnyVal with MetricLength
{ def typeStr: String = "Megametres"
  override def compare(that: Length): Int = megametresNum.compare(that.megametresNum)
  override def metresNum: Double = megametresNum * 1000000
  override def kilometresNum: Double = megametresNum * 1000
  override def gigametresNum: Double = megametresNum / 1000
  override def +(operand: Length): Megametres = Megametres(megametresNum = operand.megametresNum)
  override def -(operand: Length): Megametres = Megametres(megametresNum - operand.megametresNum)
  override def unary_- : Megametres = Megametres(-megametresNum)
  override def *(operand: Double): Megametres = Megametres(megametresNum * operand)
  override def /(operand: Double): Megametres = Megametres(megametresNum / operand)
  override def max(operand: MetricLength): Megametres = Megametres(megametresNum.max(operand.megametresNum))
  override def min(operand: MetricLength): MetricLength = Megametres(megametresNum.min(operand.megametresNum))
}

object Megametres
{ /** Factory apply method for [[Megametres]]. */
  def apply(megametresNum: Double): Megametres = new Megametres(megametresNum)
}

/** Measurement of [[Length]] in Gigametres. can be negative. */
final class GigaMetres(val gigametresNum: Double) extends AnyVal with MetricLength
{ def typeStr: String = "Gigametres"

  override def compare(that: Length): Int = gigametresNum.compare(that.gigametresNum)

  override def metresNum: Double = gigametresNum * 1000000000
  override def kilometresNum: Double = gigametresNum * 1000000
  override def megametresNum: Double = gigametresNum * 1000

  override def +(operand: Length): GigaMetres = GigaMetres(gigametresNum = operand.gigametresNum)
  override def -(operand: Length): GigaMetres = GigaMetres(gigametresNum - operand.gigametresNum)
  override def unary_- : GigaMetres = GigaMetres(-gigametresNum)
  override def *(operand: Double): GigaMetres = GigaMetres(gigametresNum * operand)
  override def /(operand: Double): GigaMetres = GigaMetres(gigametresNum / operand)
  override def max(operand: MetricLength): GigaMetres = GigaMetres(gigametresNum.max(operand.gigametresNum))
  override def min(operand: MetricLength): MetricLength = GigaMetres(gigametresNum.min(operand.gigametresNum))
}

object GigaMetres
{ /** Factory apply method for [[Gigametres]]. */
  def apply(gigametresNum: Double): GigaMetres = new GigaMetres(gigametresNum)
}