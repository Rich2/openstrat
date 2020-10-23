/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait HexReg extends Polygon6Plus
{
  /** Translate geometric transformation on a Polygon returns a Polygon. The return type of this method will be narrowed further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(offset: Vec2): HexReg = HexReg.cenV6(cen + offset, v6 + offset)

  /** Translate geometric transformation on a Polygon returns a Polygon. The return type of this method will be narrowed  further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(xOffset: Double, yOffset: Double): Polygon = super.slate(xOffset, yOffset)

  /** Uniform scaling against both X and Y axes transformation on a polygon returning a Polygon. Use the xyScale method for differential scaling. The
   * return type of this method will be narrowed further in descendant traits / classes. */
  override def scale(operand: Double): Polygon = super.scale(operand)

  /** Mirror, reflection transformation of a Polygon across the X axis, returns a Polygon. */
  override def negY: Polygon = super.negY

  /** Mirror, reflection transformation of Polygon across the Y axis, returns a Polygon. */
  override def negX: Polygon = super.negX

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a Polygon, returns a Polygon. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate90: Polygon = super.rotate90

  /** Rotate 180 degrees 2D geometric transformation on a Polygon, returns a Polygon. The return type will be narrowed in sub traits / classes. */
  override def rotate180: Polygon = super.rotate180

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a Polygon, returns a Polygon. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate270: Polygon = super.rotate270

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): Polygon = super.prolign(matrix)
}

object HexReg
{
  def cenV6(cen: Vec2, v6: Vec2): HexReg = HexRegImp(cen.x, cen.y, v6.x, v6.y)

  case class HexRegImp(xCen: Double, yCen: Double, x6: Double, y6: Double) extends HexReg
  {
    override def apply(index: Int): Vec2 = ???

    override def cen: Vec2 = Vec2(xCen, yCen)
    def v6: Vec2 = Vec2(x6, y6)
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
    override def foreachVert(f: Vec2 => Unit): Unit = { f(v1); f(v2); f(v3); f(v4); f(v5); f(v6) }

    override def foreachVertTail[U](f: Vec2 => U): Unit = { f(v2); f(v3); f(v4); f(v5); f(v6) }

    override def ptsArray: Array[Double] = ???

    override def xVertsArray: Array[Double] = ???

    override def yVertsArray: Array[Double] = ???

    override def foreachPairTail[U](f: (Double, Double) => U): Unit = ???

    /** The number of vertices and also the number of sides in this Polygon. */
    override def vertsNum: Int = ???

    /** Returns the X component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
    override def xVertGet(index: Int): Double = ???

    /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
    override def yVertGet(index: Int): Double = ???

    /** Reflect 2D geometric transformation across a line, line segment or ray on a polygon, returns a Polygon. The Return type will be narrowed in sub
     * traits / classes. */
    override def reflect(lineLike: LineLike): Polygon = ???

    /** XY scaling 2D geometric transformation on a Polygon returns a Polygon. This allows different scaling factors across X and Y dimensions. The
     * return type will be narrowed in some, but not all descendant Polygon types. */
    override def xyScale(xOperand: Double, yOperand: Double): Polygon = ???
  }

}
