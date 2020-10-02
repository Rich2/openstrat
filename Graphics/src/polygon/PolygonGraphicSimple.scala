/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black, pWeb._

/** This trait may be removed. */
trait PolygonGraphicSimple extends PolygonGraphic with ShapeGraphicSimple
{ //type ThisT <: PolygonGraphicSimple
  //override def shape: Polygon
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

  override def xShear(operand: Double): PolygonGraphicSimple

  override def yShear(operand: Double): PolygonGraphicSimple

  override def reflect(lineLike: LineLike): PolygonGraphicSimple

  override def nonShapeAttribs: Arr[XmlAtt] = ???

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): PolygonGraphicSimple

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): PolygonGraphicSimple

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): PolygonGraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: PolygonGraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: PolygonGraphicSimple

  override def prolign(matrix: ProlignMatrix): PolygonGraphicSimple

  override def rotateRadians(radians: Double): PolygonGraphicSimple

  //override def reflect(lineLike: LineLike): PolygonGraphicSimple

  override def xyScale(xOperand: Double, yOperand: Double): PolygonGraphicSimple

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  override def canEqual(that: Any): Boolean = ???
}



/** Immutable Graphic element that defines and fills a Polygon. This element can be trnsformed through all the Affine transformations and a
 * PolygonFill will be returned. */
trait PolygonFill extends PolygonGraphicSimple with ShapeFill
{ //type ThisT <: PolygonFill
  // override def fTrans(f: Vec2 => Vec2): PolygonFill = PolygonFill(shape.fTrans(f), colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonFill(shape, colour)
  override def toDraw(lineWidth: Double = 2, newColour: Colour = colour): PolygonDraw = shape.draw(lineWidth, newColour)  

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): PolygonFill = PolygonFill(shape.slate(offset), colour)

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): PolygonFill = PolygonFill(shape.slate(xOffset, yOffset), colour)

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): PolygonFill = PolygonFill(shape.scale(operand), colour)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: PolygonFill = PolygonFill(shape.negY, colour)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: PolygonFill = PolygonFill(shape.negX, colour)

  override def prolign(matrix: ProlignMatrix): PolygonFill = PolygonFill(shape.prolign(matrix), colour)

  override def rotateRadians(radians: Double): PolygonFill = PolygonFill(shape.rotateRadians(radians), colour)

  override def xShear(operand: Double): PolygonFill = ???

  override def yShear(operand: Double): PolygonFill = ???

  override def reflect(lineLike: LineLike): PolygonFill = ???

 //S override def reflect(line: Line): PolygonFill = ???

  override def xyScale(xOperand: Double, yOperand: Double): PolygonFill = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  override def canEqual(that: Any): Boolean = ???
}

object PolygonFill
{
  def apply(shape: Polygon, colour: Colour): PolygonFill = new PolygonFillImp(shape, colour)
  /*implicit val persistImplicit: Persist2[Polygon, Colour, PolygonFill] = Persist2("PolyFill", "poly", _.shape, "colour", _.colour, apply)*/

