/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
  
  override def slate(xOperand: Double, yOperand: Double): PhiRectangle = PhiRectangle.s1s3(sd0Cen.slate(xOperand, yOperand), sd2Cen.slate(xOperand, yOperand))
  override def slate(operand: VecPt2): PhiRectangle = PhiRectangle.s1s3(sd0Cen.slate(operand), sd2Cen.slate(operand))
  override def scale(operand: Double): PhiRectangle = PhiRectangle.s1s3(sd0Cen.scale(operand), sd2Cen.scale(operand))
  override def negX: PhiRectangle = PhiRectangle.s1s3(sd0Cen.negX, sd2Cen.negX)  
  override def negY: PhiRectangle = PhiRectangle.s1s3(sd0Cen.negY, sd2Cen.negY)
  override def prolign(matrix: ProlignMatrix): PhiRectangle = PhiRectangle.s1s3(sd0Cen.prolign(matrix), sd2Cen.prolign(matrix))
  override def reflect(lineLike: LineLike): PhiRectangle = PhiRectangle.s1s3(sd0Cen.reflect(lineLike), sd2Cen.reflect(lineLike))
  override def rotate(rotation: AngleVec): PhiRectangle = PhiRectangle.s1s3(sd0Cen.rotate(rotation), sd2Cen.rotate(rotation))
}

/** Companion object for the PhiRectangle trait. It contains the [[PhiRectangle.PhiRectangleGen]] implementation class an apply factory method that delegates to
 * it. */
object PhiRectangle
{
  def apply(height: Double, rotation: AngleVec, cen: Pt2): PhiRectangle =
  { val rtVec = rotation.toVec(height * Phi / 2)
    val upVec = (rotation + 90.degsVec).toVec(height / 2)
    val array = Rectangle.unsafeVecsCen(rtVec, upVec, cen)
    new PhiRectangleGen(array)
  }

  def s1s3(s1Cen: Pt2, s3Cen: Pt2): PhiRectangle = ???//PhiRectangleImp(s1Cen.x, s1Cen.y, s3Cen.x, s3Cen.y)

  class PhiRectangleGen(val arrayUnsafe: Array[Double]) extends PhiRectangle, PolygonLikeDbl2[Pt2], Pt2SeqSpec
  { override type ThisT = PhiRectangleGen
    override def fromArray(array: Array[Double]): PhiRectangleGen = new PhiRectangleGen(array)
    
    

    override def width2: Double = sd0Cen.distTo(sd2Cen)

    override def cenX: Double = v0x \/ v2x
    override def cenY: Double = v0y \/ v2y

    override def v0x: Double = arrayUnsafe(0)
    override def v0y: Double = arrayUnsafe(1)
    override def v0: Pt2 = Pt2(arrayUnsafe(0), arrayUnsafe(1))
    override def vLastX: Double = arrayUnsafe(numVerts - 2)
    override def vLastY: Double = arrayUnsafe(numVerts - 1)
    override def vLast: Pt2 = Pt2(vLastX, vLastY)
    override def side0: LineSeg = LineSeg(v0x, v0y, vertX(1), vertY(1))
    override def sd0CenX: Double = v0x \/ vertX(1)
    override def sd0CenY: Double = v0y \/ vertY(1)
    override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
    override def vertX(index: Int): Double = arrayUnsafe(index * 2)
    override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)
    override def unsafeNegX: Array[Double] = arrayD1Map(d => -d)
    override def unsafeNegY: Array[Double] = arrayD2Map(d => -d)
    override def sides: LineSegArr = new LineSegArr(arrayForSides)
  }
}

class PhiRect(val width: Double) extends Rect, PhiRectangle, PolygonLikeDbl2[Pt2]//, Pt2SeqSpec
{ override type ThisT = PhiRect
  override def fromArray(array: Array[Double]): PhiRect = ??? // new PhiRect(array)

  override def typeStr: String = "PhiRect"

  def height: Double = ???

  override def vertOrder: Int = ???

  /** Constructs a [[Dbl2Elem]] from 2 [[Double]]s. */
  override def elemFromDbls(d1: Double, d2: Double): Pt2 = ???

