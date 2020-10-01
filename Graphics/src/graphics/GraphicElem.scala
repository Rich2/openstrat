/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A GraphicElem, graphic element is either an element that can be rendered to a display (or printed) or is an active element in a display or
 *  both. */
trait GraphicElem extends TransElem
{
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[CanvasPlatform]] interface. */
  def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = {}
  
  /** Translate geometric transformation. */
  def slate(offset: Vec2): GraphicElem

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): GraphicElem

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): GraphicElem  
  
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def negY: GraphicElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def negX: GraphicElem

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def reflectXParallel(yOffset: Double): GraphicElem

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def reflectYParallel(xOffset: Double): GraphicElem

  def prolign(matrix: ProlignMatrix): GraphicElem

  def rotateRadians(radians: Double): GraphicElem

  def reflect(line: Line): GraphicElem

  override def xyScale(xOperand: Double, yOperand: Double): GraphicElem
}

/** Companion object for the DisplayElem trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object GraphicElem
{
  implicit val slateImplicit: Slate[GraphicElem] = (obj: GraphicElem, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[GraphicElem] = (obj: GraphicElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[GraphicElem] = (obj: GraphicElem, radians: Double) => obj.rotateRadians(radians)
  implicit val XYScaleImplicit: XYScale[GraphicElem] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[GraphicElem] = (obj, matrix) => obj.prolign(matrix)
  
  implicit val reflectAxisImplicit: ReflectAxes[GraphicElem] = new ReflectAxes[GraphicElem]
  { /** Reflect, mirror across the X axis. */
    override def negYT(obj: GraphicElem): GraphicElem = obj.negY

    /** Reflect, mirror across the Y axis. */
    override def negXT(obj: GraphicElem): GraphicElem = obj.negX
  }

  implicit val reflectAxisOffsetImplicit: ReflectAxesOffset[GraphicElem] = new ReflectAxesOffset[GraphicElem]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: GraphicElem, yOffset: Double): GraphicElem = obj.reflectXParallel(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: GraphicElem, xOffset: Double): GraphicElem = obj.reflectYParallel(xOffset)
  }  
}