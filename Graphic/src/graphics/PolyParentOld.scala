/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

@deprecated case class PolyParentOld(cen: Vec2, poly: Polygon, evObj: Any, elems: ArrOld[PaintElem]) extends GraphicParentOld with PolyActiveTr
{
  def fTrans(f: Vec2 => Vec2): PolyParentOld = new PolyParentOld(f(cen), poly.fTrans(f), evObj, elems.trans(f))
  override def addElems(newElems: ArrOld[PaintElem]): PolyParentOld = new PolyParentOld(cen, poly, evObj, elems ++ newElems)
  override def mutObj(newObj: AnyRef): PolyParentOld = new PolyParentOld(cen, poly, newObj, elems)
}

object PolyParentOld
{
  def fill(cen: Vec2, poly: Polygon, evObj: Any, colour: Colour) = new PolyParentOld(cen, poly, evObj, ArrOld(poly.fill(colour)))

  /** Not sure if this is double filling the polygon */
  def fillDraw(cen: Vec2, poly: Polygon, evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) =
    new PolyParentOld(cen, poly, evObj, ArrOld(PolyFillDraw(poly, fillColour, lineWidth, lineColour)))

  def draw(cen: Vec2, poly: Polygon, evObj: Any, lineWidth: Double, lineColour: Colour = Black) =
      new PolyParentOld(cen, poly, evObj, ArrOld(PolyDraw(poly, lineWidth, lineColour)))

  def fillText(cen: Vec2, poly: Polygon, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4, fontColour: Colour = Colour.Black,
      align: TextAlign = CenAlign) =
        new PolyParentOld(cen, poly, evObj, ArrOld(poly.fill(fillColour), TextGraphic(str, fontSize, poly.polyCentre, fontColour, align)))
  
  def fillContrastText(cen: Vec2, poly: Polygon, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4) =
    fillText(cen, poly, evObj, fillColour, str, fontSize, fillColour.contrast)
}