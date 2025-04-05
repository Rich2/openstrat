/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis */
final class HexParrX(val arrayUnsafe: Array[Double]) extends Hexlign with Tell2[Double, Pt2]
{ override type ThisT = HexParrX
  override def fromArray(array: Array[Double]): HexParrX = new HexParrX(array)

  override def typeStr = "HexXlign"
  override def name1: String = "height"
  override def name2: String = "cen"
  def height: Double = (v1y - v2y).abs
  override def apoDiameter: Double = (v1y - v2y).abs
  override def diameter: Double = v5.distTo(v2)
  override def width: Double = v5.distTo(v2)
  override def apo: Double = apoDiameter / 2
  override def radius: Double = diameter / 2
  override def tell1: Double = height
  override def tell2: Pt2 = cen
  override implicit def show1: Show[Double] = Show.doubleEv
  override implicit def show2: Show[Pt2] = Pt2.persistEv
  override def tellDepth: Int = 3

  /** maps the vertices of this [[HexParrX]] to a new [[HexparrX]] instance. */
  override def vertsTrans(f: Pt2 => Pt2): HexParrX = HexParrX.fromArray(arrayElemMap(f))

  override def slate(delta: VecPt2): HexParrX = vertsTrans(_.slate(delta))
  override def slate(xOperand: Double, yOperand: Double): HexParrX = vertsTrans(_.slate(xOperand, yOperand))
  override def slateX(xOperand: Double): HexParrX = vertsTrans(_.slateX(xOperand))
  override def slateY(yOperand: Double): HexParrX = vertsTrans(_.slateY(yOperand))
  override def scale(operand: Double): HexParrX = vertsTrans(_.scale(operand))
  override def negX: HexParrX = HexParrX.fromArray(unsafeNegX)
  override def negY: HexParrX = HexParrX.fromArray(unsafeNegY)
  override def rotate90: HexParrY = HexParrY.fromArray(arrayElemMap(_.rotate90))
  override def rotate180: HexParrX = vertsTrans(_.rotate180)
  override def rotate270: HexParrY = HexParrY.fromArray(arrayElemMap(_.rotate270))
  override def prolign(matrix: ProlignMatrix): HexParrX = HexParrX(apoDiameter, cen.prolign(matrix))
}

/** Companion object for the regular hexagon aligned to the X Axis class. It has a limited set of 2D geometric transformation type class instances as
 * the type can not be maintained through all affine transformations. */
object HexParrX
{ /** Apply factory method for HexXlign, Creates a regular hexagon with 2 of its side aligned to the X axis. */
  def apply(height: Double, cen: Pt2 = Pt2Z): HexParrX = apply(height, cen.x, cen.y)

  /** Apply factory method for [[HexParrX]], Creates a regular hexagon with 2 of its side aligned to the Y axis. */
  def apply(height: Double, xCen: Double, yCen: Double): HexParrX = new HexParrX(unsafe(height, xCen, yCen))

  def unsafe(height: Double, xCen: Double, yCen: Double): Array[Double] =
  {
    val h2: Double = height / 2
    val dsq3: Double = height / 3.sqrt
    val d2sq3: Double = height / (3.sqrt * 2)
    Array[Double](
      xCen + d2sq3, yCen + h2,
      xCen + dsq3, yCen,
      xCen + d2sq3, yCen - h2,
      xCen - d2sq3, yCen - h2,
      xCen - dsq3, yCen,
      xCen - d2sq3, yCen + h2
    )
  }

  def unapply(input: HexParrX): Some[(Double, Pt2)] = Some((input.height, input.cen))
  def fromArray(array: Array[Double]): HexParrX = new HexParrX(array)

  /** [[Show]] and [[Unshow]] type class instances / evidence for [[HexParrX]]. */
  implicit val persistEv: Persist2Both[Double, Pt2, HexParrX] = Persist2Both[Double, Pt2, HexParrX]("HexXlign", "height", _.height,"cen", _.cen, apply)

  implicit val slateImplicit: SlateXY[HexParrX] = (obj: HexParrX, dx: Double, dy: Double) => obj.slate(dx, dy)
  implicit val scaleImplicit: Scale[HexParrX] = (obj: HexParrX, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexParrX] = (obj, matrix) => obj.prolign(matrix)
}