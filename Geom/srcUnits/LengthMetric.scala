/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pParse.*

/** Metric units of measurement. Many convenient operators are provided for metric units. Operations that mix units from different measurement system are
 * generally provided with named operators, to highlight the programmer is doing this. */
trait MetricUnits extends Any

/** A metric measurement of [[Length]] such as [[Metres]] or the [[Kilometres]] */
trait LengthMetric extends Any with Length with MetricUnits with TellDblBased
{ override def +(operand: Length): LengthMetric
  override def -(operand: Length): LengthMetric
  override def toRectArea(operand: Length): AreaMetric

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
  { def * (operand: LengthMetric): AreaMetric = thisLength.toRectArea(operand)
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
  override def toRectArea(operand: Length): Metrares = Metrares(metresNum * operand.metresNum)
  override def /(operand: Double): Metres = Metres(metresNum / operand)
  override def divByLength(operand: Length): Double = metresNum / operand.metresNum
  @inline def yardsNum: Double = metresNum * 1.09361
  @inline def milesNum: Double = metresNum / 1609.34
  override def max(operand: LengthMetric): Metres = Metres(metresNum.max(operand.metresNum))
  override def min(operand: LengthMetric): Metres = ife(metresNum < operand.metresNum, this, operand.toMetres)
  override def compare(that: Length): Int = metresNum.compare(that.metresNum)
  override def nonNeg: Boolean = metresNum >= 0
  override def pos: Boolean = metresNum > 0
  override def neg: Boolean = metresNum < 0
  @inline override def kilometresNum: Double = metresNum * 1e-3
  @inline override def megametresNum: Double = metresNum * 1e-6
  @inline override def gigametresNum: Double = metresNum * 1e-9
  @inline override def millimetresNum: Double = metresNum * 1000
  @inline override def micrometresNum: Double = metresNum * 1e6
  @inline override def nanometresNum: Double = metresNum * 1e9
  @inline override def angstromsNum: Double = metresNum * 1e10
}

/** Companion object for the [[Metres]] class. */
object Metres
{ /** Factory apply method for [[Metres]]. */
  def apply(metres: Double): Metres = new Metres(metres)

  implicit class LengthMetricExtensions(thisLength: Metres)
  { /** Extension operator method to produce [[Metrares]], multiplying this [[Length]] by an operand [[Length]]. */
    def *(operand: LengthMetric): Metrares = thisLength.toRectArea(operand)
  }
}

/** Measurement of [[Length]] in Kilometres. can be negative. */
final class Kilometres(val kilometresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Kilometres"
  override def unitsDbl: Double = kilometresNum
  override def endingStr: String = "km"
  override def compare(that: Length): Int = kilometresNum.compare(that.kilometresNum)
  @inline override def metresNum: Double = kilometresNum * 1000
  @inline override def megametresNum: Double = kilometresNum * 1e-3
  @inline override def gigametresNum: Double = kilometresNum * 1e-6
  @inline override def millimetresNum: Double = kilometresNum * 1e6
  @inline override def micrometresNum: Double = kilometresNum * 1e9
  @inline override def nanometresNum: Double = kilometresNum * 1e12
  @inline override def angstromsNum: Double = kilometresNum * 1e13
  override def +(operand: Length): Kilometres = Kilometres(kilometresNum = operand.kilometresNum)
  override def -(operand: Length): Kilometres = Kilometres(kilometresNum - operand.kilometresNum)
  override def unary_- : Kilometres = Kilometres(-kilometresNum)
  override def *(operand: Double): Kilometres = Kilometres(kilometresNum * operand)
  override def toRectArea(operand: Length): Kilares = Kilares(kilometresNum * operand.kilometresNum)
  override def /(operand: Double): Kilometres = Kilometres(kilometresNum / operand)
  override def divByLength(operand: Length): Double = kilometresNum / operand.kilometresNum
  override def max(operand: LengthMetric): Kilometres = Kilometres(kilometresNum.max(operand.kilometresNum))
  override def min(operand: LengthMetric): Kilometres = Kilometres(kilometresNum.min(operand.kilometresNum))
  override def nonNeg: Boolean = kilometresNum >= 0
  override def pos: Boolean = kilometresNum > 0
  override def neg: Boolean = kilometresNum < 0
}

object Kilometres
{ /** Factory apply method for kilometres. */
  def apply(kilometresNum: Double): Kilometres = new Kilometres(kilometresNum)

