/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black

/** Circular Arc Draw PaintElem. */
final case class CArcDraw(arc: CArc, lineWidth: Double = 2.0, lineColour: Colour = Black) extends DisplayElem with SimilarPreserve
{ type ThisT = CArcDraw

  override def fTrans(f: Vec2 => Vec2): CArcDraw = CArcDraw(arc.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.cArcDraw(this)
  def startText = TextGraphic("st", 10, arc.pStart, lineColour)
  def clkStr: String = ife(arc.clock, "clk", "anti")
  def cenText: TextGraphic = TextGraphic(clkStr, 10, arc.cen, lineColour)
  def endText: TextGraphic = TextGraphic("end", 10, arc.pEnd, lineColour)
  def cenEndTexts = Arr(cenText, endText)
  def startCenEndTexts = Arr(startText, cenText, endText)
  
  def shear(xScale: Double, yScale: Double): AffinePreserve = ???
  override def scaleXY(xOperand: Double, yOperand: Double): DisplayElem = ???

  override def shearX(operand: Double): TransElem = ???

  override def shearY(operand: Double): TransElem = ???
}