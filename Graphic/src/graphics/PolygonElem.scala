/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv.CanvasPlatform, Colour.Black

trait PolygonElem extends PaintFullElem with GraphicBoundedFull
{ type ThisT <: PolygonElem
  def poly: Polygon
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
}

/** Immutable Graphic element that defines and fills a Polygon. */
case class PolygonFill(poly: Polygon, colour: Colour) extends PolygonElem
{ override type ThisT = PolygonFill
  override def fTrans(f: Vec2 => Vec2): PolygonFill = PolygonFill(poly.fTrans(f), colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polyFill(poly, colour)
}

object PolygonFill
{ implicit val persistImplicit: Persist2[Polygon, Colour, PolygonFill] = Persist2("PolyFill", "poly", _.poly, "colour", _.colour, apply)
}

/** Immutable Graphic element that defines and fills a Polygon. */
case class PolygonFillActive(poly: Polygon, pointerId: Any, colour: Colour) extends PolygonElem with PolyActive
{ override type ThisT = PolygonFillActive
  override def fTrans(f: Vec2 => Vec2): PolygonFillActive = PolygonFillActive(poly.fTrans(f), pointerId, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polyFill(poly, colour)
}

/** Immutable Graphic element that defines and draws a Polygon. */
case class PolygonDraw(poly: Polygon, lineWidth: Double, colour: Colour = Black) extends PolygonElem
{ override type ThisT = PolygonDraw
  override def fTrans(f: Vec2 => Vec2): PolygonDraw = PolygonDraw(poly.fTrans(f), lineWidth, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polyDraw(poly, lineWidth, colour)
}

object PolygonDraw
{ implicit val persistImplicit: Persist3[Polygon, Double, Colour, PolygonDraw] =
    Persist3("PolyFill", "poly", _.poly, "lineWidth", _.lineWidth, "colour", _.colour, apply)
}

/** Immutable Graphic element that defines, fills and draws a Polygon. */
case class PolygonFillDraw(poly: Polygon, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PolygonElem
{ override type ThisT = PolygonFillDraw
  override def fTrans(f: Vec2 => Vec2): PolygonFillDraw = PolygonFillDraw(poly.fTrans(f), fillColour, lineWidth, lineColour)
  def noFill: PolygonDraw = PolygonDraw(poly, lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = { cp.polyFill(poly, fillColour); cp.polyDraw(poly, lineWidth, lineColour) }
}

object PolygonFillDraw
{ implicit val persistImplicit: Persist4[Polygon, Colour, Double, Colour, PolygonFillDraw] =
    Persist4("PolyFill", "poly", _.poly, "fillColour", _.fillColour, "lineWidth", _.lineWidth, "lineColour", _.lineColour, apply)
}

/** A pointable polygon without visual */
case class PolygonActiveOnly(poly: Polygon, pointerId: Any) extends GraphicFullElem with PolyActive
{ override type ThisT = PolygonActiveOnly
  override def fTrans(f: Vec2 => Vec2): PolygonActiveOnly = PolygonActiveOnly(poly.fTrans(f), pointerId)
}

case class PolygonFillText(poly: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, textColour: Colour = Black) extends PolygonElem
{ override type ThisT = PolygonFillText
  override def fTrans(f: Vec2 => Vec2): PolygonFillText = PolygonFillText(poly.fTrans(f), fillColour, str,fontSize, textColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, poly.boundingRect.cen, textColour, CenAlign)
  def fillOnly: PolygonFill = PolygonFill(poly, fillColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polyFill(poly, fillColour)
    cp.textGraphic(textOnly)
  }
}

case class PolygonFillDrawText(poly: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2, lineColour: Colour = Black)
  extends PolygonElem
{ override type ThisT = PolygonFillDrawText
  override def fTrans(f: Vec2 => Vec2): PolygonFillDrawText = PolygonFillDrawText(poly.fTrans(f), fillColour, str,fontSize, lineWidth, lineColour)
  def drawOnly: PolygonDraw = PolygonDraw(poly, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, poly.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolygonFillDraw = PolygonFillDraw(poly, fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polyFill(poly, fillColour)
    cp.polyDraw(poly, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }
}

case class PolygonAll(poly: Polygon, pointerId: Any, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2,
  lineColour: Colour = Black) extends PolygonElem with PolyActive
{ override type ThisT = PolygonAll
  override def fTrans(f: Vec2 => Vec2): PolygonAll = PolygonAll(poly.fTrans(f), pointerId, fillColour, str, fontSize, lineWidth, lineColour)
  def drawOnly: PolygonDraw = PolygonDraw(poly, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, poly.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolygonFillDraw = PolygonFillDraw(poly, fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polyFill(poly, fillColour)
    cp.polyDraw(poly, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }
}

object PolygonFillDrawText
{ implicit val persistImplicit: Persist6[Polygon, Colour, String, Int, Double, Colour, PolygonFillDrawText] =
  Persist6("PolyFill", "poly", _.poly, "fillColour", _.fillColour, "str", _.str, "fontSize", _.fontSize, "lineWidth", _.lineWidth,
    "lineColour", _.lineColour, apply)
}

case class PolygonFillTextActive(poly: Polygon, pointerId: Any, fillColour: Colour, str: String, fontSize: Int = 24) extends PolygonElem
  with PolyActive
{ override type ThisT = PolygonFillTextActive
  override def fTrans(f: Vec2 => Vec2): PolygonFillTextActive = PolygonFillTextActive(poly.fTrans(f), pointerId, fillColour, str, fontSize)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, poly.boundingRect.cen, Black, CenAlign)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = { cp.polyFill(poly, fillColour); cp.textGraphic(textOnly) }
}