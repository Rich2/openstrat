/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black

/** To be removed. Circular Arc Draw PaintElem. */
final case class CArcDrawDep(arc: CArcDep, lineWidth: Double = 2.0, lineColour: Colour = Black) extends CanvElem with SimilarPreserve// with CanvElem
{ type ThisT = CArcDrawDep

  override def fTrans(f: Pt2 => Pt2): CArcDrawDep = CArcDrawDep(arc.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.cArcDrawDep(this)
  def startText = TextGraphic("st", arc.pStart, 10, lineColour)
  def clkStr: String = ife(arc.clock, "clk", "anti")
  def cenText: TextGraphic = TextGraphic(clkStr, arc.cen, 10, lineColour)
  def endText: TextGraphic = TextGraphic("end", arc.pEnd, 10, lineColour)
  def cenEndTexts = Arr(cenText, endText)
  def startCenEndTexts = Arr(startText, cenText, endText)
  
  def shear(xScale: Double, yScale: Double): AffinePreserve = ???
  override def xyScale(xOperand: Double, yOperand: Double): CanvElem = ???

  override def xShear(operand: Double): CanvElem = ???

  override def yShear(operand: Double): CanvElem = ???
}