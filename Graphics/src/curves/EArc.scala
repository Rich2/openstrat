/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait EArc extends CurveSeg
{
  /** The X component of the centre of the Elliptical arc. */
  def xCen: Double

  /** The Y component of the centre of the Elliptical arc. */
  def yCen: Double

  /** The centre of the Elliptical arc. */
  def cen: Pt2

  /** Translate 2D geometric transformation on this EArc. The Return type will be narrowed in sub traits and  classes. */
  override def slate(offset: Vec2Like): EArc

  /** Translate 2D geometric transformation. The Return type will be narrowed in sub traits. */
  override def slate(xOffset: Double, yOffset: Double): EArc

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
  override def rotate(angle: Angle): EArc

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
}

object EArc
{
  /** Elliptical Arc. I think its important not to encode unnecessary data, not because of space concerns but because this may allow contradictory data.
   *  I've replaced  3 scalars and 2 booleans in the JavaFx encoding with 4 scalars. */
  final case class EArcImp(xStart: Double, yStart: Double, xCen: Double, yCen: Double, x1: Double, y1: Double, xEnd: Double, yEnd: Double) extends
    EArc {
    //override def fTrans(f: Vec2 => Vec2): EArc = ???

    override def cen: Pt2 = ???

    override def slate(offset: Vec2Like): EArcImp = ???

    override def rotate(angle: Angle): EArcImp = ???

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

    /** Draws this geometric element to produce a [[GraphElem]] graphical element, tht can be displayed or printed. */
    override def draw(lineColour: Colour, lineWidth: Double): GraphicElem = ???
  }

  object EArcImp {
    //def apply(): EArc = new EArc
  }
}