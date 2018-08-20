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

case class PolyFill(verts: Vec2s, colour: Colour) extends PolyElem[PolyFill]
{ override def fTrans(f: Vec2 => Vec2): PolyFill = PolyFill(verts.fTrans(f), colour) }

case class PolyDraw(verts: Vec2s, lineWidth: Double, lineColour: Colour = Black) extends PolyElem[PolyDraw]
{ override def fTrans(f: Vec2 => Vec2): PolyDraw = PolyDraw(verts.fTrans(f), lineWidth, lineColour) }

case class PolyFillDraw(verts: Vec2s, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PolyElem[PolyFillDraw]
{ override def fTrans(f: Vec2 => Vec2) = PolyFillDraw(verts.fTrans(f), fillColour, lineWidth, lineColour) }

case class ShapeFill(segs: List[ShapeSeg], fillColour: Colour) extends PaintElem[ShapeFill]
{ override def fTrans(f: Vec2 => Vec2) = ShapeFill(segs.fTrans(f), fillColour) }

case class ShapeDraw(segs: List[ShapeSeg], lineWidth: Double, lineColour: Colour = Black) extends PaintElem[ShapeDraw]
{ override def fTrans(f: Vec2 => Vec2) = ShapeDraw(segs.fTrans(f), lineWidth, lineColour) }

case class ShapeFillDraw(segs: List[ShapeSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PaintElem[ShapeFillDraw]
{ override def fTrans(f: Vec2 => Vec2) = ShapeFillDraw(segs.fTrans(f), fillColour, lineWidth, lineColour) }

case class ArcDraw(arc: Arc, lineWidth: Double, lineColour: Colour = Black) extends PaintElem[ArcDraw]
{ override def fTrans(f: Vec2 => Vec2) = ArcDraw(arc.fTrans(f), lineWidth, lineColour) }

case class TextGraphic(posn: Vec2, str: String, fontSize: Int, fontColour: Colour = Colour.Black, align: TextAlign = TextCen) extends PaintElem[TextGraphic]
{ override def fTrans(f: Vec2 => Vec2) = TextGraphic(f(posn), str, fontSize, fontColour, align) }
object TextGraphic
{
   def xy(x: Double, y: Double, text: String, fontSize: Int, colour: Colour = Black, align: TextAlign = TextCen) =
      new TextGraphic(Vec2(x, y), text, fontSize, colour, align)
   def lines(posn: Vec2, strs: List[String], fontSize: Int, fontColour: Colour = Colour.Black, lineSpacing: Double = 1.0): List[TextGraphic] =
   {
      val len = strs.length
      ife(len == 0,
            Nil,
            strs.iMap((str, i) => TextGraphic(posn.addY(((len -1) / 2.0 - i) * fontSize * lineSpacing), str, fontSize, fontColour, TextCen))
         )
   }
}

case class LineDraw(lineSeg: Line2, lineWidth: Double, linesColour: Colour = Black) extends PaintElem[LineDraw]
{ override def fTrans(f: Vec2 => Vec2) = LineDraw(lineSeg.fTrans(f), lineWidth, linesColour) }

case class LinesDraw(lineSegs: Line2s, lineWidth: Double, linesColour: Colour = Black) extends PaintElem[LinesDraw]
{ override def fTrans(f: Vec2 => Vec2) = LinesDraw(lineSegs.fTrans(f), lineWidth, linesColour) }

sealed trait TextAlign
{
   def jsStr: String
}
case object TextCen extends TextAlign { def jsStr = "center" }
case object TextLeft extends TextAlign { def jsStr = "left" }
case object TextRight extends TextAlign { def jsStr = "right" }

