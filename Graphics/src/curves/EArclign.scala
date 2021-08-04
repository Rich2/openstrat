/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** An arc based on an ellipse whose axes are aligned to the X and Y axes. This is a trait as the [[EArclign.EArclignImp]] and [[CArc]] classes both
 * fulfill this interface. */
trait EArclign extends EArc
{
  /** The radius of the axis of the ellipse aligned to the X axis. */
  def xRadius: Double

  /** The radius of the axis of the ellipse aligned to the Y axis. */
  def yRadius: Double

  override def addRotations(delta: Int): EArclign

  /** Translate 2D geometric transformation on this EArclign returns an EArclign. */
  override def slateXY(xDelta: Double, yDelta: Double): EArclign =
    EArclign(pStart.xySlate(xDelta, yDelta), cen.xySlate(xDelta, yDelta), xRadius, yRadius, pEnd.xySlate(xDelta, yDelta), counter)

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): EArclign =
    EArclign(pStart.scale(operand), cen.scale(operand), xRadius * operand, yRadius * operand, pEnd.scale(operand), counter)

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  override def negY: EArclign = EArclign(pStart.negY, cen.negY, xRadius, yRadius, pEnd.negY, -counter)

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  override def negX: EArclign = EArclign(pStart.negX, cen.negX, xRadius, yRadius, pEnd.negX, -counter)

  override def rotate90: EArclign = ???

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): EArclign =
    EArclign(pStart.prolign(matrix), cen.prolign(matrix), xRadius, yRadius, pEnd.prolign(matrix), counter)
}

/** Companion object for [[EArclign]] trait, an arc that is based on Ellipse aligned to the X and Y axes. */
object EArclign
{
  def apply(pStart: Pt2, cen: Pt2, xRadius: Double, yRadius: Double, pEnd: Pt2, counter: Int): EArclign =
    new EArclignImp(pStart.x, pStart.y, cen.x, cen.y, xRadius, yRadius, pEnd.x, pEnd.y, counter)

  /** Creates an Elliptical arc. */
  def pos(pStart: Pt2, cen: Pt2, xRadius: Double, yRadius: Double, pEnd: Pt2): EArclign = EArclignImp(pStart.x, pStart.y, cen.x, cen.y,
    xRadius, yRadius, pEnd.x, pEnd.y, ife(pStart.x == pEnd.x & pStart.y == pEnd.y, 0, 1))

  /** Creates an Elliptical arc. */
  def pos(xStart: Double, yStart: Double, xCen: Double, yCen: Double, xRadius: Double, yRadius: Double, xEnd: Double, yEnd: Double): EArclign =
    EArclignImp(xStart, yStart, xCen, yCen, xRadius, yRadius, xEnd, yEnd, ife(xStart == xEnd & yStart == yEnd, 0, 1))

  /** Creates an Elliptical arc. */
  def neg(pStart: Pt2, cen: Pt2, xRadius: Double, yRadius: Double, pEnd: Pt2): EArclign = EArclignImp(pStart.x, pStart.y, cen.x, cen.y,
    xRadius, yRadius, pEnd.x, pEnd.y, ife(pStart.x == pEnd.x & pStart.y == pEnd.y, 0, -1))

  /** Creates an Elliptical arc. */
  def neg(xStart: Double, yStart: Double, xCen: Double, yCen: Double, xRadius: Double, yRadius: Double, xEnd: Double, yEnd: Double): EArclign =
    EArclignImp(xStart, yStart, xCen, yCen, xRadius, yRadius, xEnd, yEnd, ife(xStart == xEnd & yStart == yEnd, 0, -1))

  /** implementation class fpr Elliptical Arc. This class stores the start point, the centre point, axis vertex 1, by convention the vertex on the
   *  right of the ellipse, axis vertex 4, by convention the vertex at the top of the Ellipse and the rotation counter, to allow arcs of greter than
   *  360 degrees and less than -360 degrees. */
  final case class EArclignImp(startX: Double, startY: Double, cenX: Double, cenY: Double, xRadius: Double, yRadius: Double,
                               endX: Double, endY: Double, counter: Int) extends EArclign
  {
    //override def fTrans(f: Vec2 => Vec2): EArclign = ???

    override def cen: Pt2 = Pt2(cenX, cenY)
    override def radius1: Double = xRadius
    override def radius2: Double = yRadius
    override def axesPt1: Pt2 = cen.addX(xRadius)
    override def axesPt1x: Double = cenX + xRadius
    override def axesPt1y: Double = cenY
    override def axesPt2: Pt2 = cen.subY(yRadius)

    /** The X component of the start point of axis 2. By default this is at the bottom of the Ellipse. Mathematically this can be referred to as a vertex for the major
     * axis or a co-vertex for the minor axis.. */
    override def axesPt2x: Double = ???


    /** The y component of the start point of axis 2. By default this is at the bottom of the Ellipse. Mathematically this can be referred to as a
     * vertex for the major axis or a co-vertex for the minor axis. */
    override def axesPt2y: Double = ???

    override def axesPt3: Pt2 = cen.subX(xRadius)


    override def axesPt3x: Double = ???

    override def axesPt3y: Double = ???

    override def axesPt4: Pt2 = cen.addY(yRadius)
    override def axesPt4x: Double = cenX
    override def axesPt4y: Double = cenY + radius2

    override def cenP1: Vec2 = xRadius vv 0
    override def cenP2: Vec2 = 0 vv - yRadius
    override def cenP3: Vec2 = -xRadius vv 0
    override def cenP4: Vec2 = 0 vv yRadius

    def addRotations(delta: Int): EArclignImp = EArclignImp(startX, startY, cenX, cenY, xRadius, yRadius, endX, endY, counter + delta)
  }
}