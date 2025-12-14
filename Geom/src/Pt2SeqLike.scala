/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** The purpose of this trait is to provide the helper method for Vec2 transformations. */
trait Pt2SeqLike extends Any, PointDbl2SeqLike[Pt2], SeqLikeImutDbl2[Pt2]
{
  def arrTrans(f: Pt2 => Pt2): Array[Double] =
  { val newArray = new Array[Double](arrayUnsafe.length)
    var count = 0
    while (count < arrayUnsafe.length)
    { val pt = Pt2(arrayUnsafe(count), arrayUnsafe(count + 1))
      val newVec = f(pt)
      newArray(count) = newVec.x
      newArray(count + 1) = newVec.y
      count += 2
    }
    newArray
  }
  final override def fElemStr: Pt2 => String = _.str
}

trait Pt2SeqSpec extends Any, Pt2SeqLike, SeqSpecDbl2[Pt2]
{ final override def elemFromDbls(d1: Double, d2: Double): Pt2 = Pt2(d1, d2)
  protected def arraySlate(operand: VecPt2): Array[Double] = arrayD1D2Map(_ + operand.x)(_ + operand.y)
  protected def arraySlateXY(xOperand: Double, yOperand: Double): Array[Double] = arrayD1D2Map(_ + xOperand)(_ + yOperand)
  protected def arraySlateX(xOperand: Double): Array[Double] = arrayD1Map(_ + xOperand)
  protected def arraySlateY(yOperand: Double): Array[Double] = arrayD2Map(_ + yOperand)
  protected def arrayScale(operand: Double): Array[Double] = arrayUnsafe.map(_ * operand)
  protected def arrayNegX: Array[Double] = arrayD1Map(-_)
  protected def arrayNegY: Array[Double] = arrayD2Map(-_)
  protected def arrayProlign(matrix: AxlignMatrix): Array[Double] = arrayElemMap(_.prolign(matrix))
  protected def arrayRotate90: Array[Double] = arrayElemMap(_.rotate90)
  protected def arrayRotate180: Array[Double] = arrayElemMap(_.rotate180)
  protected def arrayRotate270: Array[Double] = arrayElemMap(_.rotate270)
  protected def arrayRotate(rotation: AngleVec): Array[Double] = arrayElemMap(_.rotate(rotation))
  protected def arrayReflect(lineLike: LineLike): Array[Double] = arrayElemMap(_.mirror(lineLike))
  protected def arrayScaleXY(xOperand: Double, yOperand: Double): Array[Double] = arrayD1D2Map(_ * xOperand)(_ * yOperand)
  protected def arrayShearX(operand: Double): Array[Double] = arrayElemMap(_.xShear(operand))
  protected def arrayShearY(operand: Double): Array[Double] = arrayElemMap(_.yShear(operand))
  def xVertsArray: Array[Double] = elem1sArray
  def yVertsArray: Array[Double] = elem2sArray
}

/** The default Array[Double] based collection class for [[Pt2]]s. Use Polygon or LinePath to represent those structures. Conversion to and from [[Polygon]]
 * class and [[LinePath]] class should not entail a runtime cost. */
final class Pt2Arr(val arrayUnsafe: Array[Double]) extends AffinePreserve, Pt2SeqLike, ArrDbl2[Pt2]
{ type ThisT = Pt2Arr
  def fromArray(array: Array[Double]): Pt2Arr = new Pt2Arr(array)
  override def typeStr: String = "Pt2s"

  @inline def lengthFull: Int = arrayUnsafe.length / 2
  @inline def toPolygon: PolygonGen = new PolygonGen(arrayUnsafe)
  @inline def toLinePath: LinePath = new LinePath(arrayUnsafe)

  /** Method for creating new data elements from 2 [[Double]]s In the case of [[ArrDbl2]] this will be thee type of the elements of the sequence. */
  override def elemFromDbls(d1: Double, d2: Double): Pt2 = Pt2(d1, d2)

  /** Geometric transformation by the function from a 2 dimensional Vector value to a 2 dimensional vector value. */
  def ptsTrans(f: Pt2 => Pt2): Pt2Arr =  new Pt2Arr(arrTrans(f))
}

