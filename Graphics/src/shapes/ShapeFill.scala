/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A simple plain colour fill graphic. */
trait ShapeFill extends ShapeGraphicSimple
{ /** The colour of this fill graphic. */
  def colour: Colour

  /** The fill attribute for SVG. */
  def fillAttrib: FillAttrib = FillAttrib(colour)
  override def nonShapeAttribs: Arr[XmlAtt] = Arr(fillAttrib)

  def toDraw(lineWidth: Double = 2, newColour: Colour = colour): ShapeDraw

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): ShapeFill

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): ShapeFill

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeFill

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: ShapeFill

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: ShapeFill

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXParallel(yOffset: Double): ShapeFill

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYParallel(xOffset: Double): ShapeFill

  override def prolign(matrix: ProlignMatrix): ShapeFill

  override def rotateRadians(radians: Double): ShapeFill

  override def reflect(line: Line): ShapeFill

  override def xyScale(xOperand: Double, yOperand: Double): ShapeFill  

  override def reflect(line: LineSeg): ShapeFill

  override def xShear(operand: Double): ShapeFill

  override def yShear(operand: Double): ShapeFill  
}

object ShapeFill
{
  implicit val slateImplicit: Slate[ShapeFill] = (obj: ShapeFill, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[ShapeFill] = (obj: ShapeFill, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeFill] = (obj: ShapeFill, radians: Double) => obj.rotateRadians(radians)
  implicit val XYScaleImplicit: XYScale[ShapeFill] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[ShapeFill] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxisImplicit: ReflectAxis[ShapeFill] = new ReflectAxis[ShapeFill]
  { /** Reflect, mirror across the X axis. */
    override def reflectXT(obj: ShapeFill): ShapeFill = obj.reflectX

    /** Reflect, mirror across the Y axis. */
    override def reflectYT(obj: ShapeFill): ShapeFill = obj.reflectY
  }

  implicit val reflectAxisOffsetImplicit: ReflectAxisOffset[ShapeFill] = new ReflectAxisOffset[ShapeFill]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: ShapeFill, yOffset: Double): ShapeFill = obj.reflectXParallel(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: ShapeFill, xOffset: Double): ShapeFill = obj.reflectYParallel(xOffset)
  }
}