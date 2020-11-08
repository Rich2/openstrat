/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Golden rectangle, a rectangle whose side lengths are in the golden ratio, 1 : 1 + 5 2 {\displaystyle 1:{\tfrac {1+{\sqrt {5}}}{2}}} 1:{\tfrac
 *  {1+{\sqrt {5}}}{2}}, which is 1 : φ {\displaystyle 1:\varphi } 1:\varphi (the Greek letter phi), where φ {\displaystyle \varphi } \varphi is
 *  approximately 1.618. Golden rectangles exhibit a special form of self-similarity: All rectangles created by adding or removing a square are Golden
 *  rectangles as well. A method to construct a golden rectangle. Owing to the Pythagorean theorem,[a] the diagonal dividing one half of a square
 *  equals the radius of a circle whose outermost point is also the corner of a golden rectangle added to the square. */
trait PhiRectangle extends Rectangle
{
  def width1: Double = width2 * Phi

  /** Translate geometric transformation on a PhiRectangle returns a PhiRectangle. */
  //override def slate(offset: Vec2Like): PhiRectangle = PhiRectangle.s1s3(s1Cen + offset, s3Cen + offset)

  /** Translate geometric transformation on a PhiRectangle returns a PhiRectangle. */
  override def slate(xOffset: Double, yOffset: Double): PhiRectangle = PhiRectangle.s1s3(s1Cen.addXY(xOffset, yOffset), s3Cen.addXY(xOffset, yOffset))

  /** Uniform scaling transformation on a PhiRectangle returns a PhiRectangle. */
  override def scale(operand: Double): PhiRectangle = PhiRectangle.s1s3(s1Cen * operand, s3Cen * operand)

  /** Mirror, reflection transformation across the X axis on a PhiRectangle, returns a PhiRectangle. */
  override def negY: PhiRectangle = PhiRectangle.s1s3(s1Cen.negY, s3Cen.negY)

  /** Mirror, reflection transformation across the X axis on a PhiRectangle, returns a PhiRectangle. */
  override def negX: PhiRectangle = PhiRectangle.s1s3(s1Cen.negX, s3Cen.negX)

  override def prolign(matrix: ProlignMatrix): PhiRectangle = PhiRectangle.s1s3(s1Cen.prolign(matrix), s3Cen.prolign(matrix))

  override def reflect(lineLike: LineLike): PhiRectangle = PhiRectangle.s1s3(s1Cen.reflect(lineLike), s3Cen.reflect(lineLike))

  override def rotate(angle: Angle): PhiRectangle = PhiRectangle.s1s3(s1Cen.rotate(angle), s3Cen.rotate(angle))

  override def slateTo(newCen: Pt2): PhiRectangle =
  { val v = newCen.vecFrom(cen)
    slate(v.x, v.y)
  }
}

/** Companion object for the PhiRectangle trait. It contains the [[PhiRectangle.PhiRectangleImp]] implementation class an apply factory method that
 *  delegates to it. */
object PhiRectangle
{
  def apply(height: Double, rotation: Angle, cen: Pt2): PhiRectangle =
  { val s1Cen: Pt2 = cen + Pt2(0, height / 2).rotate(rotation)
    val s3Cen: Pt2 = cen + Pt2(0, -height / 2).rotate(rotation)
    PhiRectangleImp(s1Cen.x, s1Cen.y, s3Cen.x, s3Cen.y)
  }

  def s1s3(s1Cen: Pt2, s3Cen: Pt2): PhiRectangle = PhiRectangleImp(s1Cen.x, s1Cen.y, s3Cen.x, s3Cen.y)

