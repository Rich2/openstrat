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
 
case class TextGraphic(str: String, fontSize: Int = 24, posn: Vec2 = Vec2Z, colour: Colour = Black, align: TextAlign = TextCen,
    zOrder: Int = 0) extends PaintElem
{
  override def fTrans(f: Vec2 => Vec2) = TextGraphic(str, fontSize, f(posn), colour, align, zOrder)
}

/** Not sure if this is a good object to have */
object TextGraphicCen
{
  def apply(str: String, fontSize: Int, posn: Vec2 = Vec2Z, colour: Colour = Black, zOrder: Int = 0): TextGraphic =
    new TextGraphic(str, fontSize, posn, colour, TextCen, zOrder)
}

object TextGraphic
{ 
  def lines(strs: List[String], fontSize: Int = 24, posn: Vec2 = Vec2Z, fontColour: Colour = Black, lineSpacing: Double = 1,
      align: TextAlign = TextCen): List[TextGraphic] =  
  { val len = strs.length
    if(len == 0) Nil
      else strs.iMap((str, i) => TextGraphic(str, fontSize, posn.addY(((len -1) / 2.0 - i) * fontSize * lineSpacing), fontColour, align))        
  }
}

case class TextOutline(posn: Vec2, str: String, fontSize: Int, colour: Colour, lineWidth: Double, align: TextAlign = TextCen,
    zOrder: Int = 0) extends PaintElem
{
  override def fTrans(f: Vec2 => Vec2) = TextOutline(f(posn), str, fontSize, colour, lineWidth, align)  
}