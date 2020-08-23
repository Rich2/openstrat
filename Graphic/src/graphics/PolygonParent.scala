/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

/** Polygon based Graphic class that constains a number of child Graphic Elements. */
case class PolygonParent(cen: Vec2, poly: Polygon, pointerId: Any, children: Arr[DisplayElem]) extends DisplayParent with DisplayActive
{ type ThisT = PolygonParent
  override def ptInside(pt: Vec2): Boolean = poly.ptInside(pt)
  override def addElems(newElems: Arr[DisplayElem]): PolygonParent = new PolygonParent(cen, poly, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolygonParent = new PolygonParent(cen, poly, newObj, children)
  override def boundingRect: BoundingRect = poly.boundingRect
  def reflectXOffset(yOffset: Double): PolygonParent =
    PolygonParent(cen.reflectXOffset(yOffset), poly.reflectXOffset(yOffset), pointerId, children.mirrorXOffset(yOffset))

  def reflectYOffset(xOffset: Double): PolygonParent =
    PolygonParent(cen.reflectYOffset(xOffset), poly.reflectYOffset(xOffset), pointerId, children.mirrorYOffset(xOffset))

  def rotateRadians(radians: Double): PolygonParent =
    PolygonParent(cen.rotateRadians(radians), poly.rotateRadians(radians), pointerId, children.rotateRadians(radians))

  def slate(offset: Vec2): PolygonParent = PolygonParent(cen + offset, poly.slate(offset), pointerId, children.slate(offset))

  def slate(xOffset: Double, yOffset: Double): PolygonParent = slate(xOffset vv yOffset)

  def scale(operand: Double): PolygonParent = PolygonParent(cen * operand, poly.scale(operand), pointerId, children.scale(operand))

  override def shearX(operand: Double): TransElem = ???

  override def shearY(operand: Double): TransElem = ???

  def reflect(line: Sline): PolygonParent = ??? // PolygonParent(cen.mirror(line), poly.mirror(line), pointerId, children.mirror(line))
  def reflect(line: Line): PolygonParent = ???
  override def reflectX: PolygonParent = PolygonParent(cen.reflectX, poly.reflectX, pointerId, children.mirrorX)
  override def reflectY: PolygonParent = PolygonParent(cen.reflectY, poly.reflectY, pointerId, children.mirrorY)

  override def prolign(matrix: ProlignMatrix): PolygonParent = PolygonParent(cen.prolign(matrix), poly.prolign(matrix), pointerId, children.prolign(matrix))

  override def rotate90: PolygonParent = PolygonParent(cen.rotate90, poly.rotate90, pointerId, children.rotate90)

  override def rotate180: PolygonParent = ???

  override def rotate270: PolygonParent = ???

  override def scaleXY(xOperand: Double, yOperand: Double): PolygonParent = ???
}

object PolygonParent
{
  def fill(cen: Vec2, poly: Polygon, evObj: Any, colour: Colour): PolygonParent = new PolygonParent(cen, poly, evObj, Arr(poly.fillOld(colour)))

  def draw(cen: Vec2, poly: Polygon, evObj: Any, lineWidth: Double, lineColour: Colour = Black): PolygonParent =
    new PolygonParent(cen, poly, evObj, Arr(PolygonDraw(poly, lineWidth, lineColour)))

  /** Not sure if this is double filling the polygon */
  def fillDraw(cen: Vec2, poly: Polygon, evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): PolygonParent =
    new PolygonParent(cen, poly, evObj, Arr(PolygonFillDraw(poly, fillColour, lineWidth, lineColour)))


  def fillText(cen: Vec2, poly: Polygon, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4, fontColour: Colour = Colour.Black,
               align: TextAlign = CenAlign): PolygonParent =
    new PolygonParent(cen, poly, evObj, Arr(poly.fillOld(fillColour), TextGraphic(str, fontSize, poly.polyCentre, fontColour, align)))

  def fillContrastText(cen: Vec2, poly: Polygon, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4): PolygonParent =
    fillText(cen, poly, evObj, fillColour, str, fontSize, fillColour.contrast)
}