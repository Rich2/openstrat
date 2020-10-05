/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

/** Polygon based Graphic class that constains a number of child Graphic Elements. */
case class PolygonParentOld(cen: Vec2, poly: PolygonImp, pointerId: Any, children: Arr[GraphicElem]) extends GraphicParentOld with GraphicActiveOld
{ type ThisT = PolygonParentOld
  override def ptInside(pt: Vec2): Boolean = poly.ptInside(pt)
  override def addElems(newElems: Arr[GraphicElem]): PolygonParentOld = new PolygonParentOld(cen, poly, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolygonParentOld = new PolygonParentOld(cen, poly, newObj, children)
  override def boundingRect: BoundingRect = poly.boundingRect

  def rotate(angle: Angle): PolygonParentOld = PolygonParentOld(cen.rotate(angle), poly.rotate(angle), pointerId, children.rotate(angle))

  def slate(offset: Vec2): PolygonParentOld = PolygonParentOld(cen + offset, poly.slate(offset), pointerId, children.slate(offset))

  def slate(xOffset: Double, yOffset: Double): PolygonParentOld = slate(xOffset vv yOffset)

  def scale(operand: Double): PolygonParentOld = PolygonParentOld(cen * operand, poly.scale(operand), pointerId, children.scale(operand))

  override def xShear(operand: Double): BoundedGraphic = ???

  override def yShear(operand: Double): BoundedGraphic = ???

  def reflect(lineLike: LineLike): PolygonParentOld = ??? // PolygonParent(cen.mirror(line), poly.mirror(line), pointerId, children.mirror(line))
  override def negY: PolygonParentOld = PolygonParentOld(cen.negY, poly.negY, pointerId, children.negY)
  override def negX: PolygonParentOld = PolygonParentOld(cen.negX, poly.negX, pointerId, children.negX)


  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return
   * type will be narrowed in sub traits / classes. */
  override def rotate90: PolygonParentOld = PolygonParentOld(cen.rotate90, poly.rotate90, pointerId, children.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a PolygonParentOld, returns a PolygonParentOld. The return type will be narrowed in sub traits
   *  / classes. */
  override def rotate180: PolygonParentOld = PolygonParentOld(cen.rotate180, poly.rotate180, pointerId, children.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a PolygonParentOld, returns a PolygonParentOld.
   *  The return type will be narrowed in sub traits / classes. */
  override def rotate270: PolygonParentOld = PolygonParentOld(cen.rotate270, poly.rotate270, pointerId, children.rotate270)

  override def prolign(matrix: ProlignMatrix): PolygonParentOld = PolygonParentOld(cen.prolign(matrix), poly.prolign(matrix), pointerId, children.prolign(matrix))

  override def xyScale(xOperand: Double, yOperand: Double): PolygonParentOld = ???
}

object PolygonParentOld
{
  def fill(cen: Vec2, poly: PolygonImp, evObj: Any, colour: Colour): PolygonParentOld = new PolygonParentOld(cen, poly, evObj, Arr(poly.fill(colour)))

  def draw(cen: Vec2, poly: PolygonImp, evObj: Any, lineWidth: Double, lineColour: Colour = Black): PolygonParentOld =
    new PolygonParentOld(cen, poly, evObj, Arr(PolygonDraw(poly, lineWidth, lineColour)))

  /** Not sure if this is double filling the polygon */
  def fillDraw(cen: Vec2, poly: PolygonImp, evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): PolygonParentOld =
    new PolygonParentOld(cen, poly, evObj, Arr(PolygonFill(poly, fillColour), PolygonDraw(poly, lineWidth, lineColour)))

  def fillText(cen: Vec2, poly: PolygonImp, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4, fontColour: Colour = Colour.Black,
               align: TextAlign = CenAlign): PolygonParentOld =
    new PolygonParentOld(cen, poly, evObj, Arr(poly.fill(fillColour), TextGraphic(str, fontSize, poly.polyCentre, fontColour, align)))

  def fillContrastText(cen: Vec2, poly: PolygonImp, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4): PolygonParentOld =
    fillText(cen, poly, evObj, fillColour, str, fontSize, fillColour.contrast)
}