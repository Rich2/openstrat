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


  def vertArrows(hr: HexReg, colour: Colour): GraphicElems =
  {
    val verts = hr.vertsIFlatMap(1){(pt, i) => pt.textArrowToward(hr.cen, "V" + i.str)}
    val sides = Pt2s(hr.sd1Cen, hr.sd2Cen, hr.sd3Cen, hr.sd4Cen, hr.sd5Cen, hr.sd6Cen).iFlatMap(1){(pt, i) =>
      pt.textArrowToward(hr.cen, "Side" + i.str)}
    verts ++ sides +- hr.draw(colour) +- TextGraphic(hr.toString, 10, hr.cen, colour)
  }

  val h4 = HexXlign(250, 200, 290)
  val h4d = vertArrows(h4, Green)

  val h5 = HexYlign(250, -200, 290)
  val h5d = vertArrows(h5, DarkMagenta)

  repaint(htv ++ hts +- hd +- c1 +- c2 ++ hc +- h3d ++ h4d ++  h5d)
}