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
  override def slateXY(xDelta: Double, yDelta: Double): ShapeFill

  /** Translate geometric transformation. */
  //override def slate(offset: Vec2Like): ShapeFill

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

  override def rotate90: ShapeFill
  override def rotate180: ShapeFill
  override def rotate270: ShapeFill

  override def rotate(angle: AngleVec): ShapeFill

  override def reflect(lineLike: LineLike): ShapeFill

  override def scaleXY(xOperand: Double, yOperand: Double): ShapeFill

  override def shearX(operand: Double): ShapeFill

  override def shearY(operand: Double): ShapeFill
}

object ShapeFill
{
  implicit val slateImplicit: Slate[ShapeFill] = (obj: ShapeFill, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[ShapeFill] = (obj: ShapeFill, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeFill] = (obj: ShapeFill, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: ScaleXY[ShapeFill] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[ShapeFill] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[ShapeFill] = new TransAxes[ShapeFill]
  { override def negYT(obj: ShapeFill): ShapeFill = obj.negY
    override def negXT(obj: ShapeFill): ShapeFill = obj.negX
    override def rotate90(obj: ShapeFill): ShapeFill = obj.rotate90
    override def rotate180(obj: ShapeFill): ShapeFill = obj.rotate180
    override def rotate270(obj: ShapeFill): ShapeFill = obj.rotate270
  }
}