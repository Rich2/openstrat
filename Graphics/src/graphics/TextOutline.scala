/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

case class TextOutline(str: String, fontSize: Int = 24, posn: Pt2 = Pt2Z, colour: Colour = Black, lineWidth: Double = 1.0,
                       align: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Alphabetic, zOrder: Int = 0) extends GraphicAffineElem
{ override type ThisT = TextOutline
  override def fTrans(f: Pt2 => Pt2) = TextOutline(str, fontSize, f(posn), colour, lineWidth, align, baseLine)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.textOutline(this)

  override def negY: TextOutline = ???
}