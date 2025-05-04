/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package geom

/** A trapezium or trapeezoid. */
class Trapezium(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double, val v3x: Double, val v3y: Double) extends
  QuadrilateralFields
{ override def typeStr: String = "Trapezium"
}

/** Probably worth keeping */
object Trapezium
{  
}

object TrapezoidIsosceles
{
  def apply(baseWidth: Double, topWidth: Double, height: Double): Polygon = Polygon.dbls(
      -topWidth /2, height/2,
      topWidth/2, height / 2,
      baseWidth/2, -height/2,
      - baseWidth/2, -height/2)
}