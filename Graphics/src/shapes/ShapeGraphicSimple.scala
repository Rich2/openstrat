/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

trait ShapeGraphicSimple extends ShapeGraphic with GraphicSimple
{
  def nonShapeAttribs: Arr[XmlAtt]
  final override def attribs: Arr[XmlAtt] = shapeAttribs ++ nonShapeAttribs

  /** Translate geometric transformation. */
  def slate(offset: Vec2): ShapeGraphicSimple

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): ShapeGraphicSimple

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): ShapeGraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def reflectX: ShapeGraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def reflectY: ShapeGraphicSimple

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def reflectXParallel(yOffset: Double): ShapeGraphicSimple

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def reflectYParallel(xOffset: Double): ShapeGraphicSimple

  def prolign(matrix: ProlignMatrix): ShapeGraphicSimple

  def rotateRadians(radians: Double): ShapeGraphicSimple

  def reflect(line: Line): ShapeGraphicSimple

  override def xyScale(xOperand: Double, yOperand: Double): ShapeGraphicSimple
}



/** A simple no compound graphic that draws a shape. The line has a sinlge width and colour. */
trait ShapeDraw extends ShapeGraphicSimple
{ /** The line width of this draw graphic */
  def lineWidth: Double

  /** The line colour of this draw graphic. */
  def lineColour: Colour

  def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(lineWidth)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(lineColour)
  def nonShapeAttribs: Arr[XmlAtt] = Arr(strokeWidthAttrib, strokeAttrib)
}