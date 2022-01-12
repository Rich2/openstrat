/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Regular Hexagon */
trait HexReg extends ShapeCentred with Polygon6Plus with ShowPrec
{ override def typeStr = "HexReg"

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

  override def vertsForeach[U](f: Pt2 => U): Unit = { f(v1); f(v2); f(v3); f(v4); f(v5); f(v6); () }

  override def foreachVertTail[U](f: Pt2 => U): Unit = { f(v2); f(v3); f(v4); f(v5); f(v6); () }
  override def foreachVertPairTail[U](f: (Double, Double) => U): Unit = { f(v1x, v1y);  f(v2x, v2y); f(v3x, v3y);  f(v4x, v4y); f(v5x, v5y); f(v6x, y6); () }

  override def vertsArrayX: Array[Double] = Array(v1x, v2x, v3x, v4x, v5x, v6x)

  override def vertsArrayY: Array[Double] = Array(v1y, v2y, v3y, v4y, v5y, y6)
  override def vertsArray: Array[Double] = ???

  override def vert(index: Int): Pt2 = index match
  { case 1 => v1
    case 2 => v2
    case 3 => v3
    case 4 => v4
    case 5 => v5
    case 6 => v6
    case n => excep(s"$n is out of range for a Hexagon vertex")
  }

  /** Returns the X component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  override def xVert(index: Int): Double = index match
  { case 1 => v1x
    case 2 => v2x
    case 3 => v3x
    case 4 => v4x
    case 5 => v5x
    case 6 => v6x
    case n => excep(s"$n is out of range for a Hexagon vertex")
  }

  /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  override def yVert(index: Int): Double = index match
  { case 1 => v1y
    case 2 => v2y
    case 3 => v3y
    case 4 => v4y
    case 5 => v5y
    case 6 => y6
    case n => excep(s"$n is out of range for a Hexagon vertex")
  }

  /** A Hexagon has 6 vertices. */
  final override def vertsNum: Int = 6

  /** Translate geometric transformation on a HexReg returns a HexReg. The return type of this method will be narrowed  further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slateXY(xDelta: Double, yDelta: Double): HexReg = HexReg.sd4Sd1(sd4Cen.addXY(xDelta, yDelta), sd1Cen.addXY(xDelta, yDelta))

  /** Uniform scaling against both X and Y axes transformation on a HexReg returning a HexReg. Use the xyScale method for differential scaling. The
   * return type of this method will be narrowed further in descendant traits / classes. */
  override def scale(operand: Double): HexReg = HexReg.sd4Sd1(sd4Cen.scale(operand), sd1Cen.scale(operand))

  /** Mirror, reflection transformation of a HexReg across the X axis, returns a HexReg. */
  override def negY: HexReg = HexReg.sd4Sd1(sd4Cen.negY, sd1Cen.negY)

  /** Mirror, reflection transformation of HexReg across the Y axis, returns a HexReg. */
  override def negX: HexReg = HexReg.sd4Sd1(sd4Cen.negX, sd1Cen.negX)

  override def rotate90: HexReg = HexReg.sd4Sd1(sd4Cen.rotate90, sd1Cen.rotate90)
  override def rotate180: HexReg = HexReg.sd4Sd1(sd4Cen.rotate180, sd1Cen.rotate180)
  override def rotate270: HexReg = HexReg.sd4Sd1(sd4Cen.rotate270, sd1Cen.rotate270)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): HexReg = HexReg.sd4Sd1(sd4Cen.prolign(matrix), sd1Cen.prolign(matrix))

  override def rotate(angle: AngleVec): HexReg = HexReg.sd4Sd1(sd4Cen.rotate(angle), sd1Cen.rotate(angle))

  /** Reflect 2D geometric transformation across a line, line segment or ray on a HexReg, returns a HexReg. The Return type will be narrowed in sub
   * traits / classes. */
  override def reflect(lineLike: LineLike): HexReg = HexReg.sd4Sd1(sd4Cen.reflect(lineLike), sd1Cen.reflect(lineLike))
}

/** Companion object for HegReg trait, contains [[HexRegImp]] implementation case for the general case of regular Hexagons. */
object HexReg
{
  /** Factory method for regular hexagon [[HexReg]]. Takes the inner diameter the rotation and then centre point. A rotation of 0 degrees places side
   * 4 at the bottom parallel to the X axis and side1 at the top. */
  def apply(dInner: Double, rotation: AngleVec, cen: Pt2 = Pt2Z): HexReg =
  { val v4 = (Ang90 + rotation).toVec2(dInner /2)
    val sd4Cen = cen + v4//.subY(dInner / 2).rotate(rotation)
    val sd1Cen = cen - v4 //.addY(dInner / 2).rotate(rotation)
    HexRegImp(sd4Cen.x, sd4Cen.y, sd1Cen.x, sd1Cen.y)
  }

