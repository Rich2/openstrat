/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait BoundedGraphic extends GraphicElem with BoundedElem
{ def slate(offset: Vec2): BoundedGraphic
  def slate(xOffset: Double, yOffset: Double): BoundedGraphic
  def scale(operand: Double): BoundedGraphic

  def negY: BoundedGraphic
  def negX: BoundedGraphic
  def prolign(matrix: ProlignMatrix): BoundedGraphic
  /*def rotate90: DisplayBounded
  def rotate180: DisplayBounded
  def rotate270: DisplayBounded*/
  def rotate(angle: Angle): BoundedGraphic
  override def reflect(lineLike: LineLike): BoundedGraphic
  override def xyScale(xOperand: Double, yOperand: Double): BoundedGraphic

  override def xShear(operand: Double): BoundedGraphic
  override def yShear(operand: Double): BoundedGraphic
}

/** Companion object for the DisplayBounded trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object BoundedGraphic
{
  implicit val slateImplicit: Slate[BoundedGraphic] = (obj: BoundedGraphic, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[BoundedGraphic] = (obj: BoundedGraphic, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[BoundedGraphic] = (obj: BoundedGraphic, angle: Angle) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[BoundedGraphic] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)

  implicit val reflectAxesImplicit: ReflectAxes[BoundedGraphic] = new ReflectAxes[BoundedGraphic]
  { override def negYT(obj: BoundedGraphic): BoundedGraphic = obj.negY
    override def negXT(obj: BoundedGraphic): BoundedGraphic = obj.negX
  }

  implicit val shearImplicit: Shear[BoundedGraphic] = new Shear[BoundedGraphic]
  { override def xShearT(obj: BoundedGraphic, yFactor: Double): BoundedGraphic = obj.xShear(yFactor)
    override def yShearT(obj: BoundedGraphic, xFactor: Double): BoundedGraphic = obj.yShear(xFactor)
  }

  implicit val prolignImplicit: Prolign[BoundedGraphic] = (obj, matrix) => obj.prolign(matrix)
}


/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBoundedSimer extends GraphicSimElem with BoundedGraphic
{ type ThisT <: GraphicBoundedSimer

  override def xyScale(xOperand: Double, yOperand: Double): ThisT
}

/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBoundedAffine extends GraphicBoundedSimer with GraphicAffineElem
{ type ThisT <: GraphicBoundedAffine
}