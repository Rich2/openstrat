/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Specialised Array based immutable collection class for [[Polygon]]s.  */
final class PolygonGenArr(val arrayOfArraysUnsafe: Array[Array[Double]]) extends AnyVal, ArrArrayDbl[PolygonGen]
{ override type ThisT = PolygonGenArr
  override def typeStr: String = "PolygonArr"
  override def elemFromArray(array: Array[Double]): PolygonGen = new PolygonGen(array)
  override def fromArrayArray(aad: Array[Array[Double]]): PolygonGenArr = new PolygonGenArr(aad)  
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
    { array(count) = input(count).arrayUnsafe
      count += 1
    }
    new PolygonGenArr(array)
  }

  given eqEv: EqT[PolygonGenArr] = ArrArrayDblEq[PolygonGen, PolygonGenArr]

  given slateEv: Slate2[PolygonGenArr] = new Slate2[PolygonGenArr]
  { override def slate(obj: PolygonGenArr, operand: VecPt2): PolygonGenArr = obj.slate(operand)
    override def slateXY(obj: PolygonGenArr, xOperand: Double, yOperand: Double): PolygonGenArr = obj.slate(Pt2(xOperand, yOperand))
  }

  given scaleEv: Scale[PolygonGenArr] = (obj: PolygonGenArr, operand: Double) => obj.map(_.scale(operand))
  given rotateEv: Rotate[PolygonGenArr] = (obj: PolygonGenArr, angle: AngleVec) => obj.map(_.rotate(angle))
  given prolignEv: Prolign[PolygonGenArr] = (obj, matrix) => obj.map(_.prolign(matrix))
  given XYScaleEv: ScaleXY[PolygonGenArr] = (obj, xOperand, yOperand) => obj.map(_.scaleXY(xOperand, yOperand))
  given reflectEv: Reflect[PolygonGenArr] = (obj: PolygonGenArr, lineLike: LineLike) => obj.map(_.reflect(lineLike))

  given reflectAxesEv: TransAxes[PolygonGenArr] = new TransAxes[PolygonGenArr]
  { override def negXT(obj: PolygonGenArr): PolygonGenArr = obj.negX
    override def negYT(obj: PolygonGenArr): PolygonGenArr = obj.negY
    override def rotate90(obj: PolygonGenArr): PolygonGenArr = obj.rotate90
    override def rotate180(obj: PolygonGenArr): PolygonGenArr = obj.rotate180
    override def rotate270(obj: PolygonGenArr): PolygonGenArr = obj.rotate270
  }
}

class PolygonGenBuff(val bufferUnsafe: ArrayBuffer[Array[Double]]) extends AnyVal with BuffArrayDbl[PolygonGen]
{ override type ThisT = PolygonGenBuff
  override def typeStr: String = "PolygonBuff"
  override def fElemStr: PolygonGen => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonGen = new PolygonGen(array)
}

object PolygonGenBuff
{ def apply(initLen: Int = 4): PolygonGenBuff = new PolygonGenBuff(new ArrayBuffer[Array[Double]](initLen))
}