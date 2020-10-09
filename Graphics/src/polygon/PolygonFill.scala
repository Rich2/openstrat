/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._

/** Immutable Graphic element that defines and fills a Polygon. This element can be trnsformed through all the Affine transformations and a
 * PolygonFill will be returned. */
trait PolygonFill extends PolygonGraphicSimple with ShapeFill
{ //type ThisT <: PolygonFill
  // override def fTrans(f: Vec2 => Vec2): PolygonFill = PolygonFill(shape.fTrans(f), colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonFill(shape, colour)
  override def toDraw(lineWidth: Double = 2, newColour: Colour = colour): PolygonDraw = shape.draw(lineWidth, newColour)

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): PolygonFill = PolygonFill(shape.slate(offset), colour)

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): PolygonFill = PolygonFill(shape.slate(xOffset, yOffset), colour)

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): PolygonFill = PolygonFill(shape.scale(operand), colour)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: PolygonFill = PolygonFill(shape.negY, colour)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: PolygonFill = PolygonFill(shape.negX, colour)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a PolygonFill, returns a PolygonFill. The return
   * type will be narrowed in sub traits / classes. */
  override def rotate90: PolygonFill = PolygonFill(shape.rotate90, colour)

  /** Rotate 180 degrees 2D geometric transformation on a PolygonFill, returns a PolygonFill. The return type will be narrowed in sub traits /
   * classes. */
  override def rotate180: PolygonFill =  PolygonFill(shape.rotate180, colour)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a PolygonFill, returns a PolygonFill. The return
   * type will be narrowed in sub traits / classes. */
  override def rotate270: PolygonFill =  PolygonFill(shape.rotate270, colour)

  override def prolign(matrix: ProlignMatrix): PolygonFill = PolygonFill(shape.prolign(matrix), colour)

  override def rotate(angle: Angle): PolygonFill = PolygonFill(shape.rotate(angle), colour)

  override def xShear(operand: Double): PolygonFill = ???

  override def yShear(operand: Double): PolygonFill = ???

  override def reflect(lineLike: LineLike): PolygonFill = ???

  override def xyScale(xOperand: Double, yOperand: Double): PolygonFill = ???

  override def slateTo(newCen: Vec2): PolygonFill = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  override def canEqual(that: Any): Boolean = ???
}

object PolygonFill
{
  def apply(shape: Polygon, colour: Colour): PolygonFill = new PolygonFillImp(shape, colour)
  /*implicit val persistImplicit: Persist2[Polygon, Colour, PolygonFill] = Persist2("PolyFill", "poly", _.shape, "colour", _.colour, apply)*/

  implicit val slateImplicit: Slate[PolygonFill] = (obj: PolygonFill, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[PolygonFill] = (obj: PolygonFill, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonFill] = (obj: PolygonFill, angle: Angle) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[PolygonFill] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[PolygonFill] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[PolygonFill] = new TransAxes[PolygonFill]
  { override def negYT(obj: PolygonFill): PolygonFill = obj.negY
    override def negXT(obj: PolygonFill): PolygonFill = obj.negX
    override def rotate90T(obj: PolygonFill): PolygonFill = obj.rotate90
    override def rotate180T(obj: PolygonFill): PolygonFill = obj.rotate180
    override def rotate270T(obj: PolygonFill): PolygonFill = obj.rotate270
  }

  /** Immutable Graphic element that defines and fills a Polygon. This element can be trnsformed through all the Affine transformations and a
   * PolygonFill will be returned.
   * @constructor create a new PolygonFill with the underlying polygon and a colour.
   * @param shape The Polygon shape.
   * @param colour The colour of this graphic. */
  final case class PolygonFillImp(shape: Polygon, colour: Colour) extends PolygonFill
  { // override type ThisT = PolygonFillImp
    // override def fTrans(f: Vec2 => Vec2): PolygonFillImp = PolygonFillImp(shape.fTrans(f), colour)

    //override def toDraw(lineWidth: Double = 2, newColour: Colour = colour): PolygonDraw = shape.draw(lineWidth, newColour)
  }
}