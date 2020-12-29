/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LsA12(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A12")
{
  val c1 = Circle(200).draw()
  val c2 = Circle(231).draw(DarkBlue)

  val h1: HexXlign = HexXlign(200)
  val hd = h1.draw()
  val htv = h1.vertsMap(v => Circle(25, v).fill(Pink))
  val hts = h1.vertsMap(v => TextGraphic(v.str0, 15, v))
  val h2: HexXlign = h1.xSlate(-400)
  val hc = h2.sidesIMap((s, i) => s.draw(Colour.rainbow.cycleGet(i), 2))
  val h3d = HexYlign(231, 231, 0).draw(DarkBlue)


  def vertArrows(hex: HexReg): GraphicElems = {
    val hex1 = hex.v1.textArrowToward(hex.cen, "V1")
    val hex2 = hex.v2.textArrowToward(hex.cen, "V2")
    val hex3 = hex.v3.textArrowToward(hex.cen, "V3")
    val hex4 = hex.v4.textArrowToward(hex.cen, "V4")
    val hex5 = hex.v5.textArrowToward(hex.cen, "V5")
    val hex6 = hex.v6.textArrowToward(hex.cen, "V6")
    hex1 ++ hex2 ++ hex3 ++ hex4 ++ hex5 ++ hex6
  }

  val h4 = HexXlign(250, 150, 290)
  val h4d = h4.draw(Green)

  val h5 = HexYlign(250, -200, 290)
  val h5d = h5.draw(DarkMagenta)

  repaint(htv ++ hts +- hd +- c1 +- c2 ++ hc +- h3d +- h4d ++ vertArrows(h4) +- h5d ++ vertArrows(h5))
}