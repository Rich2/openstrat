/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

sealed trait TextAlign
{ def jsStr: String
}

case object TextCen extends TextAlign { def jsStr = "center" }
case object TextLeft extends TextAlign { def jsStr = "left" }
case object TextRight extends TextAlign { def jsStr = "right" }
 
case class TextGraphic(str: String, posn: Vec2, fontSize: Int, colour: Colour = Black, align: TextAlign = TextCen, zOrder: Int = 0) extends 
PaintElem[TextGraphic]
{
  override def fTrans(f: Vec2 => Vec2) = TextGraphic(str, f(posn), fontSize, colour, align, zOrder)
}

/** Not sure if this is a good object to have */
object TextGraphicCen
{
  def apply(str: String, posn: Vec2, fontSize: Int, colour: Colour = Black, zOrder: Int = 0): TextGraphic =
    new TextGraphic(str, posn, fontSize, colour, TextCen, zOrder)
}

object TextGraphic
{
  def xy(str: String, x: Double, y: Double, fontSize: Int, colour: Colour = Black, align: TextAlign = TextCen) =
      new TextGraphic(str, Vec2(x, y), fontSize, colour, align)
  
  def lines(posn: Vec2, strs: List[String], fontSize: Int, fontColour: Colour = Black, lineSpacing: Double = 1.0): List[TextGraphic] =  
  { val len = strs.length
    if(len == 0) Nil
      else strs.iMap((str, i) => TextGraphic(str, posn.addY(((len -1) / 2.0 - i) * fontSize * lineSpacing), fontSize, fontColour, TextCen))        
  }
}

case class TextOutline(posn: Vec2, str: String, fontSize: Int, colour: Colour, lineWidth: Double, align: TextAlign = TextCen,
    zOrder: Int = 0) extends PaintElem[TextOutline]
{
  override def fTrans(f: Vec2 => Vec2) = TextOutline(f(posn), str, fontSize, colour, lineWidth, align)  
}