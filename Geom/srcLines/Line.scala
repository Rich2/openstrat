/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** An infinite length 2-dimensional straight line trait. Note this is the mathematical definition of a line. In SVG and other APIs the name line is used for a
 * line segment, which in openstrat is called a [[LineSeg]] */
sealed trait Line extends LineLike
{ /** Reflects, mirrors a point across this line. */
  def reflectPt(pt: Pt2): Pt2

  override def slate(operand: VecPt2): Line = ???
  override def slate(xOperand: Double, yOperand: Double): Line
  override def slateX(xOperand: Double): Line
  override def slateY(yOperand: Double): Line
  override def scale(operand: Double): Line = ???
  override def negY: Line = ???
  override def negX: Line = ???

  /** Transforms this Line using a [[AxlignMatrix]]. */
  override def prolign(matrix: AxlignMatrix): Line = ???

  override def rotate90: Line = ???
  override def rotate180: Line = ???
  override def rotate270: Line = ???

  override def rotate(rotation: AngleVec): Line = ???
  override def reflect(lineLike: LineLike): Line = ???
  override def scaleXY(xOperand: Double, yOperand: Double): Line = ???
  override def shearX(operand: Double): Line = ???
  override def shearY(operand: Double): Line = ???
}

/** An infinite length 2-dimensional straight line defined in terms of its X value and and an offset. It is defined for all values of Y, but not for all values
 * of x if the xFactor is 0. */
sealed case class YLine(xFactor: Double, offset: Double) extends Line
{ def y(x: Double): Double = xFactor * x + offset

  /** The point at which the line crosses the Y Axis, unless this YLine is the YAxis in which case it is merely a point where the line intersects the
   * Y axis. */
  def yIntersection: Pt2 = Pt2(0, offset)

  override def reflectPt(pt: Pt2): Pt2 =
  {
    val v2: Pt2 = Pt2(1, xFactor + offset)
    val lineDelta: Vec2 = yIntersection >> v2
    val lineUnitVector: Vec2 = lineDelta / lineDelta.magnitude
    val r1: Vec2 = (pt >> yIntersection) - 2 * (pt >> yIntersection).dot(lineUnitVector) * lineUnitVector
    yIntersection + r1
  }
  
  override def slate(offset: VecPt2): Line = ???
  override def slate(xOperand: Double, yOperand: Double): Line = ???
  override def slateX(xOperand: Double): Line = ???
  override def slateY(yOperand: Double): Line = ???
}

/** An infinite length 2-dimensional straight line defined in terms of its Y value and and an offset. It is defined for all values of X, but not for all values
 * of x if the xFactor is 0. */
sealed case class XLine(yFactor: Double, offset: Double) extends Line
{ def x(y: Double): Double = yFactor * y + offset

  /** The point at which the line crosses the Y Axis, unless this YLine is the YAxis in which case it is merely a point where the line intersects the Y axis. */
  def xIntersection: Pt2 = Pt2(offset, 0)

  override def reflectPt(pt: Pt2): Pt2 =
  { val v2: Pt2 = Pt2(yFactor + offset, 1)
    val lineDelta =  xIntersection >> v2
    val lineUnitVector = lineDelta / lineDelta.magnitude
    val r1 = (pt >> xIntersection) - 2 * (pt >> xIntersection).dot(lineUnitVector) * lineUnitVector
    xIntersection + r1
  }
  
  override def slate(xOperand: Double, yOperand: Double): Line = ???
  override def slateX(xOperand: Double): Line = ???
  override def slateY(yOperand: Double): Line = ???
}

/** An infinite length 2-dimensional straight line that is parallel to the X Axis. It is defined for all values of Y, but for only 1 value of X. */
sealed class YParallel(offset: Double) extends YLine(0, offset )
{
  /** Translate 2D geometric transformation. This abstract method returns a [[Line]]. The Return type will be narrowed in sub traits. */
  override def slate(xOperand: Double, yOperand: Double): YLine = ???
}

/** An infinite length 2-dimensional straight line that is parallel to the X Axis. It is defined for all values of X, but for only 1 value of Y. */
sealed class XParallel(offset: Double) extends XLine(0, offset )
{
  /** Translate 2D geometric transformation. This abstract method returns a [[Line]]. The Return type will be narrowed in sub traits. */
  override def slate(xOperand: Double, yOperand: Double): XLine = ???
}

sealed trait XorYAxis extends Line

/** The Y Axis in 2-dimensional space. */
object YAxis extends XParallel( 0) with XorYAxis

/** The X Axis in 2-dimensional space. */
object XAxis extends XParallel( 0) with XorYAxis