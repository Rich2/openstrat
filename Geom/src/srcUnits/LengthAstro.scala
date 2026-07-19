/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Length can be negative. The underlying data is stored in light-years. */
class LightYears(val lightYearsNum: Double) extends AnyVal, LengthNotMetric
{ extension (thisLength: Length)
  { def lightYearsNum: Double = thisLength match
    { case lys: LightYears => lys.lightYearsNum
      case l => l.metresNum / toMetresFactor
    }
  }
  override def metresNum: Double = lightYearsNum * toMetresFactor
  override def unary_- : LightYears = LightYears(-lightYearsNum)
  override def +(operand: Length): LightYears = LightYears(lightYearsNum + operand.lightYearsNum)
  override def -(operand: Length): LightYears = LightYears(lightYearsNum + operand.lightYearsNum)
  override def *(operand: Double): LightYears = LightYears(lightYearsNum * operand)
  override def /(operand: Double): LightYears = LightYears(lightYearsNum / operand)
  override def toRectArea(operand: Length): Kilares = Kilares(kilometresNum * operand.kilometresNum)
  override def divByLength(operand: Length): Double = lightYearsNum / operand.lightYearsNum
  override def nonNeg: Boolean = lightYearsNum >= 0
  override def pos: Boolean = lightYearsNum > 0
  override def neg: Boolean = lightYearsNum < 0
  inline override def toPicometreFactor: Double = toMetresFactor * 1e12
  inline override def toMetresFactor: Double = 9460730472580800l
  override def toKilometresFactor: Double = 9460730472580.8
  override def compare(that: Length): Int = lightYearsNum match
  { case n if n > that.lightYearsNum => 1
    case n if n == that.lightYearsNum => 0
    case _ => -1
  }
}

object LightYears
{ /** Factory apply method for [[LightYears]]. */
  def apply(lighyYearsNum: Double): LightYears = new LightYears(lighyYearsNum)
}