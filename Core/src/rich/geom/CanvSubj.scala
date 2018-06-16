/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
import Colour.Black
import pDisp._

/** Currently this trait combines 2 things */
trait CanvSubj[T <: CanvSubj[T]] extends CanvObj[T]
{
   def cen: Vec2
   def elems: Seq[CanvEl[_]]
   def boundingRect: BoundingRect
   def tL: T = slate(boundingRect.bR)
   def tR: T = slate(boundingRect.bL)
   def bL: T = slate(boundingRect.tR)
   def bR: T = slate(boundingRect.tL)
   def width = boundingRect.width
   def evObj: AnyRef
   def ptIn: Vec2 => Boolean
   def retObj: Vec2 => Option[AnyRef] = ptIn(_).toOption(evObj)
   def addElems(newElems: Seq[CanvEl[_]]): T
   def addElem(newElem: CanvEl[_]): T = addElems(Seq(newElem))
   def mutObj(newObj: AnyRef): T
}

trait PolySubjBase[T <: PolySubjBase[T]] extends CanvSubj[T]
{
   def poly: Vec2s
   def boundingRect: BoundingRect = poly.boundingRect
   override def ptIn: Vec2 => Boolean = poly.ptInPolygon
}

case class PolySubj(cen: Vec2, poly: Vec2s, evObj: AnyRef, elems: Seq[CanvEl[_]]) extends PolySubjBase[PolySubj]
{  
   def fTrans(f: Vec2 => Vec2): PolySubj = new PolySubj(f(cen), poly.fTrans(f), evObj, elems.fTrans(f))
   //def boundingRect: BoundingRect = poly.boundingRect
   //override def ptIn: Vec2 => Boolean = poly.ptInPolygon
   override def addElems(newElems: Seq[CanvEl[_]]): PolySubj = new PolySubj(cen, poly, evObj, elems ++ newElems)
   override def mutObj(newObj: AnyRef): PolySubj = new PolySubj(cen, poly, newObj, elems)
}

object PolySubj
{
   def fill(cen: Vec2, poly: Vec2s, evObj: AnyRef, colour: Colour) = new PolySubj(cen, poly, evObj, Seq(FillPoly(poly, colour)))
   
   /** Not sure if this is double filling the polygon */
   def fillDraw(cen: Vec2, poly: Vec2s, evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) =
      new PolySubj(cen, poly, evObj, Seq(FillDrawPoly(poly, fillColour, lineWidth, lineColour)))
   
   def draw(cen: Vec2, poly: Vec2s, evObj: AnyRef, lineWidth: Double, lineColour: Colour = Black) =
      new PolySubj(cen, poly, evObj, Seq(DrawPoly(poly, lineWidth, lineColour)))
   def fillText(cen: Vec2, poly: Vec2s, evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 4,
         fontColour: Colour = Colour.Black, align: TextAlign = TextCen) =
      new PolySubj(cen, poly, evObj, Seq(FillPoly(poly, fillColour), FillText(poly.polyCentre, str, fontSize, fontColour, align)))
   def fillContrastText(cen: Vec2, poly: Vec2s, evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 4) =
      fillText(cen, poly, evObj, fillColour, str, fontSize, fillColour.contrast)
}

case class ShapeSubj(cen: Vec2, shape: Seq[ShapeSeg], evObj: AnyRef, elems: Seq[CanvEl[_]]) extends CanvSubj[ShapeSubj]
{  
   def fTrans(f: Vec2 => Vec2): ShapeSubj = ShapeSubj(f(cen), shape.fTrans(f), evObj, elems.fTrans(f))
   def boundingRect: BoundingRect = shape.boundingRect
   override def ptIn: Vec2 => Boolean = shape.ptInShape
   override def addElems(newElems: Seq[CanvEl[_]]): ShapeSubj = ShapeSubj(cen, shape, evObj, elems ++ newElems)
   override def mutObj(newObj: AnyRef): ShapeSubj = ShapeSubj(cen, shape, newObj, elems)
}

object ShapeSubj
{
   def fill(cen: Vec2, shape: Seq[ShapeSeg], evObj: AnyRef, colour: Colour) = ShapeSubj(cen, shape, evObj, Seq(FillShape(shape, colour)))
   
   def fillDraw(cen: Vec2, shape: Seq[ShapeSeg], evObj: AnyRef, fillColour: Colour, lineWidth: Int, lineColour: Colour) =
      ShapeSubj(cen, shape, evObj, Seq(FillDrawShape(shape, fillColour, lineWidth, lineColour)))
   
   def draw(cen: Vec2, shape: Seq[ShapeSeg], evObj: AnyRef, lineWidth: Double, lineColour: Colour = Colour.Black) =
      ShapeSubj(cen, shape, evObj, Seq(DrawShape(shape, lineWidth, lineColour)))     
}