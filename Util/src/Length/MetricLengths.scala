/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;

/** Common trait for metric units of length. */
trait MetricLength extends Any with Length
{
  @inline override def yardsNum: Double = metresNum * 1.09361
  @inline override def milesNum: Double = metresNum / 1609.34
  @inline override def mMilesNum: Double = metresNum / 1609340000
}

/** Length in metres. */
final class Metres(val metresNum: Double) extends AnyVal with MetricLength
{ def typeStr: String = "Dist"
  //def str = persistD1(metres)
  override def +(operand: Length): Metres = Metres(metresNum + operand.metresNum)
  override def -(operand: Length): Metres = Metres(metresNum - operand.metresNum)
  override def unary_- : Metres = Metres(-metresNum)
  override def *(operand: Double): Metres = Metres(metresNum * operand)
  override def /(operand: Double): Metres = Metres(metresNum / operand)

  /** Returns the max length of this and the operand length in [[Metres]]. */
  override def max(operand: Length): Metres = new Metres(metresNum.max(operand.metresNum))

  def min(operand: Metres): Metres = ife(metresNum < operand.metresNum, this, operand)
  def kmStr2 = (metresNum / 1000).str2 + "km"
  override def compare(that: Length): Int = metresNum.match3(_ == that.metresNum, 0, _ > that.metresNum, 1,-1)

  def pos: Boolean = metresNum >= 0
  def neg: Boolean = metresNum < 0

  @inline override def kMetresNum: Double = metresNum / 1000
  @inline override def mMetresNum: Double = metresNum / 1000000
  @inline override def gMetresNum: Double = metresNum / 1000000000
}

/** Companion object for the [[Metres] class. */
object Metres
{ def apply(metres: Double): Metres = new Metres(metres)

  implicit class MetreExtensions(thisMetres: Metres)
  { def * (operand: Length): Area = new Area(thisMetres.metresNum * operand.metresNum)
  }

  //implicit object DistPersist extends PersistDbl1[Metres]("Dist", "metres",_.metres, new Metres(_))
}

/** Length in kilometres. */
final class KMetres(override val kMetresNum: Double) extends AnyVal with MetricLength
{
  override def + (operand: Length): KMetres = KMetres(kMetresNum + operand.metresNum)
  override def - (operand: Length): KMetres = KMetres(kMetresNum - operand.metresNum)
  override def unary_- : KMetres = KMetres(-kMetresNum)
  override def *(operand: Double): KMetres = KMetres(kMetresNum * operand)
  override def /(operand: Double) : KMetres = KMetres(kMetresNum / operand)
  override def toString: String = s"${kMetresNum}km"
  /** Returns the max length of this and the operand length in [[KMetres]]. */
  override def max(operand: Length): KMetres = new KMetres(kMetresNum.max(operand.kMetresNum))

  override def compare(that: Length): Int = (kMetresNum - that.kMetresNum).match3(_ < 0, -1, _ == 0, 0, 1)

  @inline override def metresNum: Double = kMetresNum * 1000
  @inline override def mMetresNum: Double = kMetresNum / 1000
  @inline override def gMetresNum: Double = kMetresNum / 1000000
}

object KMetres
{ def apply(kMetres: Double): KMetres = new KMetres(kMetres)
}

/** Length in megametres or thousands of kilometres. */
final class MMetres(override val mMetresNum: Double) extends AnyVal with MetricLength
{
  /** Adds the operand length to this MMetres. Returns the value in MMetres. */
  override def +(operand: Length): MMetres = new MMetres(mMetresNum + operand.mMetresNum)

  /** Subtracts the operand length to this MMetres. Returns the value in MMetres. */
  override def -(operand: Length): MMetres = new MMetres(mMetresNum - operand.mMetresNum)

  /** Negates this MMetres. Returns the value in MMetres. */
  override def unary_- : MMetres = new MMetres(-mMetresNum)

  /** Multiplies this MMetres by the operand scalar [[Double]]. Returns the value in MMetres class. */
  override def *(operand: Double): MMetres = new MMetres(mMetresNum * operand)

  /** Divides this MMetres by the operand scalar [[Double]]. Returns the value in MMetres class. */
  override def /(operand: Double): MMetres = new MMetres(mMetresNum / operand)

  /** Returns the max length of this and the operand length in [[MMetres]]. */
  override def max(operand: Length): MMetres = new MMetres(mMetresNum.max(operand.mMetresNum))

  override def compare(that: Length): Int = (mMetresNum - that.mMetresNum).match3(_ < 0, -1, _ == 0, 0, 1)

  @inline override def metresNum: Double = mMetresNum * 1000000
  @inline override def kMetresNum: Double = mMetresNum * 1000
  @inline override def gMetresNum: Double = mMetresNum / 1000
}

/** Length measure in GigaMetres or millions of kilometres. */
final class GMetres(override val gMetresNum: Double) extends AnyVal with MetricLength
{
  override def + (operand: Length): GMetres = GMetres(gMetresNum + operand.gMetresNum)
  override def - (operand: Length): GMetres = GMetres(gMetresNum - operand.gMetresNum)
  override def unary_- : GMetres = GMetres(-gMetresNum)
  override def *(operand: Double): GMetres = GMetres(gMetresNum * operand)
  override def /(operand: Double) : GMetres = GMetres(gMetresNum / operand)

  /** Returns the max lLength of this and the operand value in [[GMetres]]. */
  override def max(operand: Length): GMetres = new GMetres(gMetresNum.max(operand.gMetresNum))

  override def compare(that: Length): Int = (gMetresNum - that.gMetresNum).match3(_ < 0, -1, _ == 0, 0, 1)

  @inline override def metresNum: Double = gMetresNum * 1000000000
  @inline override def kMetresNum: Double = gMetresNum * 1000000
  @inline override def mMetresNum: Double = gMetresNum * 1000
}

/** Companion object for the GMetres GigaMeters (1000s of KMs) class contains apply factory method. */
object GMetres
{ /** Apply factory for GMetres. */
  def apply(gMetres: Double): GMetres = new GMetres(gMetres)
}