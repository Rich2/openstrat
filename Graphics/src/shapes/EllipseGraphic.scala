/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._, pCanv._, Colour.Black

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
trait EllipseFill extends EllipseGraphicSimple with ShapeFill
{ type ThisT <: EllipseFill
  
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.ellipseFill(shape, colour)

  override def toDraw(lineWidth: Double = 2, newColour: Colour = colour): EllipseDraw = shape.draw(lineWidth, newColour)
}

object EllipseFill
{
  def apply(shape: Ellipse, colour: Colour): EllipseFill = EllipseFillImp(shape, colour)

  /** A simple single colour fill of a circle graphic. */
  final case class EllipseFillImp(shape: Ellipse, colour: Colour) extends EllipseFill
  { type ThisT = EllipseFill
    override def fTrans(f: Vec2 => Vec2): ThisT = EllipseFill(shape.fTrans(f), colour)

    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.ellipseFill(shape, colour)

    override def xyScale(xOperand: Double, yOperand: Double): GraphicSimple = ???

    override def xShear(operand: Double): TransElem = ???

    override def yShear(operand: Double): TransElem = ???

    override def svgElem(bounds: BoundingRect): SvgElem = ???

    override def svgStr: String = ???   
  }
}

trait EllipseDraw extends EllipseGraphicSimple with ShapeDraw
{
  type ThisT <: EllipseDraw
}

object EllipseDraw
{
  def apply(shape: Ellipse, lineWidth: Double = 2.0, lineColour: Colour = Black): EllipseDraw = EllipseDrawImp(shape, lineWidth, lineColour)

  /** A simple draw of a circle graphic. */
  final case class EllipseDrawImp(shape: Ellipse, lineWidth: Double = 2.0, lineColour: Colour = Black) extends EllipseDraw
  { type ThisT = EllipseDraw

    override def fTrans(f: Vec2 => Vec2): EllipseDraw = EllipseDrawImp(shape.fTrans(f), lineWidth, lineColour)

    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.ellipseDraw(shape, lineWidth, lineColour)

    override def xyScale(xOperand: Double, yOperand: Double): GraphicSimple = ???

    override def xShear(operand: Double): TransElem = ???

    override def yShear(operand: Double): TransElem = ???

    override def svgElem(bounds: BoundingRect): SvgElem = ???

    override def svgStr: String = ???
  }

}
