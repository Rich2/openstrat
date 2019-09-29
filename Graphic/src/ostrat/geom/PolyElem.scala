/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv.CanvasPlatform, Colour.Black

trait PolyElem extends Any with PaintElem
{
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
}

/** Immutable Graphic element that defines, fills a Polygon. */ 
case class PolyFill(poly: Polygon, colour: Colour) extends PolyElem
{ override def fTrans(f: Vec2 => Vec2): PolyFill = PolyFill(poly.fTrans(f), colour)
  override def rendElem(cp: CanvasPlatform): Unit = cp.polyFill(this)
}

object PolyFill
{ implicit val persistImplicit: Persist2[Polygon, Colour, PolyFill] = Persist2("PolyFill", "poly", _.poly, "colour", _.colour, apply)
}

/** Immutable Graphic element that defines, draws a Polygon. */
case class PolyDraw(poly: Polygon, lineWidth: Double, colour: Colour = Black) extends PolyElem
{ override def fTrans(f: Vec2 => Vec2): PolyDraw = PolyDraw(poly.fTrans(f), lineWidth, colour)
  override def rendElem(cp: CanvasPlatform): Unit = cp.polyDraw(this)
}

case class PolyFillDraw(poly: Polygon, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PolyElem
{ override def fTrans(f: Vec2 => Vec2) = PolyFillDraw(poly.fTrans(f), fillColour, lineWidth, lineColour)
  def noFill: PolyDraw = PolyDraw(poly, lineWidth, lineColour)
  override def rendElem(cp: CanvasPlatform): Unit = cp.polyFillDraw(this)
}

/** A pointable polygon without visual */
case class PolyActive(poly: Polygon, evObj: AnyRef) extends GraphicElem with PolyActiveTr
{ override def fTrans(f: Vec2 => Vec2): PolyActive = PolyActive(poly.fTrans(f), evObj) }

case class PolyFillText(poly: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, textColour: Colour = Black) extends PolyElem
{
  override def fTrans(f: Vec2 => Vec2) = PolyFillText(poly.fTrans(f), fillColour, str,fontSize, textColour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = { cp.polyFill(fillOnly); cp.textGraphic(textOnly) }
  def textOnly: TextGraphic = TextGraphic(str, fontSize, poly.boundingRect.cen, textColour, CenAlign)
  def fillOnly: PolyFill = PolyFill(poly, fillColour)
}

case class PolyFillDrawText(poly: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2,
   lineColour: Colour = Black) extends PolyElem
{
  override def fTrans(f: Vec2 => Vec2) = PolyFillDrawText(poly.fTrans(f), fillColour, str,fontSize, lineWidth, lineColour)
  def drawOnly: PolyDraw = PolyDraw(poly, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, poly.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolyFillDraw = PolyFillDraw(poly, fillColour, lineWidth, lineColour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = { cp.polyFillDraw(fillDrawOnly); cp.textGraphic(textOnly) }
}