/** Companion object for the [[Pt2Arr]] sequence class. Contains factory apply method and implicit instances for a number of type classes. */
object Pt2Arr extends CompanionSlDbl2[Pt2, Pt2Arr]
{
  override def fromArray(array: Array[Double]): Pt2Arr = new Pt2Arr(array)

  /** Builder for [[Arr]] of [[Pt2]]s via the flatMap method. The call site does not need to know that the element type is [[Pt2]] hence why this type class
   * instance / evidence is in the [[Pt2Arr]] companion object, while the corresponding [[BuilderMapArrDbl2]] type class instance is in the
   *  [[Pt2]] companion object. */
  given builderArrFlatEv: BuilderArrFlat[Pt2Arr] =  new BuilderFlatArrDbl2[Pt2Arr]
  { override type BuffT = Pt2Buff
    override def fromDblArray(array: Array[Double]): Pt2Arr = new Pt2Arr(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): Pt2Buff = new Pt2Buff(inp)
  }

  /** [[Show]] type class instance / evidence for [[Pt2Arr]] */
  given showEv: ShowSequ[Pt2, Pt2Arr] = ShowSequ[Pt2, Pt2Arr]()

  /** [[Unshow]] type class instance / evidence for [[Pt2Arr]] */
  given unshowEv: UnshowSeq[Pt2, Pt2Arr] = UnshowSeq[Pt2, Pt2Arr]()

  given eqTEv: EqT[Pt2Arr] = (a1, a2) => a1.arrayUnsafe.sameElements(a2.arrayUnsafe)

  given slateEv: Slate2[Pt2Arr] = new Slate2[Pt2Arr]
  { override def slate(obj: Pt2Arr, operand: VecPt2): Pt2Arr = obj.slate(operand)
    override def slateXY(obj: Pt2Arr, xOperand: Double, yOperand: Double): Pt2Arr = obj.slate(xOperand, yOperand)
    override def slateX(obj: Pt2Arr, xOperand: Double): Pt2Arr = obj.slateX(xOperand)
    override def slateY(obj: Pt2Arr, yOperand: Double): Pt2Arr = obj.slateY(yOperand)
  }

  given scaleEv: Scale[Pt2Arr] = (obj: Pt2Arr, operand: Double) => obj.scale(operand)
  given rotateEv: Rotate[Pt2Arr] = (obj: Pt2Arr, angle: AngleVec) => obj.rotate(angle)
  given prolignEv: Prolign[Pt2Arr] = (obj, matrix) => obj.prolign(matrix)
  given XYScaleEv: ScaleXY[Pt2Arr] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  given reflectEv: Mirror[Pt2Arr] = (obj: Pt2Arr, lineLike: LineLike) => obj.mirror(lineLike)

  given reflectAxesEv: TransAxes[Pt2Arr] = new TransAxes[Pt2Arr]
  { override def negYT(obj: Pt2Arr): Pt2Arr = obj.negY
    override def negXT(obj: Pt2Arr): Pt2Arr = obj.negX
    override def rotate90(obj: Pt2Arr): Pt2Arr = obj.rotate90
    override def rotate180(obj: Pt2Arr): Pt2Arr = obj.rotate180
    override def rotate270(obj: Pt2Arr): Pt2Arr = obj.rotate270
  }

  given shearEv: Shear[Pt2Arr] = new Shear[Pt2Arr]
  { override def shearXT(obj: Pt2Arr, yFactor: Double): Pt2Arr = obj.shearX(yFactor)
    override def shearYT(obj: Pt2Arr, xFactor: Double): Pt2Arr = obj.shearY(xFactor)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[Pt2]]s collections. */
final class Pt2Buff(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[Pt2]
{ override def typeStr: String = "BuffPt2"
  def elemFromDbls(d1: Double, d2: Double): Pt2 = Pt2(d1, d2)
}

object Pt2Buff extends CompanionBuffDbl2[Pt2, Pt2Buff]
{ override def fromBuffer(buffer: ArrayBuffer[Double]): Pt2Buff = new Pt2Buff(buffer)
}