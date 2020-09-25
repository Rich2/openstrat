/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

/** Polygon based Graphic class that constains a number of child Graphic Elements. */
case class PolygonParentOld(cen: Vec2, poly: PolygonGen, pointerId: Any, children: Arr[GraphicElem]) extends GraphicParentOld with GraphicActiveOld
{ type ThisT = PolygonParentOld
  override def ptInside(pt: Vec2): Boolean = poly.ptInside(pt)
  override def addElems(newElems: Arr[GraphicElem]): PolygonParentOld = new PolygonParentOld(cen, poly, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolygonParentOld = new PolygonParentOld(cen, poly, newObj, children)
  override def boundingRect: BoundingRect = poly.boundingRect
  def reflectXParallel(yOffset: Double): PolygonParentOld =
    PolygonParentOld(cen.reflectXOffset(yOffset), poly.reflectXParallel(yOffset), pointerId, children.reflectXOffset(yOffset))

  def reflectYParallel(xOffset: Double): PolygonParentOld =
    PolygonParentOld(cen.reflectYOffset(xOffset), poly.reflectYParallel(xOffset), pointerId, children.reflectYOffset(xOffset))

  def rotateRadians(radians: Double): PolygonParentOld =
    PolygonParentOld(cen.rotateRadians(radians), poly.rotateRadians(radians), pointerId, children.rotateRadians(radians))

  def slate(offset: Vec2): PolygonParentOld = PolygonParentOld(cen + offset, poly.slate(offset), pointerId, children.slate(offset))

  def slate(xOffset: Double, yOffset: Double): PolygonParentOld = slate(xOffset vv yOffset)

  def scale(operand: Double): PolygonParentOld = PolygonParentOld(cen * operand, poly.scale(operand), pointerId, children.scale(operand))

  override def xShear(operand: Double): TransElem = ???

  override def yShear(operand: Double): TransElem = ???

  def reflect(line: LineSeg): PolygonParentOld = ??? // PolygonParent(cen.mirror(line), poly.mirror(line), pointerId, children.mirror(line))
  def reflect(line: Line): PolygonParentOld = ???
  override def reflectX: PolygonParentOld = PolygonParentOld(cen.reflectX, poly.reflectX, pointerId, children.reflectX)
  override def reflectY: PolygonParentOld = PolygonParentOld(cen.reflectY, poly.reflectY, pointerId, children.reflectY)

  override def prolign(matrix: ProlignMatrix): PolygonParentOld = PolygonParentOld(cen.prolign(matrix), poly.prolign(matrix), pointerId, children.prolign(matrix))

  override def xyScale(xOperand: Double, yOperand: Double): PolygonParentOld = ???
}

object PolygonParentOld
{
  def fill(cen: Vec2, poly: PolygonGen, evObj: Any, colour: Colour): PolygonParentOld = new PolygonParentOld(cen, poly, evObj, Arr(poly.fill(colour)))

  def draw(cen: Vec2, poly: PolygonGen, evObj: Any, lineWidth: Double, lineColour: Colour = Black): PolygonParentOld =
    new PolygonParentOld(cen, poly, evObj, Arr(PolygonDraw(poly, lineWidth, lineColour)))

  /** Not sure if this is double filling the polygon */
  def fillDraw(cen: Vec2, poly: PolygonGen, evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): PolygonParentOld =
    new PolygonParentOld(cen, poly, evObj, Arr(PolygonFill(poly, fillColour), PolygonDraw(poly, lineWidth, lineColour)))

  def fillText(cen: Vec2, poly: PolygonGen, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4, fontColour: Colour = Colour.Black,
               align: TextAlign = CenAlign): PolygonParentOld =
    new PolygonParentOld(cen, poly, evObj, Arr(poly.fill(fillColour), TextGraphic(str, fontSize, poly.polyCentre, fontColour, align)))

  def fillContrastText(cen: Vec2, poly: PolygonGen, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4): PolygonParentOld =
    fillText(cen, poly, evObj, fillColour, str, fontSize, fillColour.contrast)
}