  /** Factory method for regular hexagon [[HexReg]]. Takes the inner diameter the rotation and then centre point. A rotation of 0 degrees places side
   * 4 at the bottom parallel to the X axis and side1 at the top. */
  def apply(dInner: Double, rotation: AngleVec, xCen: Double, yCen: Double): HexReg = apply(dInner, rotation, xCen pp yCen)

  /** Factory method for HexReg, taking 2 points as parameters, the centre of side 4, followed by the centre of side 1. In the default alignment for
   * a regular hexagon both Y values will be 0. */
  def sd4Sd1(sd4Cen: Pt2, sd1Cen: Pt2): HexReg = HexRegImp(sd4Cen.x, sd4Cen.y, sd1Cen.x, sd1Cen.y)

  implicit val showImplicit: ShowPrecisionT[HexReg] = new ShowPrecisionT[HexReg]
  { override def typeStr: String = "HexReg"
    override def strT(obj: HexReg): String = obj.str
    override def showT(obj: HexReg, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.show(way, maxPlaces, 0)
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
  final case class HexRegImp(sd4CenX: Double, sd4CenY: Double, sd1CenX: Double, sd1CenY: Double) extends HexReg with ShowPrec2[Pt2, Pt2]
  {
    override def name1: String = "sd4Cen"
    override def name2: String = "sd1Cen"

    override def show1: Pt2 = sd4Cen
    override def show2: Pt2 = sd1Cen
    override implicit def showT1: ShowPrecisionT[Pt2] = Pt2.persistImplicit
    override implicit def showT2: ShowPrecisionT[Pt2] = Pt2.persistImplicit
    override def syntaxDepth: Int = 3

    override def vert(index: Int): Pt2 = index match {
      case 1 => v1
      case 2 => v2
      case 3 => v3
      case 4 => v4
      case 5 => v5
      case 6 => v6
      case n => excep(s"There is no vertex $n on a Hexagon.")
    }
    def sd4Cen: Pt2 = Pt2(sd4CenX, sd4CenY)
    def sd1Cen: Pt2 = Pt2(sd1CenX, sd1CenY)
    def cenX: Double = (sd1CenX + sd4CenX) / 2
    def cenY: Double = (sd1CenY + sd4CenY) / 2
    def s1CenRMax: Pt2 = cen + (cen >> sd4Cen) * 2 / Sqrt3
    @inline override def cen: Pt2 = Pt2(cenX, cenY)
    @inline override def diameterIn: Double = sd1Cen.distTo(sd4Cen)
    override def v1: Pt2 = s1CenRMax.rotateAbout(cen,  - Deg30)
    override def v1x: Double = v1.x
    override def v1y: Double = v1.y
    override def v2: Pt2 = s1CenRMax.rotateAbout(cen, - Deg90)
    override def v2x: Double = v2.x
    override def v2y: Double = v2.y
    override def v3: Pt2 = s1CenRMax.rotateAbout(cen, -Deg150)
    override def v3x: Double = v3.x
    override def v3y: Double = v3.y
    override def v4: Pt2 = s1CenRMax.rotateAbout(cen, Deg150)
    override def v4x: Double = v4.x
    override def v4y: Double = v4.y
    override def v5: Pt2 = s1CenRMax.rotateAbout(cen, Deg90)
    override def v5x: Double = v5.x
    override def v5y: Double = v5.y
    def v6: Pt2 = s1CenRMax.rotateAbout(cen, Deg30)
    override def v6x: Double = v6.x
    override def y6: Double = v6.y

    override def sd2CenX: Double = average(v1x, v2x)
    override def sd2CenY: Double = average(v1y, v2y)
    override def sd2Cen: Pt2 = v1 midPt v2
    override def sd3CenX: Double = average(v2x, v3x)
    override def sd3CenY: Double = average(v2y, v3y)
    override def sd3Cen: Pt2 = v2 midPt v3
    override def sd5CenX: Double = average(v4x, v5x)
    override def sd5CenY: Double = average(v4y, v5y)
    override def sd5Cen: Pt2 = v4 midPt v5
    override def sd6CenX: Double = average(v5x, v6x)
    override def sd6CenY: Double = average(v5y, y6)
    override def sd6Cen: Pt2 = v5 midPt v6
  }
}