package ostrat; package pZero
import geom._, pgui._, Colour._, prid._, phex._

case class TessGui(canv: CanvasPlatform) extends CanvasNoPanels("TessGuis")
{
  val g1 = HGridReg(-2, 2, -4, 4)
  val g2 = HGridReg(-4, 6, -8, 10)
  val s1 = g1.sidesMap(hs => hs.lineSegHC.transLineSeg(80).draw())
  val s2 = g2.sidesMap(hs => hs.lineSegHC.transLineSeg(40, 0, 2).slateY(0).draw(Red))
  repaint(s2 ++ s1)
}