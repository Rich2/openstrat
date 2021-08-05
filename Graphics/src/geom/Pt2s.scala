/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** The default Array[Double] based collection class for [[Pt2]]s. Use Polygon or LinePath to represent those structures. Conversion to and from
 *  [[Polygon]] class and [[LinePath]] class should not entail a runtime cost. */
final class Pt2s(val arrayUnsafe: Array[Double]) extends AffinePreserve with Pt2sLike with Dbl2sSeq[Pt2]
{ type ThisT = Pt2s
  def unsafeFromArray(array: Array[Double]): Pt2s = new Pt2s(array)
  override def typeStr: String = "P2s"

  @inline def lengthFull: Int = arrayUnsafe.length / 2
  @inline def xStart: Double = arrayUnsafe(0)
  @inline def yStart: Double = arrayUnsafe(1)
  @inline def pStart: Pt2 = Pt2(xStart, yStart)
  @inline def toPolygon: PolygonGen = new PolygonGen(arrayUnsafe)
  @inline def toLinePath: LinePath = new LinePath(arrayUnsafe)

  /** Geometric transformation by the function from a 2 dimensional Vector value to a 2 dimensional vector value. */
  def ptsTrans(f: Pt2 => Pt2): Pt2s =  new Pt2s(arrTrans(f))

  /** Closes the line Path into a Polygon, by mirroring across the yAxis. This is useful for describing symetrical across the y Axis polygons, with
   * the minimum number of points. The implementation is efficient, but is logical equivalent of myVec2s ++ myVec2s.reverse.negX. */
  def yMirrorClose: PolygonGen =
  { val acc = appendArray(elemsNum)
    var count = arrLen

    foreachReverse { orig =>
      acc(count) = - orig.x
      acc(count + 1) = orig.y
      count += 2
    }
    new PolygonGen(acc)
  }

  def toPathDraw(lineWidth: Double, colour: Colour = Colour.Black): LinePathDraw = LinePathDraw(this.toLinePath, lineWidth, colour)
}

object Pt2s extends Dbl2sDataCompanion[Pt2, Pt2s]
{
  override def fromArrayDbl(array: Array[Double]): Pt2s = new Pt2s(array)

  implicit val persistImplicit: Dbl2sDataPersist[Pt2, Pt2s] = new Dbl2sDataPersist[Pt2, Pt2s]("Pt2s")
  { override def fromArray(value: Array[Double]): Pt2s = new Pt2s(value)
  }

  implicit val arrFlatBuilderImplicit: SeqFlatBuilder[Pt2s] =  new Dbl2sArrFlatBuilder[Pt2, Pt2s]
  { override type BuffT = Pt2Buff
    override def fromDblArray(array: Array[Double]): Pt2s = new Pt2s(array)
    override def fromDblBuffer(inp: ArrayBuffer[Double]): Pt2Buff = new Pt2Buff(inp)
  }

  implicit val slateImplicit: Slate[Pt2s] = (obj: Pt2s, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[Pt2s] = (obj: Pt2s, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Pt2s] = (obj: Pt2s, angle: AngleVec) => obj.map(_.rotate(angle))
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
final class Pt2Buff(val unsafeBuff: ArrayBuffer[Double]) extends AnyVal with Dbl2sBuffer[Pt2]
{ def dblsToT(d1: Double, d2: Double): Pt2 = Pt2(d1, d2)
}