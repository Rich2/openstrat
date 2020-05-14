/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

object CircleIcon

final case class Circle(radius: Double, x: Double, y: Double) extends GeomElemNew
{ /** This is wong. */
  override def fTrans(f: Vec2 => Vec2): GeomElemNew = { deb("This is wrong."); Circle(radius, f(vCen)) }
  def vCen: Vec2 = x vv y

  override def slate(offset: Vec2): Circle = Circle(radius, vCen + offset)

  override def mirrorXOffset(yOffset: Double): GeomElemNew = Circle(radius, vCen.mirrorXOffset(yOffset))

  override def mirrorYOffset(xOffset: Double): GeomElemNew = Circle(radius, vCen.mirrorYOffset(xOffset))

  override def prolign(matrix: ProlignMatrix): Circle =
  { val xa = ife(matrix.negX, -x, x)
    val ya = ife(matrix.negY, -y, y)
    Circle(radius * matrix.vFactor, xa + matrix.xDelta, ya + matrix.yDelta)
  }
}

/** This object provides factory methods for circles. */
object Circle
{ def apply(radius: Double, cen: Vec2 = Vec2Z): Circle = new Circle(radius, cen.x, cen.y)
  implicit val slateImplicit: Slate[Circle] = (circle, offset) => circle.slate(offset)
}

final case class CircleOld(radius: Double, x: Double, y: Double) extends EllipseLike
{ //override type AlignT = Circle
  override def fTrans(f: Vec2 => Vec2): EllipseLike = ???
  def vCen: Vec2 = x vv y
  override def shear(xScale: Double, yScale: Double): Ellipse = new Ellipse(x, y, x + radius, 0, radius)
  override def rotateRadians(radians: Double): CircleOld = CircleOld(radius, vCen.rotateRadians(radians))
  override def slateOld(offset: Vec2): CircleOld = CircleOld(radius, x + offset.x, y + offset.y)
  override def scaleOld(operand: Double): CircleOld = CircleOld(radius * operand, x * operand, y * operand)

  override def mirror(line: Line2): CircleOld = CircleOld(radius, vCen.mirror(line))

  def fill(colour: Colour): CircleFill = CircleFill(this, colour)
  def draw(lineWidth: Double = 2, colour: Colour): CircleDraw = CircleDraw(this, lineWidth, colour)
  def fillDraw(fillColour: Colour, lineWidth: Double = 2, lineColour: Colour): CircleFillDraw =
    CircleFillDraw(this, fillColour, lineWidth, lineColour)
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
  { val fSegs = segs(radius).slateOld(posn)
    PolyCurveFill(fSegs, colour)
  }
}