/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black, pWeb._

/** This trait may be removed. */
trait PolygonGraphicSimple extends PolygonGraphic with ShapeGraphicSimple
{ def xHead: Double = shape.x0
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

  override def xyScale(xOperand: Double, yOperand: Double): PolygonGraphicSimple
}

object PolygonGraphicSimple
{
  implicit val slateImplicit: Slate[PolygonGraphicSimple] = (obj: PolygonGraphicSimple, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[PolygonGraphicSimple] = (obj: PolygonGraphicSimple, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonGraphicSimple] = (obj: PolygonGraphicSimple, radians: Double) => obj.rotateRadians(radians)
  implicit val XYScaleImplicit: XYScale[PolygonGraphicSimple] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[PolygonGraphicSimple] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: ReflectAxes[PolygonGraphicSimple] = new ReflectAxes[PolygonGraphicSimple]
  { override def negYT(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.negY
    override def negXT(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.negX
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