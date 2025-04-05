/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait SquareGraphic extends RectangleGraphic
{ override def shape: Square
  override def slate(operand: VecPt2): SquareGraphic
  override def slate(xOperand: Double, yOperand: Double): SquareGraphic
  override def scale(operand: Double): SquareGraphic
  override def negY: SquareGraphic
  override def negX: SquareGraphic
}

trait SquareGraphicSimple extends SquareGraphic, RectangleGraphicSimple
{ override def slate(operand: VecPt2): SquareGraphicSimple
  override def slate(xOperand: Double, yOperand: Double): SquareGraphicSimple
  override def scale(operand: Double): SquareGraphicSimple
  override def negX: SquareGraphicSimple
  override def negY: SquareGraphicSimple
}

trait SquareFill extends SquareGraphicSimple, RectangleFill
{ override def slate(operand: VecPt2): SquareFill
  override def slate(xOperand: Double, yOperand: Double): SquareFill
  override def scale(operand: Double): SquareFill
  override def negX: SquareFill
  override def negY: SquareFill
  override def rotate90: SquareFill
  override def rotate180: SquareFill
  override def rotate270: SquareFill
  override def prolign(matrix: ProlignMatrix): SquareFill
  override def rotate(rotation: AngleVec): SquareFill = SquareFill(shape.rotate(rotation), fillFacet)
  override def reflect(lineLike: LineLike): SquareFill = SquareFill(shape.reflect(lineLike), fillFacet)
}

object SquareFill
{ /** Factory apply method for the general case of [[SquareFill]]. Use [[SqlignFill]] for one aligned to the X and Y axes. */
  def apply(shape: Square, fillFacet: FillFacet): SquareFillGen = SquareFillGen(shape, fillFacet)
  
  /** Implementation for the general case of [[Square]] as opposed to the specific case of [[Sqlign]]. */
  case class SquareFillGen(shape: Square, fillFacet: FillFacet) extends SquareFill
  { override type ThisT = SquareFillGen
    override def slate(operand: VecPt2): SquareFillGen = SquareFillGen(shape.slate(operand), fillFacet)
    override def slate(xOperand: Double, yOperand: Double): SquareFillGen = SquareFillGen(shape.slate(xOperand, yOperand), fillFacet)
    override def slateX(xOperand: Double): SquareFillGen = SquareFillGen(shape.slateX(xOperand), fillFacet)
    override def slateY(yOperand: Double): SquareFillGen = SquareFillGen(shape.slateY(yOperand), fillFacet)
    override def scale(operand: Double): SquareFillGen = SquareFillGen(shape.scale(operand), fillFacet)
    override def negX: SquareFillGen = SquareFillGen(shape.negX, fillFacet)
    override def negY: SquareFillGen = SquareFillGen(shape.negY, fillFacet)
    override def rotate90: SquareFillGen = SquareFillGen(shape.rotate90, fillFacet)
    override def rotate180: SquareFillGen = SquareFillGen(shape.rotate180, fillFacet)
    override def rotate270: SquareFillGen = SquareFillGen(shape.rotate180, fillFacet)
    override def prolign(matrix: ProlignMatrix): SquareFillGen = SquareFillGen(shape.prolign(matrix), fillFacet)
  }
}