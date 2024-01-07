/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Specialised Array based immutable collection class for [[Polygon]]s.  */
final class PolygonGenArr(val unsafeArrayOfArrays: Array[Array[Double]]) extends AnyVal with ArrArrayDbl[PolygonGen]
{ override type ThisT = PolygonGenArr
  override def typeStr: String = "PolygonArr"
  override def unsafeFromArrayArray(aad: Array[Array[Double]]): PolygonGenArr = new PolygonGenArr(aad)
  override def apply(index: Int): PolygonGen = new PolygonGen(unsafeArrayOfArrays(index))
  override def fElemStr: Polygon => String = _.toString
}

/** Companion object for the [[PolygonGenArr]] class. */
object PolygonGenArr
{
  def apply(input: PolygonGen*): PolygonGenArr =
  {
    val array: Array[Array[Double]] = new Array[Array[Double]](input.length)
    var count = 0

    while (count < input.length)
    { array(count) = input(count).unsafeArray
      count += 1
    }
    new PolygonGenArr(array)
  }

  implicit val eqImplicit: EqT[PolygonGenArr] = ArrArrayDblEq[PolygonGen, PolygonGenArr]

  implicit val slateImplicit: Slate[PolygonGenArr] = (obj: PolygonGenArr, dx: Double, dy: Double) => obj.map(_.slateXY(dx, dy))
  implicit val scaleImplicit: Scale[PolygonGenArr] = (obj: PolygonGenArr, operand: Double) => obj.map(_.scale(operand))
  implicit val rotateImplicit: Rotate[PolygonGenArr] = (obj: PolygonGenArr, angle: AngleVec) => obj.map(_.rotate(angle))
  implicit val prolignImplicit: Prolign[PolygonGenArr] = (obj, matrix) => obj.map(_.prolign(matrix))
  implicit val XYScaleImplicit: ScaleXY[PolygonGenArr] = (obj, xOperand, yOperand) => obj.map(_.scaleXY(xOperand, yOperand))
  implicit val reflectImplicit: Reflect[PolygonGenArr] = (obj: PolygonGenArr, lineLike: LineLike) => obj.map(_.reflect(lineLike))

  implicit val reflectAxesImplicit: TransAxes[PolygonGenArr] = new TransAxes[PolygonGenArr] {
    override def negYT(obj: PolygonGenArr): PolygonGenArr = obj.negY

    override def negXT(obj: PolygonGenArr): PolygonGenArr = obj.negX

    override def rotate90(obj: PolygonGenArr): PolygonGenArr = obj.rotate90

    override def rotate180(obj: PolygonGenArr): PolygonGenArr = obj.rotate180

    override def rotate270(obj: PolygonGenArr): PolygonGenArr = obj.rotate270
  }
}

class PolygonGenBuff(val unsafeBuffer: ArrayBuffer[Array[Double]]) extends AnyVal with BuffArrayDbl[PolygonGen]
{ override type ThisT = PolygonGenBuff
  override def typeStr: String = "PolygonBuff"
  override def fElemStr: PolygonGen => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonGen = new PolygonGen(array)
}

object PolygonGenBuff
{ def apply(initLen: Int = 4): PolygonGenBuff = new PolygonGenBuff(new ArrayBuffer[Array[Double]](initLen))
}