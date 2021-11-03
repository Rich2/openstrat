/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Circular arc. Has a rotation counter to allow rotation deltas greater than 360 degrees and less than - 360 degrees. The CArc is intended to
 *  function as closely as possible to the functioning of CArcTails in a curve path. Hence the decision to store the three points as fields rather
 *  using the [[AngleVec]] of the arc which would allow less data. This is to avoid calculation /rounding errors in the start and end points, which
 *  will be used by other [[CurveSeg]]s in curve paths.
 *  @groupdesc EllipticalGroup Class members that treat this circular arc as a special case of an elliptical arc.
 *  @groupname EllipticalGroup Elliptical Members
 *  @groupprio EllipticalGroup 1010 */
class CArc private(val startX: Double, val startY: Double, val cenX: Double, val cenY: Double, val endX: Double, val endY: Double,
                   val counter: Int) extends EArclign
{ /** The centre of this circular arc. */
  override def cen: Pt2 = Pt2(cenX, cenY)

  /** Radius of the this circular arc. */
  def radius: Double = cen.distTo(pStart)

  /** The chord of this Arc */
  def chord: LineSeg = pStart.lineTo(pEnd)

  /** The mid point of the chord of this arc. */
  def chordCen: Pt2 = pStart.midPt(pEnd)

  def addRotations(delta: Int): CArc = new CArc(startX, startY, cenX, cenY, endX, endY, counter + delta)

  /** Draws this geometric element to produce a [[CArcDraw]] graphical element, that can be displayed or printed. */
  override def draw(lineColour: Colour, lineWidth: Double): CArcDraw = CArcDraw(this, lineColour, lineWidth)
  /** Translate 2D geometric transformation on this CArc returns a CArc. */
  override def slateXY(xDelta: Double, yDelta: Double): CArc = CArc(pStart.addXY(xDelta, yDelta), cen.addXY(xDelta, yDelta),
    pEnd.addXY(xDelta, yDelta), counter)

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): CArc = CArc(pStart.scale(operand), cen.scale(operand), pEnd.scale(operand), counter)

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y on this CArc returns a CArc. */
  override def negY: CArc = CArc(pStart.negY, cen.negY, pEnd.negY, -counter)

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating Xon this CArc returns a CArc. */
  override def negX: CArc = CArc(pStart.negX, cen.negX, pEnd.negX, -counter)

  /** Rotation of 90 degrees, 2D geometric transformation on a GeomElem. The return type will be narrowed in sub classes and
   * traits. */
  override def rotate90: CArc = ???

  /** Rotation of 180 degrees, 2D geometric transformation on a GeomElem. The return type will be narrowed in sub classes and
   * traits. */
  override def rotate180: CArc = ???

  /** Rotation positive or anti clockwise 270 degrees, 2D geometric transformation on a GeomElem. The return type will be narrowed in sub classes and
   * traits. */
  override def rotate270: CArc = ???

  /** 2D Transformation using a [[ProlignMatrix]]. */
  override def prolign(matrix: ProlignMatrix): CArc = ???

  /** Rotation 2D geometric transformation on a CArc returns a CArc. */
  override def rotate(angle: AngleVec): CArc = CArc(pStart.rotate(angle), cen.rotate(angle), pEnd.rotate(angle), counter)

  /** Reflect 2D geometric transformation across a line, line segment or ray on a CArc returns a CArc. */
  override def reflect(lineLike: LineLike): CArc = CArc(pStart.reflect(lineLike), cen.reflect(lineLike), pEnd.reflect(lineLike), counter)

  /* EllipticalGroup Class members that treat this circular arc as a special case of an elliptical arc. */

  /** The end of elliptical axis 1. By default this is the right vertex of the Ellipse, so this point on the circle is given although there is no
   * actual vertex there on this circle, which is a special case of an ellipse. */
  override def axesPt1: Pt2 = cen.addX(radius)

  /** The Y component of the end point of axis 1, treating this circular arc as an elliptical arc. Axis1 is specified as horizontal and point 1 is
   *  specified as the right of the circle this CArc is based on. */
  override def axesPt1x: Double = cenX + radius

  /** The Y component of the end point of axis 1, treating this circular arc as an elliptical arc. Axis1 is specified as horizontal and point 1 is
   *  specified as the right of the circle this CArc is based on. */
  override def axesPt1y: Double = cenY

  /** The start of elliptical axis 2. By default this is the bottom vertex of the Ellipse, so this point on the circle is given although there is no
   *  actual vertex there on this circle, which is a special case of an ellipse. */
  override def axesPt2: Pt2 = cen.subY(radius)

  /** The X component of the start point of axis 2. By default this is at the bottom of the Ellipse. Mathematically this can be referred to as a vertex for the major
   * axis or a co-vertex for the minor axis.. */
  override def axesPt2x: Double = ???

  /** The y component of the start point of axis 2. By default this is at the bottom of the Ellipse. Mathematically this can be referred to as a
   * vertex for the major axis or a co-vertex for the minor axis. */
  override def axesPt2y: Double = ???

  /** The start of elliptical axis 1. By default this is the left vertex of the Ellipse, so this point on the circle is given although there is no
   * actual vertex there on this circle, which is a special case of an ellipse. */
  override def axesPt3: Pt2 = cen.subX(radius)

  /** The X component of the start point of elliptical axis 1. By default this is the left vertex of the Ellipse, so this point on the circle is given
   *  although there is no actual vertex there on this circle, which is a special case of an ellipse. */
  override def axesPt3x: Double = cenX - radius

  override def axesPt3y: Double = cenX

  /** The end of elliptical axis 2. By default this is the bottom vertex of the Ellipse, so this point on the circle is given although there is no
   *  actual vertex there on this circle, which is a special case of an ellipse. */
  override def axesPt4: Pt2 = cen.addY(radius)


  /** The X component of the end of elliptical axis 2. By default this is the bottom vertex of the Ellipse, so this point on the circle is given
   *  although there is no actual vertex there on this circle, which is a special case of an ellipse. */
  override def axesPt4x: Double = cenX

  /** The Y component of the end of elliptical axis 2. By default this is the bottom vertex of the Ellipse, so this point on the circle is given
   *  although there is no actual vertex there on this circle, which is a special case of an ellipse. */
  override def axesPt4y: Double = cenY + radius

  override def cenP1: Vec2 = cen >> axesPt1
  override def cenP2: Vec2 = cen >> axesPt2
  override def cenP3: Vec2 = cen >> axesPt3
  override def cenP4: Vec2 = cen >> axesPt4
  override def xRadius: Double = cen.distTo(pStart)
  override def yRadius: Double = cen.distTo(pStart)
  override def radius1: Double = cen.distTo(pStart)
  override def radius2: Double = cen.distTo(pStart)
}

