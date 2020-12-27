/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A quantity or measurement of length. */
trait Length extends Any
{ /** The value of this length expressed metres. */
  def metres: Double
  def +(operand: Length): Length
  def kMetres: Double = metres / 1000
}

final class KMetres(override val kMetres: Double) extends AnyVal with Length
{
  override def metres: Double = kMetres * 1000
  override def + (operand: Length): KMetres = KMetres(kMetres + operand.metres)
}

object KMetres
{
  def apply(kMetres: Double): KMetres = new KMetres(kMetres)
}