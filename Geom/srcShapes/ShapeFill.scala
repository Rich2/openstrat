/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** Base [[ShapeFill]] trait for multiple geometries. */
trait ShapeGeomlessFill
{ /** The colour of this fill graphic. */
  def fillFacet: FillFacet
}

/** A simple plain colour fill graphic. */
trait ShapeFill extends ShapeGeomlessFill, ShapeGraphicSimple
{ override def nonShapeAttribs: RArr[XmlAtt] = fillFacet.attribs// Arr(fillAttrib)

  def toDraw(lineWidth: Double = 2, newColour: Colour): ShapeDraw

  override def slate(offset: VecPt2): ShapeFill
  override def slate(xDelta: Double, yDelta: Double): ShapeFill
  override def scale(operand: Double): ShapeFill
  override def scaleXY(xOperand: Double, yOperand: Double): ShapeFill  
  override def negY: ShapeFill
  override def negX: ShapeFill
  override def prolign(matrix: ProlignMatrix): ShapeFill
  override def rotate90: ShapeFill
  override def rotate180: ShapeFill
  override def rotate270: ShapeFill
  override def rotate(angle: AngleVec): ShapeFill
  override def reflect(lineLike: LineLike): ShapeFill
  override def shearX(operand: Double): ShapeFill
  override def shearY(operand: Double): ShapeFill
}

object ShapeFill
{ /** Implicit [[Slate]] type class instance / evidence for [[ShapeFill]]. */
  implicit val slateEv: Slate[ShapeFill] = (obj: ShapeFill, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance / evidence for [[ShapeFill]]. */
  implicit val slateXYEv: SlateXY[ShapeFill] = (obj: ShapeFill, dx: Double, dy: Double) => obj.slate(dx, dy)

  /** Implicit [[Scale]] type class instance / evidence for [[ShapeFill]]. */
  implicit val scaleEv: Scale[ShapeFill] = (obj: ShapeFill, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[ShapeFill]]. */
  implicit val rotateEv: Rotate[ShapeFill] = (obj: ShapeFill, angle: AngleVec) => obj.rotate(angle)

  /** implicit [[ScaleXY]] type class instance / evidence for [[ShapeFill]]. */
  implicit val scaleXYEv: ScaleXY[ShapeFill] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** implicit [[Prolign]] type class instance / evidence for [[ShapeFill]]. */
  implicit val prolignEv: Prolign[ShapeFill] = (obj, matrix) => obj.prolign(matrix)

  /** implicit [[TransAxes]] type class instance / evidence for [[ShapeFill]]. */
  implicit val transAxesEv: TransAxes[ShapeFill] = new TransAxes[ShapeFill]
  { override def negYT(obj: ShapeFill): ShapeFill = obj.negY
    override def negXT(obj: ShapeFill): ShapeFill = obj.negX
    override def rotate90(obj: ShapeFill): ShapeFill = obj.rotate90
    override def rotate180(obj: ShapeFill): ShapeFill = obj.rotate180
    override def rotate270(obj: ShapeFill): ShapeFill = obj.rotate270
  }
}

/** Graphical fill trait for shapes specified with [[Length]] coordinates. */
trait ShapeLen2Fill extends ShapeGeomlessFill, ShapeLen2GraphicSimple