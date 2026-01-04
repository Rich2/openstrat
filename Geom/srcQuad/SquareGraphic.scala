/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

import ostrat.Colour.Black

/** 2-dimensional graphic bsed on a [[Square]] */
trait SquareGraphic extends RectangleGraphic
{ override def shape: Square
  override def slate(operand: VecPt2): SquareGraphic
  override def slate(xOperand: Double, yOperand: Double): SquareGraphic
  override def slateFrom(operand: VecPt2): SquareGraphic
  override def slateFrom(xOperand: Double, yOperand: Double): SquareGraphic
  override def scale(operand: Double): SquareGraphic
  override def negY: SquareGraphic
  override def negX: SquareGraphic
}

trait SquareGraphicSimple extends SquareGraphic, RectangleGraphicSimple
{ override def slate(operand: VecPt2): SquareGraphicSimple
  override def slate(xOperand: Double, yOperand: Double): SquareGraphicSimple
  override def slateFrom(operand: VecPt2): SquareGraphicSimple
  override def slateFrom(xOperand: Double, yOperand: Double): SquareGraphicSimple
  override def scale(operand: Double): SquareGraphicSimple
  override def negX: SquareGraphicSimple
  override def negY: SquareGraphicSimple
}

/** Graphic that draws a [[Square]]. */
trait SquareDraw extends RectangleDraw, SquareGraphicSimple
{ override def slate(operand: VecPt2): SquareDraw = SquareDraw(shape.slate(operand), lineWidth, lineColour)
  override def slate(xOperand: Double, yOperand: Double): SquareDraw = SquareDraw(shape.slate(xOperand, yOperand), lineWidth, lineColour)
  override def slateFrom(operand: VecPt2): SquareDraw = SquareDraw(shape.slateFrom(operand), lineWidth, lineColour)
  override def slateFrom(xOperand: Double, yOperand: Double): SquareDraw = SquareDraw(shape.slateFrom(xOperand, yOperand), lineWidth, lineColour)
  override def scale(operand: Double): SquareDraw = SquareDraw(shape.scale(operand), lineWidth, lineColour)
  override def negX: SquareDraw = SquareDraw(shape.negX, lineWidth, lineColour)
  override def negY: SquareDraw = SquareDraw(shape.negY, lineWidth, lineColour)
  override def rotate90: SquareDraw = SquareDraw(shape.rotate90, lineWidth, lineColour)
  override def rotate180: SquareDraw = SquareDraw(shape.rotate180, lineWidth, lineColour)
  override def rotate270: SquareDraw = SquareDraw(shape.rotate270, lineWidth, lineColour)
  override def prolign(matrix: AxlignMatrix): SquareDraw = SquareDraw(shape.prolign(matrix), lineWidth, lineColour)
  override def rotate(rotation: AngleVec): SquareDraw = SquareDraw(shape.rotate(rotation), lineWidth, lineColour)
  override def mirror(lineLike: LineLike): SquareDraw = SquareDraw(shape.mirror(lineLike), lineWidth, lineColour) 
}

/** Companion object for SquareDraw contains factory method and implementation class. */
object SquareDraw
{
  def apply(shape: Square, lineWidth: Double = 2, lineColour: Colour = Black): SquareDraw = SquareDrawImp(shape, lineWidth, lineColour)

  /** Immutable Graphic element that defines and draws a Polygon. */
  case class SquareDrawImp(shape: Square, lineWidth: Double = 2, lineColour: Colour = Black) extends SquareDraw
 
}

trait SquareFill extends SquareGraphicSimple, RectangleFill
{ override def slate(operand: VecPt2): SquareFill
  override def slate(xOperand: Double, yOperand: Double): SquareFill
  override def slateFrom(operand: VecPt2): SquareFill
  override def slateFrom(xOperand: Double, yOperand: Double): SquareFill
  override def slateX(xOperand: Double): SquareFill
  override def slateY(yOperand: Double): SquareFill
  override def scale(operand: Double): SquareFill
  override def negX: SquareFill
  override def negY: SquareFill
  override def rotate90: SquareFill
  override def rotate180: SquareFill
  override def rotate270: SquareFill
  override def prolign(matrix: AxlignMatrix): SquareFill
  override def rotate(rotation: AngleVec): SquareFill = SquareFill(shape.rotate(rotation), fillFacet)
  override def mirror(lineLike: LineLike): SquareFill = SquareFill(shape.mirror(lineLike), fillFacet)
}

object SquareFill
{ /** Factory apply method for the general case of [[SquareFill]]. Use [[SqlignFill]] for one aligned to the X and Y axes. */
  def apply(shape: Square, fillFacet: FillFacet): SquareFillGen = SquareFillGen(shape, fillFacet)
  
  /** Implementation for the general case of [[Square]] as opposed to the specific case of [[Sqlign]]. */
  case class SquareFillGen(shape: Square, fillFacet: FillFacet) extends SquareFill
  { override type ThisT = SquareFillGen
    override def slate(operand: VecPt2): SquareFillGen = SquareFillGen(shape.slate(operand), fillFacet)
    override def slate(xOperand: Double, yOperand: Double): SquareFillGen = SquareFillGen(shape.slate(xOperand, yOperand), fillFacet)
    override def slateFrom(operand: VecPt2): SquareFillGen = SquareFillGen(shape.slateFrom(operand), fillFacet)
    override def slateFrom(xOperand: Double, yOperand: Double): SquareFillGen = SquareFillGen(shape.slateFrom(xOperand, yOperand), fillFacet)
    override def slateX(xOperand: Double): SquareFillGen = SquareFillGen(shape.slateX(xOperand), fillFacet)
    override def slateY(yOperand: Double): SquareFillGen = SquareFillGen(shape.slateY(yOperand), fillFacet)
    override def scale(operand: Double): SquareFillGen = SquareFillGen(shape.scale(operand), fillFacet)
    override def negX: SquareFillGen = SquareFillGen(shape.negX, fillFacet)
    override def negY: SquareFillGen = SquareFillGen(shape.negY, fillFacet)
    override def rotate90: SquareFillGen = SquareFillGen(shape.rotate90, fillFacet)
    override def rotate180: SquareFillGen = SquareFillGen(shape.rotate180, fillFacet)
    override def rotate270: SquareFillGen = SquareFillGen(shape.rotate180, fillFacet)
    override def prolign(matrix: AxlignMatrix): SquareFillGen = SquareFillGen(shape.prolign(matrix), fillFacet)
  }
}