/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** GraphicSimple is a non compound graphic element that can be rendered to a display (or printed) or is an active element in a display, but can't be
 *  both that require a [[GraphicCompound]]. */
trait GraphicSimple extends GraphicElem
{
  /** Translate geometric transformation. */
  override def slate(offset: Vec2): GraphicSimple

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): GraphicSimple

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): GraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: GraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: GraphicSimple

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a GraphicSimple, returns a GraphicSimple. The
   *  return type will be narrowed in sub traits / classes. */
  override def rotate90: GraphicSimple

  /** Rotate 180 degrees 2D geometric transformation on a GraphicSimple, returns a GraphicSimple. The return type will be narrowed in sub traits /
   * classes. */
  override def rotate180: GraphicSimple

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a GraphicSimple, returns a GraphicSimple. The
   *  return type will be narrowed in sub traits / classes. */
  override def rotate270: GraphicSimple

  override def prolign(matrix: ProlignMatrix): GraphicSimple

  override def rotate(angle: Angle): GraphicSimple

  override def reflect(lineLike: LineLike): GraphicSimple

  override def xyScale(xOperand: Double, yOperand: Double): GraphicSimple
}

/** Companion object for the [[GraphicSimple]] trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object GraphicSimple
{
  implicit val slateImplicit: Slate[GraphicSimple] = (obj: GraphicSimple, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[GraphicSimple] = (obj: GraphicSimple, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[GraphicSimple] = (obj: GraphicSimple, angle: Angle) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[GraphicSimple] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[GraphicSimple] = (obj, matrix) => obj.prolign(matrix)
  
  implicit val reflectAxesImplicit: TransAxes[GraphicSimple] = new TransAxes[GraphicSimple]
  { override def negYT(obj: GraphicSimple): GraphicSimple = obj.negY
    override def negXT(obj: GraphicSimple): GraphicSimple = obj.negX
    override def rotate90T(obj: GraphicSimple): GraphicSimple = obj.rotate90
    override def rotate180T(obj: GraphicSimple): GraphicSimple = obj.rotate180
    override def rotate270T(obj: GraphicSimple): GraphicSimple = obj.rotate270
  }  
}
