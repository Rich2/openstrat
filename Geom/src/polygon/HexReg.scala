/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Regular Hexagon */
trait HexReg extends ShapeCentred with Polygon6Plus with ShowDec
{ type ThisT <: HexReg
  override def typeStr = "HexReg"

  def cenX: Double = (v0x + v3x) / 2
  def cenY: Double = (v0y + v3y) / 2

  def mapHexReg(f: Pt2 => Pt2): HexReg = HexReg.fromArray(unsafeMap(f))

  //sd3CenX: Double, sd3CenY: Double, sd0CenX: Double, sd0CenY: Double
  /** The diameter of the inner circle of this regular hexagon. The shorter diameter from the centre of a side to the centre of the opposite side. */
  def diameterIn: Double

  /** The radius of the inner circle of this regular hexagon. The shorter radius from the centre of the hexagon to the centre of a side. */
  @inline def radiusIn: Double = diameterIn / 2

  /** The radius of the outer circle of this regular hexagon. The longer radius length from the centre of the Hexagon to a vertex. Also the length of
   *  the hexagon side. */
  @inline final def radiusOut: Double = diameterIn / Sqrt3

  /** The diameter of the outer circle of this regular hexagon. The longer diameter length from a vertex to the opposite vertex. This lenght is twice
   * the length of the hexagon side. */
  @inline final def diameterOut: Double = diameterIn * 2 / Sqrt3

  /** A Hexagon has 6 vertices. */
  final override def vertsNum: Int = 6

  /** Translate geometric transformation on a HexReg returns a HexReg. The return type of this method will be narrowed  further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slateXY(xDelta: Double, yDelta: Double): HexReg = mapHexReg(_.addXY(xDelta, yDelta))

  /** Uniform scaling against both X and Y axes transformation on a HexReg returning a HexReg. Use the xyScale method for differential scaling. The
   * return type of this method will be narrowed further in descendant traits / classes. */
  override def scale(operand: Double): HexReg = mapHexReg(_.scale(operand))

  /** Mirror, reflection transformation of a HexReg across the X axis, returns a HexReg. */
  override def negY: HexReg = HexReg.fromArray(unsafeNegY)

  /** Mirror, reflection transformation of HexReg across the Y axis, returns a HexReg. */
  override def negX: HexReg = HexReg.fromArray(unsafeNegX)

  override def rotate90: HexReg = mapHexReg(_.rotate90)
  override def rotate180: HexReg = mapHexReg(_.rotate180)
  override def rotate270: HexReg = mapHexReg(_.rotate270)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): HexReg = mapHexReg(_.prolign(matrix))

  override def rotate(angle: AngleVec): HexReg = mapHexReg(_.rotate(angle))

  /** Reflect 2D geometric transformation across a line, line segment or ray on a HexReg, returns a HexReg. The Return type will be narrowed in sub
   * traits / classes. */
  override def reflect(lineLike: LineLike): HexReg = mapHexReg(_.reflect(lineLike))
}

/** Companion object for HegReg trait, contains [[HexRegImp]] implementation case for the general case of regular Hexagons. */
object HexReg
{
  /** Factory method for regular hexagon [[HexReg]]. Takes the inner diameter the rotation and then centre point. A rotation of 0 degrees places side
   * 4 at the bottom parallel to the X axis and side1 at the top. */
  def apply(dInner: Double, rotation: AngleVec, cen: Pt2 = Pt2Z): HexReg = HexParrX(dInner, cen).rotate(rotation)

  /** Factory method for regular hexagon [[HexReg]]. Takes the inner diameter the rotation and then centre point. A rotation of 0 degrees places side
   * 4 at the bottom parallel to the X axis and side1 at the top. */
  def apply(dInner: Double, rotation: AngleVec, xCen: Double, yCen: Double): HexReg = apply(dInner, rotation, xCen pp yCen)

  def fromArray(array: Array[Double]): HexReg = new HexRegImp(array)

  /** Factory method for HexReg, taking 2 points as parameters, the centre of side 4, followed by the centre of side 1. In the default alignment for
   * a regular hexagon both Y values will be 0. */
  //def sd4Sd1(sd4Cen: Pt2, sd1Cen: Pt2): HexReg = HexRegImp(sd4Cen.x, sd4Cen.y, sd1Cen.x, sd1Cen.y)

  implicit val showImplicit: ShowT[HexReg] = new ShowT[HexReg]
  { override def typeStr: String = "HexReg"
    override def strT(obj: HexReg): String = obj.str
    override def showDecT(obj: HexReg, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.showDec(way, maxPlaces, 0)
    override def syntaxDepthT(obj: HexReg): Int = 2
  }

  implicit val slateImplicit: Slate[HexReg] = (obj: HexReg, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[HexReg] = (obj: HexReg, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[HexReg] = (obj: HexReg, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[HexReg] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[HexReg] = new TransAxes[HexReg]
  { override def negYT(obj: HexReg): HexReg = obj.negY
    override def negXT(obj: HexReg): HexReg = obj.negX
    override def rotate90(obj: HexReg): HexReg = obj.rotate90
    override def rotate180(obj: HexReg): HexReg = obj.rotate180
    override def rotate270(obj: HexReg): HexReg = obj.rotate180
  }

  /** Implementation class for the [[HexReg]] trait. */
  final class HexRegImp(val unsafeArray: Array[Double]) extends HexReg with Show2[Pt2, Pt2]
  { override type ThisT = HexRegImp

    override def unsafeFromArray(array: Array[Double]): HexRegImp = new HexRegImp(array)

    override def name1: String = "sd4Cen"
    override def name2: String = "sd1Cen"

    override def show1: Pt2 = sd3Cen
    override def show2: Pt2 = sd0Cen
    override implicit def showT1: ShowT[Pt2] = Pt2.persistImplicit
    override implicit def showT2: ShowT[Pt2] = Pt2.persistImplicit
    override def syntaxDepth: Int = 3

    def s1CenRMax: Pt2 = cen + (cen >> sd3Cen) * 2 / Sqrt3
    @inline override def cen: Pt2 = Pt2(cenX, cenY)
    @inline override def diameterIn: Double = sd0Cen.distTo(sd3Cen)
  }

  object HexRegImp {
    /*def apply(sd3CenX: Double, sd3CenY: Double, sd0CenX: Double, sd0CenY: Double) : HexRegImp = {
      val h = 20 //height / 2
      val array = Array[Double](20 - h /2, 20 + h,
        20 + h /2, 20 + h,
        h, 0,
        0, 0,
        0, 0,
        0, 0)
      new HexRegImp(array)
    }*/
  }
}