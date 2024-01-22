/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** The default Array[Double] based collection class for [[Vec2]]s. Use Polygon or LinePath to represent those structures. Conversion to and from
 *  [[Polygon]] class and [[LinePath]] class should not entail a runtime cost. */
final class Vec2Arr(val unsafeArray: Array[Double]) extends /*AffinePreserve with*/ ArrDbl2[Vec2]
{ type ThisT = Vec2Arr
  def fromArray(array: Array[Double]): Vec2Arr = new Vec2Arr(array)
  override def typeStr: String = "Vec2s"

  @inline def lengthFull: Int = unsafeArray.length / 2
  @inline def toPolygon: PolygonGen = new PolygonGen(unsafeArray)
  @inline def toLinePath: LinePath = new LinePath(unsafeArray)

  /** Geometric transformation by the function from a 2 dimensional Vector value to a 2 dimensional vector value. */
  def ptsTrans(f: Vec2 => Vec2): Vec2Arr =  new Vec2Arr(arrTrans(f))

  def arrTrans(f: Vec2 => Vec2): Array[Double] =
  { val newArray = new Array[Double](unsafeArray.length)
    var count = 0
    while (count < unsafeArray.length)
    {
      val newVec = f(unsafeArray(count) vv unsafeArray(count + 1))
      newArray(count) = newVec.x
      newArray(count + 1) = newVec.y
      count += 2
    }
    newArray
  }
  override def fElemStr: Vec2 => String = _.str
  final override def seqDefElem(d1: Double, d2: Double): Vec2 = Vec2.apply(d1, d2)
}

/** Companion object for the [[Vec2Arr]] sequence class. Contains factory apply method and implicit instances for a number of type classes. */
object Vec2Arr extends CompanionSeqLikeDbl2[Vec2, Vec2Arr]
{  override def fromArray(array: Array[Double]): Vec2Arr = new Vec2Arr(array)

  implicit val arrFlatBuilderImplicit: BuilderArrFlat[Vec2Arr] =  new BuilderArrDbl2Flat[Vec2Arr]
  { override type BuffT = Vec2Buff
    override def fromDblArray(array: Array[Double]): Vec2Arr = new Vec2Arr(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): Vec2Buff = new Vec2Buff(inp)
  }

  /** [[Show]] type class instance / evidence for [[Vec2Arr]]. */
  implicit lazy val showEv: ShowSequ[Vec2, Vec2Arr] = ShowSequ[Vec2, Vec2Arr]()

  /** [[Unshow]] type class instance / evidence for [[Vec2Arr]]. */
  implicit lazy val unshowEv: UnshowSeq[Vec2, Vec2Arr] = UnshowSeq[Vec2, Vec2Arr]()

//  implicit val slateImplicit: Slate[Vec2s] = (obj: Vec2s, dx: Double, dy: Double) => obj.slateXY(dx, dy)
//  implicit val scaleImplicit: Scale[Vec2s] = (obj: Vec2s, operand: Double) => obj.scale(operand)
//  implicit val rotateImplicit: Rotate[Vec2s] = (obj: Vec2s, angle: AngleVec) => obj.rotate(angle)
//  implicit val prolignImplicit: Prolign[Vec2s] = (obj, matrix) => obj.prolign(matrix)
//  implicit val XYScaleImplicit: ScaleXY[Vec2s] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
//  implicit val reflectImplicit: Reflect[Vec2s] = (obj: Vec2s, lineLike: LineLike) => obj.reflect(lineLike)
//
//  implicit val reflectAxesImplicit: TransAxes[Vec2s] = new TransAxes[Vec2s]
//  { override def negYT(obj: Vec2s): Vec2s = obj.negY
//    override def negXT(obj: Vec2s): Vec2s = obj.negX
//    override def rotate90(obj: Vec2s): Vec2s = obj.rotate90
//    override def rotate180(obj: Vec2s): Vec2s = obj.rotate180
//    override def rotate270(obj: Vec2s): Vec2s = obj.rotate270
//  }
//
//  implicit val shearImplicit: Shear[Vec2s] = new Shear[Vec2s]
//  { override def shearXT(obj: Vec2s, yFactor: Double): Vec2s = obj.shearX(yFactor)
//    override def shearYT(obj: Vec2s, xFactor: Double): Vec2s = obj.shearY(xFactor)
//  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[Vec2]]s collections. */
final class Vec2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[Vec2]
{ override def typeStr: String = "BuffVec2"
  def newElem(d1: Double, d2: Double): Vec2 = Vec2(d1, d2)
}

object Vec2Buff extends CompanionBuffDbl2[Vec2, Vec2Buff]
{ override def fromBuffer(buffer: ArrayBuffer[Double]): Vec2Buff = new Vec2Buff(buffer)
}