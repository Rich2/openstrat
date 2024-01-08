/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZero
import geom._, pgui._, Colour._, prid._, phex._

case class TessGui(canv: CanvasPlatform) extends CanvasNoPanels("TessGui")
{ val d1 = 80.0
  val d2 = d1 / 2
  val br1 = -2
  val tr1 = 2
  val lc1 = -4
  val rc1 = 4
  val g1 = HGridReg.minMax(br1, tr1, lc1, rc1)
  val f1: HCoord => Pt2 = hc => hc.toPt2Reg.origin(g1.cenVec).scale(d1)
  val s1 = g1.sepsMap(hs => hs.lineSegHC.map(f1).draw())
  val g2 = HGridReg.minMax(br1 * 2, tr1 * 2 + 2, lc1 * 2, rc1 * 2 + 4)
  val g3 = HGridReg.minMax(br1 * 4, tr1 * 4 + 2, lc1 * 4, rc1 * 4 + 4)
  val f2: HCoord => Pt2 = hc => hc.toPt2Reg.origin(g1.cenVec).xyOrigin(2, 3.sqrt).scale(d2)
  val s2 = g2.sepsMap(hs => hs.lineSegHC.map(f2).draw(lineColour = Red))
  val t2 = g2.map(hc => f2(hc).textAt(hc.rcStr, 12, Red))
  val f3: HCoord => Pt2 = hc => hc.toPt2Reg.origin(g1.cenVec).xyOrigin(0, 0).scale(d2 / 2)
  val s3 = g3.sepsMap(hs => hs.lineSegHC.map(f3).draw(lineColour = Green))
  repaint(s3 ++ s1)//++ s2 ++ s1 ++ t2)
}