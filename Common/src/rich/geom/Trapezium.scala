/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom

object Trapezium {
  
}

object TrapezoidIsosceles
{
   def apply(baseWidth: Double, topWidth: Double, height: Double): Vec2s =
      Vec2s.xy(-topWidth /2, height/2, topWidth/2, height / 2, baseWidth/2, - height/2, -baseWidth/2, - height/2)
}

object Diamond
{
   def apply() = Vec2s.xy(0, 0.5, Tan30 / 2, 0, 0, - 0.5, -Tan30/2, 0)
}