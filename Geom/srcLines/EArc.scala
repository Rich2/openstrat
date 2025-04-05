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
  def lsCenStart: LineSeg = cen.lineSegTo(pStart)

  /** The vector [Vec2] from the centre of the arc to the end point of the arc. */
  def cenEnd: Vec2 = cen >> pEnd

  /** The angle of the end point of the arc, relative to its centre. */
  def endAngle: Angle = pEnd.angleFrom(cen)

  /** The angle of the end point of the arc, relative to its centre. */
  def endAngleYDown: Angle = pEnd.angleFromYDown(cen)

  /** The value of the angle of the end point of the arc, relative to its centre in degrees. */
  def endDegs: Double = endAngle.degs

  /** The line segment [LineSeg] from the centre of the arc to the end point of the arc. */
  def lsCenEnd: LineSeg = cen.lineSegTo(pEnd)

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
    EArc(pStart.slate(operand), cen.slate(operand), axesPt1.slate(operand), axesPt4.slate(operand), pEnd.slate(operand), rotationsInt)
  
  override def slate(xOperand: Double, yOperand: Double): EArc = EArc(pStart.slate(xOperand, yOperand), cen.slate(xOperand, yOperand),
    axesPt1.slate(xOperand, yOperand), axesPt4.slate(xOperand, yOperand), pEnd.slate(xOperand, yOperand), rotationsInt)

  override def slateX(xOperand: Double): EArc = EArc(pStart.slateX(xOperand), cen.slateX(xOperand), axesPt1.slateX(xOperand), axesPt4.slateX(xOperand),
    pEnd.slateX(xOperand), rotationsInt)

  override def slateY(yOperand: Double): EArc = EArc(pStart.slateY(yOperand), cen.slateY(yOperand), axesPt1.slateY(yOperand), axesPt4.slateY(yOperand),
    pEnd.slateY(yOperand), rotationsInt)
  
  override def scale(operand: Double): EArc =
    EArc(pStart.scale(operand), cen.scale(operand), axesPt1.scale(operand), axesPt4.scale(operand), pEnd.scale(operand), rotationsInt)

  override def negX: EArc = EArc(pStart.negX, cen.negX, axesPt1.negX, axesPt4.negX, pEnd.negX, -rotationsInt)
  override def negY: EArc = EArc(pStart.negY, cen.negY, axesPt1.negY, axesPt4.negY, pEnd.negY, -rotationsInt)
  
  override def prolign(matrix: ProlignMatrix): EArc =
    EArc(pStart.prolign(matrix), cen.prolign(matrix), axesPt1.prolign(matrix), axesPt4.prolign(matrix), pEnd.prolign(matrix), rotationsInt)
  
  override def rotate(angle: AngleVec): EArc =
    EArc(pStart.rotate(angle), cen.rotate(angle), axesPt1.rotate(angle), axesPt4.rotate(angle), pEnd.rotate(angle), rotationsInt)

  override def rotate90: EArc = EArc(pStart.rotate90, cen.rotate90, axesPt1.rotate90, axesPt4.rotate90, pEnd.rotate90, rotationsInt)
  override def rotate180: EArc = EArc(pStart.rotate180, cen.rotate180, axesPt1.rotate180, axesPt4.rotate180, pEnd.rotate180, rotationsInt)
  override def rotate270: EArc = EArc(pStart.rotate270, cen.rotate270, axesPt1.rotate270, axesPt4.rotate270, pEnd.rotate270, rotationsInt)
  
  override def reflect(lineLike: LineLike): EArc =
    EArc(pStart.reflect(lineLike), cen.reflect(lineLike), axesPt1.reflect(lineLike), axesPt4.reflect(lineLike), pEnd.reflect(lineLike), rotationsInt)
  
  override def scaleXY(xOperand: Double, yOperand: Double): EArc = EArc(pStart.xyScale(xOperand, yOperand), cen.xyScale(xOperand, yOperand),
    axesPt1.xyScale(xOperand, yOperand), axesPt4.xyScale(xOperand, yOperand), pEnd.xyScale(xOperand, yOperand), rotationsInt)
  
  override def shearX(operand: Double): EArc =
    EArc(pStart.xShear(operand), cen.xShear(operand), axesPt1.xShear(operand), axesPt4.xShear(operand), pEnd.xShear(operand), rotationsInt)
  
  override def shearY(operand: Double): EArc =
    EArc(pStart.xShear(operand), cen.yShear(operand), axesPt1.yShear(operand), axesPt4.yShear(operand), pEnd.yShear(operand), rotationsInt)

  override def draw(lineWidth: Double = 2, lineColour: Colour = Black): EArcDraw = EArcDraw(this, lineColour, lineWidth)
}

object EArc
{
  def apply(pStart: Pt2, cen: Pt2, axisV1: Pt2, axisV4: Pt2, pEnd: Pt2, counter: Int): EArc =
    new EArcImp(pStart.x, pStart.y, cen.x, cen.y, axisV1.x, axisV1.y, axisV4.x, axisV4.y, pEnd.x, pEnd.y, counter)

  /** implementation class fpr Elliptical Arc. This class stores the start point, the centre point, axis vertex 1, by convention the vertex on the right of the
   * ellipse, axis vertex 4, by convention the vertex at the top of the Ellipse and the rotation counter, to allow arcs of greater than 360 degrees and less
   * than -360 degrees. */
  final case class EArcImp(startX: Double, startY: Double, cenX: Double, cenY: Double, axesPt1x: Double, axesPt1y: Double, axesPt4x: Double, axesPt4y: Double,
    endX: Double, endY: Double, rotationsInt: Int) extends EArc
  { override def cen: Pt2 = Pt2(cenX, cenY)
    override def radius1: Double = cen.distTo(axesPt1)
    override def radius2: Double = cen.distTo(axesPt4)
    override def axesPt1: Pt2 = Pt2(axesPt1x, axesPt1y)
    override def axesPt2: Pt2 = cen + cenP2
    override def axesPt2x: Double = axesPt2.x
    override def axesPt2y: Double = axesPt2.y
    override def axesPt3: Pt2 = cen + cenP3
    override def axesPt3x: Double = axesPt3.x
    override def axesPt3y: Double = axesPt3.y
    override def axesPt4: Pt2 = Pt2(axesPt4x, axesPt4y)
    override def cenP1: Vec2 = cen >> axesPt1
    override def cenP2: Vec2 = -cenP4
    override def cenP3: Vec2 = -cenP1
    override def cenP4: Vec2 = cen >> axesPt4

    def addRotations(delta: Int): EArcImp = new EArcImp(startX, startY, cenX, cenY, axesPt1x, axesPt1y, axesPt4x, axesPt4y, endX, endY, rotationsInt + delta)
  }
}