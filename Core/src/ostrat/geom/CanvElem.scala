/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** The base trait for all objects on a canvas / panel. The objects are recomposed for each frame. The Canvas objects must be recomposed
 *  each time there is a change within the application state or the user view of that application state. */
trait CanvElem[T] extends Any with Transable[T]

/* Base trait for all passive objects  on a canvas / panel */
trait PaintElem[T] extends Any with CanvElem[T]

trait PolyElem[A] extends Any with PaintElem[A]
{
   def verts: Vec2s
   def xHead: Double = verts.head1
   def yHead: Double = verts.head2
   def vertsLength: Int = verts.length
   def xArray: Array[Double] = verts.elem1sArray
   def yArray: Array[Double] = verts.elem2sArray
}

case class FillPoly(verts: Vec2s, colour: Colour) extends PolyElem[FillPoly]
{ override def fTrans(f: Vec2 => Vec2): FillPoly = FillPoly(verts.fTrans(f), colour) }

case class DrawPoly(verts: Vec2s, lineWidth: Double, lineColour: Colour = Black) extends PolyElem[DrawPoly]
{ override def fTrans(f: Vec2 => Vec2): DrawPoly = DrawPoly(verts.fTrans(f), lineWidth, lineColour) }

case class FillDrawPoly(verts: Vec2s, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PolyElem[FillDrawPoly]
{ override def fTrans(f: Vec2 => Vec2) = FillDrawPoly(verts.fTrans(f), fillColour, lineWidth, lineColour) }

case class FillShape(segs: List[ShapeSeg], fillColour: Colour) extends PaintElem[FillShape]
{ override def fTrans(f: Vec2 => Vec2) = FillShape(segs.fTrans(f), fillColour) }

case class DrawShape(segs: List[ShapeSeg], lineWidth: Double, lineColour: Colour = Black) extends PaintElem[DrawShape]
{ override def fTrans(f: Vec2 => Vec2) = DrawShape(segs.fTrans(f), lineWidth, lineColour) }

case class FillDrawShape(segs: List[ShapeSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PaintElem[FillDrawShape]
{ override def fTrans(f: Vec2 => Vec2) = FillDrawShape(segs.fTrans(f), fillColour, lineWidth, lineColour) }

case class DrawArc(arc: Arc, lineWidth: Double, lineColour: Colour = Black) extends PaintElem[DrawArc]
{ override def fTrans(f: Vec2 => Vec2) = DrawArc(arc.fTrans(f), lineWidth, lineColour) }

case class FillText(posn: Vec2, str: String, fontSize: Int, fontColour: Colour = Colour.Black, align: TextAlign = TextCen) extends PaintElem[FillText]
{ override def fTrans(f: Vec2 => Vec2) = FillText(f(posn), str, fontSize, fontColour, align) }
object FillText
{
   def xy(x: Double, y: Double, text: String, fontSize: Int, colour: Colour = Black, align: TextAlign = TextCen) =
      new FillText(Vec2(x, y), text, fontSize, colour, align)
   def lines(posn: Vec2, strs: List[String], fontSize: Int, fontColour: Colour = Colour.Black, lineSpacing: Double = 1.0): List[FillText] =
   {
      val len = strs.length
      ife(len == 0,
            Nil,
            strs.iMap((str, i) => FillText(posn.addY(((len -1) / 2.0 - i) * fontSize * lineSpacing), str, fontSize, fontColour, TextCen))
         )
   }
}
case class FillTextRel(str: String, fontSize: Int, fontColour: Colour = Colour.Black, posn: Vec2 = Vec2Z, align: TextAlign = TextCen) extends
   PaintElem[FillTextRel]{ override def fTrans(f: Vec2 => Vec2) = this }

case class FillDrawTextPoly(verts: Vec2s, fillColour: Colour, lineWidth: Double, lineColour: Colour, str: String, fontSize: Int,
      fontColour: Colour) extends PaintElem[FillDrawTextPoly]
{ override def fTrans(f: Vec2 => Vec2) = FillDrawTextPoly(verts.fTrans(f), fillColour, lineWidth, lineColour, str, fontSize, fontColour) }

case class FillTextPoly(verts: Vec2s, fillColour: Colour, str: String, fontSize: Int, fontColour: Colour) extends PaintElem[FillTextPoly]
{ override def fTrans(f: Vec2 => Vec2) = FillTextPoly(verts.fTrans(f), fillColour, str, fontSize, fontColour) }

case class DrawTextPoly(verts: Vec2s, lineWidth: Double, lineColour: Colour, str: String, fontSize: Int,
      fontColour: Colour) extends PaintElem[DrawTextPoly]
{ override def fTrans(f: Vec2 => Vec2) = DrawTextPoly(verts.fTrans(f), lineWidth, lineColour, str, fontSize, fontColour) }

case class LineDraw(lineSeg: Line2, lineWidth: Double, linesColour: Colour = Black) extends PaintElem[LineDraw]
{ override def fTrans(f: Vec2 => Vec2) = LineDraw(lineSeg.fTrans(f), lineWidth, linesColour) }

case class PolyLineDraw(lineSegs: List[Line2], lineWidth: Double, linesColour: Colour = Black) extends PaintElem[PolyLineDraw]
{ override def fTrans(f: Vec2 => Vec2) = PolyLineDraw(lineSegs.fTrans(f), lineWidth, linesColour) }

sealed trait TextAlign
{
   def jsStr: String
}
case object TextCen extends TextAlign { def jsStr = "center" }
case object TextLeft extends TextAlign { def jsStr = "left" }
case object TextRight extends TextAlign { def jsStr = "right" }

