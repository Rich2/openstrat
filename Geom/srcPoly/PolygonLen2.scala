/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A polygon specified in [[Length]] units. */
trait PolygonLen2[VT <: PtLen2] extends Any, GeomLen2Elem, PolygonLikeDbl2[VT], ShapeLen2
{ type ThisT <: PolygonLen2[VT]
  type SideT <: LineSegLen2[VT]

  override def slate(operand: VecPtLen2): PolygonLen2[VT]  
  override def slate(xOperand: Length, yOperand: Length): PolygonLen2[VT]
  override def slateX(xOperand: Length): PolygonLen2[VT]
  override def slateY(yOperand: Length): PolygonLen2[VT]
  override def scale(operand: Double): PolygonLen2[VT]
  def mapGeom2(operand: Length): Polygon

  def revY: PolygonLen2[VT]
  def revYIf(cond: Boolean): PolygonLen2[VT]
  def rotate180: PolygonLen2[VT]
  def rotate180If(cond: Boolean): PolygonLen2[VT]
  def rotate180IfNot(cond: Boolean): PolygonLen2[VT]
}

/** Companion object for [[PolygonLen2]]. Does not provide factory methods. Use the specific [[Length]] unit classes such as [[PolyonM2]] and [[PolygonKm2]].
 * Does contain geometric transformation type class instances. */
object PolygonLen2
{ /** implicit [[StateLen2]] type class instance / evidence for [[PolygonLen2]]. */
  implicit val slateLen2Ev: SlateLen2[PolygonLen2[PtLen2]] = (obj, operand) => obj.slate(operand)

  /** implicit [[StateLenXY]] type class instance / evidence for [[PolygonLen2]]. */
  implicit val slateLenXYEv: SlateLenXY[PolygonLen2[PtLen2]] = (obj, xOp, yOp) => obj.slate(xOp, yOp)

  /** implicit [[Scale]] type class instance / evidence for [[PolygonLen2]]. */
  implicit val scaleEv: Scale[PolygonLen2[PtLen2]] = (obj, operand) => obj.scale(operand)

  /** implicit [[MapGeom2]] type class instance / evidence for [[PolygonLen2]]. */
  implicit val mapGeom2Ev: MapGeom2[PolygonLen2[PtLen2], Polygon] = (obj, operand) => obj.mapGeom2(operand)
}

/** A polygon graphic where the point are specified in [[Length]] units. */
trait PolygonLen2Graphic extends ShapeLen2Graphic
{ override def shape: PolygonLen2[PtLen2]
  override def slate(operand: VecPtLen2): PolygonLen2Graphic
  override def slate(xOperand: Length, yOperand: Length): PolygonLen2Graphic
  override def slateX(xOperand: Length): PolygonLen2Graphic
  override def slateY(yOperand: Length): PolygonLen2Graphic
  override def scale(operand: Double): PolygonLen2Graphic
  override def mapGeom2(operand: Length): PolygonGraphic
}

/** A polygon graphic where the point are specified in [[Length]] units. */
trait PolygonLen2Fill extends PolygonLen2Graphic, ShapeLen2Fill
{ override def slate(operand: VecPtLen2): PolygonLen2Fill
  override def slate(xOperand: Length, yOperand: Length): PolygonLen2Fill
  override def slateX(xOperand: Length): PolygonLen2Fill
  override def slateY(yOperand: Length): PolygonLen2Fill
  override def scale(operand: Double): PolygonLen2Fill
  override def mapGeom2(operand: Length): PolygonFill
}