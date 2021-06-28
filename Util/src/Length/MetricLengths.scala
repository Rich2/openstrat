/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;

/** Common trait for metric units of length. */
trait MetricLength extends Any with Length
{ @inline override def miles: Double = metres / 1609.34
  @inline override def yards: Double = metres * 1.09361
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
  @inline override def mMetres: Double = metres / 1000000
  @inline override def gMetres: Double = metres / 1000000000
}

/** Companion object for the [[Metres] class. */
object Metres
{ def apply(metres: Double): Metres = new Metres(metres)

  implicit class MetreExtensions(thisMetres: Metres)
  { def * (operand: Length): Area = new Area(thisMetres.metres * operand.metres)
  }

  //implicit object DistPersist extends PersistDbl1[Metres]("Dist", "metres",_.metres, new Metres(_))
}

/** Length in kilometres. */
final class KMetres(override val kMetres: Double) extends AnyVal with MetricLength
{
  override def + (operand: Length): KMetres = KMetres(kMetres + operand.metres)
  override def - (operand: Length): KMetres = KMetres(kMetres - operand.metres)
  override def unary_- : KMetres = KMetres(-kMetres)
  override def *(operand: Double): KMetres = KMetres(kMetres * operand)
  override def /(operand: Double) : KMetres = KMetres(kMetres / operand)

  override def compare(that: Length): Int = (kMetres - that.kMetres) match {
    case d if d < 0 => -1
    case 0 => 0
    case _ => 1
  }

  @inline override def metres: Double = kMetres * 1000
  @inline override def mMetres: Double = kMetres / 1000
  @inline override def gMetres: Double = kMetres / 1000000
}

object KMetres
{ def apply(kMetres: Double): KMetres = new KMetres(kMetres)
}

/** Length in megametres or thousands of kilometres. */
final class MMetres(override val mMetres: Double) extends AnyVal with MetricLength
{
  /** Adds the operand length to this MMetres. Returns the value in MMetres. */
  override def +(operand: Length): MMetres = new MMetres(mMetres + operand.mMetres)

  /** Subtracts the operand length to this MMetres. Returns the value in MMetres. */
  override def -(operand: Length): MMetres = new MMetres(mMetres - operand.mMetres)

  /** Negates this MMetres. Returns the value in MMetres. */
  override def unary_- : MMetres = new MMetres(-mMetres)

  /** Multiplies this MMetres by the operand scalar [[Double]]. Returns the value in MMetres class. */
  override def *(operand: Double): MMetres = new MMetres(mMetres * operand)

  /** Divides this MMetres by the operand scalar [[Double]]. Returns the value in MMetres class. */
  override def /(operand: Double): MMetres = new MMetres(mMetres / operand)

  override def compare(that: Length): Int = (mMetres - that.mMetres) match {
    case d if d < 0 => -1
    case 0 => 0
    case _ => 1
  }

  @inline override def metres: Double = mMetres * 1000000
  @inline override def kMetres: Double = mMetres * 1000
  @inline override def gMetres: Double = mMetres / 1000
}

/** Length measure in GigaMetres or millions of kilometres. */
final class GMetres(override val gMetres: Double) extends AnyVal with MetricLength
{
  override def + (operand: Length): GMetres = GMetres(gMetres + operand.gMetres)
  override def - (operand: Length): GMetres = GMetres(gMetres - operand.gMetres)
  override def unary_- : GMetres = GMetres(-gMetres)
  override def *(operand: Double): GMetres = GMetres(gMetres * operand)
  override def /(operand: Double) : GMetres = GMetres(gMetres / operand)

  override def compare(that: Length): Int = (gMetres - that.gMetres)match {
    case d if d < 0 => -1
    case 0 => 0
    case _ => 1
  }

  @inline override def metres: Double = gMetres * 1000000000
  @inline override def kMetres: Double = gMetres * 1000000
  @inline override def mMetres: Double = gMetres * 1000
}

/** Companion object for the GMetres GigaMeters (1000s of KMs) class contains apply factory method. */
object GMetres
{ /** Apply factory for GMetres. */
  def apply(gMetres: Double): GMetres = new GMetres(gMetres)
}