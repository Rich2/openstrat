/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This trait is for layout. For placing Display elements in rows and columns. It includes [[PolygonGraphic]] and [[ShapeGraphic]]s but not [[Line]]
 *  and [[Curve]] graphics. */
trait GraphicBounded extends Graphic2Elem, BoundedElem
{
  def slate(operand: VecPt2): GraphicBounded
  def slate(xOperand: Double, yOperand: Double): GraphicBounded
  def slateX(xOperand: Double): GraphicBounded
  def slateY(yOperand: Double): GraphicBounded
  def scale(operand: Double): GraphicBounded

  def negY: GraphicBounded
  def negX: GraphicBounded

  def prolign(matrix: AxlignMatrix): GraphicBounded

  override def rotate90: GraphicBounded
  override def rotate180: GraphicBounded
  override def rotate270: GraphicBounded
}

/** Companion object for the BoundedGraphic trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object GraphicBounded
{
  given slate2Ev: Slate2[GraphicBounded] = new Slate2[GraphicBounded]
  { override def slate(obj: GraphicBounded, operand: VecPt2): GraphicBounded = obj.slate(operand)
    override def slateXY(obj: GraphicBounded, xOperand: Double, yOperand: Double): GraphicBounded = obj.slate(xOperand, yOperand)
    override def slateX(obj: GraphicBounded, xOperand: Double): GraphicBounded = obj.slateX(xOperand)
    override def slateY(obj: GraphicBounded, yOperand: Double): GraphicBounded = obj.slateY(yOperand)
  }

  given scaleEv: Scale[GraphicBounded] = (obj: GraphicBounded, operand: Double) => obj.scale(operand)
//  given rotateEv: Rotate[GraphicBounded] = (obj: GraphicBounded, angle: AngleVec) => obj.rotate(angle)
//  given XYScaleEv: ScaleXY[GraphicBounded] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
//
//  given transAxesEv: TransAxes[GraphicBounded] = new TransAxes[GraphicBounded]
//  { override def negYT(obj: GraphicBounded): GraphicBounded = obj.negY
//    override def negXT(obj: GraphicBounded): GraphicBounded = obj.negX
//    override def rotate90(obj: GraphicBounded): GraphicBounded = obj.rotate90
//    override def rotate180(obj: GraphicBounded): GraphicBounded = obj.rotate180
//    override def rotate270(obj: GraphicBounded): GraphicBounded = obj.rotate270
//  }
//
//  given shearEv: Shear[GraphicBounded] = new Shear[GraphicBounded]
//  { override def shearXT(obj: GraphicBounded, yFactor: Double): GraphicBounded = obj.shearX(yFactor)
//    override def shearYT(obj: GraphicBounded, xFactor: Double): GraphicBounded = obj.shearY(xFactor)
//  }
//
  given prolignEv: Prolign[GraphicBounded] = (obj, matrix) => obj.prolign(matrix)
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