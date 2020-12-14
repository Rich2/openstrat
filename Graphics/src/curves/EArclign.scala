/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait EArclign extends EArc
{
  def xRadius: Double
  def yRadius: Double

  override def addRotations(delta: Int): EArclign


  /** Translate 2D geometric transformation. The Return type will be narrowed in sub traits. */
  override def slate(xOffset: Double, yOffset: Double): EArclign

  /** Translate 2D geometric transformation on this EArclign. The Return type will be narrowed in sub traits and  classes. */
  override def slate(offset: Vec2Like): EArclign

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): EArclign

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  override def negY: EArclign

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  override def negX: EArclign

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): EArclign

  /** Draws this geometric element to produce a [[GraphElem]] graphical element, that can be displayed or printed. */
  //override def draw(lineColour: Colour, lineWidth: Double): EArclign
}

object EArclignlign
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
  }
}