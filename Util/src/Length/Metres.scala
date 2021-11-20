/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;

/** Length in metres. */
final class Metres(val metresNum: Double) extends AnyVal with Length
{ def typeStr: String = "Dist"
  //def str = persistD1(metres)
  override def +(operand: Length): Metres = Metres(metresNum + operand.metresNum)
  override def -(operand: Length): Metres = Metres(metresNum - operand.metresNum)
  override def unary_- : Metres = Metres(-metresNum)
  override def *(operand: Double): Metres = Metres(metresNum * operand)
  override def /(operand: Double): Metres = Metres(metresNum / operand)
  @inline override def yardsNum: Double = metresNum * 1.09361
  @inline override def milesNum: Double = metresNum / 1609.34
  @inline override def mMilesNum: Double = metresNum / 1609340000
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