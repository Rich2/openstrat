/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package geom

/** Probably worth keeping */
object Trapezium
{  
}

object TrapezoidIsosceles
{
  def apply(baseWidth: Double, topWidth: Double, height: Double): Polygon = Polygon.fromDbls(
      -topWidth /2, height/2,
      topWidth/2, height / 2,
      baseWidth/2, -height/2,
      - baseWidth/2, -height/2)
}