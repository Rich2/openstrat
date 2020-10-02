/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

trait ShapeGraphicSimple extends ShapeGraphic with GraphicSimple
{
  def nonShapeAttribs: Arr[XmlAtt]
  final override def attribs: Arr[XmlAtt] = shapeAttribs ++ nonShapeAttribs

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): ShapeGraphicSimple

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): ShapeGraphicSimple

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeGraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: ShapeGraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: ShapeGraphicSimple

  override def prolign(matrix: ProlignMatrix): ShapeGraphicSimple

  override def rotateRadians(radians: Double): ShapeGraphicSimple

  override def reflect(lineLike: LineLike): ShapeGraphicSimple

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