/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Probably worth keeping */
object Trapezium
{  
}

object TrapezoidIsosceles
{
  def apply(baseWidth: Double, topWidth: Double, height: Double): PolygonGen = PolygonGen(
      -topWidth /2  pp height/2,
      topWidth/2    pp height / 2,
      baseWidth/2   pp - height/2,
      - baseWidth/2 pp - height/2)
}