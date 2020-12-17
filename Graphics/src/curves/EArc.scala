/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

/** Elliptical Arc. the trait has 2 implementations [[CArc]] and [[EArc.EArcImp]]. */
trait EArc extends CurveSeg
{
  /** The X component of the centre of the Elliptical arc. */
  def xCen: Double

  /** The Y component of the centre of the Elliptical arc. */
  def yCen: Double

  /** The centre of this elliptical arc. this method has been left abstract to allow the comment to be overridden in CArc. */
  def cen: Pt2

  def radius1: Double

  def radius2: Double

  /** The end point of axis 1. By default this is on the right of the Ellipse. Mathematically this can be referred to as a vertex for the major axis
   *  or a co-vertex for the minor axis. */
  def pAxes1: Pt2

  /** The start point of axis 2. By default this is at the bottom of the Ellipse. Mathematically this can be referred to as a vertex for the major
   *  axis or a co-vertex for the minor axis. */
  def pAxes2: Pt2

  /** The start point of axis 1. By default this is on the left of the Ellipse. Mathematically this can be referred to as a vertex for the major axis
   *  or a co-vertex for the minor axis. */
  def pAxes3: Pt2

  /** The end point of axis 2. By default this is at the top of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or
   *  a co-vertex for the minor axis. */
  def pAxes4: Pt2

  /** The 2D vector [[Vec2]] from the centre point to pAxes1, the end point of axis 1 , by default on the right of the Ellipse this arc is based
   *  on. */
  def cenP1: Vec2

  /** The 2D vector [[Vec2]] from the centre point to pAxes2, the start point of axis 2, by default at the bottom of the Ellipse this arc is based
   *  on. */
  def cenP2: Vec2

  /** The 2D vector [[Vec2]] from the centre point to pAxes3, the start point of axis 1 , by default on the left of the Ellipse this arc is based
   *  on. */
  def cenP3: Vec2

  /** The 2D vector [[Vec2]] from the centre point to pAxes4, the end point of axis 2, by default at the top of the Ellipse this arc is based on. */
  def cenP4: Vec2

  /** The vector [Vec2] from the centre of the arc to the start point of the arc. */
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
  { case 0 => 0.degs
    case c if c > 0  => startAngle.deltaPosTo(endAngle).addDegs((c - 1) * 360)
    case c => startAngle.deltaNegTo(endAngle).addDegs((c + 1) * 360)
  }

  def angleDeltaYDown: AngleVec = -angleDelta

  def angleDeltaLimited: AngleVec = ife(counter > 0, startAngle.deltaPosTo(endAngle), startAngle.deltaNegTo(endAngle))

  def angleDeltaLimitedYDown: AngleVec = -angleDeltaLimited

  /** Translate 2D geometric transformation. The Return type will be narrowed in sub traits. */
  override def slate(xOffset: Double, yOffset: Double): EArc =
    EArc(pStart.slate(xOffset, yOffset), cen.slate(xOffset, yOffset), pAxes1.slate(xOffset, yOffset), pAxes4.slate(xOffset, yOffset),
    pEnd.slate(xOffset, yOffset), counter)

  /** Translate 2D geometric transformation on this EArc. The Return type will be narrowed in sub traits and  classes. */
  override def slate(offset: Vec2Like): EArc

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): EArc

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  override def negY: EArc

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  override def negX: EArc

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): EArc

  /** Rotation 2D geometric transformation on a EArc. The return type will be narrowed in sub classes and traits. */
  override def rotate(angle: AngleVec): EArc

  /** Reflect 2D geometric transformation across a line, line segment or ray on a EArc. The return type will be narrowed in sub classes and
   * traits. */
  override def reflect(lineLike: LineLike): EArc

  /** XY scaling 2D geometric transformation on a EArc. This allows different scaling factors across X and Y dimensions. The return type will be
   * narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): EArc = ???

  /** Shear 2D geometric transformation along the X Axis on a EArc. The return type will be narrowed in sub classes and traits. */
  override def xShear(operand: Double): EArc = ???

  /** Shear 2D geometric transformation along the Y Axis on a EArc. The return type will be narrowed in sub classes and traits. */
  override def yShear(operand: Double): EArc = ???

  override def draw(lineColour: Colour = Black, lineWidth: Double = 2): EArcDraw = EArcDraw(this, lineColour, lineWidth)
}

object EArc
{
  def apply(pStart: Pt2, cen: Pt2, axisV1: Pt2, axisV4: Pt2, pEnd: Pt2, counter: Int): EArc =
    new EArcImp(pStart.x, pStart.y, cen.x, cen.y, axisV1.x, axisV1.y, axisV4.x, axisV4.y, pEnd.x, pEnd.y, counter)

  /** implementation class fpr Elliptical Arc. This class stores the start point, the centre point, axis vertex 1, by convention the vertex on the
   *  right of the ellipse, axis vertex 4, by convention the vertex at the top of the Ellipse and the rotation counter, to allow arcs of greater than
   *  360 degrees and less than -360 degrees. */
  final case class EArcImp(xStart: Double, yStart: Double, xCen: Double, yCen: Double, xAxisV1: Double, yAxisV1: Double, xAxis4: Double,
                           yAxis4: Double, xEnd: Double, yEnd: Double, counter: Int) extends EArc
  {
    override def cen: Pt2 = Pt2(xCen, yCen)

    override def radius1: Double = cen.distTo(pAxes1)

    override def radius2: Double = cen.distTo(pAxes4)

    override def pAxes1: Pt2 = xAxisV1 pp yAxisV1
    override def pAxes2: Pt2 = cen + cenP2
    override def pAxes3: Pt2 = cen + cenP3
    override def pAxes4: Pt2 = xAxis4 pp yAxis4
    override def cenP1: Vec2 = cen >> pAxes1
    override def cenP2: Vec2 = -cenP4
    override def cenP3: Vec2 = -cenP1
    override def cenP4: Vec2 = cen >> pAxes4

    def addRotations(delta: Int): EArcImp = new EArcImp(xStart, yStart, xCen, yCen, xAxisV1, yAxisV1, xAxis4, yAxis4, xEnd, yEnd, counter + delta)

    override def slate(offset: Vec2Like): EArcImp = ???

    override def rotate(angle: AngleVec): EArcImp = ???

    override def scale(operand: Double): EArcImp = ???

    //override def rotate(angle: Angle): EArc = ???

    //override def shear(xScale: Double, yScale: Double): EArc = ???

    override def reflect(lineLike: LineLike): EArcImp = ???

    /** Translate geometric transformation. */
    override def slate(xOffset: Double, yOffset: Double): EArcImp = ???

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in EArcNew to allow the return type to be narrowed
     * in sub classes. */
    override def negY: EArcImp = ???

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in EArcNew to allow the return type to be narrowed
     * in sub classes. */
    override def negX: EArcImp = ???

    override def prolign(matrix: ProlignMatrix): EArcImp = ???

    override def xyScale(xOperand: Double, yOperand: Double): EArcImp = ???

    override def xShear(operand: Double): EArcImp = ???

    override def yShear(operand: Double): EArcImp = ???
  }

  object EArcImp {
    //def apply(): EArc = new EArc
  }
}