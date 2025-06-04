/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Regular Hexagon. a = √3 * r / 2. r = 2 * √3 * a. */
trait HexReg extends ShapeCentred, Polygon6Plus, Tell
{ type ThisT <: HexReg
  override def typeStr = "HexReg"

  override def v1x: Double = xVertsArray(1)
  override def v1y: Double = yVertsArray(1)
  override def v2x: Double = xVertsArray(2)
  override def v2y: Double = yVertsArray(2)
  final override def v3x: Double = arrayUnsafe(6)
  final override def v3y: Double = arrayUnsafe(7)
  final override def cenX: Double = v0x \/ v3x
  final override def cenY: Double = v0y \/ v3y
  final override def cen: Pt2 = Pt2(cenX, cenY)
  def mapHexReg(f: Pt2 => Pt2): HexReg = HexReg.fromArray(arrayElemMap(f))

  /** The diameter of the inner circle of this regular hexagon. The shorter diameter from the centre of a side to the centre of the opposite side. */
  def apoDiameter: Double

  /** The apothem or the radius of the inner circle of this regular hexagon. The shorter radius from the centre of the hexagon to the centre of a side. */
  @inline def apo: Double

  /** The radius of the outer circle of this regular hexagon. The longer radius length from the centre of the Hexagon to a vertex. Also, the length of the
   * hexagon side. */
  @inline def radius: Double

  /** The diameter of the outer circle of this regular hexagon. The longer diameter length from a vertex to the opposite vertex. This length is twice the length
   * of the hexagon side. */
  @inline def diameter: Double

  /** A Hexagon has 6 vertices. */
  final override def numVerts: Int = 6
  
  override def slate(delta: VecPt2): HexReg = mapHexReg(_.slate(delta))
  override def slate(xOperand: Double, yOperand: Double): HexReg = mapHexReg(_.slate(xOperand, yOperand))
  override def slateX(xOperand: Double): HexReg = mapHexReg(_.slateX(xOperand))
  override def slateY(yOperand: Double): HexReg = mapHexReg(_.slateY(yOperand))
  override def scale(operand: Double): HexReg = mapHexReg(_.scale(operand))
  override def negX: HexReg = mapHexReg(_.negX)
  override def negY: HexReg = mapHexReg(_.negY)
  
  override def rotate90: HexReg = mapHexReg(_.rotate90)
  override def rotate180: HexReg = mapHexReg(_.rotate180)
  override def rotate270: HexReg = mapHexReg(_.rotate270)
  override def prolign(matrix: AxlignMatrix): HexReg = mapHexReg(_.prolign(matrix))
  override def rotate(rotation: AngleVec): HexReg = mapHexReg(_.rotate(rotation))
  override def reflect(lineLike: LineLike): HexReg = mapHexReg(_.reflect(lineLike))

  /** The area of this [[HexReg]]. 3 * √3 * r² / 2. Or 2 * √3 * a²  */
  def area: Double = radius.squared * 3.sqrt * 3 / 2
}

/** Companion object for HegReg trait, contains [[HexRegImp]] implementation case for the general case of regular Hexagons. */
object HexReg
{
  /** Factory method for regular hexagon [[HexReg]]. Takes the inner diameter the rotation and then centre point. A rotation of 0 degrees places side 4 at the
   * bottom parallel to the X axis and side1 at the top. */
  def apply(dInner: Double, rotation: AngleVec, xCen: Double, yCen: Double): HexReg =
  {
    val h2: Double = dInner / 2
    val dsq3: Double = dInner / 3.sqrt
    val d2sq3: Double = dInner / (3.sqrt * 2)
    val array = Array[Double](
      d2sq3, h2,
      dsq3, 0,
      d2sq3, -h2,
      -d2sq3, -h2,
      -dsq3, 0,
      -d2sq3, h2,
    )

    val hr = new HexRegImp(array)
    hr.rotate(rotation).slate(xCen, yCen)
  }

  /** Factory method for regular hexagon [[HexReg]]. Takes the inner diameter the rotation and then centre point. A rotation of 0 degrees places side 4 at the
   * bottom parallel to the X axis and side1 at the top. */
  def apply(dInner: Double, rotation: AngleVec, cen: Pt2): HexReg = apply(dInner, rotation, cen.x, cen.y)

  def apply(dInner: Double, rotation: AngleVec): HexReg = apply(dInner, rotation, 0, 0)

  def fromArray(array: Array[Double]): HexReg = new HexRegImp(array)

  given showEv: Show[HexReg] = new Show[HexReg]
  { override def typeStr: String = "HexReg"
    override def strT(obj: HexReg): String = obj.str
    override def show(obj: HexReg, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = -1): String = obj.tell(way, maxPlaces, minPlaces)
    override def syntaxDepth(obj: HexReg): Int = 2
  }

  given slate2Ev: Slate2[HexReg] = new Slate2[HexReg]
  { override def slate(obj: HexReg, operand: VecPt2): HexReg = obj.slate(operand)    
    override def slateXY(obj: HexReg, xOperand: Double, yOperand: Double): HexReg = obj.slate(xOperand, yOperand)
    override def slateX(obj: HexReg, xOperand: Double): HexReg = obj.slateX(xOperand)
    override def slateY(obj: HexReg, yOperand: Double): HexReg = obj.slateY(yOperand)
  }  
  
  given scaleEv: Scale[HexReg] = (obj: HexReg, operand: Double) => obj.scale(operand)
  given rotateEv: Rotate[HexReg] = (obj: HexReg, angle: AngleVec) => obj.rotate(angle)
  given prolignEv: Prolign[HexReg] = (obj, matrix) => obj.prolign(matrix)

  given reflectAxesEv: TransAxes[HexReg] = new TransAxes[HexReg]
  { override def negYT(obj: HexReg): HexReg = obj.negY
    override def negXT(obj: HexReg): HexReg = obj.negX
    override def rotate90(obj: HexReg): HexReg = obj.rotate90
    override def rotate180(obj: HexReg): HexReg = obj.rotate180
    override def rotate270(obj: HexReg): HexReg = obj.rotate180
  }

  def apoToArea(a: Double): Double = 2.0 * 3.sqrt * a.squared

  /** Implementation class for the [[HexReg]] trait. */
  final class HexRegImp(val arrayUnsafe: Array[Double]) extends HexReg with Tell2[Pt2, Pt2]
  { override type ThisT = HexRegImp

    override def fromArray(array: Array[Double]): HexRegImp = new HexRegImp(array)

    override def name1: String = "sd4Cen"
    override def name2: String = "sd1Cen"

    override def tell1: Pt2 = sd3Cen
    override def tell2: Pt2 = sd0Cen
    override implicit def show1: Show[Pt2] = Pt2.persistEv
    override implicit def show2: Show[Pt2] = Pt2.persistEv
    override def tellDepth: Int = 3

    def s1CenRMax: Pt2 = cen + (cen >> sd3Cen) * 2 / Sqrt3

    /** The radius of the inner circle of this regular hexagon. The shorter radius from the centre of the hexagon to the centre of a side. */
    override def apo: Double = diameter * 3.sqrt / 4

    /** The radius of the outer circle of this regular hexagon. The longer radius length from the centre of the Hexagon to a vertex. Also, the length of the
     * hexagon side. */
    override def radius: Double = diameter / 2

    /** The diameter of the outer circle of this regular hexagon. The longer diameter length from a vertex to the opposite vertex. This length is twice the
     * length of the hexagon side. */
    override def diameter: Double = v5.distTo(v2)

    @inline override def apoDiameter: Double = diameter * 3.sqrt / 2

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
}