  case class PhiRectangleImp(xS1Cen: Double, yS1Cen: Double, xS3Cen: Double, yS3Cen: Double) extends PhiRectangle
  {
    override def xCen: Double = (xS1Cen + xS1Cen) / 2
    override def yCen: Double = (yS1Cen + yS1Cen) / 2
    override def s1Cen: Pt2 = Pt2(xS1Cen, yS1Cen)
    override def s3Cen: Pt2 = Pt2(xS3Cen, yS3Cen)
    override def width2: Double = s1Cen.distTo(s3Cen)
    override def alignAngle: Angle = s1Cen.angleFrom(s3Cen) - Deg90
    override def v1: Pt2 = s1Cen + Pt2(width2 / 2, 0).rotate(alignAngle)
    override def x1: Double = v1.x
    override def y1: Double = v1.y
    override def v2: Pt2 = s3Cen + Pt2(width2 / 2, 0).rotate(alignAngle)
    override def x2: Double = v2.x
    override def y2: Double = v2.y
    override def v3: Pt2 = s3Cen + Pt2(-width2 / 2, 0).rotate(alignAngle)
    override def x3: Double = v2.x
    override def y3: Double = v2.y
    override def v4: Pt2 = s1Cen + Pt2(-width2 / 2, 0).rotate(alignAngle)
    override def x4: Double = v2.x
    override def y4: Double = v2.y
    override def s2Cen: Pt2 = s1Cen mid s2Cen
    override def s4Cen: Pt2 = s3Cen mid s4Cen
  }
}

case class PhiRect(height: Double, xCen: Double, yCen: Double) extends Rect with PhiRectangle
{
  override def width: Double = width1
  override def width2: Double = height
  /** Translate geometric transformation on a PhiRect returns a PhiRect. */
  //override def slate(offset: Vec2Like): PhiRect = PhiRect(height, cen + offset)

  /** Translate geometric transformation on a PhiRect returns a PhiRect. */
  override def slate(xOffset: Double, yOffset: Double): PhiRect = PhiRect(height, xCen + xOffset, yCen + yOffset)

  /** Uniform scaling transformation on a PhiRect returns a PhiRect. */
  override def scale(operand: Double): PhiRect = PhiRect(height * operand, cen * operand)

  /** Mirror, reflection transformation across the X axis on a PhiRect, returns a PhiRect. */
  override def negY: PhiRect = PhiRect(height, cen.negY)

  /** Mirror, reflection transformation across the X axis on a PhiRect, returns a PhiRect. */
  override def negX: PhiRect = PhiRect(height, cen.negX)

  override def prolign(matrix: ProlignMatrix): PhiRect = ??? // PhiRectangle.s1s3(s1Cen.prolign(matrix), s3Cen.prolign(matrix))

  override def slateTo(newCen: Pt2): PhiRect =
  { val v = cen.vecTo(newCen)
    slate(v.x, v.y)
  }
}

object PhiRect
{
  def apply(height: Double, cen: Pt2 = Vec2Z): PhiRect = PhiRect(height, cen.x, cen.y)
}

case class PhiRectY(width: Double, xCen: Double, yCen: Double) extends Rect with PhiRectangle
{
  override def height: Double = width1
  override def width2: Double = width
  /** Translate geometric transformation on a PhiRectY returns a PhiRectY. */
  //override def slate(offset: Vec2Like): PhiRectY = PhiRectY(width, cen + offset)

  /** Translate geometric transformation on a PhiRectY returns a PhiRectY. */
  override def slate(xOffset: Double, yOffset: Double): PhiRectY = PhiRectY(width, xCen + xOffset, yCen + yOffset)

  /** Uniform scaling transformation on a PhiRectY returns a PhiRectY. */
  override def scale(operand: Double): PhiRectY = PhiRectY(width * operand, cen * operand)

  /** Mirror, reflection transformation across the X axis on a PhiRectY, returns a PhiRectY. */
  override def negY: PhiRectY = PhiRectY(width, cen.negY)

  /** Mirror, reflection transformation across the X axis on a PhiRectY, returns a PhiRectY. */
  override def negX: PhiRectY = PhiRectY(width, cen.negX)

  override def prolign(matrix: ProlignMatrix): PhiRectY = ??? // PhiRectYangle.s1s3(s1Cen.prolign(matrix), s3Cen.prolign(matrix))

  override def slateTo(newCen: Pt2): PhiRectY =
  { val v = cen.vecTo(newCen)
    slate(v.x, v.y)
  }
}

object PhiRectY
{
  def apply(width: Double, cen: Pt2 = Vec2Z): PhiRectY = PhiRectY(width, cen.x, cen.y)
}
