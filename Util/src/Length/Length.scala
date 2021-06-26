/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A quantity or measurement of length. The final classes include [[Metre]], [[KMetres]]. The purpose of the separate classes is for displaying
 *  values. 100M and 0.1KMetres have the same value but will be displayed differently. */
trait Length extends Any
{ /** The value of this length expressed metres. */
  def metres: Double
  def +(operand: Length): Length
  def -(operand: Length): Length
  def unary_- : Length
  def *(operand: Double): Length
  def /(operand: Double): Length
  def kMetres: Double = metres / 1000
}

object Length
{
  implicit class LengthExtensions(val thisLength: Length)
  { def / (operand: Length): Double = thisLength.metres / operand.metres
  }
}

final class KMetres(override val kMetres: Double) extends AnyVal with Length
{ override def metres: Double = kMetres * 1000
  override def + (operand: Length): KMetres = KMetres(kMetres + operand.metres)
  override def - (operand: Length): KMetres = KMetres(kMetres - operand.metres)
  override def unary_- : KMetres = KMetres(-kMetres)
  override def *(operand: Double): KMetres = KMetres(kMetres * operand)
  override def /(operand: Double) : KMetres = KMetres(kMetres / operand)
}

object KMetres
{ def apply(kMetres: Double): KMetres = new KMetres(kMetres)
}

/** Length measure in GigaMetres or millions of kilometres. */
final class GMetres(override val kMetres: Double) extends AnyVal with Length
{
  override def metres: Double = kMetres * 1000
  override def + (operand: Length): KMetres = KMetres(kMetres + operand.metres)
  override def - (operand: Length): KMetres = KMetres(kMetres - operand.metres)
  override def unary_- : KMetres = KMetres(-kMetres)
  override def *(operand: Double): KMetres = KMetres(kMetres * operand)
  override def /(operand: Double) : KMetres = KMetres(kMetres / operand)
}

/** Companion object for the GMetres GigaMeters (1000s of KMs) class contains apply factory method. */
object GMetres
{ /** Apply factory for GMetres. */
  def apply(gMetres: Double): GMetres = new GMetres(gMetres)
}