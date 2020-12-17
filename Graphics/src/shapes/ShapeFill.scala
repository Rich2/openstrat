/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A simple plain colour fill graphic. */
trait ShapeFill extends ShapeGraphicSimple
{ /** The colour of this fill graphic. */
  def fill: FillFacet

  override def nonShapeAttribs: Arr[XmlAtt] = fill.attribs// Arr(fillAttrib)

  def toDraw(lineWidth: Double = 2, newColour: Colour): ShapeDraw

  /** Translate geometric transformation. */
  override def xySlate(xOffset: Double, yOffset: Double): ShapeFill

  /** Translate geometric transformation. */
  override def slate(offset: Vec2Like): ShapeFill

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeFill

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: ShapeFill

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: ShapeFill

  override def prolign(matrix: ProlignMatrix): ShapeFill

  override def rotate(angle: AngleVec): ShapeFill

  override def reflect(lineLike: LineLike): ShapeFill

  override def xyScale(xOperand: Double, yOperand: Double): ShapeFill

  override def xShear(operand: Double): ShapeFill

  override def yShear(operand: Double): ShapeFill  
}

object ShapeFill
{
  implicit val slateImplicit: Slate[ShapeFill] = (obj: ShapeFill, dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[ShapeFill] = (obj: ShapeFill, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeFill] = (obj: ShapeFill, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[ShapeFill] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[ShapeFill] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: ReflectAxes[ShapeFill] = new ReflectAxes[ShapeFill]
  { override def negYT(obj: ShapeFill): ShapeFill = obj.negY
    override def negXT(obj: ShapeFill): ShapeFill = obj.negX
  }
}