/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Circular arc. Has a rotation counter to allow rotation deltas greater than 360 degrees and less than - 360 degrees. The CArc is intended to
 *  function as closely as possible to the functioning of CArcTails in a curve path. Hence the decision to store the three points as fields rather
 *  using the [[AngleVec]] of the arc which would allow less data. This is to avoid calculation /rounding errors in the start and end points, which
 *  will be used by other [[CurveSeg]]s in curve paths. */
class CArc private(val xStart: Double, val yStart: Double, val xCen: Double, val yCen: Double, val xEnd: Double, val yEnd: Double,
  val counter: Int) extends EArc
{ /** The centre of this circular arc. */
  override def cen: Pt2 = Pt2(xCen, yCen)

  /** Radius of the this circular arc. */
  def radius: Double = cen.distTo(pStart)

  /** Draws this geometric element to produce a [[CArcDraw]] graphical element, that can be displayed or printed. */
  override def draw(lineColour: Colour, lineWidth: Double): GraphicElem = CArcDraw(this, lineColour, lineWidth)

  /** Translate 2D geometric transformation on this CArc returns a CArc. */
  override def slate(xOffset: Double, yOffset: Double): CArc = CArc(pStart.addXY(xOffset, yOffset), cen.addXY(xOffset, yOffset),
    pEnd.addXY(xOffset, yOffset), counter)

  /** Translate 2D geometric transformation on this CArc. The Return type will be narrowed in sub traits and  classes. */
  override def slate(offset: Vec2Like): CArc = CArc(pStart.slate(offset), cen.slate(offset), pEnd.slate(offset), counter)

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): CArc = CArc(pStart.scale(operand), cen.scale(operand), pEnd.scale(operand), counter)

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y on this CArc returns a CArc. */
  override def negY: CArc = CArc(pStart.negY, cen.negY, pEnd.negY, -counter)

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating Xon this CArc returns a CArc. */
  override def negX: CArc = CArc(pStart.negX, cen.negX, pEnd.negX, -counter)

  /** 2D Transformation using a [[ProlignMatrix]]. */
  override def prolign(matrix: ProlignMatrix): CArc = ???

  /** Rotation 2D geometric transformation on a CArc returns a CArc. */
  override def rotate(angle: AngleVec): CArc = CArc(pStart.rotate(angle), cen.rotate(angle), pEnd.rotate(angle), counter)

  /** Reflect 2D geometric transformation across a line, line segment or ray on a CArc returns a CArc. */
  override def reflect(lineLike: LineLike): CArc = CArc(pStart.reflect(lineLike), cen.reflect(lineLike), pEnd.reflect(lineLike), counter)

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  override def canEqual(that: Any): Boolean = ???
}

object CArc
{ /** Factory method for creating circular arcs. */
  def apply(pStart: Pt2, cen: Pt2, pEnd: Pt2, counter: Int): CArc = new CArc(pStart.x, pStart.y, cen.x, cen.y, pEnd.x, pEnd.y, counter)

  /** Creates a positive direction or anti clockwise circular arc, with an [[AngleVec]] from 0 until 360 degrees. */
  def pos(pStart: Pt2, cen: Pt2, pEnd: Pt2): CArc = new CArc(pStart.x, pStart.y, cen.x, cen.y, pEnd.x, pEnd.y, ife(pStart == pEnd, 0, 1))

  /** Creates a negative direction or clockwise circular arc, with an [[AngleVec]] from 0 until -360 degrees. */
  def neg(pStart: Pt2, cen: Pt2, pEnd: Pt2): CArc = new CArc(pStart.x, pStart.y, cen.x, cen.y, pEnd.x, pEnd.y, ife(pStart == pEnd, 0, -1))
}