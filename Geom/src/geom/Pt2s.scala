/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** The default Array[Double] based collection class for [[Pt2]]s. Use Polygon or LinePath to represent those structures. Conversion to and from
 *  [[Polygon]] class and [[LinePath]] class should not entail a runtime cost. */
final class Pt2s(val unsafeArray: Array[Double]) extends AffinePreserve with Pt2sLike with ArrDbl2s[Pt2]
{ type ThisT = Pt2s
  def unsafeFromArray(array: Array[Double]): Pt2s = new Pt2s(array)
  override def typeStr: String = "Pt2s"

  @inline def lengthFull: Int = unsafeArray.length / 2
  @inline def toPolygon: PolygonGen = new PolygonGen(unsafeArray)
  @inline def toLinePath: LinePath = new LinePath(unsafeArray)

  /** Geometric transformation by the function from a 2 dimensional Vector value to a 2 dimensional vector value. */
  def ptsTrans(f: Pt2 => Pt2): Pt2s =  new Pt2s(arrTrans(f))
}

/** Companion object for the [[Pt2s]] sequence class. Contains factory apply method and implicit instances for a number of type classes. */
object Pt2s extends DataDbl2sCompanion[Pt2, Pt2s]
{
  override def fromArrayDbl(array: Array[Double]): Pt2s = new Pt2s(array)

  implicit val persistImplicit: DataDbl2sPersist[Pt2, Pt2s] = new DataDbl2sPersist[Pt2, Pt2s]("Pt2s")
  { override def fromArray(value: Array[Double]): Pt2s = new Pt2s(value)
  }

  implicit val arrFlatBuilderImplicit: ArrFlatBuilder[Pt2s] =  new ArrDbl2sFlatBuilder[Pt2, Pt2s]
  { override type BuffT = BuffPt2
    override def fromDblArray(array: Array[Double]): Pt2s = new Pt2s(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): BuffPt2 = new BuffPt2(inp)
  }

  implicit val slateImplicit: Slate[Pt2s] = (obj: Pt2s, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[Pt2s] = (obj: Pt2s, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Pt2s] = (obj: Pt2s, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Pt2s] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: ScaleXY[Pt2s] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val reflectImplicit: Reflect[Pt2s] = (obj: Pt2s, lineLike: LineLike) => obj.reflect(lineLike)

  implicit val reflectAxesImplicit: TransAxes[Pt2s] = new TransAxes[Pt2s]
  { override def negYT(obj: Pt2s): Pt2s = obj.negY
    override def negXT(obj: Pt2s): Pt2s = obj.negX
    override def rotate90(obj: Pt2s): Pt2s = obj.rotate90
    override def rotate180(obj: Pt2s): Pt2s = obj.rotate180
    override def rotate270(obj: Pt2s): Pt2s = obj.rotate270
  }

  implicit val shearImplicit: Shear[Pt2s] = new Shear[Pt2s]
  { override def shearXT(obj: Pt2s, yFactor: Double): Pt2s = obj.shearX(yFactor)
    override def shearYT(obj: Pt2s, xFactor: Double): Pt2s = obj.shearY(xFactor)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[Pt2]]s collections. */
final class BuffPt2(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl2Buff[Pt2]
{ override def typeStr: String = "BuffPt2"
  def dblsToT(d1: Double, d2: Double): Pt2 = Pt2(d1, d2)
}

object BuffPt2
{
  def empty: BuffPt2 = new BuffPt2(new Buff[Double](4))
}