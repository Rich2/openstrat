/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package geom

trait Pt3LengthUnit
{ def xMetres: Double
  def yMetres: Double
  def zMetres: Double
}

final class Pt3Km(val xKMetres: Double, val yKMetres: Double, val zKMetres: Double) extends Pt3LengthUnit
{
  override def xMetres: Double = xMetres * 1000
  override def yMetres: Double = xMetres * 1000
  override def zMetres: Double = xMetres * 1000
}