  //override def width: Double = width1
  override def width2: Double = height

  override def cenX: Double = v0x \/ v2x
  override def cenY: Double = v0y \/ v2y

  override def slate(xOperand: Double, yOperand: Double): PhiRect = PhiRect(height, cenX + xOperand, cenY + yOperand)
  override def slate(operand: VecPt2): PhiRect = PhiRect(height, cen.slate(operand))
  override def scale(operand: Double): PhiRect = PhiRect(height * operand, cen.scale(operand))
  override def negX: PhiRect = PhiRect(height, cen.negX)
  override def negY: PhiRect = PhiRect(height, cen.negY)
  override def prolign(matrix: ProlignMatrix): PhiRect = ??? // PhiRectangle.s1s3(s1Cen.prolign(matrix), s3Cen.prolign(matrix))

  override def vLast: Pt2 = Pt2(vLastX, vLastY)
  override def side0: LineSeg = LineSeg(v0x, v0y, vertX(1), vertY(1))
  override def sd0CenX: Double = v0x \/ vertX(1)
  override def sd0CenY: Double = v0y \/ vertY(1)
  override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
  override def vertX(index: Int): Double = arrayUnsafe(index * 2)
  override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)
  override def unsafeNegX: Array[Double] = arrayD1Map(d => -d)
  override def unsafeNegY: Array[Double] = arrayD2Map(d => -d)
}

object PhiRect
{
  def apply(height: Double, cen: Pt2 = Pt2Z): PhiRect = ???// PhiRect(height, cen.x, cen.y)
  def apply(height: Double, cenX: Double, cenY: Double): PhiRect = ???
}

/** Not sure what this class is. */
final class PhiRectY(val width: Double) extends Rect, PhiRectangle, PolygonLikeDbl2[Pt2]//, Pt2SeqSpec
{ override type ThisT = PhiRectY
  override def fromArray(array: Array[Double]): PhiRectY = ??? // new PhiRectY(array)
  override def typeStr: String = "PhiRectY"

  //def width: Double = ???

  override def height: Double = width1
  override def width2: Double = width

  override def vertOrder: Int = ???

  /** Constructs a [[Dbl2Elem]] from 2 [[Double]]s. */
  override def elemFromDbls(d1: Double, d2: Double): Pt2 = ???

  override def cenX: Double = v0x \/ v2x
  override def cenY: Double = v0y \/ v2y

  override def slate(xOperand: Double, yOperand: Double): PhiRectY = PhiRectY(width, cenX + xOperand, cenY + yOperand)
  override def slate(operand: VecPt2): PhiRectY = PhiRectY(width, cen.slate(operand))
  override def scale(operand: Double): PhiRectY = PhiRectY(width * operand, cen.scale(operand))
  override def negX: PhiRectY = PhiRectY(width, cen.negX)  
  override def negY: PhiRectY = PhiRectY(width, cen.negY)
  override def prolign(matrix: ProlignMatrix): PhiRectY = ??? // PhiRectYangle.s1s3(s1Cen.prolign(matrix), s3Cen.prolign(matrix))

  /*override def slateTo(newCen: Pt2): PhiRectY =
  { val v = cen.vecTo(newCen)
    slate(v.x, v.y)
  }*/

  override def vLast: Pt2 = Pt2(vLastX, vLastY)
  override def side0: LineSeg = LineSeg(v0x, v0y, vertX(1), vertY(1))
  override def sd0CenX: Double = v0x \/ vertX(1)
  override def sd0CenY: Double = v0y \/ vertY(1)
  override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
  override def vertX(index: Int): Double = arrayUnsafe(index * 2)
  override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)
  override def unsafeNegX: Array[Double] = arrayD1Map(d => -d)
  override def unsafeNegY: Array[Double] = arrayD2Map(d => -d)

}

object PhiRectY
{
  def apply(width: Double, cen: Pt2 = Pt2Z): PhiRectY = ???// PhiRectY(width, cen.x, cen.y)
  def apply(width: Double, cenX: Double, cenY: Double): PhiRectY = ???
}