  implicit class LengthMetricExtensions(thisLength: Kilometres)
  { /** Extension operator method to produce [[Kilares]], multiplying this [[Kilometres]] by an operand [[Length]]. */
    def *(operand: Length): Kilares = thisLength.toRectArea(operand)
  }

  implicit val unshow: Unshow[Kilometres] = new Unshow[Kilometres]
  { override def typeStr: String = "Kilometres"

    override def fromExpr(expr: Expr) = expr match
    { case dh: DigitHeadAlphaToken if dh.alphaStr == "km" => Succ(Kilometres(dh.num))
      case _ => expr.failExc("Kilometre not found")
    }
  }
}

/** Measurement of [[Length]] in Megametres. can be negative. */
final class Megametres(val megametresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Megametres"
  override def unitsDbl: Double = megametresNum
  override def endingStr: String = "Mm"
  override def compare(that: Length): Int = megametresNum.compare(that.megametresNum)
  @inline override def metresNum: Double = megametresNum * 1e6
  @inline override def kilometresNum: Double = megametresNum * 1000
  @inline override def gigametresNum: Double = megametresNum * 1e-3
  @inline override def millimetresNum: Double = megametresNum * 1e9
  @inline override def micrometresNum: Double = megametresNum * 1e12
  @inline override def nanometresNum: Double = megametresNum * 1e15
  @inline override def angstromsNum: Double = megametresNum * 1e16
  override def +(operand: Length): Megametres = Megametres(megametresNum = operand.megametresNum)
  override def -(operand: Length): Megametres = Megametres(megametresNum - operand.megametresNum)
  override def unary_- : Megametres = Megametres(-megametresNum)
  override def *(operand: Double): Megametres = Megametres(megametresNum * operand)
  override def toRectArea(operand: Length): Kilares = Kilares(kilometresNum * operand.kilometresNum)
  override def /(operand: Double): Megametres = Megametres(megametresNum / operand)
  override def divByLength(operand: Length): Double = megametresNum / operand.megametresNum
  override def max(operand: LengthMetric): Megametres = Megametres(megametresNum.max(operand.megametresNum))
  override def min(operand: LengthMetric): Megametres = Megametres(megametresNum.min(operand.megametresNum))
  override def nonNeg: Boolean = megametresNum >= 0
  override def pos: Boolean = megametresNum > 0
  override def neg: Boolean = megametresNum < 0
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
  @inline override def metresNum: Double = gigametresNum * 1e9
  @inline override def kilometresNum: Double = gigametresNum * 1e6
  @inline override def megametresNum: Double = gigametresNum * 1000
  @inline override def millimetresNum: Double = gigametresNum * 1e12
  @inline override def micrometresNum: Double = gigametresNum * 1e15
  @inline override def nanometresNum: Double = gigametresNum * 1e18
  @inline override def angstromsNum: Double = metresNum * 1e19
  override def +(operand: Length): Gigametres = Gigametres(gigametresNum = operand.gigametresNum)
  override def -(operand: Length): Gigametres = Gigametres(gigametresNum - operand.gigametresNum)
  override def unary_- : Gigametres = Gigametres(-gigametresNum)
  override def *(operand: Double): Gigametres = Gigametres(gigametresNum * operand)
  override def toRectArea(operand: Length): Kilares = Kilares(kilometresNum * operand.kilometresNum)
  override def /(operand: Double): Gigametres = Gigametres(gigametresNum / operand)
  override def divByLength(operand: Length): Double = megametresNum / operand.megametresNum
  override def max(operand: LengthMetric): Gigametres = Gigametres(gigametresNum.max(operand.gigametresNum))
  override def min(operand: LengthMetric): Gigametres = Gigametres(gigametresNum.min(operand.gigametresNum))
  override def nonNeg: Boolean = gigametresNum >= 0
  override def pos: Boolean = gigametresNum > 0
  override def neg: Boolean = gigametresNum < 0
}

object Gigametres
{ /** Factory apply method for [[Gigametres]]. */
  def apply(gigametresNum: Double): Gigametres = new Gigametres(gigametresNum)
}

/** Measurement of [[Length]] in Millimetres. can be negative. */
final class Millimetres(val millimetresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Millimetres"
  override def unitsDbl: Double = millimetresNum
  override def endingStr: String = "mm"
  override def compare(that: Length): Int = millimetresNum.compare(that.millimetresNum)
  @inline override def metresNum: Double = millimetresNum * 1e-3
  @inline override def kilometresNum: Double = millimetresNum * 1e-6
  @inline override def megametresNum: Double = millimetresNum * 1e-9
  @inline override def gigametresNum: Double = millimetresNum * 1e-12
  @inline override def micrometresNum: Double = millimetresNum * 1e3
  @inline override def nanometresNum: Double = millimetresNum * 1e6
  @inline override def angstromsNum: Double = millimetresNum * 1e7
  override def +(operand: Length): Millimetres = Millimetres(millimetresNum = operand.millimetresNum)
  override def -(operand: Length): Millimetres = Millimetres(millimetresNum - operand.millimetresNum)
  override def unary_- : Millimetres = Millimetres(-millimetresNum)
  override def *(operand: Double): Millimetres = Millimetres(millimetresNum * operand)
  override def toRectArea(operand: Length): Millares = Millares(millimetresNum * operand.millimetresNum)
  override def /(operand: Double): Millimetres = Millimetres(millimetresNum / operand)
  override def divByLength(operand: Length): Double = millimetresNum / operand.millimetresNum
  override def max(operand: LengthMetric): Millimetres = Millimetres(millimetresNum.max(operand.millimetresNum))
  override def min(operand: LengthMetric): Millimetres = Millimetres(millimetresNum.min(operand.millimetresNum))
  override def nonNeg: Boolean = millimetresNum >= 0
  override def pos: Boolean = millimetresNum > 0
  override def neg: Boolean = millimetresNum < 0
}

object Millimetres
{ /** Factory apply method for [[Millimetres]]. */
  def apply(millimetresNum: Double): Millimetres = new Millimetres(millimetresNum)

