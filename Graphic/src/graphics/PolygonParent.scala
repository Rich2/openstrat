/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** Polygon based Graphic class that constains a number of child Graphic Elements. */
case class PolygonParentFull(cen: Vec2, poly: PolygonGen, pointerId: Any, children: Arr[PaintFullElem]) extends GraphicParentFull with PolyActiveFull
{ type AlignT = PolygonParentFull
  def fTrans(f: Vec2 => Vec2): PolygonParentFull = new PolygonParentFull(f(cen), poly.fTrans(f), pointerId, children.trans(f))
  override def addElems(newElems: Arr[PaintFullElem]): PolygonParentFull = new PolygonParentFull(cen, poly, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolygonParentFull = new PolygonParentFull(cen, poly, newObj, children)
}

/** Companion object of the PolygonParent case class. */
object PolygonParentFull
{
  def fill(cen: Vec2, poly: PolygonGen, evObj: Any, colour: Colour): PolygonParentFull = new PolygonParentFull(cen, poly, evObj, Arr(poly.fill(colour)))

  /** Not sure if this is double filling the polygon */
  def fillDraw(cen: Vec2, poly: PolygonGen, evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): PolygonParentFull =
    new PolygonParentFull(cen, poly, evObj, Arr(PolygonFillDraw(poly, fillColour, lineWidth, lineColour)))

  def draw(cen: Vec2, poly: PolygonGen, evObj: Any, lineWidth: Double, lineColour: Colour = Black): PolygonParentFull =
    new PolygonParentFull(cen, poly, evObj, Arr(PolygonDraw(poly, lineWidth, lineColour)))

  def fillText(cen: Vec2, poly: PolygonGen, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4, fontColour: Colour = Colour.Black,
               align: TextAlign = CenAlign): PolygonParentFull =
    new PolygonParentFull(cen, poly, evObj, Arr(poly.fill(fillColour), TextGraphic(str, fontSize, poly.polyCentre, fontColour, align)))

  def fillContrastText(cen: Vec2, poly: PolygonGen, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4): PolygonParentFull =
    fillText(cen, poly, evObj, fillColour, str, fontSize, fillColour.contrast)
}

/** Polygon based Graphic class that constains a number of child Graphic Elements. */
case class PolygonParent(cen: Vec2, poly: PolygonGen, pointerId: Any, children: Arr[PaintElem]) extends GraphicParent
{ type AlignT = PolygonParent
  override def addElems(newElems: Arr[PaintElem]): PolygonParent = new PolygonParent(cen, poly, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolygonParent = new PolygonParent(cen, poly, newObj, children)
  override def boundingRect: BoundingRect = poly.boundingRect
  def mirrorXOffset(yOffset: Double): PolygonParent =
    PolygonParent(cen.mirrorXOffset(yOffset), poly.mirrorXOffset(yOffset), pointerId, children.mirrorParallelX(yOffset))

  def mirrorYOffset(xOffset: Double): PolygonParent =
    PolygonParent(cen.mirrorYOffset(xOffset), poly.mirrorYOffset(xOffset), pointerId, children.mirrorParallelY(xOffset))

  def rotateRadians(radians: Double): PolygonParent =
    PolygonParent(cen.rotateRadians(radians), poly.rotateRadians(radians), pointerId, children.rotateRadians(radians))

  def slate(offset: Vec2): PolygonParent = PolygonParent(cen + offset, poly.slate(offset), pointerId, children.slate(offset))

  def scale(operand: Double): PolygonParent = PolygonParent(cen * operand, poly.scale(operand), pointerId, children.scale(operand))

  override def shear(xScale: Double, yScale: Double): TransAffer = ???

  override def mirror(line: Line2): PolygonParent = ???
}