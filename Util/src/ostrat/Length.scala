/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;

/** Length can be negative. The underlying data is stored in metres. */
final class Length(val metresNum: Double) extends AnyVal with Ordered[Length]
{ def typeStr: String = "Dist"
  def str = "Length".appendParenth(metresNum.toString)// persistD1(metres)
  def +(operand: Length): Length = Length(metresNum + operand.metresNum)
  def -(operand: Length): Length = Length(metresNum - operand.metresNum)
  def unary_- : Length = Length(-metresNum)
  def *(operand: Double): Length = Length(metresNum * operand)
  def /(operand: Double): Length = Length(metresNum / operand)
  @inline def yardsNum: Double = metresNum * 1.09361
  @inline def milesNum: Double = metresNum / 1609.34
  @inline def mMilesNum: Double = metresNum / 1609340000
  /** Returns the max length of this and the operand length in [[Length]]. */
  def max(operand: Length): Length = new Length(metresNum.max(operand.metresNum))

  def min(operand: Length): Length = ife(metresNum < operand.metresNum, this, operand)
  def kmStr2 = (metresNum / 1000).str2 + "km"

  override def compare(that: Length): Int = metresNum match {
    case l if l == that.metresNum => 0
    case l if l > that.metresNum => 1
    case _ => -1
  }

  def pos: Boolean = metresNum >= 0
  def neg: Boolean = metresNum < 0

  @inline def kMetresNum: Double = metresNum / 1000
  @inline def mMetresNum: Double = metresNum / 1000000
  @inline def gMetresNum: Double = metresNum / 1000000000
}

/** Companion object for the [[Length] class. */
object Length
{ def apply(metres: Double): Length = new Length(metres)

  implicit class MetreExtensions(thisMetres: Length)
  { def * (operand: Length): Area = new Area(thisMetres.metresNum * operand.metresNum)
    def / (operand: Length): Double = thisMetres.metresNum / operand.metresNum

  }

  //implicit object DistPersist extends PersistDbl1[Metres]("Dist", "metres",_.metres, new Metres(_))
}