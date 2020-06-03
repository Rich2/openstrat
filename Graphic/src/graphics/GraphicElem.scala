/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A GraphicElem is either an element that can be rendered to a display or printed. */
trait GraphicElem extends DisplayElem
{
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[ostrat.pCanv.CanvasPlatform]] interface. */
  def rendToCanvas(cp: pCanv.CanvasPlatform): Unit

  /** Translate geometric transformation. */
  def slate(offset: Vec2): GraphicElem

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): GraphicElem

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): GraphicElem

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def mirrorYOffset(xOffset: Double): GraphicElem

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): GraphicElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorX: GraphicElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorY: GraphicElem

  def prolign(matrix: ProlignMatrix): GraphicElem

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  def rotate90: GraphicElem
  
  /** Rotates 180 degrees or Pi radians. */  
  def rotate180: GraphicElem

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  def rotate270: DisplayElem

  override def rotateRadians(radians: Double): GraphicElem

  def mirror(line: Line2): GraphicElem

  override def scaleXY(xOperand: Double, yOperand: Double): GraphicElem
}

/** Companion object for [[ostrat.geom.GraphicElem]] contains various implicit instances for the transformation type classes. */
object GraphicElem
{ implicit val slateImplicit: Slate[GraphicElem] = (obj: GraphicElem, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[GraphicElem] = (obj: GraphicElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[GraphicElem] = (obj: GraphicElem, radians: Double) => obj.rotateRadians(radians)
  
  implicit val mirrorAxisImplicit: MirrorAxis[GraphicElem] = new MirrorAxis[GraphicElem]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def mirrorXOffset(obj: GraphicElem, yOffset: Double): GraphicElem = obj.mirrorXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def mirrorYOffset(obj: GraphicElem, xOffset: Double): GraphicElem = obj.mirrorYOffset(xOffset)
  }

  implicit val prolignImplicit: Prolign[GraphicElem] = (obj, matrix) => obj.prolign(matrix)
}