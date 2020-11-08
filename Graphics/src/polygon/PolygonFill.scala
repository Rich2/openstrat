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
  override def toDraw(lineWidth: Double = 2, newColour: Colour = colour): PolygonDraw = shape.draw(newColour, lineWidth)

  /** Translate geometric transformation. */
  override def slate(offset: Vec2Like): PolygonFill = PolygonFill(shape.slate(offset), colour)

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

  override def prolign(matrix: ProlignMatrix): PolygonFill = PolygonFill(shape.prolign(matrix), colour)

  override def rotate(angle: Angle): PolygonFill = PolygonFill(shape.rotate(angle), colour)

  override def xShear(operand: Double): PolygonFill = ???

  override def yShear(operand: Double): PolygonFill = ???

  override def reflect(lineLike: LineLike): PolygonFill = ???

  override def xyScale(xOperand: Double, yOperand: Double): PolygonFill = ???

  override def slateTo(newCen: Pt2): PolygonFill = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  override def canEqual(that: Any): Boolean = ???
}

/** Companion object for PolygonFill trait. Contains an implementation class [[PolygonFillImp]], a factory method returning the PolygonFill type and
 * implicit instances for the 2D geometric transformation type classes. */
object PolygonFill
{
  def apply(shape: Polygon, colour: Colour): PolygonFill = new PolygonFillImp(shape, colour)
  /*implicit val persistImplicit: Persist2[Polygon, Colour, PolygonFill] = Persist2("PolyFill", "poly", _.shape, "colour", _.colour, apply)*/

  implicit val slateImplicit: Slate[PolygonFill] = (obj: PolygonFill, dx: Double, dy: Double) => obj.slate(dx, dy)
  implicit val scaleImplicit: Scale[PolygonFill] = (obj: PolygonFill, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonFill] = (obj: PolygonFill, angle: Angle) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[PolygonFill] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[PolygonFill] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: ReflectAxes[PolygonFill] = new ReflectAxes[PolygonFill]
  { override def negYT(obj: PolygonFill): PolygonFill = obj.negY
    override def negXT(obj: PolygonFill): PolygonFill = obj.negX
  }

  /** Immutable Graphic element that defines and fills a Polygon. This element can be trnsformed through all the Affine transformations and a
   * PolygonFill will be returned.
   * @constructor create a new PolygonFill with the underlying polygon and a colour.
   * @param shape The Polygon shape.
   * @param colour The colour of this graphic. */
  final case class PolygonFillImp(shape: Polygon, colour: Colour) extends PolygonFill
  {
    // override def fTrans(f: Vec2 => Vec2): PolygonFillImp = PolygonFillImp(shape.fTrans(f), colour)

    //override def toDraw(lineWidth: Double = 2, newColour: Colour = colour): PolygonDraw = shape.draw(lineWidth, newColour)
  }
}