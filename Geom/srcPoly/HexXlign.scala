/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis */
final class HexXlign(val arrayUnsafe: Array[Double]) extends Hexlign, Tell2[Double, Pt2]
{ override type ThisT = HexXlign
  override def fromArray(array: Array[Double]): HexXlign = new HexXlign(array)

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

  /** maps the vertices of this [[HexXlign]] to a new [[HexparrX]] instance. */
  def vertsTrans(f: Pt2 => Pt2): HexXlign = HexXlign.fromArray(arrayElemMap(f))

  override def slate(delta: VecPt2): HexXlign = vertsTrans(_.slate(delta))
  override def slate(xOperand: Double, yOperand: Double): HexXlign = vertsTrans(_.slate(xOperand, yOperand))
  override def slateFrom(delta: VecPt2): HexXlign = vertsTrans(_.slateFrom(delta))
  override def slateFrom(xOperand: Double, yOperand: Double): HexXlign = vertsTrans(_.slateFrom(xOperand, yOperand))
  override def slateX(xOperand: Double): HexXlign = vertsTrans(_.slateX(xOperand))
  override def slateY(yOperand: Double): HexXlign = vertsTrans(_.slateY(yOperand))
  override def scale(operand: Double): HexXlign = vertsTrans(_.scale(operand))
  override def negX: HexXlign = vertsTrans(_.negX)
  override def negY: HexXlign = vertsTrans(_.negY)
  override def rotate90: HexYlign = HexYlign.fromArray(arrayElemMap(_.rotate90))
  override def rotate180: HexXlign = vertsTrans(_.rotate180)
  override def rotate270: HexYlign = HexYlign.fromArray(arrayElemMap(_.rotate270))
  override def prolign(matrix: AxlignMatrix): HexXlign = HexXlign(apoDiameter, cen.prolign(matrix))
  override def v0x: Double = arrayUnsafe(0)
  override def v0y: Double = arrayUnsafe(1)
  override def v0: Pt2 = Pt2(arrayUnsafe(0), arrayUnsafe(1))
  override def vLastX: Double = arrayUnsafe(numVerts - 2)
  override def vLastY: Double = arrayUnsafe(numVerts - 1)
  override def vLast: Pt2 = Pt2(vLastX, vLastY)
  override def side0: LSeg2 = LSeg2(v0x, v0y, vertX(1), vertY(1))
  override def sd0CenX: Double = v0x \/ vertX(1)
  override def sd0CenY: Double = v0y \/ vertY(1)
  override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
  override def vertX(index: Int): Double = arrayUnsafe(index * 2)
  override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)
  override def sides: LSeg2Arr = new LSeg2Arr(arrayForSides)
}

/** Companion object for the regular hexagon aligned to the X Axis class. It has a limited set of 2D geometric transformation type class instances as the type
 * can not be maintained through all affine transformations. */
object HexXlign
{ /** Apply factory method for HexXlign, Creates a regular hexagon with 2 of its side aligned to the X axis. */
  def apply(height: Double, cen: Pt2 = Origin2): HexXlign = apply(height, cen.x, cen.y)

  /** Apply factory method for [[HexXlign]], Creates a regular hexagon with 2 of its side aligned to the Y axis. */
  def apply(height: Double, xCen: Double, yCen: Double): HexXlign = new HexXlign(unsafe(height, xCen, yCen))

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

  def unapply(input: HexXlign): Some[(Double, Pt2)] = Some((input.height, input.cen))
  def fromArray(array: Array[Double]): HexXlign = new HexXlign(array)

  /** [[Show]] and [[Unshow]] type class instances / evidence for [[HexXlign]]. */
  given persistEv: Persist2Both[Double, Pt2, HexXlign] = Persist2Both[Double, Pt2, HexXlign]("HexXlign", "height", _.height,"cen", _.cen, apply)

  given slate2Ev: Slate2[HexXlign] = new Slate2[HexXlign]
  { override def slate(obj: HexXlign, operand: VecPt2): HexXlign = obj.slate(operand) 
    override def slateXY(obj: HexXlign, xOperand: Double, yOperand: Double): HexXlign = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: HexXlign, operand: VecPt2): HexXlign = obj.slateFrom(operand)
    override def slateFromXY(obj: HexXlign, xOperand: Double, yOperand: Double): HexXlign = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: HexXlign, xOperand: Double): HexXlign = obj.slateX(xOperand)
    override def slateY(obj: HexXlign, yOperand: Double): HexXlign = obj.slateY(yOperand)
  }
  
  given scaleEv: Scale[HexXlign] = (obj: HexXlign, operand: Double) => obj.scale(operand)
  given prolignEv: Prolign[HexXlign] = (obj, matrix) => obj.prolign(matrix)
}