  implicit class LengthMetricExtensions(thisLength: Millimetres)
  { /** Extension operator method to produce [[Millares]], multiplying this [[Millimetres]] by an operand [[Length]]. */
    def *(operand: Length): Millares = thisLength.toRectArea(operand)
  }

  /** Implicit [[Unshow]] type class instance / evidence for [[Millimetres]]. */
  implicit val unshow: Unshow[Millimetres] = new Unshow[Millimetres]
  { override def typeStr: String = "Millimetres"

    override def fromExpr(expr: Expr) = expr match
    { case dh: DigitHeadAlphaToken if dh.alphaStr == "mm" => Succ(Millimetres(dh.num))
      case _ => expr.failExc("Millimetre not found")
    }
  }
}

/** Measurement of [[Length]] in [[Micrometres]]. can be negative. */
final class Micrometres(val micrometresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Micrometres"
  override def unitsDbl: Double = micrometresNum
  override def endingStr: String = "μm"
  override def compare(that: Length): Int = micrometresNum.compare(that.micrometresNum)
  @inline override def metresNum: Double = micrometresNum * 1e-6
  @inline override def kilometresNum: Double = micrometresNum * 1e-9
  @inline override def megametresNum: Double = micrometresNum * 1e-12
  @inline override def gigametresNum: Double = micrometresNum * 1e-15
  @inline override def millimetresNum: Double = micrometresNum * 1e-3
  @inline override def nanometresNum: Double = micrometresNum * 1e3
  @inline override def angstromsNum: Double = micrometresNum * 1e4
  override def +(operand: Length): Micrometres = Micrometres(micrometresNum + operand.micrometresNum)
  override def -(operand: Length): Micrometres = Micrometres(micrometresNum - operand.micrometresNum)
  override def unary_- : Micrometres = Micrometres(-micrometresNum)
  override def *(operand: Double): Micrometres = Micrometres(micrometresNum * operand)
  override def toRectArea(operand: Length): Millares = Millares(millimetresNum * operand.millimetresNum)
  override def /(operand: Double): Micrometres = Micrometres(micrometresNum / operand)
  override def divByLength(operand: Length): Double = micrometresNum / operand.micrometresNum
  override def max(operand: LengthMetric): Micrometres = Micrometres(micrometresNum.max(operand.micrometresNum))
  override def min(operand: LengthMetric): Micrometres = Micrometres(micrometresNum.min(operand.micrometresNum))
  override def nonNeg: Boolean = micrometresNum >= 0
  override def pos: Boolean = micrometresNum > 0
  override def neg: Boolean = micrometresNum < 0
}

object Micrometres
{ /** Factory apply method for [[Micrometres]]. */
  def apply(micrometresNum: Double): Micrometres = new Micrometres(micrometresNum)