  implicit val slateImplicit: Slate[PolygonFill] = (obj: PolygonFill, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[PolygonFill] = (obj: PolygonFill, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonFill] = (obj: PolygonFill, radians: Double) => obj.rotateRadians(radians)
  implicit val XYScaleImplicit: XYScale[PolygonFill] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[PolygonFill] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: ReflectAxes[PolygonFill] = new ReflectAxes[PolygonFill]
  { override def negYT(obj: PolygonFill): PolygonFill = obj.negY
    override def negXT(obj: PolygonFill): PolygonFill = obj.negX
  }
  
  /** Immutable Graphic element that defines and fills a Polygon. This element can be trnsformed through all the Affine transformations and a
   * PolygonFill will be returned.
   * @constructor create a new PolygonFill with the underlying polygon and a colour.
   * @param shape The Polygon shape.
   * @param colour The colour of this graphic. */
  final case class PolygonFillImp(shape: Polygon, colour: Colour) extends PolygonFill
  { // override type ThisT = PolygonFillImp
   // override def fTrans(f: Vec2 => Vec2): PolygonFillImp = PolygonFillImp(shape.fTrans(f), colour)

    //override def toDraw(lineWidth: Double = 2, newColour: Colour = colour): PolygonDraw = shape.draw(lineWidth, newColour)
  }
}


/** Immutable Graphic element that defines and draws a Polygon. */
trait PolygonDraw extends ShapeDraw with PolygonGraphicSimple
{ //override def fTrans(f: Vec2 => Vec2): PolygonDraw = PolygonDraw(shape.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(shape, lineWidth, lineColour)
}

object PolygonDraw
{
  def apply (shape: Polygon, lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDrawImp(shape, lineWidth, lineColour)
  
  /*implicit val persistImplicit: Persist3[Polygon, Double, Colour, PolygonDraw] =
    Persist3("PolyFill", "poly", _.shape, "lineWidth", _.lineWidth, "colour", _.lineColour, apply)*/

  /** Immutable Graphic element that defines and draws a Polygon. */
  case class PolygonDrawImp(shape: Polygon, lineWidth: Double = 2, lineColour: Colour = Black) extends PolygonDraw with GraphicAffineElem with
    GraphicBoundedAffine
  { override type ThisT = PolygonDrawImp
    override def fTrans(f: Vec2 => Vec2): PolygonDrawImp = PolygonDrawImp(shape.fTrans(f), lineWidth, lineColour)
    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(shape, lineWidth, lineColour)
  }
}

/** An active transparent pointable polygon */
trait PolygonActiveOld extends GraphicActiveOld
{ def shape: Polygon
  override def boundingRect = shape.boundingRect
  override def ptInside(pt: Vec2): Boolean = shape.ptInside(pt)
}

/** A pointable polygon without visual */
case class PolygonActiveOnly(shape: Polygon, pointerId: Any) extends GraphicAffineElem with PolygonActiveOld
{ override type ThisT = PolygonActiveOnly
  override def fTrans(f: Vec2 => Vec2): PolygonActiveOnly = PolygonActiveOnly(shape.fTrans(f), pointerId)
}

/** A pointable polygon without visual */
case class PolygonClickable(shape: Polygon, pointerId: Any) extends GraphicAffineElem with GraphicClickable
{ override type ThisT = PolygonClickable
  override def fTrans(f: Vec2 => Vec2): PolygonClickable = PolygonClickable(shape.fTrans(f), pointerId)
  override def boundingRect = shape.boundingRect
  override def ptInside(pt: Vec2): Boolean = shape.ptInside(pt)
}

case class PolygonFillTextOld(shape: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, textColour: Colour = Black) extends
  PolygonGraphicSimple with GraphicAffineElem with GraphicBoundedAffine
{ override type ThisT = PolygonFillTextOld
  override def fTrans(f: Vec2 => Vec2): PolygonFillTextOld = PolygonFillTextOld(shape.fTrans(f), fillColour, str,fontSize, textColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, textColour, CenAlign)
  def fillOnly: PolygonFill = PolygonFill(shape, fillColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polygonFill(shape, fillColour)
    cp.textGraphic(textOnly)
  }

 // override def attribs: Arr[XmlAtt] = ???
}

case class PolygonFillDrawTextOld(shape: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2,
  lineColour: Colour = Black) extends PolygonGraphicSimple with GraphicAffineElem with GraphicBoundedAffine
{ override type ThisT = PolygonFillDrawTextOld
  override def fTrans(f: Vec2 => Vec2): PolygonFillDrawTextOld = PolygonFillDrawTextOld(shape.fTrans(f), fillColour, str,fontSize, lineWidth, lineColour)
  def drawOnly: PolygonDraw = PolygonDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
 // def fillDrawOnly: PolygonFillDraw = PolygonFillDraw(shape, fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polygonFill(shape, fillColour)
    cp.polygonDraw(shape, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }

 // override def attribs: Arr[XmlAtt] = ???
}

case class PolygonAll(shape: Polygon, pointerId: Any, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2,
                      lineColour: Colour = Black) extends PolygonGraphicSimple with PolygonActiveOld with GraphicAffineElem with GraphicBoundedAffine
{ override type ThisT = PolygonAll
  override def fTrans(f: Vec2 => Vec2): PolygonAll = PolygonAll(shape.fTrans(f), pointerId, fillColour, str, fontSize, lineWidth, lineColour)
  def drawOnly: PolygonDraw = PolygonDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
 // def fillDrawOnly: PolygonFillDraw = PolygonFillDraw(shape, fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.polygonFill(shape, fillColour)
    cp.polygonDraw(shape, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }

//  override def attribs: Arr[XmlAtt] = ???
}

/** A polygon graphic, filled with a uniform colour with text at its centre, that responds actively to mouse trackpad events. */
case class PolygonFillTextActive(shape: Polygon, pointerId: Any, fillColour: Colour, str: String, fontSize: Int = 24) extends PolygonGraphicSimple
  with PolygonActiveOld with GraphicAffineElem with GraphicBoundedAffine
{ override type ThisT = PolygonFillTextActive
  override def fTrans(f: Vec2 => Vec2): PolygonFillTextActive = PolygonFillTextActive(shape.fTrans(f), pointerId, fillColour, str, fontSize)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = { cp.polygonFill(shape, fillColour); cp.textGraphic(textOnly) }

  //override def attribs: Arr[XmlAtt] = ???
}