/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A polygon specified in [[Length]] units. */
trait PolygonLen2[+VT <: PtLen2] extends Any, GeomLen2Elem, PolygonDbl2[VT], ShapeLen2
{ type ThisT <: PolygonLen2[VT]
  type SideT <: LSegLen2[VT]

  /** The X component of vertex v0, will throw on a 0 vertices polygon. */
  def v0x: Length

  /** The Y component of vertex v1, will throw on a 0 vertices polygon. */
  def v0y: Length

  /** Vertex v0, will throw on a 0 vertices polygon. By convention the default position for this vertex is at the top or 12 o'clock position of the polygon or
   * the vertex immediately anti-clockwise if there is no vertex in this position. */
  def v0: PtLen2

  override def fill(fillFacet: FillFacet): PolygonLen2Fill = PolygonLen2Fill(this, fillFacet)

  override def draw(lineWidth: Double, lineColour: Colour): GraphicLen2Elem = ???

  override def fillDraw(fillFacet: FillFacet, lineColour: Colour, lineWidth: Double): GraphicLen2Elem = ???
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
  implicit val slateLen2Ev: SlateLen2[PolygonLen2[PtLen2]] = new SlateLen2[PolygonLen2[PtLen2]]
  { override def slateT(obj: PolygonLen2[PtLen2], delta: VecPtLen2): PolygonLen2[PtLen2] = obj.slate(delta)
    override def slateXY(obj: PolygonLen2[PtLen2], xDelta: Length, yDelta: Length): PolygonLen2[PtLen2] = obj.slate(xDelta, yDelta)
    override def slateX(obj: PolygonLen2[PtLen2], xDelta: Length): PolygonLen2[PtLen2] = obj.slateX(xDelta)
    override def slateY(obj: PolygonLen2[PtLen2], yDelta: Length): PolygonLen2[PtLen2] = obj.slateY(yDelta)
  }

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

object PolygonLen2Graphic
{
  /** Implicit [[MapGeom2]] type class instance / evidence for [[PolygonLen2Graphic]] to [[PolygonGraphic]] */
  implicit val mapGeomEv: MapGeom2[PolygonLen2Graphic, PolygonGraphic] = (obj, operand) => obj.mapGeom2(operand)
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

object PolygonLen2Fill
{ def apply(shape: PolygonLen2[?], fillFacet: FillFacet): PolygonLen2Fill = PolygonLen2FillGen(shape, fillFacet)
}

case class PolygonLen2FillGen(shape: PolygonLen2[?], fillFacet: FillFacet) extends PolygonLen2Fill
{ override def slate(operand: VecPtLen2): PolygonLen2Fill = ???
  override def slate(xOperand: Length, yOperand: Length): PolygonLen2Fill = ???
  override def slateX(xOperand: Length): PolygonLen2Fill = ???
  override def slateY(yOperand: Length): PolygonLen2Fill = ???
  override def scale(operand: Double): PolygonLen2Fill = ???
  override def mapGeom2(operand: Length): PolygonFill = PolygonFill(shape.mapGeom2(operand), fillFacet)
}