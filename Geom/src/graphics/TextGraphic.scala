/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black

/* The alignment of text can be left right or centre. This may want to extend from a more general alignment trait. If such is useful. */
sealed trait TextAlign
{ def jsStr: String
}

case object CenAlign extends TextAlign { def jsStr = "center" }
case object LeftAlign extends TextAlign { def jsStr = "left" }
case object RightAlign extends TextAlign { def jsStr = "right" }

/** Baseline style for text. */
sealed trait BaseLine
{ def jsStr: String
}

/** Companion object for [[BaseLine]] trait, contains the object value instances of the trait. */
object BaseLine
{ case object Top extends BaseLine { def jsStr = "top" }
  case object Middle extends BaseLine { def jsStr = "middle" }
  case object Alphabetic extends BaseLine { def jsStr = "alphabetic" }
  case object Bottom extends BaseLine { def jsStr = "bottom" }

  /** Implemented as VPos.Top on JavaFx Canvas. */
  case object Hanging extends BaseLine { def jsStr = "hanging" }

  /** Implemented as geometry.VPos.BASELINE on JavaFx Canvas. */
  case object Ideographic extends BaseLine { def jsStr = "ideographic" }
}

/** A Graphical display of Text.
 * @param posn The point to orient from. By default this Vec2 defines the centre but from right or left depending  on alignment. */
case class TextGraphic(str: String, fontSize: Double, xPosn: Double, yPosn: Double, colour: Colour, align: TextAlign, baseLine: BaseLine) extends
  GraphicAffineElem with CanvElem
{ type ThisT = TextGraphic
  def posn: Pt2 = Pt2(xPosn, yPosn)
  override def ptsTrans(f: Pt2 => Pt2) = TextGraphic(str, fontSize, f(posn), colour, align, baseLine)
  override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = cp.textGraphic(this)
}

object TextGraphic
{
  def apply(str: String, fontSize: Double = 24, posn: Pt2 = Pt2Z, colour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle) = new TextGraphic(str, fontSize, posn.x, posn.y, colour, align, baseLine)

  def xy(str: String, fontSize: Double = 24, xPosn: Double, yPosn: Double, colour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle) = new TextGraphic(str, fontSize, xPosn, yPosn, colour, align, baseLine)

  def lines(strs: StringArr, fontSize: Int = 24, posn: Pt2 = Pt2Z, fontColour: Colour = Black, lineSpacing: Double = 1,
    align: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Alphabetic): Arr[TextGraphic] =
  { val len = strs.length
    if(len == 0) Arr()
    else strs.iMap((i, str) => TextGraphic(str, fontSize, posn.addY(((len -1) / 2.0 - i) * fontSize * lineSpacing), fontColour, align, baseLine))
  }
}