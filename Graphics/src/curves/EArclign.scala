/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait EArclign extends EArc
{
  def xRadius: Double
  def yRadius: Double
}

object EArclign
{
  /** Creates an Elliptical arc. */
  def alignPos(pStart: Pt2, cen: Pt2, xRadius: Double, yRadius: Double, pEnd: Pt2): EArclign = EArclignImp(pStart.x, pStart.y, cen.x, cen.y,
    xRadius, yRadius, pEnd.x, pEnd.y, ife(pStart.x == pEnd.x & pStart.y == pEnd.y, 0, 1))

  /** implementation class fpr Elliptical Arc. This class stores the start point, the centre point, axis vertex 1, by convention the vertex on the
   *  right of the ellipse, axis vertex 4, by convention the vertex at the top of the Ellipse and the rotation counter, to allow arcs of greter than
   *  360 degrees and less than -360 degrees. */
  final case class EArclignImp(xStart: Double, yStart: Double, xCen: Double, yCen: Double, xRadius: Double, yRadius: Double,
    xEnd: Double, yEnd: Double, counter: Int) extends EArclign
  {
    //override def fTrans(f: Vec2 => Vec2): EArclign = ???
    //def xCen: Double = xAxis4
    //def yCen: Double = yAxis1
    override def cen: Pt2 = ???

    def addRotations(delta: Int): EArclignImp = EArclignImp(xStart, yStart, xCen, yCen, xRadius, yRadius, xEnd, yEnd, counter + delta)

    override def slate(offset: Vec2Like): EArclignImp = ???

    override def rotate(angle: AngleVec): EArclignImp = ???

    override def scale(operand: Double): EArclignImp = ???

    //override def rotate(angle: Angle): EArclign = ???

    //override def shear(xScale: Double, yScale: Double): EArclign = ???

    override def reflect(lineLike: LineLike): EArclignImp = ???

    /** Translate geometric transformation. */
    override def slate(xOffset: Double, yOffset: Double): EArclignImp = ???

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in EArclignNew to allow the return type to be narrowed
     * in sub classes. */
    override def negY: EArclignImp = ???

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in EArclignNew to allow the return type to be narrowed
     * in sub classes. */
    override def negX: EArclignImp = ???

    override def prolign(matrix: ProlignMatrix): EArclignImp = ???

    override def xyScale(xOperand: Double, yOperand: Double): EArclignImp = ???

    override def xShear(operand: Double): EArclignImp = ???

    override def yShear(operand: Double): EArclignImp = ???

    /** Draws this geometric element to produce a [[GraphElem]] graphical element, tht can be displayed or printed. */
    override def draw(lineColour: Colour, lineWidth: Double): GraphicElem = ???
  }
}