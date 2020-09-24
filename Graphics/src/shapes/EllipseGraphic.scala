/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

trait EllipseGraphic extends ShapeGraphic
{ override def shape: Ellipse
  @inline final def cen: Vec2 = shape.cen
  @inline final def xCen: Double = shape.xCen
  @inline final def yCen: Double = shape.yCen
}

/** A Simple circle based graphic. Not sure if this trait is useful. */
trait EllipseGraphicSimple extends EllipseGraphic with ShapeGraphicSimple with SimilarPreserve
{ type ThisT <: EllipseGraphicSimple

}
/** A simple single colour fill of a circle graphic. */
final case class EllipseFill(shape: Ellipse, colour: Colour) extends EllipseGraphicSimple with ShapeFill
{ type ThisT = EllipseFill
  override def fTrans(f: Vec2 => Vec2): ThisT = EllipseFill(shape.fTrans(f), colour)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.ellipseFill(shape, colour)
  override def xyScale(xOperand: Double, yOperand: Double): GraphicSimple = ???
  override def xShear(operand: Double): TransElem = ???

  override def yShear(operand: Double): TransElem = ???
  override def svgElem(bounds: BoundingRect): SvgElem = ???

  override def svgStr: String = ???
}
