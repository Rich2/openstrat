/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon */
trait HexReg extends ShapeCentred with Polygon6Plus with Show
{  override def typeStr = "HexReg"

  /** The diameter of the inner circle of this regular hexagon. The shorter diameter from the centre of a side to the centre of the opposite side. */
  def dInner: Double

  /** The radius of the inner circle of this regular hexagon. The shorter radius from the centre of the hexagon to the centre of a side. */
  @inline def rInner: Double = dInner / 2

  /** The radius of the outer circle of this regular hexagon. The longer radius length from the centre of the Hexagon to a vertex. Also the length of
   *  the hexagon side. */
  @inline final def rOuter: Double = dInner / Sqrt3

  /** The diameter of the outer circle of this regular hexagon. The longer diameter length from a vertex to the opposite vertex. This lenght is twice
   * the length of the hexagon side. */
  @inline final def dOuter: Double = dInner * 2 / Sqrt3

  override def foreachVert[U](f: Pt2 => U): Unit = { f(v1); f(v2); f(v3); f(v4); f(v5); f(v6); () }

  override def foreachVertTail[U](f: Pt2 => U): Unit = { f(v2); f(v3); f(v4); f(v5); f(v6); () }
  override def foreachPairTail[U](f: (Double, Double) => U): Unit = { f(x1, y1);  f(x2, y2); f(x3, y3);  f(x4, y4); f(x5, y5); f(x6, y6); () }

  override def xVertsArray: Array[Double] = Array(x1, x2, x3, x4, x5, x6)

  override def yVertsArray: Array[Double] = Array(y1, y2, y3, y4, y5, y6)
  override def ptsArray: Array[Double] = ???

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
  { case 1 => x1
    case 2 => x2
    case 3 => x3
    case 4 => x4
    case 5 => x5
    case 6 => x6
    case n => excep(s"$n is out of range for a Hexagon vertex")
  }

  /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  override def yVert(index: Int): Double = index match
  { case 1 => y1
    case 2 => y2
    case 3 => y3
    case 4 => y4
    case 5 => y5
    case 6 => y6
    case n => excep(s"$n is out of range for a Hexagon vertex")
  }

  /** A Hexagon has 6 vertices. */
  final override def vertsNum: Int = 6

  /** Translate geometric transformation on a HexReg returns a HexReg. The return type of this method will be narrowed  further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def xySlate(xOffset: Double, yOffset: Double): HexReg = HexReg.sd4Sd1(sd4Cen.addXY(xOffset, yOffset), sd1Cen.addXY(xOffset, yOffset))

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

  implicit val slateImplicit: Slate[HexReg] = (obj: HexReg, dx: Double, dy: Double) => obj.xySlate(dx, dy)
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
  final case class HexRegImp(xSd4Cen: Double, ySd4Cen: Double, xSd1Cen: Double, ySd1Cen: Double) extends HexReg
  {
    override def str: String = "HexReg".appendParenthSemis(sd4Cen.strComma, sd1Cen.strComma)


    /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
    override def show(way: Show.Way, decimalPlaces: Int): String = ???

    override def vert(index: Int): Pt2 = index match {
      case 1 => v1
      case 2 => v2
      case 3 => v3
      case 4 => v4
      case 5 => v5
      case 6 => v6
      case n => excep(s"There is no vertex $n on a Hexagon.")
    }
    def sd4Cen: Pt2 = Pt2(xSd4Cen, ySd4Cen)
    def sd1Cen: Pt2 = Pt2(xSd1Cen, ySd1Cen)
    def xCen: Double = (xSd1Cen + xSd4Cen) / 2
    def yCen: Double = (ySd1Cen + ySd4Cen) / 2
    def s1CenRMax: Pt2 = cen + (cen >> sd4Cen) * 2 / Sqrt3
    @inline override def cen: Pt2 = Pt2(xCen, yCen)
    @inline override def dInner: Double = sd1Cen.distTo(sd4Cen)
    override def v1: Pt2 = s1CenRMax.rotateAbout(cen,  - Deg30)
    override def x1: Double = v1.x
    override def y1: Double = v1.y
    override def v2: Pt2 = s1CenRMax.rotateAbout(cen, - Deg90)
    override def x2: Double = v2.x
    override def y2: Double = v2.y
    override def v3: Pt2 = s1CenRMax.rotateAbout(cen, -Deg150)
    override def x3: Double = v3.x
    override def y3: Double = v3.y
    override def v4: Pt2 = s1CenRMax.rotateAbout(cen, Deg150)
    override def x4: Double = v4.x
    override def y4: Double = v4.y
    override def v5: Pt2 = s1CenRMax.rotateAbout(cen, Deg90)
    override def x5: Double = v5.x
    override def y5: Double = v5.y
    def v6: Pt2 = s1CenRMax.rotateAbout(cen, Deg30)
    override def x6: Double = v6.x
    override def y6: Double = v6.y

    override def xSd2Cen: Double = average(x1, x2)
    override def ySd2Cen: Double = average(y1, y2)
    override def sd2Cen: Pt2 = v1 midPt v2
    override def xSd3Cen: Double = average(x2, x3)
    override def ySd3Cen: Double = average(y2, y3)
    override def sd3Cen: Pt2 = v2 midPt v3
    override def xSd5Cen: Double = average(x4, x5)
    override def ySd5Cen: Double = average(y4, y5)
    override def sd5Cen: Pt2 = v4 midPt v5
    override def xSd6Cen: Double = average(x5, x6)
    override def ySd6Cen: Double = average(y5, y6)
    override def sd6Cen: Pt2 = v5 midPt v6
  }
}