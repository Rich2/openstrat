/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*, Colour.Black

/** Base [[ShapeFill]] trait for multiple geometries. */
trait ShapeGeomlessFill
{ /** The colour of this fill graphic. */
  def fillFacet: FillFacet
}

/** A simple plain colour fill graphic. */
trait ShapeFill extends ShapeGeomlessFill, ShapeGraphicSimple
{ override def nonShapeAttribs: RArr[XHAtt] = fillFacet.attribs

  /** Converts this fill graphic into a draw. */
  def toDraw(lineWidth: Double = 2, newColour: Colour = Black): ShapeDraw

  override def slate(offset: VecPt2): ShapeFill
  override def slate(xOperand: Double, yOperand: Double): ShapeFill
  override def slateX(xOperand: Double): ShapeFill
  override def slateY(yOperand: Double): ShapeFill
  override def scale(operand: Double): ShapeFill
  override def scaleXY(xOperand: Double, yOperand: Double): ShapeFill  
  override def negY: ShapeFill
  override def negX: ShapeFill
  override def prolign(matrix: AxlignMatrix): ShapeFill
  override def rotate90: ShapeFill
  override def rotate180: ShapeFill
  override def rotate270: ShapeFill
  override def rotate(rotation: AngleVec): ShapeFill
  override def reflect(lineLike: LineLike): ShapeFill
  override def shearX(operand: Double): ShapeFill
  override def shearY(operand: Double): ShapeFill
}

object ShapeFill
{ /** Implicit [[Slate2]] type class instance / evidence for [[ShapeFill]]. */
  given slate2Ev: Slate2[ShapeFill] = new Slate2[ShapeFill]
  { override def slate(obj: ShapeFill, operand: VecPt2): ShapeFill = obj.slate(operand)    
    override def slateXY(obj: ShapeFill, xOperand: Double, yOperand: Double): ShapeFill = obj.slate(xOperand, yOperand)
    override def slateX(obj: ShapeFill, xOperand: Double): ShapeFill = obj.slateX(xOperand)
    override def slateY(obj: ShapeFill, yOperand: Double): ShapeFill = obj.slateY(yOperand)
  }
  
  /** Implicit [[Scale]] type class instance / evidence for [[ShapeFill]]. */
  given scaleEv: Scale[ShapeFill] = (obj: ShapeFill, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[ShapeFill]]. */
  given rotateEv: Rotate[ShapeFill] = (obj: ShapeFill, angle: AngleVec) => obj.rotate(angle)

  /** implicit [[ScaleXY]] type class instance / evidence for [[ShapeFill]]. */
  given scaleXYEv: ScaleXY[ShapeFill] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** implicit [[Prolign]] type class instance / evidence for [[ShapeFill]]. */
  given prolignEv: Prolign[ShapeFill] = (obj, matrix) => obj.prolign(matrix)

  /** implicit [[TransAxes]] type class instance / evidence for [[ShapeFill]]. */
  given transAxesEv: TransAxes[ShapeFill] = new TransAxes[ShapeFill]
  { override def negYT(obj: ShapeFill): ShapeFill = obj.negY
    override def negXT(obj: ShapeFill): ShapeFill = obj.negX
    override def rotate90(obj: ShapeFill): ShapeFill = obj.rotate90
    override def rotate180(obj: ShapeFill): ShapeFill = obj.rotate180
    override def rotate270(obj: ShapeFill): ShapeFill = obj.rotate270
  }
}

/** Graphical fill trait for shapes specified with [[Length]] coordinates. */
trait ShapeLen2Fill extends ShapeGeomlessFill, ShapeLen2Graphic