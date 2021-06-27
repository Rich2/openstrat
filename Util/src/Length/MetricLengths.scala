/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;

/** Common trait for metric units of length. */
trait MetricLength extends Any with Length
{
  @inline override def miles: Double = metres / 1609.34
}

/** Length in metres. */
final class Metres(val metres: Double) extends AnyVal with MetricLength
{ def typeStr: String = "Dist"
  //def str = persistD1(metres)
  override def +(operand: Length): Metres = Metres(metres + operand.metres)
  override def -(operand: Length): Metres = Metres(metres - operand.metres)
  override def unary_- : Metres = Metres(-metres)
  override def *(operand: Double): Metres = Metres(metres * operand)
  override def /(operand: Double): Metres = Metres(metres / operand)
  def max(operand: Metres): Metres = ife(metres > operand.metres, this, operand)
  def min(operand: Metres): Metres = ife(metres < operand.metres, this, operand)
  def kmStr2 = (metres / 1000).str2 + "km"
  override def compare(that: Length): Int = metres.match3(_ == that.metres, 0, _ > that.metres, 1,-1)

  def pos: Boolean = metres >= 0
  def neg: Boolean = metres < 0

  @inline override def kMetres: Double = metres / 1000
}

/** Companion object for the [[Metres] class. */
object Metres
{ def apply(metres: Double): Metres = new Metres(metres)

  implicit class MetreExtensions(thisDist: Metres)
  { def * (operand: Metres): Area = new Area(thisDist.metres * operand.metres)
  }

  //implicit object DistPersist extends PersistDbl1[Metres]("Dist", "metres",_.metres, new Metres(_))
}

final class KMetres(override val kMetres: Double) extends AnyVal with MetricLength
{ override def metres: Double = kMetres * 1000
  override def + (operand: Length): KMetres = KMetres(kMetres + operand.metres)
  override def - (operand: Length): KMetres = KMetres(kMetres - operand.metres)
  override def unary_- : KMetres = KMetres(-kMetres)
  override def *(operand: Double): KMetres = KMetres(kMetres * operand)
  override def /(operand: Double) : KMetres = KMetres(kMetres / operand)

  override def compare(that: Length): Int = ???
}

object KMetres
{ def apply(kMetres: Double): KMetres = new KMetres(kMetres)
}

/** Length measure in GigaMetres or millions of kilometres. */
final class GMetres(override val kMetres: Double) extends AnyVal with MetricLength
{
  override def metres: Double = kMetres * 1000
  override def + (operand: Length): KMetres = KMetres(kMetres + operand.metres)
  override def - (operand: Length): KMetres = KMetres(kMetres - operand.metres)
  override def unary_- : KMetres = KMetres(-kMetres)
  override def *(operand: Double): KMetres = KMetres(kMetres * operand)
  override def /(operand: Double) : KMetres = KMetres(kMetres / operand)

  override def compare(that: Length): Int = ???
}

/** Companion object for the GMetres GigaMeters (1000s of KMs) class contains apply factory method. */
object GMetres
{ /** Apply factory for GMetres. */
  def apply(gMetres: Double): GMetres = new GMetres(gMetres)
}