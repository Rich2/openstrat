/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

final case class CircleOld(radius: Double, x: Double, y: Double) extends AffineElem
{ override type SimerT = CircleOld
  override def fTrans(f: Vec2 => Vec2): CircleOld = CircleOld(radius, f(vCen))
  def vCen: Vec2 = x vv y
  override def shear(xScale: Double, yScale: Double): CircleOld = ??? // new EllipseGen(x, y, x + radius, 0, radius)
  override def rotateRadians(radians: Double): CircleOld = CircleOld(radius, vCen.rotateRadians(radians))
  override def slate(offset: Vec2): CircleOld = CircleOld(radius, x + offset.x, y + offset.y)
  override def scale(operand: Double): CircleOld = CircleOld(radius * operand, x * operand, y * operand)

  override def mirror(line: Line2): CircleOld = CircleOld(radius, vCen.mirror(line))

  def fill(colour: Colour): CircleFillOld = CircleFillOld(this, colour)
  def draw(lineWidth: Double = 2, colour: Colour): CircleDrawOld = CircleDrawOld(this, lineWidth, colour)
  def fillDraw(fillColour: Colour, lineWidth: Double = 2, lineColour: Colour): CircleFillDraw =
    CircleFillDraw(this, fillColour, lineWidth, lineColour)

 // override def mirrorX: CircleOld = ???
}

/** This object provides factory methods for circles. */
object CircleOld
{
  def apply(radius: Double, cen: Vec2 =Vec2Z): CircleOld = new CircleOld(radius, cen.x, cen.y)

  def segs(scale: Double = 1.0): PolyCurve =
  { val a = ArcSeg(Vec2Z, Vec2(0.5 * scale, 0))
    val sg1 = (1 to 4).map(i => (a.rotate(Angle(- math.Pi / 2 * i))))
    PolyCurve(sg1 :_*)
  }

  def fillNew(colour: Colour): Unit = ???

  def fill(radius: Double, colour: Colour, posn: Vec2 = Vec2Z): PolyCurveFill =
  { val fSegs = segs(radius).slate(posn)
    PolyCurveFill(fSegs, colour)
  }
}
