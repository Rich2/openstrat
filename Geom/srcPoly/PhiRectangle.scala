/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Golden rectangle, a rectangle whose side lengths are in the golden ratio, 1 : 1 + 5 2 {\displaystyle 1:{\tfrac {1+{\sqrt {5}}}{2}}} 1:{\tfrac
 *  {1+{\sqrt {5}}}{2}}, which is 1 : φ {\displaystyle 1:\varphi } 1:\varphi (the Greek letter phi), where φ {\displaystyle \varphi } \varphi is
 *  approximately 1.618. Golden rectangles exhibit a special form of self-similarity: All rectangles created by adding or removing a square are Golden
 *  rectangles as well. A method to construct a golden rectangle. Owing to the Pythagorean theorem,[a] the diagonal dividing one half of a square
 *  equals the radius of a circle whose outermost point is also the corner of a golden rectangle added to the square. */
trait PhiRectangle extends Rectangle
{ type ThisT <: PhiRectangle
  override def typeStr: String = "PhiRectangle"
  //def width1: Double = width2 * Phi

  /** Translate geometric transformation on a PhiRectangle returns a PhiRectangle. */
  override def slateXY(xDelta: Double, yDelta: Double): PhiRectangle = PhiRectangle.s1s3(sd0Cen.addXY(xDelta, yDelta), sd2Cen.addXY(xDelta, yDelta))

  /** Translate geometric transformation on a PhiRectangle returns a PhiRectangle. */
  override def slate(offset: Vec2Like): PhiRectangle = PhiRectangle.s1s3(sd0Cen.slate(offset), sd2Cen.slate(offset))

  /** Uniform scaling transformation on a PhiRectangle returns a PhiRectangle. */
  override def scale(operand: Double): PhiRectangle = PhiRectangle.s1s3(sd0Cen.scale(operand), sd2Cen.scale(operand))

  /** Mirror, reflection transformation across the X axis on a PhiRectangle, returns a PhiRectangle. */
  override def negY: PhiRectangle = PhiRectangle.s1s3(sd0Cen.negY, sd2Cen.negY)

  /** Mirror, reflection transformation across the X axis on a PhiRectangle, returns a PhiRectangle. */
  override def negX: PhiRectangle = PhiRectangle.s1s3(sd0Cen.negX, sd2Cen.negX)

  override def prolign(matrix: ProlignMatrix): PhiRectangle = PhiRectangle.s1s3(sd0Cen.prolign(matrix), sd2Cen.prolign(matrix))

  override def reflect(lineLike: LineLike): PhiRectangle = PhiRectangle.s1s3(sd0Cen.reflect(lineLike), sd2Cen.reflect(lineLike))

  override def rotate(angle: AngleVec): PhiRectangle = PhiRectangle.s1s3(sd0Cen.rotate(angle), sd2Cen.rotate(angle))
}

/** Companion object for the PhiRectangle trait. It contains the [[PhiRectangle.PhiRectangleImp]] implementation class an apply factory method that
 *  delegates to it. */
object PhiRectangle
{
  def apply(height: Double, rotation: AngleVec, cen: Pt2): PhiRectangle =
  { val rtVec = rotation.toVec(height * Phi / 2)
    val upVec = (rotation + 90.degsVec).toVec(height / 2)
    val array = Rectangle.unsafeVecsCen(rtVec, upVec, cen)
    new PhiRectangleImp(array)
  }

  def s1s3(s1Cen: Pt2, s3Cen: Pt2): PhiRectangle = ???//PhiRectangleImp(s1Cen.x, s1Cen.y, s3Cen.x, s3Cen.y)

  class PhiRectangleImp(val unsafeArray: Array[Double]) extends PhiRectangle
  { override type ThisT = PhiRectangleImp
    override def fromArray(array: Array[Double]): PhiRectangleImp = new PhiRectangleImp(array)

    override def width2: Double = sd0Cen.distTo(sd2Cen)
  }
}

class PhiRect(val unsafeArray: Array[Double]) extends Rect with PhiRectangle
{ override type ThisT = PhiRect
  override def fromArray(array: Array[Double]): PhiRect = new PhiRect(array)

  override def typeStr: String = "PhiRect"

  def height: Double = ???
  override def width: Double = width1
  override def width2: Double = height

  /** Translate geometric transformation on a PhiRect returns a PhiRect. */
  override def slateXY(xDelta: Double, yDelta: Double): PhiRect = PhiRect(height, cenX + xDelta, cenY + yDelta)

  /** Translate geometric transformation on a PhiRect returns a PhiRect. */
  override def slate(offset: Vec2Like): PhiRect = PhiRect(height, cen.slate(offset))

  /** Uniform scaling transformation on a PhiRect returns a PhiRect. */
  override def scale(operand: Double): PhiRect = PhiRect(height * operand, cen.scale(operand))

  /** Mirror, reflection transformation across the X axis on a PhiRect, returns a PhiRect. */
  override def negY: PhiRect = PhiRect(height, cen.negY)

  /** Mirror, reflection transformation across the X axis on a PhiRect, returns a PhiRect. */
  override def negX: PhiRect = PhiRect(height, cen.negX)

  override def prolign(matrix: ProlignMatrix): PhiRect = ??? // PhiRectangle.s1s3(s1Cen.prolign(matrix), s3Cen.prolign(matrix))
}

object PhiRect
{
  def apply(height: Double, cen: Pt2 = Pt2Z): PhiRect = ???// PhiRect(height, cen.x, cen.y)
  def apply(height: Double, cenX: Double, cenY: Double): PhiRect = ???
}

/** Not sure what this class is. */
final class PhiRectY(val unsafeArray: Array[Double]) extends Rect with PhiRectangle
{ override type ThisT = PhiRectY
  override def fromArray(array: Array[Double]): PhiRectY = new PhiRectY(array)
  override def typeStr: String = "PhiRectY"

  def width: Double = ???

  override def height: Double = width1
  override def width2: Double = width

  /** Translate geometric transformation on a PhiRectY returns a PhiRectY. */
  override def slateXY(xDelta: Double, yDelta: Double): PhiRectY = PhiRectY(width, cenX + xDelta, cenY + yDelta)

  /** Translate geometric transformation on a PhiRectY returns a PhiRectY. */
  override def slate(offset: Vec2Like): PhiRectY = PhiRectY(width, cen.slate(offset))

  /** Uniform scaling transformation on a PhiRectY returns a PhiRectY. */
  override def scale(operand: Double): PhiRectY = PhiRectY(width * operand, cen.scale(operand))

  /** Mirror, reflection transformation across the X axis on a PhiRectY, returns a PhiRectY. */
  override def negY: PhiRectY = PhiRectY(width, cen.negY)

  /** Mirror, reflection transformation across the X axis on a PhiRectY, returns a PhiRectY. */
  override def negX: PhiRectY = PhiRectY(width, cen.negX)

  override def prolign(matrix: ProlignMatrix): PhiRectY = ??? // PhiRectYangle.s1s3(s1Cen.prolign(matrix), s3Cen.prolign(matrix))

  /*override def slateTo(newCen: Pt2): PhiRectY =
  { val v = cen.vecTo(newCen)
    slate(v.x, v.y)
  }*/
}

object PhiRectY
{
  def apply(width: Double, cen: Pt2 = Pt2Z): PhiRectY = ???// PhiRectY(width, cen.x, cen.y)
  def apply(width: Double, cenX: Double, cenY: Double): PhiRectY = ???
}