  implicit class LengthMetricExtensions(thisLength: Micrometres)
  { /** Extension operator method to produce [[Millares]], multiplying this [[Micrometres]] by an operand [[Length]]. */
     def *(operand: Length): Millares = thisLength.toRectArea(operand)
  }
}

/** Measurement of [[Length]] in [[Nanometres]]. can be negative. */
final class Nanometres(val nanometresNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Nanometres"
  override def unitsDbl: Double = nanometresNum
  override def endingStr: String = "nm"
  override def compare(that: Length): Int = nanometresNum.compare(that.nanometresNum)
  @inline override def metresNum: Double = nanometresNum * 1e-9
  @inline override def kilometresNum: Double = nanometresNum * 1e-12
  @inline override def megametresNum: Double = nanometresNum * 1e-15
  @inline override def gigametresNum: Double = nanometresNum * 1e-18
  @inline override def millimetresNum: Double = nanometresNum * 1e-6
  @inline override def micrometresNum: Double = nanometresNum * 1e-3
  @inline override def angstromsNum: Double = nanometresNum * 10
  override def +(operand: Length): Nanometres = Nanometres(nanometresNum + operand.nanometresNum)
  override def -(operand: Length): Nanometres = Nanometres(nanometresNum - operand.nanometresNum)
  override def unary_- : Nanometres = Nanometres(-nanometresNum)
  override def *(operand: Double): Nanometres = Nanometres(nanometresNum * operand)
  override def toRectArea(operand: Length): Millares = Millares(millimetresNum * operand.millimetresNum)
  override def /(operand: Double): Nanometres = Nanometres(nanometresNum / operand)
  override def divByLength(operand: Length): Double = nanometresNum / operand.nanometresNum
  override def max(operand: LengthMetric): Nanometres = Nanometres(nanometresNum.max(operand.nanometresNum))
  override def min(operand: LengthMetric): Nanometres = Nanometres(nanometresNum.min(operand.nanometresNum))
  override def nonNeg: Boolean = nanometresNum >= 0
  override def pos: Boolean = nanometresNum > 0
  override def neg: Boolean = nanometresNum < 0
}

object Nanometres
{ /** Factory apply method for [[Nanometres]]. */
  def apply(nanometresNum: Double): Nanometres = new Nanometres(nanometresNum)

  implicit class LengthMetricExtensions(thisLength: Nanometres)
  { /** Extension operator method to produce [[Millares]], multiplying this [[Nanometres]] by an operand [[Length]]. */
    def *(operand: Length): Millares = thisLength.toRectArea(operand)
  }
}

/** Measurement of [[Length]] in angstroms. can be negative. */
final class Angstroms(val angstromsNum: Double) extends AnyVal with LengthMetric
{ override def typeStr: String = "Angstroms"
  override def unitsDbl: Double = angstromsNum
  override def endingStr: String = "Å"
  override def compare(that: Length): Int = angstromsNum.compare(that.angstromsNum)
  @inline override def metresNum: Double = angstromsNum * 1e-10
  @inline override def kilometresNum: Double = angstromsNum * 1e-13
  @inline override def megametresNum: Double = angstromsNum * 1e-16
  @inline override def gigametresNum: Double = angstromsNum * 1e-19
  @inline override def millimetresNum: Double = angstromsNum * 1e-7
  @inline override def micrometresNum: Double = angstromsNum * 1e-4
  @inline override def nanometresNum: Double = angstromsNum * 1e-1
  override def +(operand: Length): Angstroms = Angstroms(angstromsNum + operand.angstromsNum)
  override def -(operand: Length): Angstroms = Angstroms(angstromsNum - operand.angstromsNum)
  override def unary_- : Angstroms = Angstroms(-angstromsNum)
  override def *(operand: Double): Angstroms = Angstroms(angstromsNum * operand)
  override def toRectArea(operand: Length): Metrares = Metrares(metresNum * operand.metresNum)
  override def /(operand: Double): Angstroms = Angstroms(angstromsNum / operand)
  override def divByLength(operand: Length): Double = megametresNum / operand.megametresNum
  override def max(operand: LengthMetric): Angstroms = Angstroms(angstromsNum.max(operand.angstromsNum))
  override def min(operand: LengthMetric): Angstroms = Angstroms(angstromsNum.min(operand.angstromsNum))
  override def nonNeg: Boolean = angstromsNum >= 0
  override def pos: Boolean = angstromsNum > 0
  override def neg: Boolean = angstromsNum < 0
}

object Angstroms
{ /** Factory apply method for [[Angstroms]]. */
  def apply(angstromsNum: Double): Angstroms = new Angstroms(angstromsNum)
}