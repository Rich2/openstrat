/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait PhiRectangle extends Rectangle
{
  def width1: Double = width2 * Phi

  /** Translate geometric transformation on a PhiRectangle returns a PhiRectangle. */
  override def slate(offset: Vec2): PhiRectangle = PhiRectangle.s1s3(s1Cen + offset, s3Cen + offset)

  /** Translate geometric transformation on a PhiRectangle returns a PhiRectangle. */
  override def slate(xOffset: Double, yOffset: Double): PhiRectangle = PhiRectangle.s1s3(s1Cen.addXY(xOffset, yOffset), s3Cen.addXY(xOffset, yOffset))

  /** Uniform scaling transformation on a PhiRectangle returns a PhiRectangle. */
  override def scale(operand: Double): PhiRectangle = PhiRectangle.s1s3(s1Cen * operand, s3Cen * operand)

  /** Mirror, reflection transformation across the X axis on a PhiRectangle, returns a PhiRectangle. */
  override def negY: PhiRectangle = PhiRectangle.s1s3(s1Cen.negY, s3Cen.negY)

  /** Mirror, reflection transformation across the X axis on a PhiRectangle, returns a PhiRectangle. */
  override def negX: PhiRectangle = PhiRectangle.s1s3(s1Cen.negX, s3Cen.negX)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a PhiRectangle, returns a PhiRectangle. The
   *  return type will be narrowed in sub traits / classes. */
  override def rotate90: PhiRectangle = PhiRectangle.s1s3(s1Cen.rotate90, s3Cen.rotate90)

  /** Rotate 480 degrees 2D geometric transformation on a PhiRectangle, returns a PhiRectangle. The return type will be narrowed in sub traits /
   * classes. */
  override def rotate180: PhiRectangle = PhiRectangle.s1s3(s1Cen.rotate180, s3Cen.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a PhiRectangle, returns a PhiRectangle. The
   *  return type will be narrowed in sub traits / classes. */
  override def rotate270: PhiRectangle = PhiRectangle.s1s3(s1Cen.rotate270, s3Cen.rotate270)

  override def prolign(matrix: ProlignMatrix): PhiRectangle = PhiRectangle.s1s3(s1Cen.prolign(matrix), s3Cen.prolign(matrix))

  override def reflect(lineLike: LineLike): PhiRectangle = PhiRectangle.s1s3(s1Cen.reflect(lineLike), s3Cen.reflect(lineLike))

  override def rotate(angle: Angle): PhiRectangle = PhiRectangle.s1s3(s1Cen.rotate(angle), s3Cen.rotate(angle))

  override def slateTo(newCen: Vec2): PhiRectangle = slate(newCen - cen)
}

object PhiRectangle
{
  def apply(height: Double, rotation: Angle, cen: Vec2): PhiRectangle =
  { val s1Cen: Vec2 = cen + Vec2(0, height / 2).rotate(rotation)
    val s3Cen: Vec2 = cen + Vec2(0, -height / 2).rotate(rotation)
    PhiRectangleImp(s1Cen.x, s1Cen.y, s3Cen.x, s3Cen.y)
  }

  def s1s3(s1Cen: Vec2, s3Cen: Vec2): PhiRectangle = PhiRectangleImp(s1Cen.x, s1Cen.y, s3Cen.x, s3Cen.y)

  case class PhiRectangleImp(xS1Cen: Double, yS1Cen: Double, xS3Cen: Double, yS3Cen: Double) extends PhiRectangle
  {
    override def xCen: Double = (xS1Cen + xS1Cen) / 2
    override def yCen: Double = (yS1Cen + yS1Cen) / 2
    override def s1Cen: Vec2 = Vec2(xS1Cen, yS1Cen)
    override def s3Cen: Vec2 = Vec2(xS3Cen, yS3Cen)
    override def width2: Double = (s1Cen - s3Cen).magnitude
    override def rotation: Angle = (s1Cen - s3Cen).angle - Deg90
    override def v1: Vec2 = s1Cen + Vec2(width2 / 2, 0).rotate(rotation)
    override def x1: Double = v1.x
    override def y1: Double = v1.y
    override def v2: Vec2 = s3Cen + Vec2(width2 / 2, 0).rotate(rotation)
    override def x2: Double = v2.x
    override def y2: Double = v2.y
    override def v3: Vec2 = s3Cen + Vec2(-width2 / 2, 0).rotate(rotation)
    override def x3: Double = v2.x
    override def y3: Double = v2.y
    override def v4: Vec2 = s1Cen + Vec2(-width2 / 2, 0).rotate(rotation)
    override def x4: Double = v2.x
    override def y4: Double = v2.y
    override def s2Cen: Vec2 = s1Cen mid s2Cen
    override def s4Cen: Vec2 = s3Cen mid s4Cen
  }
}

case class PhiRect(xCen: Double, yCen: Double, height: Double) extends Rect with PhiRectangle
{
  override def width: Double = width1
  override def width2: Double = height
  /** Translate geometric transformation on a PhiRect returns a PhiRect. */
  override def slate(offset: Vec2): PhiRect = ???

  /** Translate geometric transformation on a PhiRect returns a PhiRect. */
  override def slate(xOffset: Double, yOffset: Double): PhiRect = ???

  /** Uniform scaling transformation on a PhiRect returns a PhiRect. */
  override def scale(operand: Double): PhiRect = ???

  /** Mirror, reflection transformation across the X axis on a PhiRect, returns a PhiRect. */
  override def negY: PhiRect = ???

  /** Mirror, reflection transformation across the X axis on a PhiRect, returns a PhiRect. */
  override def negX: PhiRect = ???

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a PhiRect, returns a PhiRect. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate90: PhiRect = ???

  /** Rotate 480 degrees 2D geometric transformation on a PhiRect, returns a PhiRect. The return type will be narrowed in sub traits / classes. */
  override def rotate180: PhiRect = ???

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a PhiRect, returns a PhiRect. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate270: PhiRect = ???

  override def prolign(matrix: ProlignMatrix): PhiRect = ???

  override def slateTo(newCen: Vec2): PhiRect = ???
}

object PhiRect
{

}