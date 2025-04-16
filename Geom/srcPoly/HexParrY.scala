/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Regular Hexagon where two of the sides are parallel to the Y Axis. This will be the standard Hex for the Tiling module. */
final class HexParrY(val arrayUnsafe: Array[Double]) extends Hexlign, Tell2[Double, Pt2]
{ override type ThisT = HexParrY
  override def fromArray(array: Array[Double]): HexParrY = new HexParrY(array)

  override def typeStr = "HexParrY"
  def width: Double = (v1x - v4x).abs

  override def name1: String = "width"
  override def name2: String = "cen"
  override def apoDiameter: Double = (v1x - v5x).abs
  override def height: Double = (v0y - v3y).abs
  override def diameter: Double = (v0y - v3y).abs
  override def apo: Double = apoDiameter / 2
  override def radius: Double = diameter / 2
  override def tell1: Double = width
  override def tell2: Pt2 = cen
  override implicit def show1: Show[Double] = Show.doubleEv
  override implicit def show2: Show[Pt2] = Pt2.persistEv
  override def tellDepth: Int = 3

  /** maps the vertices of this [[HexParrY]] to a new [[HexparrY]] instance. */
  override def vertsTrans(f: Pt2 => Pt2): HexParrY = HexParrY.fromArray(arrayElemMap(f))

  /** Translate 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def slate(delta: VecPt2): HexParrY = vertsTrans(_.slate(delta))

  /** Translate 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def slate(xOperand: Double, yOperand: Double): HexParrY = vertsTrans(_.slate(xOperand, yOperand))

  /** Uniform scaling against both X and Y axes 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def scale(operand: Double): HexParrY = vertsTrans(_.scale(operand))

  /** Mirror, reflection 2D geometric transformation of this HexYlign across the X axis, negates Y, returns a HexYlign. */
  override def negY: HexParrY = HexParrY.fromArray(unsafeNegY)

  /** Mirror, reflection 2D geometric transformation of this HexYlign across the Y axis, negates X, returns a HexYlign. */
  override def negX: HexParrY = HexParrY.fromArray(unsafeNegX)

  /** Rotate 90 degrees positively or anti clockwise  2D geometric transformation on a HexYlign, returns a HexYlign. Equivalent to rotate 270 degrees
   *  clockwise. */
  override def rotate90: HexParrX = HexParrX.fromArray(arrayElemMap(_.rotate90))

  /** Rotate 180 degrees 2D geometric transformation on a HexYlign, returns a HexYlign. */
  override def rotate180: HexParrY = vertsTrans(_.rotate180)

  /** Rotate 270 degrees positively or anti clockwise 2D geometric transformation on a HexYlign, returns a HexYlign. Equivalent to rotate 90 degrees
   *  clockwise. */
  override def rotate270: HexParrX = HexParrX.fromArray(arrayElemMap(_.rotate270))

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes on this HexTlign returns a HexYlign. */
  override def prolign(matrix: ProlignMatrix): HexParrY = HexParrY(apoDiameter, cen.prolign(matrix))

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
}

/** Companion object for the regular hexagon aligned to the Y Axis class. It has a limited set of 2D geometric transformation type class instances as
 * the type can not be maintained through all affine transformations. */
object HexParrY
{
  /** Apply factory method for the [[HexParrY]] class takes the width and centre point as specification. The centre poit can be specified as a [[Pt2]]
   *  or 2 [[Double]]s. */
  def apply(width: Double, cen: Pt2 = Pt2Z): HexParrY = apply(width, cen.x, cen .y)

  /** Apply factory method for the [[HexParrY]] class takes the width and centre point as specification. The centre poit can be specified as a [[Pt2]]
   *  or 2 [[Double]]s. */
  def apply(width: Double, xCen: Double, yCen: Double): HexParrY =
  { val dsq3 = width / 3.sqrt
    val d2sq3: Double = width /(3.sqrt * 2)
    val w2 = width / 2
    val array = Array[Double](
      xCen, yCen + dsq3,
      xCen + w2, yCen + d2sq3,
      xCen + w2, yCen - d2sq3,
      xCen, yCen - dsq3,
      xCen - w2, yCen - d2sq3,
      xCen - w2, yCen + d2sq3)
    new HexParrY(array)
  }

  def unapply(input: HexParrY): Some[(Double, Pt2)] = Some((input.width, input.cen))

  def fromArray(array: Array[Double]): HexParrY = new HexParrY(array)

  /** [[Show]] type class instance / evidence for [[HexParrY]]. */
  implicit val showEv: Show2[Double, Pt2, HexParrY] = Show2[Double, Pt2, HexParrY]("HexYlign", "width", _.width,"cen", _.cen)

  /** [[Unshow]] type class instance / evidence for [[HexParrY]]. */
  implicit val unshowEv: Unshow[HexParrY] = Unshow2[Double, Pt2, HexParrY]("HexYlign", "width", "cen", apply)

  implicit val slateImplicit: SlateXY[HexParrY] = (obj: HexParrY, dx: Double, dy: Double) => obj.slate(dx, dy)
  implicit val scaleImplicit: Scale[HexParrY] = (obj: HexParrY, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexParrY] = (obj, matrix) => obj.prolign(matrix)
}