/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait Pt3Length
{
  def xMetres: Double
  def yMetres: Double
  def zMetres: Double
}

final class Pt3Ms(val xMetres: Double, val yMetres: Double, val zMetres: Double) extends Pt3Length


final class Pt3KMs(val xKMetres: Double, val yKMetres: Double, val zKMetres: Double) extends Pt3Length
{
  override def xMetres: Double = xMetres * 1000
  override def yMetres: Double = xMetres * 1000
  override def zMetres: Double = xMetres * 1000
}