/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/* The alignment of text. can be left right or centre. This may want to extend from a more general alignment trait. If such s useful. */
sealed trait TextAlign
{ def jsStr: String
}

case object CenAlign extends TextAlign { def jsStr = "center" }
case object LeftAlign extends TextAlign { def jsStr = "left" }
case object RightAlign extends TextAlign { def jsStr = "right" }

sealed trait BaseLine
{ def jsStr: String
}

//BASELINE NB: jfx doesnt have equivalents for "hanging" & "ideographic" so commented out here
//case object HangingBL extends BaseLine { def jsStr = "hanging" }
//case object IdeographicBL extends BaseLine { def jsStr = "ideographic" }
case object TopBL extends BaseLine { def jsStr = "top" }
case object MiddleBL extends BaseLine { def jsStr = "middle" }
case object AlphabeticBL extends BaseLine { def jsStr = "alphabetic" }
case object BottomBL extends BaseLine { def jsStr = "bottom" }

/** A Graphical display of Text.
 * @param posn The point to orient from. By default this Vec2 defines the centre but from right or left depending  on alignment. */
case class TextGraphic(str: String, fontSize: Int = 24, posn: Vec2 = Vec2Z, colour: Colour = Black, align: TextAlign = CenAlign, baseLine: BaseLine = AlphabeticBL, zOrder: Int = 0) extends PaintElem
{
  override def fTrans(f: Vec2 => Vec2) = TextGraphic(str, fontSize, f(posn), colour, align, baseLine, zOrder)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.textGraphic(this)
}

/** Not sure if this is a good object to have. */
object TextGraphicCen
{
  def apply(str: String, fontSize: Int, posn : Vec2 = Vec2Z, colour: Colour = Black, zOrder: Int = 0): TextGraphic =
    new TextGraphic(str, fontSize, posn, colour, CenAlign, AlphabeticBL, zOrder)
}

object TextGraphic
{ 
  def lines(strs: Arr[String], fontSize: Int = 24, posn: Vec2 = Vec2Z, fontColour: Colour = Black, lineSpacing: Double = 1,  align: TextAlign = CenAlign, baseLine: BaseLine = AlphabeticBL): Arr[TextGraphic] =
  { val len = strs.length
    if(len == 0) Arr()
      else strs.iMap((str, i) => TextGraphic(str, fontSize, posn.addY(((len -1) / 2.0 - i) * fontSize * lineSpacing), fontColour, align, baseLine))        
  }
}

case class TextOutline(str: String, fontSize: Int = 24, posn: Vec2 = Vec2Z, colour: Colour = Black, lineWidth: Double = 1.0, align: TextAlign = CenAlign,
  baseLine: BaseLine = AlphabeticBL, zOrder: Int = 0) extends PaintElem
{
  override def fTrans(f: Vec2 => Vec2) = TextOutline(str, fontSize, f(posn), colour, lineWidth, align, baseLine)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.textOutline(this)
}