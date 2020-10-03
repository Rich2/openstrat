/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black


/** Immutable Graphic element that defines and draws a Polygon. */
trait PolygonDraw extends ShapeDraw with PolygonGraphicSimple
{ //override def fTrans(f: Vec2 => Vec2): PolygonDraw = PolygonDraw(shape.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(shape, lineWidth, lineColour)  

  /** Translate geometric transformation on a PolygonDraw, returns a PolygonDraw. */
  override def slate(offset: Vec2): PolygonDraw = PolygonDraw(shape.slate(offset))

  /** Translate geometric transformation on a PolygonDraw, returns a PolygonDraw. */
  override def slate(xOffset: Double, yOffset: Double): PolygonDraw = PolygonDraw(shape.slate(xOffset, yOffset))

  /** Uniform scaling transformation a PolygonDraw, returns a PolygonDraw. */
  override def scale(operand: Double): PolygonDraw = PolygonDraw(shape.scale(operand))

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: PolygonDraw = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: PolygonDraw = ???

  override def prolign(matrix: ProlignMatrix): PolygonDraw = ???

  override def rotateRadians(radians: Double): PolygonDraw = ???
  override def reflect(lineLike: LineLike): PolygonDraw = ???
  override def xyScale(xOperand: Double, yOperand: Double): PolygonDraw = ???

  override def xShear(operand: Double): PolygonDraw = ???

  override def yShear(operand: Double): PolygonDraw = ???
}

object PolygonDraw
{
  def apply (shape: Polygon, lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDrawImp(shape, lineWidth, lineColour)

  /*implicit val persistImplicit: Persist3[Polygon, Double, Colour, PolygonDraw] =
    Persist3("PolyFill", "poly", _.shape, "lineWidth", _.lineWidth, "colour", _.lineColour, apply)*/

  /** Immutable Graphic element that defines and draws a Polygon. */
  case class PolygonDrawImp(shape: Polygon, lineWidth: Double = 2, lineColour: Colour = Black) extends PolygonDraw
  { //override type ThisT = PolygonDrawImp
    //override def fTrans(f: Vec2 => Vec2): PolygonDrawImp = PolygonDrawImp(shape.fTrans(f), lineWidth, lineColour)
    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(shape, lineWidth, lineColour)
  }
}