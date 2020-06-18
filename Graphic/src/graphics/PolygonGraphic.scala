/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black, pXml._

trait PolygonGraphic extends GraphicFullElem with DisplayBoundedFull with ShapeGraphic
{ type ThisT <: PolygonGraphic
  override def shape: Polygon
  def xHead: Double = shape.x0
  def yHead: Double = shape.y0

  /** The number of vertices. */
  def vertsLen: Int = shape.length

  /** Checks for 2 or more vertices */
  def ifv2: Boolean = shape.length >= 2

  /** Checks for 3 or more vertices */
  def ifv3: Boolean = shape.length >= 3

  def xArray: Array[Double] = shape.elem1sArray
  def yArray: Array[Double] = shape.elem2sArray

  override def boundingRect: BoundingRect = shape.boundingRect
  
  

  def svgStr: String = closedTagStr("rect", attribs)
}

/** Immutable Graphic element that defines and fills a Polygon. */
case class PolygonFill(shape: PolygonClass, fillColour: Colour) extends PolygonGraphic with ShapeFill
{ override type ThisT = PolygonFill
  override def fTrans(f: Vec2 => Vec2): PolygonFill = PolygonFill(shape.fTrans(f), fillColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polyFill(shape, fillColour)

  override def attribs: Arr[Attrib] = ???
}

object PolygonFill
{ implicit val persistImplicit: Persist2[PolygonClass, Colour, PolygonFill] = Persist2("PolyFill", "poly", _.shape, "colour", _.fillColour, apply)
}

/** Immutable Graphic element that defines and fills a Polygon. */
case class PolygonFillActive(shape: PolygonClass, pointerId: Any, colour: Colour) extends PolygonGraphic with PolyActiveFull
{ override type ThisT = PolygonFillActive
  override def fTrans(f: Vec2 => Vec2): PolygonFillActive = PolygonFillActive(shape.fTrans(f), pointerId, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polyFill(shape, colour)

  override def attribs: Arr[Attrib] = ???  
}

/** Immutable Graphic element that defines and draws a Polygon. */
case class PolygonDraw(shape: PolygonClass, lineWidth: Double, lineColour: Colour = Black) extends PolygonGraphic with ShapeDraw
{ override type ThisT = PolygonDraw
  override def fTrans(f: Vec2 => Vec2): PolygonDraw = PolygonDraw(shape.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polyDraw(shape, lineWidth, lineColour)

  override def attribs: Arr[Attrib] = ???
}

object PolygonDraw
{ implicit val persistImplicit: Persist3[PolygonClass, Double, Colour, PolygonDraw] =
    Persist3("PolyFill", "poly", _.shape, "lineWidth", _.lineWidth, "colour", _.lineColour, apply)
}

/** Immutable Graphic element that defines, fills and draws a Polygon. */
case class PolygonFillDraw(shape: PolygonClass, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PolygonGraphic
{ override type ThisT = PolygonFillDraw
  override def fTrans(f: Vec2 => Vec2): PolygonFillDraw = PolygonFillDraw(shape.fTrans(f), fillColour, lineWidth, lineColour)
  def noFill: PolygonDraw = PolygonDraw(shape, lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = { cp.polyFill(shape, fillColour); cp.polyDraw(shape, lineWidth, lineColour) }

  override def attribs: Arr[Attrib] = ???
}

object PolygonFillDraw
{ implicit val persistImplicit: Persist4[PolygonClass, Colour, Double, Colour, PolygonFillDraw] =
    Persist4("PolyFill", "poly", _.shape, "fillColour", _.fillColour, "lineWidth", _.lineWidth, "lineColour", _.lineColour, apply)
}

/** A pointable polygon without visual */
case class PolygonActiveOnly(shape: PolygonClass, pointerId: Any) extends DisplayAffineElem with PolyActiveFull
{ override type ThisT = PolygonActiveOnly
  override def fTrans(f: Vec2 => Vec2): PolygonActiveOnly = PolygonActiveOnly(shape.fTrans(f), pointerId)
}

case class PolygonFillText(shape: PolygonClass, fillColour: Colour, str: String, fontSize: Int = 24, textColour: Colour = Black) extends PolygonGraphic
{ override type ThisT = PolygonFillText
  override def fTrans(f: Vec2 => Vec2): PolygonFillText = PolygonFillText(shape.fTrans(f), fillColour, str,fontSize, textColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, textColour, CenAlign)
  def fillOnly: PolygonFill = PolygonFill(shape, fillColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polyFill(shape, fillColour)
    cp.textGraphic(textOnly)
  }

  override def attribs: Arr[Attrib] = ???
}

case class PolygonFillDrawText(shape: PolygonClass, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2, lineColour: Colour = Black)
  extends PolygonGraphic
{ override type ThisT = PolygonFillDrawText
  override def fTrans(f: Vec2 => Vec2): PolygonFillDrawText = PolygonFillDrawText(shape.fTrans(f), fillColour, str,fontSize, lineWidth, lineColour)
  def drawOnly: PolygonDraw = PolygonDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolygonFillDraw = PolygonFillDraw(shape, fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polyFill(shape, fillColour)
    cp.polyDraw(shape, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }

  override def attribs: Arr[Attrib] = ???
}

case class PolygonAll(shape: PolygonClass, pointerId: Any, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2,
                      lineColour: Colour = Black) extends PolygonGraphic with PolyActiveFull
{ override type ThisT = PolygonAll
  override def fTrans(f: Vec2 => Vec2): PolygonAll = PolygonAll(shape.fTrans(f), pointerId, fillColour, str, fontSize, lineWidth, lineColour)
  def drawOnly: PolygonDraw = PolygonDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolygonFillDraw = PolygonFillDraw(shape, fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polyFill(shape, fillColour)
    cp.polyDraw(shape, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }

  override def attribs: Arr[Attrib] = ???
}

object PolygonFillDrawText
{ implicit val persistImplicit: Persist6[PolygonClass, Colour, String, Int, Double, Colour, PolygonFillDrawText] =
  Persist6("PolyFill", "poly", _.shape, "fillColour", _.fillColour, "str", _.str, "fontSize", _.fontSize, "lineWidth", _.lineWidth,
    "lineColour", _.lineColour, apply)
}

case class PolygonFillTextActive(shape: PolygonClass, pointerId: Any, fillColour: Colour, str: String, fontSize: Int = 24) extends PolygonGraphic
  with PolyActiveFull
{ override type ThisT = PolygonFillTextActive
  override def fTrans(f: Vec2 => Vec2): PolygonFillTextActive = PolygonFillTextActive(shape.fTrans(f), pointerId, fillColour, str, fontSize)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = { cp.polyFill(shape, fillColour); cp.textGraphic(textOnly) }

  override def attribs: Arr[Attrib] = ???
}