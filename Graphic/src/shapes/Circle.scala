/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom



final case class CircleGen(radius: Double, xCen: Double, yCen: Double) extends Circle //GeomElemNew with Ellipse
{ /** This is wong. */
  override def fTrans(f: Vec2 => Vec2): GeomElemNew = { deb("This is wrong."); CircleGen(radius, f(cen)) }
  
  override def slate(offset: Vec2): CircleGen = CircleGen(radius, cen + offset)

  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): CircleGen = CircleGen(radius, xCen + xOffset, yCen + yOffset)

  override def scale(operand: Double): CircleGen = CircleGen(radius * operand, cen * operand)

  override def mirrorXOffset(yOffset: Double): CircleGen = CircleGen(radius, cen.mirrorXOffset(yOffset))

  override def mirrorX: CircleGen = CircleGen(radius, xCen, -yCen)

  override def mirrorYOffset(xOffset: Double): CircleGen = CircleGen(radius, cen.mirrorYOffset(xOffset))

  override def mirrorY: CircleGen = CircleGen(radius, -xCen, yCen)

  override def prolign(matrix: ProlignMatrix): CircleGen = CircleGen(radius * matrix.vFactor, cen.prolignTrans(matrix))

  override def fill(colour: Colour): GraphicElemNew = ???
}

/** This object provides factory methods for circles. */
object CircleGen
{ def apply(radius: Double, xCen: Double, yCen: Double): CircleGen = new CircleGen(radius, xCen, yCen)
  def apply(radius: Double, cen: Vec2 = Vec2Z): CircleGen = new CircleGen(radius, cen.x, cen.y)
  //implicit val slateImplicit: Slate[CircleGen] = (CircleGen, offset) => CircleGen.slate(offset)
}

trait Circle extends GeomElemNew with Ellipse
{
  def radius: Double
  @inline final def diameter: Double = radius * 2
  override def mirrorXOffset(yOffset: Double): Circle
  override def mirrorYOffset(xOffset: Double): Circle
  override def slate(offset: Vec2): Circle
  override def slate(xOffset: Double, yOffset: Double): Circle
  override def scale(operand: Double): Circle
  override def prolign(matrix: ProlignMatrix): Circle
}

/** This object provides factory methods for circles. */
object Circle extends Circle
{ override def xCen: Double = 0
  override def yCen: Double = 0
  override def radius: Double = 0.5

  override def slate(offset: Vec2): CircleGen = CircleGen(offset.x, offset.y, 0.5)
  override def slate(xOffset: Double, yOffset: Double): CircleGen = CircleGen(xOffset, yOffset, 0.5)

  override def scale(operand: Double): Circle = ???
  override def mirrorX: Circle.type =Circle
  override def mirrorY: Circle.type = Circle
  override def mirrorXOffset(yOffset: Double): CircleGen = Circle(0, -2 * yOffset, 0.5)
  override def mirrorYOffset(xOffset: Double): CircleGen = Circle(-2 * xOffset, 0,  0.5)

  override def prolign(matrix: ProlignMatrix): Circle = ???

  override def productArity: Int = 0

  override def productElement(n: Int): Any = ???

  override def fTrans(f: Vec2 => Vec2): GeomElemNew = ???
  def apply(radius: Double, xCen: Double, yCen: Double): CircleGen = new CircleGen(radius, xCen, yCen)
  def apply(radius: Double, cen: Vec2 = Vec2Z): CircleGen = new CircleGen(radius, cen.x, cen.y)

  override def fill(colour: Colour): GraphicElem = ???
  implicit val slateImplicit: Slate[CircleGen] = (CircleGen, offset) => CircleGen.slate(offset)
}
