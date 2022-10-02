/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black

/** Elliptical Arc. the trait has 2 implementations [[CArc]] and [[EArc.EArcImp]]. */
trait EArc extends EllipseBased with CurveSeg
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
  def lsCenStart: LineSeg = cen.lineTo(pStart)

  /** The vector [Vec2] from the centre of the arc to the end point of the arc. */
  def cenEnd: Vec2 = cen >> pEnd

  /** The angle of the end point of the arc, relative to its centre. */
  def endAngle: Angle = pEnd.angleFrom(cen)

  /** The angle of the end point of the arc, relative to its centre. */
  def endAngleYDown: Angle = pEnd.angleFromYDown(cen)

  /** The value of the angle of the end point of the arc, relative to its centre in degrees. */
  def endDegs: Double = endAngle.degs

  /** The line segment [LineSeg] from the centre of the arc to the end point of the arc. */
  def lsCenEnd: LineSeg = cen.lineTo(pEnd)

  def addRotations(delta: Int): EArc

  def counter: Int

  def angleDelta: AngleVec = counter match
  { case 0 => 0.degsVec
    case c if c > 0  => startAngle.deltaPosTo(endAngle).addDegs((c - 1) * 360)
    case c => startAngle.deltaNegTo(endAngle).addDegs((c + 1) * 360)
  }

  def angleDeltaYDown: AngleVec = -angleDelta

  def angleDeltaLimited: AngleVec = ife(counter > 0, startAngle.deltaPosTo(endAngle), startAngle.deltaNegTo(endAngle))

  def angleDeltaLimitedYDown: AngleVec = -angleDeltaLimited

  /** Translate 2D geometric transformation on this EArc, returns an EArc. */
  override def slateXY(xDelta: Double, yDelta: Double): EArc = EArc(pStart.xySlate(xDelta, yDelta), cen.xySlate(xDelta, yDelta),
    axesPt1.xySlate(xDelta, yDelta), axesPt4.xySlate(xDelta, yDelta), pEnd.xySlate(xDelta, yDelta), counter)

  /** Uniform 2D geometric scaling transformation, returns an EArc. */
  override def scale(operand: Double): EArc =
    EArc(pStart.scale(operand), cen.scale(operand), axesPt1.scale(operand), axesPt4.scale(operand), pEnd.scale(operand), counter)

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y, on this EArc retruns an EARc. */
  override def negY: EArc = EArc(pStart.negY, cen.negY, axesPt1.negY, axesPt4.negY, pEnd.negY, -counter)

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  override def negX: EArc = EArc(pStart.negX, cen.negX, axesPt1.negX, axesPt4.negX, pEnd.negX, -counter)

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): EArc =
    EArc(pStart.prolign(matrix), cen.prolign(matrix), axesPt1.prolign(matrix), axesPt4.prolign(matrix), pEnd.prolign(matrix), counter)

  /** Rotation 2D geometric transformation on a EArc. The return type will be narrowed in sub classes and traits. */
  override def rotate(angle: AngleVec): EArc =
    EArc(pStart.rotate(angle), cen.rotate(angle), axesPt1.rotate(angle), axesPt4.rotate(angle), pEnd.rotate(angle), counter)

  override def rotate90: EArc = EArc(pStart.rotate90, cen.rotate90, axesPt1.rotate90, axesPt4.rotate90, pEnd.rotate90, counter)
  override def rotate180: EArc = EArc(pStart.rotate180, cen.rotate180, axesPt1.rotate180, axesPt4.rotate180, pEnd.rotate180, counter)
  override def rotate270: EArc = EArc(pStart.rotate270, cen.rotate270, axesPt1.rotate270, axesPt4.rotate270, pEnd.rotate270, counter)

  /** Reflect 2D geometric transformation across a line, line segment or ray on a EArc returns an EArc. */
  override def reflect(lineLike: LineLike): EArc =
    EArc(pStart.reflect(lineLike), cen.reflect(lineLike), axesPt1.reflect(lineLike), axesPt4.reflect(lineLike), pEnd.reflect(lineLike), counter)

  /** XY scaling 2D geometric transformation on this EArc returns an EArc.This allows different
   *  scaling factors across X and Y dimensions. */
  override def scaleXY(xOperand: Double, yOperand: Double): EArc = EArc(pStart.xyScale(xOperand, yOperand), cen.xyScale(xOperand, yOperand),
    axesPt1.xyScale(xOperand, yOperand), axesPt4.xyScale(xOperand, yOperand), pEnd.xyScale(xOperand, yOperand), counter)

  /** Shear 2D geometric transformation along the X Axis on this EArc returns an EArc. */
  override def shearX(operand: Double): EArc =
    EArc(pStart.xShear(operand), cen.xShear(operand), axesPt1.xShear(operand), axesPt4.xShear(operand), pEnd.xShear(operand), counter)

  /** Shear 2D geometric transformation along the Y Axis on this EArc, returns an EArc. */
  override def shearY(operand: Double): EArc =
    EArc(pStart.xShear(operand), cen.yShear(operand), axesPt1.yShear(operand), axesPt4.yShear(operand), pEnd.yShear(operand), counter)

  override def draw(lineColour: Colour = Black, lineWidth: Double = 2): EArcDraw = EArcDraw(this, lineColour, lineWidth)
}

object EArc
{
  def apply(pStart: Pt2, cen: Pt2, axisV1: Pt2, axisV4: Pt2, pEnd: Pt2, counter: Int): EArc =
    new EArcImp(pStart.x, pStart.y, cen.x, cen.y, axisV1.x, axisV1.y, axisV4.x, axisV4.y, pEnd.x, pEnd.y, counter)

  /** implementation class fpr Elliptical Arc. This class stores the start point, the centre point, axis vertex 1, by convention the vertex on the
   *  right of the ellipse, axis vertex 4, by convention the vertex at the top of the Ellipse and the rotation counter, to allow arcs of greater than
   *  360 degrees and less than -360 degrees. */
  final case class EArcImp(startX: Double, startY: Double, cenX: Double, cenY: Double, axesPt1x: Double, axesPt1y: Double, axesPt4x: Double,
                           axesPt4y: Double, endX: Double, endY: Double, counter: Int) extends EArc
  {
    override def cen: Pt2 = Pt2(cenX, cenY)
    override def radius1: Double = cen.distTo(axesPt1)
    override def radius2: Double = cen.distTo(axesPt4)
    override def axesPt1: Pt2 = axesPt1x pp axesPt1y
    override def axesPt2: Pt2 = cen + cenP2
    override def axesPt2x: Double = axesPt2.x
    override def axesPt2y: Double = axesPt2.y
    override def axesPt3: Pt2 = cen + cenP3
    override def axesPt3x: Double = axesPt3.x
    override def axesPt3y: Double = axesPt3.y
    override def axesPt4: Pt2 = axesPt4x pp axesPt4y
    override def cenP1: Vec2 = cen >> axesPt1
    override def cenP2: Vec2 = -cenP4
    override def cenP3: Vec2 = -cenP1
    override def cenP4: Vec2 = cen >> axesPt4

    def addRotations(delta: Int): EArcImp = new EArcImp(startX, startY, cenX, cenY, axesPt1x, axesPt1y, axesPt4x, axesPt4y, endX, endY, counter + delta)
  }

  object EArcImp {
    //def apply(): EArc = new EArc
  }
}