/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This trait is for layout. For placing Display elements in rows and columns. It includes [[PolygonGraphic]] and [[ShapeGraphic]]s but not [[Line]]
 *  and [[Curve]] graphics. */
trait BoundedGraphic extends GraphicElem with BoundedElem
{
  def xySlate(xOffset: Double, yOffset: Double): BoundedGraphic
  def scale(operand: Double): BoundedGraphic

  def negY: BoundedGraphic
  def negX: BoundedGraphic

  def prolign(matrix: ProlignMatrix): BoundedGraphic

  override def rotate90: BoundedGraphic
  def rotate(angle: AngleVec): BoundedGraphic
  override def reflect(lineLike: LineLike): BoundedGraphic
  override def xyScale(xOperand: Double, yOperand: Double): BoundedGraphic

  override def xShear(operand: Double): BoundedGraphic
  override def yShear(operand: Double): BoundedGraphic
}

/** Companion object for the BoundedGraphic trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object BoundedGraphic
{
  implicit val slateImplicit: Slate[BoundedGraphic] = (obj: BoundedGraphic, dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[BoundedGraphic] = (obj: BoundedGraphic, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[BoundedGraphic] = (obj: BoundedGraphic, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[BoundedGraphic] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)

  implicit val transAxesImplicit: TransAxes[BoundedGraphic] = new TransAxes[BoundedGraphic]
  { override def negYT(obj: BoundedGraphic): BoundedGraphic = obj.negY
    override def negXT(obj: BoundedGraphic): BoundedGraphic = obj.negX
    override def rotate90(obj: BoundedGraphic): BoundedGraphic = obj.rotate90
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