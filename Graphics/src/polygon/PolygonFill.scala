/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui._

/** Immutable Graphic element that defines and fills a Polygon. This element can be trnsformed through all the Affine transformations and a
 * PolygonFill will be returned. */
trait PolygonFill extends PolygonGraphicSimple with CanvShapeFill
{ //type ThisT <: PolygonFill
  // override def fTrans(f: Vec2 => Vec2): PolygonFill = PolygonFill(shape.fTrans(f), colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonFill(this)
  override def toDraw(lineWidth: Double = 2, newColour: Colour ): PolygonDraw = shape.draw(newColour, lineWidth)

  /** Translate geometric transformation. */
  override def slateXY(xDelta: Double, yDelta: Double): PolygonFill = PolygonFill(shape.slateXY(xDelta, yDelta), fill)

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): PolygonFill = PolygonFill(shape.scale(operand), fill)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: PolygonFill = PolygonFill(shape.negY, fill)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: PolygonFill = PolygonFill(shape.negX, fill)

  override def prolign(matrix: ProlignMatrix): PolygonFill = PolygonFill(shape.prolign(matrix), fill)

  override def rotate(angle: AngleVec): PolygonFill = PolygonFill(shape.rotate(angle), fill)

  override def rotate90: PolygonFill = ???
  override def rotate180: PolygonFill = ???
  override def rotate270: PolygonFill = ???

  override def shearX(operand: Double): PolygonFill = ???

  override def shearY(operand: Double): PolygonFill = ???

  override def reflect(lineLike: LineLike): PolygonFill = ???

  override def scaleXY(xOperand: Double, yOperand: Double): PolygonFill = ???

  //override def productElement(n: Int): Any = ???

  //override def canEqual(that: Any): Boolean = ???
}

/** Companion object for PolygonFill trait. Contains an implementation class [[PolygonFillImp]], a factory method returning the PolygonFill type and
 * implicit instances for the 2D geometric transformation type classes. */
object PolygonFill
{
  def apply(shape: Polygon, fillFacet: FillFacet): PolygonFill = new PolygonFillImp(shape, fillFacet)
  /*implicit val persistImplicit: Persist2[Polygon, Colour, PolygonFill] = Persist2("PolyFill", "poly", _.shape, "colour", _.colour, apply)*/

  implicit val slateImplicit: Slate[PolygonFill] = (obj: PolygonFill, xDelta: Double, yDelta: Double) => obj.slateXY(xDelta, yDelta)
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

  /** Immutable Graphic element that defines and fills a Polygon. This element can be trnsformed through all the Affine transformations and a
   * PolygonFill will be returned.
   * @constructor create a new PolygonFill with the underlying polygon and a colour.
   * @param shape The Polygon shape.
   * @param colour The colour of this graphic. */
  final case class PolygonFillImp(shape: Polygon, fill: FillFacet) extends PolygonFill
  {
    // override def fTrans(f: Vec2 => Vec2): PolygonFillImp = PolygonFillImp(shape.fTrans(f), colour)

    //override def toDraw(lineWidth: Double = 2, newColour: Colour = colour): PolygonDraw = shape.draw(lineWidth, newColour)
  }
}