/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;

/** Length in metres. */
final class Metres(val metresNum: Double) extends AnyVal with Ordered[Metres]
{ def typeStr: String = "Dist"
  //def str = persistD1(metres)
  def +(operand: Metres): Metres = Metres(metresNum + operand.metresNum)
  def -(operand: Metres): Metres = Metres(metresNum - operand.metresNum)
  def unary_- : Metres = Metres(-metresNum)
  def *(operand: Double): Metres = Metres(metresNum * operand)
  def /(operand: Double): Metres = Metres(metresNum / operand)
  @inline def yardsNum: Double = metresNum * 1.09361
  @inline def milesNum: Double = metresNum / 1609.34
  @inline def mMilesNum: Double = metresNum / 1609340000
  /** Returns the max length of this and the operand length in [[Metres]]. */
  def max(operand: Metres): Metres = new Metres(metresNum.max(operand.metresNum))

  def min(operand: Metres): Metres = ife(metresNum < operand.metresNum, this, operand)
  def kmStr2 = (metresNum / 1000).str2 + "km"
  override def compare(that: Metres): Int = metresNum.match3(_ == that.metresNum, 0, _ > that.metresNum, 1,-1)

  def pos: Boolean = metresNum >= 0
  def neg: Boolean = metresNum < 0

  @inline def kMetresNum: Double = metresNum / 1000
  @inline def mMetresNum: Double = metresNum / 1000000
  @inline def gMetresNum: Double = metresNum / 1000000000
}

/** Companion object for the [[Metres] class. */
object Metres
{ def apply(metres: Double): Metres = new Metres(metres)

  implicit class MetreExtensions(thisMetres: Metres)
  { def * (operand: Metres): Area = new Area(thisMetres.metresNum * operand.metresNum)
    def / (operand: Metres): Double = thisMetres.metresNum / operand.metresNum

  }

  //implicit object DistPersist extends PersistDbl1[Metres]("Dist", "metres",_.metres, new Metres(_))
}