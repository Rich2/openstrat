/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.Colour.Black, pWeb._

/** A graphic based on a [[Rect], a rectangle aligned to the X and Y axes. */
trait RectGraphic extends RectangleGraphic with ShapeGraphicOrdinaled
{ override def shape: Rect
}

/** A simple non-compound graphic based on a [[Rect], a rectangle aligned to the X and Y axes. */
trait RectGraphicSimple extends RectGraphic with RectangleGraphicSimple
{
  override def svgElem: SvgElem = SvgRect(attribs)
}

/** A rectangular Graphic aligned to the axes, filled with a single colour. */
trait RectFill extends RectGraphicSimple with RectangleFill

/** Companion object for the RectFill trait, contains a RectFillImp implementation class and an apply method that delegates to it. */
object RectFill
{ /** Factory method for RectFill. A rectangular Graphic aligned to the axes, filled with a single colour. it delegates to the RectfllImp i
   * implementation class, but has a return type of RectFill. */
  def apply(rect: Rect, fillColour: Colour): RectFill = RectFillImp(rect, fillColour)

  /** An implementation class for a [RectFill]] that is not specified as a [[SquareFill]]. */
  case class RectFillImp(shape: Rect, fill: FillFacet) extends RectFill
}

/** A rectangular Graphic aligned to the axes, filled with a single colour. */
trait RectDraw extends RectGraphicSimple with RectangleDraw

/** Companion object for the [[RectDraw]] trait, contains a RectFillImp implementation class and an apply method that delegates to it. */
object RectDraw
{ /** Factory method for RectFill. A rectangular Graphic aligned to the axes, filled with a single colour. it delegates to the RectfllImp i
 * implementation class, but has a return type of RectFill. */
  def apply(rect: Rect, lineWidth: Double = 2, lineColour: Colour = Black): RectDraw = RectDrawImp(rect, lineWidth, lineColour)

  /** An implementation class for a [RectDraw]] that is not specified as a [[SquareDraw]]. */
  case class RectDrawImp(shape: Rect, lineWidth: Double = 2, lineColour: Colour = Black) extends RectDraw
}