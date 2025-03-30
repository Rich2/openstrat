/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui.*, Colour.Black

/** Immutable Graphic element that defines and fills a Polygon. This element can be trnsformed through all the Affine transformations and a
 * PolygonFill will be returned. */
trait PolygonFill extends PolygonGraphicSimple with CanvShapeFill
{ type ThisT <: PolygonFill
  // override def fTrans(f: Vec2 => Vec2): PolygonFill = PolygonFill(shape.fTrans(f), colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonFill(this)
  override def toDraw(lineWidth: Double = 2, newColour: Colour ): PolygonDraw = shape.draw(lineWidth, newColour)

  override def slate(operand: VecPt2): PolygonFill
  override def slate(xDelta: Double, yDelta: Double): PolygonFill
  override def scale(operand: Double): PolygonFill
  override def negX: PolygonFill
  override def negY: PolygonFill
  override def prolign(matrix: ProlignMatrix): PolygonFill = PolygonFill(shape.prolign(matrix), fillFacet)
  override def rotate(angle: AngleVec): PolygonFill = PolygonFill(shape.rotate(angle), fillFacet)
  override def rotate90: PolygonFill
  override def rotate180: PolygonFill
  override def rotate270: PolygonFill
  override def shearX(operand: Double): PolygonFill = ???
  override def shearY(operand: Double): PolygonFill = ???
  override def reflect(lineLike: LineLike): PolygonFill = ???
  override def scaleXY(xOperand: Double, yOperand: Double): PolygonFill = ???
}

/** Companion object for PolygonFill trait. Contains an implementation class [[PolygonFillImp]], a factory method returning the PolygonFill type and
 * implicit instances for the 2D geometric transformation type classes. */
object PolygonFill
{
  def apply(shape: Polygon, fillFacet: FillFacet): PolygonFill = new PolygonFillImp(shape, fillFacet)
  /*implicit val persistImplicit: Persist2[Polygon, Colour, PolygonFill] = Persist2("PolyFill", "poly", _.shape, "colour", _.colour, apply)*/

  implicit val slateImplicit: SlateXY[PolygonFill] = (obj: PolygonFill, xDelta: Double, yDelta: Double) => obj.slate(xDelta, yDelta)
  implicit val scaleImplicit: Scale[PolygonFill] = (obj: PolygonFill, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonFill] = (obj: PolygonFill, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: ScaleXY[PolygonFill] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[PolygonFill] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[PolygonFill] = new TransAxes[PolygonFill]
  { override def negYT(obj: PolygonFill): PolygonFill = obj.negY
    override def negXT(obj: PolygonFill): PolygonFill = obj.negX
    override def rotate90(obj: PolygonFill): PolygonFill = obj.rotate90
    override def rotate180(obj: PolygonFill): PolygonFill = obj.rotate90
    override def rotate270(obj: PolygonFill): PolygonFill = obj.rotate90
  }

  /** Immutable Graphic element that defines and fills a Polygon. This element can be transformed through all the Affine transformations and a [[PolygonFill]]
   * will be returned.
   * @constructor create a new PolygonFill with the underlying polygon and a colour.
   * @param shape The Polygon shape.
   * @param colour The colour of this graphic. */
  final case class PolygonFillImp(shape: Polygon, fillFacet: FillFacet) extends PolygonFill
  { override def slate(operand: VecPt2): PolygonFill = PolygonFillImp(shape.slate(operand), fillFacet)
    override def slate(xDelta: Double, yDelta: Double): PolygonFillImp = PolygonFillImp(shape.slate(xDelta, yDelta), fillFacet)
    override def scale(operand: Double): PolygonFillImp = PolygonFillImp(shape.scale(operand), fillFacet)
    override def negY: PolygonFillImp = PolygonFillImp(shape.negY, fillFacet)
    override def negX: PolygonFillImp = PolygonFillImp(shape.negX, fillFacet)
    override def rotate90: PolygonFillImp = PolygonFillImp(shape.rotate90, fillFacet)
    override def rotate180: PolygonFillImp = PolygonFillImp(shape.rotate180, fillFacet)
    override def rotate270: PolygonFill = PolygonFillImp(shape.rotate270, fillFacet)
    override def toDraw(lineWidth: Double = 2, newColour: Colour = Black): PolygonDraw = shape.draw(lineWidth, newColour)
  }
}