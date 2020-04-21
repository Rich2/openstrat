/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This object provides factory methods for circles. */
object Circle
{   
  def apply(scale: Double, cen: Vec2 = Vec2Z): PolyCurveCentred = PolyCurveCentred(cen, segs(scale).slate(cen))
  def apply(scale: Double, xCen: Double, yCen: Double): PolyCurveCentred = apply(scale, Vec2(xCen, yCen))
  
  def segs(scale: Double = 1.0): PolyCurve =
  { val a = ArcSeg(Vec2Z, Vec2(0.5 * scale, 0))
    val sg1 = (1 to 4).map(i => (a.rotate(Angle(- math.Pi / 2 * i))))
    PolyCurve(sg1 :_*)
  }

  def fill(radius: Double, colour: Colour, posn: Vec2 = Vec2Z): PolyCurveFill =
  { val fSegs = segs(radius).slate(posn)            
    PolyCurveFill(fSegs, colour)
  }
} 