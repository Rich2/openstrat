/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv._

object CircleIcon

final case class Circle(radius: Double, x: Double, y: Double) extends TransSimer
{ override type ThisT = Circle
  override def rotate(angle: Angle): Circle = this
  override def mirrorX: Circle = this
  override def mirrorY: Circle = this
  override def mirrorXOffset(yOffset: Double): Circle = this
  override def mirrorYOffset(xOffset: Double): Circle = this
  override def rotateRadians(radians: Double): Circle = this
  override def slate(offset: Vec2): Circle = Circle(radius, x + offset.x, y + offset.y)
  override def scale(operand: Double): Circle = Circle(radius * operand, x, y)

  def fill(colour: Colour): CircleFill = CircleFill(this, colour)
}

/** This object provides factory methods for circles. */
object Circle
{   
  //def apply(scale: Double, cen: Vec2 = Vec2Z): PolyCurveCentred = PolyCurveCentred(cen, segs(scale).slate(cen))
  //def apply(scale: Double, xCen: Double, yCen: Double): PolyCurveCentred = apply(scale, Vec2(xCen, yCen))
  def apply(radius: Double, cen: Vec2 =Vec2Z): Circle = new Circle(radius, cen.x, cen.y)

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

case class CircleFill(circle: Circle, colour: Colour) extends TransSimerUser with PaintElem
{ override type ThisT = CircleFill
  override type MemT = Circle
  override def geomMem: MemT = circle
  override def newThis(transer: Circle): CircleFill = CircleFill(transer, colour)

  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)
}