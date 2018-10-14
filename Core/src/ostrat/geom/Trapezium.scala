/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Probably worth keeping */
object Trapezium
{  
}

object TrapezoidIsosceles
{
  def apply(baseWidth: Double, topWidth: Double, height: Double): Polygon = Polygon(
      -topWidth /2  vv height/2,
      topWidth/2    vv height / 2,
      baseWidth/2   vv - height/2,
      - baseWidth/2 vv - height/2)
}

object Diamond
{
  def apply() = Polygon(
      0 vv 0.5,
      Tan30 / 2 vv 0,
      0 vv - 0.5,
      -Tan30/2 vv 0)
}