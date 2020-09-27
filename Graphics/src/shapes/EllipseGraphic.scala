/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black

/** An Ellipse based Graphic. The Ellipse can be defined as a circle. */
trait EllipseGraphic extends ShapeGraphic
{ override def shape: Ellipse
  //@inline final def cen: Vec2 = shape.cen
  @inline final def xCen: Double = shape.xCen
  @inline final def yCen: Double = shape.yCen
}

/** A Simple circle based graphic. Not sure if this trait is useful. */
trait EllipseGraphicSimple extends EllipseGraphic with ShapeGraphicSimple with SimilarAffPreserve
{ type ThisT <: EllipseGraphicSimple
  type ThisT2 <: EllipseGraphicSimple
}

/** A simple single colour fill of a circle graphic. */
trait EllipseFill extends EllipseGraphicSimple with ShapeFill
{ type ThisT <: EllipseFill
  type ThisT2 = EllipseFill
  override def fTrans2(f: Vec2 => Vec2): ThisT2 = EllipseFill(shape.fTrans(f), colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.ellipseFill(shape, colour)

  override def toDraw(lineWidth: Double = 2, newColour: Colour = colour): EllipseDraw = shape.draw(lineWidth, newColour)
}

object EllipseFill
{
  def apply(shape: Ellipse, colour: Colour): EllipseFill = EllipseFillImp(shape, colour)

  /** A simple single colour fill of an ellipse graphic. */
  final case class EllipseFillImp(shape: Ellipse, colour: Colour) extends EllipseFill
  { type ThisT = EllipseFill

    override def fTrans(f: Vec2 => Vec2): ThisT = EllipseFill(shape.fTrans(f), colour)
    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.ellipseFill(shape, colour)
    override def svgElem(bounds: BoundingRect): SvgElem = ???
    override def svgStr: String = ???   
  }
}

trait EllipseDraw extends EllipseGraphicSimple with ShapeDraw
{
  type ThisT <: EllipseDraw
  type ThisT2 = EllipseDraw
  override def fTrans2(f: Vec2 => Vec2): EllipseDraw = EllipseDraw(shape.fTrans(f), lineWidth, lineColour)
}

object EllipseDraw
{
  def apply(shape: Ellipse, lineWidth: Double = 2.0, lineColour: Colour = Black): EllipseDraw = EllipseDrawImp(shape, lineWidth, lineColour)

  /** A simple draw of a circle graphic. */
  final case class EllipseDrawImp(shape: Ellipse, lineWidth: Double = 2.0, lineColour: Colour = Black) extends EllipseDraw
  { type ThisT = EllipseDraw

    override def fTrans(f: Vec2 => Vec2): EllipseDraw = EllipseDrawImp(shape.fTrans(f), lineWidth, lineColour)

    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.ellipseDraw(shape, lineWidth, lineColour)

    override def svgElem(bounds: BoundingRect): SvgElem = ???

    override def svgStr: String = ???
  }
}