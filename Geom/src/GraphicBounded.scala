/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This trait is for layout. For placing Display elements in rows and columns. It includes [[PolygonGraphic]] and [[ShapeGraphic]]s but not [[Line]]
 *  and [[Curve]] graphics. */
trait GraphicBounded extends GraphicElem with BoundedElem
{
  def slateXY(xDelta: Double, yDelta: Double): GraphicBounded
  def scale(operand: Double): GraphicBounded

  def negY: GraphicBounded
  def negX: GraphicBounded

  def prolign(matrix: ProlignMatrix): GraphicBounded

  override def rotate90: GraphicBounded
  override def rotate180: GraphicBounded
  override def rotate270: GraphicBounded
  def rotate(angle: AngleVec): GraphicBounded
  override def reflect(lineLike: LineLike): GraphicBounded
  override def scaleXY(xOperand: Double, yOperand: Double): GraphicBounded

  override def shearX(operand: Double): GraphicBounded
  override def shearY(operand: Double): GraphicBounded
}

/** Companion object for the BoundedGraphic trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object GraphicBounded
{
  implicit val slateImplicit: Slate[GraphicBounded] = (obj: GraphicBounded, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[GraphicBounded] = (obj: GraphicBounded, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[GraphicBounded] = (obj: GraphicBounded, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: ScaleXY[GraphicBounded] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  implicit val transAxesImplicit: TransAxes[GraphicBounded] = new TransAxes[GraphicBounded]
  { override def negYT(obj: GraphicBounded): GraphicBounded = obj.negY
    override def negXT(obj: GraphicBounded): GraphicBounded = obj.negX
    override def rotate90(obj: GraphicBounded): GraphicBounded = obj.rotate90
    override def rotate180(obj: GraphicBounded): GraphicBounded = obj.rotate180
    override def rotate270(obj: GraphicBounded): GraphicBounded = obj.rotate270
  }

  implicit val shearImplicit: Shear[GraphicBounded] = new Shear[GraphicBounded]
  { override def shearXT(obj: GraphicBounded, yFactor: Double): GraphicBounded = obj.shearX(yFactor)
    override def shearYT(obj: GraphicBounded, xFactor: Double): GraphicBounded = obj.shearY(xFactor)
  }

  implicit val prolignImplicit: Prolign[GraphicBounded] = (obj, matrix) => obj.prolign(matrix)
}

/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBoundedSimer extends GraphicSimElem with GraphicBounded
{ type ThisT <: GraphicBoundedSimer
}

/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBoundedAffine extends GraphicBoundedSimer with GraphicAffineElem
{ type ThisT <: GraphicBoundedAffine
}