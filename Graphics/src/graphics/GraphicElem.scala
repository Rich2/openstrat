/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A DisplayElem is either an element that can be rendered to a display (or printed) or is an active element in a display or both. */
trait GraphicElem extends TransElem
{
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[ostrat.pCanv.CanvasPlatform]] interface. */
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
  def reflectX: GraphicElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def reflectY: GraphicElem

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def reflectXOffset(yOffset: Double): GraphicElem

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def reflectYOffset(xOffset: Double): GraphicElem

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

  implicit val reflectAxisImplicit: ReflectAxis[GraphicElem] = new ReflectAxis[GraphicElem]
  { /** Reflect, mirror across the X axis. */
    override def reflectXT(obj: GraphicElem): GraphicElem = obj.reflectX

    /** Reflect, mirror across the Y axis. */
    override def reflectYT(obj: GraphicElem): GraphicElem = obj.reflectY
  }

  implicit val reflectAxisOffsetImplicit: ReflectAxisOffset[GraphicElem] = new ReflectAxisOffset[GraphicElem]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: GraphicElem, yOffset: Double): GraphicElem = obj.reflectXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: GraphicElem, xOffset: Double): GraphicElem = obj.reflectYOffset(xOffset)
  }

  implicit val prolignImplicit: Prolign[GraphicElem] = (obj, matrix) => obj.prolign(matrix)
}