/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Specialised Array based immutable collection class for [[Polygon]]s.  */
final class PolygonArr(val unsafeArrayOfArrays: Array[Array[Double]]) extends AnyVal with ArrayDblArr[Polygon]
{ override type ThisT = PolygonArr
  override def typeStr: String = "PolygonArr"
  override def unsafeFromArrayArray(aad: Array[Array[Double]]): PolygonArr = new PolygonArr(aad)
  override def apply(index: Int): PolygonGen = new PolygonGen(unsafeArrayOfArrays(index))
  override def fElemStr: Polygon => String = _.toString
}

/** Companion object for the [[PolygonArr]] class. */
object PolygonArr
{
  def apply(input: PolygonGen*): PolygonArr =
  {
    val array: Array[Array[Double]] = new Array[Array[Double]](input.length)
    var count = 0

    while (count < input.length)
    { array(count) = input(count).unsafeArray
      count += 1
    }
    new PolygonArr(array)
  }

  implicit val eqImplicit: EqT[PolygonArr] = ArrArrayDblEq[Polygon, PolygonArr]

  implicit val slateImplicit: Slate[PolygonArr] = (obj: PolygonArr, dx: Double, dy: Double) => obj.map(_.slateXY(dx, dy))
  implicit val scaleImplicit: Scale[PolygonArr] = (obj: PolygonArr, operand: Double) => obj.map(_.scale(operand))
  implicit val rotateImplicit: Rotate[PolygonArr] = (obj: PolygonArr, angle: AngleVec) => obj.map(_.rotate(angle))
  implicit val prolignImplicit: Prolign[PolygonArr] = (obj, matrix) => obj.map(_.prolign(matrix))
  implicit val XYScaleImplicit: ScaleXY[PolygonArr] = (obj, xOperand, yOperand) => obj.map(_.scaleXY(xOperand, yOperand))
  implicit val reflectImplicit: Reflect[PolygonArr] = (obj: PolygonArr, lineLike: LineLike) => obj.map(_.reflect(lineLike))

  implicit val reflectAxesImplicit: TransAxes[PolygonArr] = new TransAxes[PolygonArr] {
    override def negYT(obj: PolygonArr): PolygonArr = obj.negY

    override def negXT(obj: PolygonArr): PolygonArr = obj.negX

    override def rotate90(obj: PolygonArr): PolygonArr = obj.rotate90

    override def rotate180(obj: PolygonArr): PolygonArr = obj.rotate180

    override def rotate270(obj: PolygonArr): PolygonArr = obj.rotate270
  }
}

class PolygonBuff(val unsafeBuffer: ArrayBuffer[Array[Double]]) extends AnyVal with ArrayDblBuff[Polygon]
{
  override type ThisT = PolygonBuff
  override def typeStr: String = "PolygonBuff"
 // override def apply(index: Int): PolygonGen = new PolygonGen(unsafeBuff(index))


  override def unsafeSetElem(i: Int, value: Polygon): Unit = unsafeBuffer(i) = value.unsafeArray

  override def fElemStr: Polygon => String = ???

  override def apply(index: Int): Polygon = ???
}

object PolygonBuff{
  def apply(initLen: Int = 4): PolygonBuff = new PolygonBuff(new ArrayBuffer[Array[Double]](initLen))
}