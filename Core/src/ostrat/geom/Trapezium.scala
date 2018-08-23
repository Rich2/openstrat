/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

object Trapezium {
  
}

object TrapezoidIsosceles
{
   def apply(baseWidth: Double, topWidth: Double, height: Double): Vec2s =
      Vec2s(-topWidth /2  vv height/2,
            topWidth/2    vv height / 2,
            baseWidth/2   vv - height/2,
            - baseWidth/2 vv - height/2)
}

object Diamond
{
   def apply() = Vec2s(
         0 vv 0.5,
         Tan30 / 2 vv 0,
         0 vv - 0.5,
         -Tan30/2 vv 0)
}