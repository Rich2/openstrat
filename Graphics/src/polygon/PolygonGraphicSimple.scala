/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black, pWeb._

/** This trait may be removed. */
trait PolygonGraphicSimple extends PolygonGraphic with GraphicAffineElem with GraphicBoundedAffine
{ type ThisT <: PolygonGraphicSimple
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
  def svgStr: String = tagVoidStr("rect", attribs)
  override def svgElem(bounds: BoundingRect): SvgElem = ???
}

/** An active transparent pointable polygon */
trait PolygonActive extends GraphicActive
{ def shape: Polygon
  override def boundingRect = shape.boundingRect
  override def ptInside(pt: Vec2): Boolean = shape.ptInside(pt)
}

/** Immutable Graphic element that defines and fills a Polygon. This element can be trnsformed through all the Affine transformations and a
 * PolygonFill will be returned.
 * @constructor create a new PolygonFill with the underlying polygon and a colour.
 * @param shape The Polygon shape.
 * @param colour The colour of this graphic. */
final case class PolygonFill(shape: Polygon, colour: Colour) extends PolygonGraphicSimple with ShapeFill
{ override type ThisT = PolygonFill
  override def fTrans(f: Vec2 => Vec2): PolygonFill = PolygonFill(shape.fTrans(f), colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polyFill(shape, colour)
  override def toDraw(lineWidth: Double = 2, newColour: Colour = colour): PolygonDraw = shape.draw(lineWidth, newColour)
}

object PolygonFill
{ implicit val persistImplicit: Persist2[Polygon, Colour, PolygonFill] = Persist2("PolyFill", "poly", _.shape, "colour", _.colour, apply)
}

/** Immutable Graphic element that defines and fills a Polygon. */
case class PolygonFillActive(shape: PolygonGen, pointerId: Any, colour: Colour) extends PolygonGraphicSimple with PolygonActive
{ override type ThisT = PolygonFillActive
  override def fTrans(f: Vec2 => Vec2): PolygonFillActive = PolygonFillActive(shape.fTrans(f), pointerId, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polyFill(shape, colour)
  override def attribs: Arr[XmlAtt] = ???
}

/** Immutable Graphic element that defines and draws a Polygon. */
case class PolygonDraw(shape: Polygon, lineWidth: Double = 2, lineColour: Colour = Black) extends PolygonGraphicSimple with ShapeDraw
{ override type ThisT = PolygonDraw
  override def fTrans(f: Vec2 => Vec2): PolygonDraw = PolygonDraw(shape.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polyDraw(shape, lineWidth, lineColour)
}

object PolygonDraw
{ implicit val persistImplicit: Persist3[Polygon, Double, Colour, PolygonDraw] =
    Persist3("PolyFill", "poly", _.shape, "lineWidth", _.lineWidth, "colour", _.lineColour, apply)
}

/** Immutable Graphic element that defines, fills and draws a Polygon. */
/*case class PolygonFillDraw(shape: Polygon, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PolygonGraphicSimple
{ override type ThisT = PolygonFillDraw
  override def fTrans(f: Vec2 => Vec2): PolygonFillDraw = PolygonFillDraw(shape.fTrans(f), fillColour, lineWidth, lineColour)
  def noFill: PolygonDraw = PolygonDraw(shape, lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = { cp.polyFill(shape, fillColour); cp.polyDraw(shape, lineWidth, lineColour) }

  override def attribs: Arr[XmlAtt] = ???
}*/

/*object PolygonFillDraw
{ implicit val persistImplicit: Persist4[Polygon, Colour, Double, Colour, PolygonFillDraw] =
    Persist4("PolyFill", "poly", _.shape, "fillColour", _.fillColour, "lineWidth", _.lineWidth, "lineColour", _.lineColour, apply)
}*/

/** A pointable polygon without visual */
case class PolygonActiveOnly(shape: Polygon, pointerId: Any) extends GraphicAffineElem with PolygonActive
{ override type ThisT = PolygonActiveOnly
  override def fTrans(f: Vec2 => Vec2): PolygonActiveOnly = PolygonActiveOnly(shape.fTrans(f), pointerId)
}

case class PolygonFillTextOld(shape: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, textColour: Colour = Black) extends
  PolygonGraphicSimple
{ override type ThisT = PolygonFillTextOld
  override def fTrans(f: Vec2 => Vec2): PolygonFillTextOld = PolygonFillTextOld(shape.fTrans(f), fillColour, str,fontSize, textColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, textColour, CenAlign)
  def fillOnly: PolygonFill = PolygonFill(shape, fillColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polyFill(shape, fillColour)
    cp.textGraphic(textOnly)
  }

  override def attribs: Arr[XmlAtt] = ???
}

case class PolygonFillDrawTextOld(shape: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2,
  lineColour: Colour = Black) extends PolygonGraphicSimple
{ override type ThisT = PolygonFillDrawTextOld
  override def fTrans(f: Vec2 => Vec2): PolygonFillDrawTextOld = PolygonFillDrawTextOld(shape.fTrans(f), fillColour, str,fontSize, lineWidth, lineColour)
  def drawOnly: PolygonDraw = PolygonDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
 // def fillDrawOnly: PolygonFillDraw = PolygonFillDraw(shape, fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polyFill(shape, fillColour)
    cp.polyDraw(shape, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }

  override def attribs: Arr[XmlAtt] = ???
}

case class PolygonAll(shape: Polygon, pointerId: Any, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2,
                      lineColour: Colour = Black) extends PolygonGraphicSimple with PolygonActive
{ override type ThisT = PolygonAll
  override def fTrans(f: Vec2 => Vec2): PolygonAll = PolygonAll(shape.fTrans(f), pointerId, fillColour, str, fontSize, lineWidth, lineColour)
  def drawOnly: PolygonDraw = PolygonDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
 // def fillDrawOnly: PolygonFillDraw = PolygonFillDraw(shape, fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polyFill(shape, fillColour)
    cp.polyDraw(shape, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }

  override def attribs: Arr[XmlAtt] = ???
}

object PolygonFillDrawTextOld
{ implicit val persistImplicit: Persist6[Polygon, Colour, String, Int, Double, Colour, PolygonFillDrawTextOld] =
  Persist6("PolyFill", "poly", _.shape, "fillColour", _.fillColour, "str", _.str, "fontSize", _.fontSize, "lineWidth", _.lineWidth,
    "lineColour", _.lineColour, apply)
}

/** A polygon graphic, filled with a uniform colour with text at its centre, that responds actively to mouse trackpad events. */
case class PolygonFillTextActive(shape: Polygon, pointerId: Any, fillColour: Colour, str: String, fontSize: Int = 24) extends PolygonGraphicSimple
  with PolygonActive
{ override type ThisT = PolygonFillTextActive
  override def fTrans(f: Vec2 => Vec2): PolygonFillTextActive = PolygonFillTextActive(shape.fTrans(f), pointerId, fillColour, str, fontSize)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = { cp.polyFill(shape, fillColour); cp.textGraphic(textOnly) }

  override def attribs: Arr[XmlAtt] = ???
}