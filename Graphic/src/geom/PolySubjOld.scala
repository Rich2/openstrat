/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

case class PolySubjOld(cen: Vec2, poly: Polygon, evObj: AnyRef, elems: ArrOld[PaintElem]) extends GraphicParentOld with PolyActiveTr
{
  def fTrans(f: Vec2 => Vec2): PolySubjOld = new PolySubjOld(f(cen), poly.fTrans(f), evObj, elems.trans(f))
  override def addElems(newElems: ArrOld[PaintElem]): PolySubjOld = new PolySubjOld(cen, poly, evObj, elems ++ newElems)
  override def mutObj(newObj: AnyRef): PolySubjOld = new PolySubjOld(cen, poly, newObj, elems)
}

object PolySubjOld
{
  def fill(cen: Vec2, poly: Polygon, evObj: AnyRef, colour: Colour) = new PolySubjOld(cen, poly, evObj, ArrOld(poly.fill(colour)))

  /** Not sure if this is double filling the polygon */
  def fillDraw(cen: Vec2, poly: Polygon, evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) =
    new PolySubjOld(cen, poly, evObj, ArrOld(PolyFillDraw(poly, fillColour, lineWidth, lineColour)))

  def draw(cen: Vec2, poly: Polygon, evObj: AnyRef, lineWidth: Double, lineColour: Colour = Black) =
      new PolySubjOld(cen, poly, evObj, ArrOld(PolyDraw(poly, lineWidth, lineColour)))

  def fillText(cen: Vec2, poly: Polygon, evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 4, fontColour: Colour = Colour.Black,
      align: TextAlign = CenAlign) =
        new PolySubjOld(cen, poly, evObj, ArrOld(poly.fill(fillColour), TextGraphic(str, fontSize, poly.polyCentre, fontColour, align)))
  
  def fillContrastText(cen: Vec2, poly: Polygon, evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 4) =
    fillText(cen, poly, evObj, fillColour, str, fontSize, fillColour.contrast)
}

