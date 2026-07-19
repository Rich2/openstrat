/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black

/** Elliptical Arc. the trait has 2 implementations [[CArc]] and [[EArc.EArcImp]]. */
trait EArc extends EllipseBased, CurveSeg
{ /** The vector [Vec2] from the centre of the arc to the start point of the arc. */
  def cenStart: Vec2 = cen >> pStart

  /** The angle of the start point of the arc, relative to its centre. */
  def startAngle: Angle = pStart.angleFrom(cen)

  /** The angle of the start point of the arc, relative to its centre for graphical systems where the Y axis points down. */
  def startAngleYDown: Angle = pStart.angleFromYDown(cen)

  /** The value of the angle of the start point of the arc, relative to its centre in degrees. */
  def startDegs: Double = startAngle.degs

  /** The value of the angle of the start point of the arc, relative to its centre in degrees. */
  def startDegsYDown: Double = startAngleYDown.degs

  /** The line segment [LineSeg] from the centre of the arc to the start point of the arc. */
  def lsCenStart: LSeg2 = cen.lineSegTo(pStart)

  /** The vector [Vec2] from the centre of the arc to the end point of the arc. */
  def cenEnd: Vec2 = cen >> pEnd

  /** The angle of the end point of the arc, relative to its centre. */
  def endAngle: Angle = pEnd.angleFrom(cen)

  /** The angle of the end point of the arc, relative to its centre. */
  def endAngleYDown: Angle = pEnd.angleFromYDown(cen)

  /** The value of the angle of the end point of the arc, relative to its centre in degrees. */
  def endDegs: Double = endAngle.degs

  /** The line segment [LineSeg] from the centre of the arc to the end point of the arc. */
  def lsCenEnd: LSeg2 = cen.lineSegTo(pEnd)

  def addRotations(delta: Int): EArc

  /** The number of full rotations in this arc. A value of 0 specifies a positive or anti-clockwise rotation of 0° >= r < 360°. A value of 1 specifies a
   *  positive or anti-clockwise rotation of 360° >= r < 720°. A value of -1 specifies a negative or clockwise rotation of 0° >= r < 360°. A value of -1
   *  specifies a negative or clockwise rotation of 360° >= r < 720°. */
  def rotationsInt: Int

  def angleDelta: AngleVec = rotationsInt match
  { case 0 => 0.degsVec
    case c if c > 0  => startAngle.deltaPosTo(endAngle).addDegs((c - 1) * 360)
    case c => startAngle.deltaNegTo(endAngle).addDegs((c + 1) * 360)
  }

  def angleDeltaYDown: AngleVec = -angleDelta

  def angleDeltaLimited: AngleVec = ife(rotationsInt > 0, startAngle.deltaPosTo(endAngle), startAngle.deltaNegTo(endAngle))

  def angleDeltaLimitedYDown: AngleVec = -angleDeltaLimited

  override def slate(operand: VecPt2): EArc =
    EArc(pStart.slate(operand), cen.slate(operand), p1.slate(operand), p0.slate(operand), pEnd.slate(operand), rotationsInt)
  
  override def slate(xOperand: Double, yOperand: Double): EArc = EArc(pStart.slate(xOperand, yOperand), cen.slate(xOperand, yOperand),
    p1.slate(xOperand, yOperand), p0.slate(xOperand, yOperand), pEnd.slate(xOperand, yOperand), rotationsInt)

  override def slateFrom(operand: VecPt2): EArc =
    EArc(pStart.slateFrom(operand), cen.slateFrom(operand), p1.slateFrom(operand), p0.slateFrom(operand), pEnd.slateFrom(operand), rotationsInt)

  override def slateFrom(xOperand: Double, yOperand: Double): EArc = EArc(pStart.slate(xOperand, yOperand), cen.slate(xOperand, yOperand),
    p1.slateFrom(xOperand, yOperand), p0.slateFrom(xOperand, yOperand), pEnd.slateFrom(xOperand, yOperand), rotationsInt)

  override def slateX(xOperand: Double): EArc = EArc(pStart.slateX(xOperand), cen.slateX(xOperand), p1.slateX(xOperand), p0.slateX(xOperand),
    pEnd.slateX(xOperand), rotationsInt)

  override def slateY(yOperand: Double): EArc = EArc(pStart.slateY(yOperand), cen.slateY(yOperand), p1.slateY(yOperand), p0.slateY(yOperand),
    pEnd.slateY(yOperand), rotationsInt)
  
