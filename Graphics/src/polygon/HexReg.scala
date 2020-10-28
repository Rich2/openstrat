/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon */
trait HexReg extends Polygon6Plus
{
  /** The maximum, longer diameter. */
  def dMin: Double
  @inline def rMin: Double = dMin / 2
  /** The maximum radius length. Also the length of the Hexagon side. */
  @inline final def rMax: Double = dMin / Sqrt3

  @inline final def dMax: Double = dMin * 2 / Sqrt3

  def s1Cen: Vec2
  def s4Cen: Vec2
  override def foreachVert(f: Vec2 => Unit): Unit = { f(v1); f(v2); f(v3); f(v4); f(v5); f(v6) }

  override def foreachVertTail[U](f: Vec2 => U): Unit = { f(v2); f(v3); f(v4); f(v5); f(v6) }

  /** A Hexagon has 6 vertices. */
  final override def vertsNum: Int = 6

  /** Translate geometric transformation on a HexReg returns a HexReg. The return type of this method will be narrowed further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(offset: Vec2): HexReg = HexReg.s4s1(s4Cen + offset, s1Cen + offset)

  /** Translate geometric transformation on a HexReg returns a HexReg. The return type of this method will be narrowed  further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(xOffset: Double, yOffset: Double): HexReg = HexReg.s4s1(s4Cen.addXY(xOffset, yOffset), s1Cen.addXY(xOffset, yOffset))

  /** Uniform scaling against both X and Y axes transformation on a HexReg returning a HexReg. Use the xyScale method for differential scaling. The
   * return type of this method will be narrowed further in descendant traits / classes. */
  override def scale(operand: Double): HexReg = HexReg.s4s1(s4Cen * operand, s1Cen * operand)

  /** Mirror, reflection transformation of a HexReg across the X axis, returns a HexReg. */
  override def negY: HexReg = HexReg.s4s1(s4Cen.negY, s1Cen.negY)

  /** Mirror, reflection transformation of HexReg across the Y axis, returns a HexReg. */
  override def negX: HexReg = HexReg.s4s1(s4Cen.negX, s1Cen.negX)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a HexReg, returns a HexReg. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate90: HexReg = HexReg.s4s1(s4Cen.rotate90, s1Cen.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a HexReg, returns a HexReg. The return type will be narrowed in sub traits / classes. */
  override def rotate180: HexReg = HexReg.s4s1(s4Cen.rotate180, s1Cen.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a HexReg, returns a HexReg. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate270: HexReg = HexReg.s4s1(s4Cen.rotate270, s1Cen.rotate270)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): HexReg = HexReg.s4s1(s4Cen.prolign(matrix), s1Cen.prolign(matrix))

  override def rotate(angle: Angle): HexReg = HexReg.s4s1(s4Cen.rotate(angle), s1Cen.rotate(angle))
}

/** Companion object for HegReg trait, contains [[HexRegImp]] implementation case for the general case of regular Hexagons. */
object HexReg
{
  def s4s1(s4Cen: Vec2, s1Cen: Vec2): HexReg = HexRegImp(s4Cen.x, s4Cen.y, s1Cen.x, s1Cen.y)

  implicit val slateImplicit: Slate[HexReg] = (obj: HexReg, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[HexReg] = (obj: HexReg, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[HexReg] = (obj: HexReg, angle: Angle) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[HexReg] = (obj, matrix) => obj.prolign(matrix)


  implicit val reflectAxesImplicit: TransAxes[HexReg] = new TransAxes[HexReg]
  { override def negYT(obj: HexReg): HexReg = obj.negY
    override def negXT(obj: HexReg): HexReg = obj.negX
    override def rotate90T(obj: HexReg): HexReg = obj.rotate90
    override def rotate180T(obj: HexReg): HexReg = obj.rotate180
    override def rotate270T(obj: HexReg): HexReg = obj.rotate270
  }

  /** Implementation class for the [[HexReg]] trait. */
  final case class HexRegImp(xs4Cen: Double, ys4Cen: Double, xs1Cen: Double, ys1Cen: Double) extends HexReg
  {
    override def vert(index: Int): Vec2 = ???
    def s4Cen: Vec2 = Vec2(xs4Cen, ys4Cen)
    def s1Cen: Vec2 = Vec2(xs1Cen, ys1Cen)
    def xCen: Double = (xs1Cen + xs4Cen) / 2
    def yCen: Double = (ys1Cen + ys4Cen) / 2
    def s1CenRMax: Vec2 = cen + (s4Cen - cen) * 2 / Sqrt3
    @inline override def cen: Vec2 = Vec2(xCen, yCen)
    @inline override def dMin: Double = (s4Cen - s1Cen).magnitude
    override def v1: Vec2 = s1CenRMax.rotateAbout(cen,  - Deg30)
    override def x1: Double = v1.x
    override def y1: Double = v1.y
    override def v2: Vec2 = s1CenRMax.rotateAbout(cen, - Deg90)
    override def x2: Double = v2.x
    override def y2: Double = v2.y
    override def v3: Vec2 = s1CenRMax.rotateAbout(cen, Deg150)
    override def x3: Double = v3.x
    override def y3: Double = v3.y
    override def v4: Vec2 = s1CenRMax.rotateAbout(cen, Deg150)
    override def x4: Double = v4.x
    override def y4: Double = v4.y
    override def v5: Vec2 = s1CenRMax.rotateAbout(cen, Deg90)
    override def x5: Double = v5.x
    override def y5: Double = v5.y

    def v6: Vec2 = s1CenRMax.rotateAbout(cen, Deg30)
    override def x6: Double = v6.x
    override def y6: Double = v6.y

    override def ptsArray: Array[Double] = ???

    override def xVertsArray: Array[Double] = ???

    override def yVertsArray: Array[Double] = ???

    override def foreachPairTail[U](f: (Double, Double) => U): Unit = ???

    /** Returns the X component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
    override def xVert(index: Int): Double = ???

    /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
    override def yVert(index: Int): Double = ???

    /** Reflect 2D geometric transformation across a line, line segment or ray on a HexReg, returns a HexReg. The Return type will be narrowed in sub
     * traits / classes. */
    override def reflect(lineLike: LineLike): HexReg = ???
  }
}