/** Companion object of CArc class, contains various factory methods for the construction of circular arcs. */
object CArc
{ /** Factory method for creating circular arcs. */
  def apply(pStart: Pt2, cen: Pt2, pEnd: Pt2, counter: Int): CArc = new CArc(pStart.x, pStart.y, cen.x, cen.y, pEnd.x, pEnd.y, counter)

  /** Creates a positive direction or anti clockwise circular arc, with an [[AngleVec]] from 0 until 360 degrees. */
  def pos(pStart: Pt2, cen: Pt2, pEnd: Pt2): CArc = new CArc(pStart.x, pStart.y, cen.x, cen.y, pEnd.x, pEnd.y, ife(pStart == pEnd, 0, 1))

  /** Creates a negative direction or clockwise circular arc, with an [[AngleVec]] from 0 until -360 degrees. */
  def pos(xStart: Double, yStart: Double, xCen: Double, yCen: Double, xEnd: Double, yEnd: Double): CArc =
    new CArc(xStart, yStart, xCen, yCen, xEnd, yEnd, ife(xStart == xEnd & yStart == yEnd, 0, 1))

  /** Creates a negative direction or clockwise circular arc, with an [[AngleVec]] from 0 until -360 degrees. */
  def neg(pStart: Pt2, cen: Pt2, pEnd: Pt2): CArc = new CArc(pStart.x, pStart.y, cen.x, cen.y, pEnd.x, pEnd.y, ife(pStart == pEnd, 0, -1))

  /** Creates a negative direction or clockwise circular arc, with an [[AngleVec]] from 0 until -360 degrees. */
  def neg(xStart: Double, yStart: Double, xCen: Double, yCen: Double, xEnd: Double, yEnd: Double): CArc =
    new CArc(xStart, yStart, xCen, yCen, xEnd, yEnd, ife(xStart == xEnd & yStart == yEnd, 0, -1))
}