  override def scale(operand: Double): EArc =
    EArc(pStart.scale(operand), cen.scale(operand), p1.scale(operand), p0.scale(operand), pEnd.scale(operand), rotationsInt)

  override def negX: EArc = EArc(pStart.negX, cen.negX, p1.negX, p0.negX, pEnd.negX, -rotationsInt)
  override def negY: EArc = EArc(pStart.negY, cen.negY, p1.negY, p0.negY, pEnd.negY, -rotationsInt)
  
  override def prolign(matrix: AxlignMatrix): EArc =
    EArc(pStart.prolign(matrix), cen.prolign(matrix), p1.prolign(matrix), p0.prolign(matrix), pEnd.prolign(matrix), rotationsInt)
  
  override def rotate(rotation: AngleVec): EArc =
    EArc(pStart.rotate(rotation), cen.rotate(rotation), p1.rotate(rotation), p0.rotate(rotation), pEnd.rotate(rotation), rotationsInt)

  override def rotate90: EArc = EArc(pStart.rotate90, cen.rotate90, p1.rotate90, p0.rotate90, pEnd.rotate90, rotationsInt)
  override def rotate180: EArc = EArc(pStart.rotate180, cen.rotate180, p1.rotate180, p0.rotate180, pEnd.rotate180, rotationsInt)
  override def rotate270: EArc = EArc(pStart.rotate270, cen.rotate270, p1.rotate270, p0.rotate270, pEnd.rotate270, rotationsInt)
  
  override def mirror(lineLike: LineLike): EArc =
    EArc(pStart.mirror(lineLike), cen.mirror(lineLike), p1.mirror(lineLike), p0.mirror(lineLike), pEnd.mirror(lineLike), rotationsInt)
  
  override def scaleXY(xOperand: Double, yOperand: Double): EArc = EArc(pStart.xyScale(xOperand, yOperand), cen.xyScale(xOperand, yOperand),
    p1.xyScale(xOperand, yOperand), p0.xyScale(xOperand, yOperand), pEnd.xyScale(xOperand, yOperand), rotationsInt)
  
  override def shearX(operand: Double): EArc =
    EArc(pStart.xShear(operand), cen.xShear(operand), p1.xShear(operand), p0.xShear(operand), pEnd.xShear(operand), rotationsInt)
  
  override def shearY(operand: Double): EArc =
    EArc(pStart.xShear(operand), cen.yShear(operand), p1.yShear(operand), p0.yShear(operand), pEnd.yShear(operand), rotationsInt)

  override def draw(lineWidth: Double = 2, lineColour: Colour = Black): EArcDraw = EArcDraw(this, lineColour, lineWidth)
}

object EArc
{
  def apply(pStart: Pt2, cen: Pt2, axisV1: Pt2, axisV4: Pt2, pEnd: Pt2, counter: Int): EArc =
    new EArcImp(pStart.x, pStart.y, cen.x, cen.y, axisV1.x, axisV1.y, axisV4.x, axisV4.y, pEnd.x, pEnd.y, counter)

  /** implementation class fpr Elliptical Arc. This class stores the start point, the centre point, axis vertex 1, by convention the vertex on the right of the
   * ellipse, axis vertex 4, by convention the vertex at the top of the Ellipse and the rotation counter, to allow arcs of greater than 360 degrees and less
   * than -360 degrees. */
  final case class EArcImp(startX: Double, startY: Double, cenX: Double, cenY: Double, p1X: Double, p1Y: Double, p0X: Double, p0Y: Double,
                           endX: Double, endY: Double, rotationsInt: Int) extends EArc
  { override def cen: Pt2 = Pt2(cenX, cenY)
    override def radius1: Double = cen.distTo(p1)
    override def radius2: Double = cen.distTo(p0)
    override def p1: Pt2 = Pt2(p1X, p1Y)
    override def p2: Pt2 = cen + cenP2
    override def p2X: Double = p2.x
    override def p2Y: Double = p2.y
    override def p3: Pt2 = cen + cenP3
    override def p3X: Double = p3.x
    override def p3Y: Double = p3.y
    override def p0: Pt2 = Pt2(p0X, p0Y)
    def addRotations(delta: Int): EArcImp = new EArcImp(startX, startY, cenX, cenY, p1X, p1Y, p0X, p0Y, endX, endY, rotationsInt + delta)
  }
}