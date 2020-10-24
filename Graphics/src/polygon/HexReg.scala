/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait HexReg extends Polygon6Plus
{
  def width: Double
  @inline final def radius: Double = width / 2
  @inline final def sideLen: Double = width * Sqrt3 / 2
  @inline final def longWidth: Double = width * Sqrt3

  /** Translate geometric transformation on a HexReg returns a HexReg. The return type of this method will be narrowed further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(offset: Vec2): HexReg = HexReg.cenV6(cen + offset, v6 + offset)

  /** Translate geometric transformation on a HexReg returns a HexReg. The return type of this method will be narrowed  further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(xOffset: Double, yOffset: Double): HexReg = HexReg.cenV6(cen.addXY(xOffset, yOffset), v6.addXY(xOffset, yOffset))

  /** Uniform scaling against both X and Y axes transformation on a HexReg returning a HexReg. Use the xyScale method for differential scaling. The
   * return type of this method will be narrowed further in descendant traits / classes. */
  override def scale(operand: Double): HexReg = HexReg.cenV6(cen * operand, v6 * operand)

  /** Mirror, reflection transformation of a HexReg across the X axis, returns a HexReg. */
  override def negY: HexReg = HexReg.cenV6(cen.negY, v6.negY)

  /** Mirror, reflection transformation of HexReg across the Y axis, returns a HexReg. */
  override def negX: HexReg = HexReg.cenV6(cen.negX, v6.negX)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a HexReg, returns a HexReg. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate90: HexReg = HexReg.cenV6(cen.rotate90, v6.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a HexReg, returns a HexReg. The return type will be narrowed in sub traits / classes. */
  override def rotate180: HexReg = HexReg.cenV6(cen.rotate180, v6.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a HexReg, returns a HexReg. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate270: HexReg = HexReg.cenV6(cen.rotate270, v6.rotate270)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): HexReg = HexReg.cenV6(cen.prolign(matrix), v6.prolign(matrix))
}

object HexReg
{
  def cenV6(cen: Vec2, v6: Vec2): HexReg = HexRegImp(cen.x, cen.y, v6.x, v6.y)

  final case class HexRegImp(xS5Cen: Double, yS5Cen: Double, xS2Cen: Double, yS2Cen: Double) extends HexReg
  {
    override def apply(index: Int): Vec2 = ???
    def s2Cen: Vec2 = Vec2(xS2Cen, yS2Cen)
    def s5Cen: Vec2 = Vec2(xS5Cen, yS5Cen)
    //override def sideLen: Double = (v6 - cen).magnitude
    def xCen: Double = (xS5Cen + xS2Cen) / 2
    def yCen: Double = (yS5Cen + yS2Cen) / 2
    @inline override def cen: Vec2 = Vec2(xCen, yCen)
    @inline override def width = (s2Cen - s5Cen).magnitude

    override def v1: Vec2 = v6.rotateAbout(cen, Deg60)
    override def x1: Double = v1.x
    override def y1: Double = v1.y
    override def v2: Vec2 = v6.rotateAbout(cen, Deg240)
    override def x2: Double = v2.x
    override def y2: Double = v2.y
    override def v3: Vec2 = v6.rotateAbout(cen, Deg180)
    override def x3: Double = v3.x
    override def y3: Double = v3.y
    override def v4: Vec2 = v6.rotateAbout(cen, Deg120)
    override def x4: Double = v4.x
    override def y4: Double = v4.y
    override def v5: Vec2 = v6.rotateAbout(cen, Deg60)
    override def x5: Double = v5.x
    override def y5: Double = v6.y
    override def x6: Double = v5.x
    override def y6: Double = v6.y
    def v6: Vec2 = Vec2(x6, y6)
    override def foreachVert(f: Vec2 => Unit): Unit = { f(v1); f(v2); f(v3); f(v4); f(v5); f(v6) }

    override def foreachVertTail[U](f: Vec2 => U): Unit = { f(v2); f(v3); f(v4); f(v5); f(v6) }

    override def ptsArray: Array[Double] = ???

    override def xVertsArray: Array[Double] = ???

    override def yVertsArray: Array[Double] = ???

    override def foreachPairTail[U](f: (Double, Double) => U): Unit = ???

    override def vertsNum: Int = 6

    /** Returns the X component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
    override def xVertGet(index: Int): Double = ???

    /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
    override def yVertGet(index: Int): Double = ???

    /** Reflect 2D geometric transformation across a line, line segment or ray on a HexReg, returns a HexReg. The Return type will be narrowed in sub
     * traits / classes. */
    override def reflect(lineLike: LineLike): HexReg = ???
  }

}
