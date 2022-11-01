/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** The purpose of this trait is to provide the helper method for Vec2 transformations. */
trait Pt2SeqLike extends PointDbl2SeqLike[Pt2] with Dbl2SeqLike[Pt2]
{
  def arrTrans(f: Pt2 => Pt2): Array[Double] =
  { val newArray = new Array[Double](unsafeArray.length)
    var count = 0
    while (count < unsafeArray.length)
    {
      val newVec = f(unsafeArray(count) pp unsafeArray(count + 1))
      newArray(count) = newVec.x
      newArray(count + 1) = newVec.y
      count += 2
    }
    newArray
  }
  final override def fElemStr: Pt2 => String = _.str
}

trait Pt2SeqSpec extends Pt2SeqLike with Dbl2SeqSpec[Pt2]
{ final override def ssElem(d1: Double, d2: Double): Pt2 = Pt2(d1, d2)
}

/** The default Array[Double] based collection class for [[Pt2]]s. Use Polygon or LinePath to represent those structures. Conversion to and from
 *  [[Polygon]] class and [[LinePath]] class should not entail a runtime cost. */
final class Pt2Arr(val unsafeArray: Array[Double]) extends AffinePreserve with Pt2SeqLike with Dbl2Arr[Pt2]
{ type ThisT = Pt2Arr
  def fromArray(array: Array[Double]): Pt2Arr = new Pt2Arr(array)
  override def typeStr: String = "Pt2s"

  @inline def lengthFull: Int = unsafeArray.length / 2
  @inline def toPolygon: PolygonGen = new PolygonGen(unsafeArray)
  @inline def toLinePath: LinePath = new LinePath(unsafeArray)

  /** Method for creating new data elements from 2 [[Double]]s In the case of [[Dbl2Arr]] this will be thee type of the elements of the sequence. */
  override def seqDefElem(d1: Double, d2: Double): Pt2 = Pt2(d1, d2)

  /** Geometric transformation by the function from a 2 dimensional Vector value to a 2 dimensional vector value. */
  def ptsTrans(f: Pt2 => Pt2): Pt2Arr =  new Pt2Arr(arrTrans(f))
}

/** Companion object for the [[Pt2Arr]] sequence class. Contains factory apply method and implicit instances for a number of type classes. */
object Pt2Arr extends Dbl2SeqLikeCompanion[Pt2, Pt2Arr]
{
  override def fromArray(array: Array[Double]): Pt2Arr = new Pt2Arr(array)

  implicit val persistImplicit: Dbl2SeqDefPersist[Pt2, Pt2Arr] = new Dbl2SeqDefPersist[Pt2, Pt2Arr]("Pt2s")
  { override def fromArray(value: Array[Double]): Pt2Arr = new Pt2Arr(value)
  }

  implicit val arrFlatBuilderImplicit: ArrFlatBuilder[Pt2Arr] =  new Dbl2ArrFlatBuilder[Pt2, Pt2Arr]
  { override type BuffT = Pt2Buff
    override def fromDblArray(array: Array[Double]): Pt2Arr = new Pt2Arr(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): Pt2Buff = new Pt2Buff(inp)
  }

  implicit val slateImplicit: Slate[Pt2Arr] = (obj: Pt2Arr, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[Pt2Arr] = (obj: Pt2Arr, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Pt2Arr] = (obj: Pt2Arr, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Pt2Arr] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: ScaleXY[Pt2Arr] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val reflectImplicit: Reflect[Pt2Arr] = (obj: Pt2Arr, lineLike: LineLike) => obj.reflect(lineLike)

  implicit val reflectAxesImplicit: TransAxes[Pt2Arr] = new TransAxes[Pt2Arr]
  { override def negYT(obj: Pt2Arr): Pt2Arr = obj.negY
    override def negXT(obj: Pt2Arr): Pt2Arr = obj.negX
    override def rotate90(obj: Pt2Arr): Pt2Arr = obj.rotate90
    override def rotate180(obj: Pt2Arr): Pt2Arr = obj.rotate180
    override def rotate270(obj: Pt2Arr): Pt2Arr = obj.rotate270
  }

  implicit val shearImplicit: Shear[Pt2Arr] = new Shear[Pt2Arr]
  { override def shearXT(obj: Pt2Arr, yFactor: Double): Pt2Arr = obj.shearX(yFactor)
    override def shearYT(obj: Pt2Arr, xFactor: Double): Pt2Arr = obj.shearY(xFactor)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[Pt2]]s collections. */
final class Pt2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl2Buff[Pt2]
{ override def typeStr: String = "BuffPt2"
  def dblsToT(d1: Double, d2: Double): Pt2 = Pt2(d1, d2)
}

object Pt2Buff
{ def apply(n: Int = 4): Pt2Buff = new Pt2Buff(new ArrayBuffer[Double](n * 2))
  def empty: Pt2Buff = new Pt2Buff(new ArrayBuffer[Double](4))
}