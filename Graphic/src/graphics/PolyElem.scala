/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv.CanvasPlatform, Colour.Black

trait PolyElem extends PaintElem with GraphicBounded
{ def poly: Polygon
  def xHead: Double = poly.head1
  def yHead: Double = poly.head2

  /** The number of vertices. */
  def vertsLen: Int = poly.length

  /** Checks for 2 or more vertices */
  def ifv2: Boolean = poly.length >= 2

  /** Checks for 3 or more vertices */
  def ifv3: Boolean = poly.length >= 3

  def xArray: Array[Double] = poly.elem1sArray
  def yArray: Array[Double] = poly.elem2sArray

  override def boundingRect: BoundingRect = poly.boundingRect
  override def fTrans(f: Vec2 => Vec2): PolyElem
}

/** Immutable Graphic element that defines and fills a Polygon. */
case class PolyFill(poly: Polygon, colour: Colour) extends PolyElem
{ override def fTrans(f: Vec2 => Vec2): PolyFill = PolyFill(poly.fTrans(f), colour)
  override def rendElem(cp: CanvasPlatform): Unit = cp.polyFill(poly, colour)
}

object PolyFill
{ implicit val persistImplicit: Persist2[Polygon, Colour, PolyFill] = Persist2("PolyFill", "poly", _.poly, "colour", _.colour, apply)
}

/** Immutable Graphic element that defines and fills a Polygon. */
case class PolyFillActive(poly: Polygon, pointerId: Any, colour: Colour) extends PolyElem with PolyActive
{ override def fTrans(f: Vec2 => Vec2): PolyFillActive = PolyFillActive(poly.fTrans(f), pointerId, colour)
  override def rendElem(cp: CanvasPlatform): Unit = cp.polyFill(poly, colour)
}

/** Immutable Graphic element that defines and draws a Polygon. */
case class PolyDraw(poly: Polygon, lineWidth: Double, colour: Colour = Black) extends PolyElem
{ override def fTrans(f: Vec2 => Vec2): PolyDraw = PolyDraw(poly.fTrans(f), lineWidth, colour)
  override def rendElem(cp: CanvasPlatform): Unit = cp.polyDraw(poly, lineWidth, colour)
}

object PolyDraw
{ implicit val persistImplicit: Persist3[Polygon, Double, Colour, PolyDraw] = Persist3("PolyFill", "poly", _.poly, "lineWidth", _.lineWidth, "colour", _.colour, apply)
}

/** Immutable Graphic element that defines, fills and draws a Polygon. */
case class PolyFillDraw(poly: Polygon, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PolyElem
{ override def fTrans(f: Vec2 => Vec2): PolyFillDraw = PolyFillDraw(poly.fTrans(f), fillColour, lineWidth, lineColour)
  def noFill: PolyDraw = PolyDraw(poly, lineWidth, lineColour)
  override def rendElem(cp: CanvasPlatform): Unit = { cp.polyFill(poly, fillColour); cp.polyDraw(poly, lineWidth, lineColour) }
}

object PolyFillDraw
{ implicit val persistImplicit: Persist4[Polygon, Colour, Double, Colour, PolyFillDraw] =
    Persist4("PolyFill", "poly", _.poly, "fillColour", _.fillColour, "lineWidth", _.lineWidth, "lineColour", _.lineColour, apply)
}

/** A pointable polygon without visual */
case class PolyActiveOnly(poly: Polygon, pointerId: Any) extends GraphicElem with PolyActive
{ override def fTrans(f: Vec2 => Vec2): PolyActiveOnly = PolyActiveOnly(poly.fTrans(f), pointerId) }

case class PolyFillText(poly: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, textColour: Colour = Black) extends PolyElem
{ override def fTrans(f: Vec2 => Vec2): PolyFillText = PolyFillText(poly.fTrans(f), fillColour, str,fontSize, textColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, poly.boundingRect.cen, textColour, CenAlign)
  def fillOnly: PolyFill = PolyFill(poly, fillColour)

  override def rendElem(cp: pCanv.CanvasPlatform): Unit =
  { cp.polyFill(poly, fillColour)
    cp.textGraphic(textOnly)
  }
}

case class PolyFillDrawText(poly: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2,
   lineColour: Colour = Black) extends PolyElem
{
  override def fTrans(f: Vec2 => Vec2): PolyFillDrawText = PolyFillDrawText(poly.fTrans(f), fillColour, str,fontSize, lineWidth, lineColour)
  def drawOnly: PolyDraw = PolyDraw(poly, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, poly.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolyFillDraw = PolyFillDraw(poly, fillColour, lineWidth, lineColour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = { cp.polyFill(poly, fillColour); cp.polyDraw(poly, lineWidth, lineColour); cp.textGraphic(textOnly) }
}

case class PolyAll(poly: Polygon, pointerId: Any, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2,
                   lineColour: Colour = Black) extends PolyElem with PolyActive
{
  override def fTrans(f: Vec2 => Vec2): PolyAll = PolyAll(poly.fTrans(f), pointerId, fillColour, str, fontSize, lineWidth, lineColour)
  def drawOnly: PolyDraw = PolyDraw(poly, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, poly.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolyFillDraw = PolyFillDraw(poly, fillColour, lineWidth, lineColour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = { cp.polyFill(poly, fillColour); cp.polyDraw(poly, lineWidth, lineColour); cp.textGraphic(textOnly) }
}

object PolyFillDrawText
{ implicit val persistImplicit: Persist6[Polygon, Colour, String, Int, Double, Colour, PolyFillDrawText] =
  Persist6("PolyFill", "poly", _.poly, "fillColour", _.fillColour, "str", _.str, "fontSize", _.fontSize, "lineWidth", _.lineWidth,
    "lineColour", _.lineColour, apply)
}