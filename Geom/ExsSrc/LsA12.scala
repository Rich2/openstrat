/* Copyright 2018-23 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

/** Lesson A12. */
case class LsA12(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A12")
{
  val c1: CircleDraw = Circle(200).draw()
  val c2: CircleDraw = Circle(231).draw(lineColour = DarkBlue)

  val h1: HexParrX = HexParrX(200)
  val hd: PolygonDraw = h1.draw()
  val htv: RArr[CircleFill] = h1.vertsMap(v => Circle(25, v).fill(Pink))
  val hts: RArr[TextGraphic] = h1.vertsMap(v => TextGraphic(v.str0, 15, v))
  val h2: HexParrX = h1.slateX(-400)
  val hc: RArr[LineSegDraw] = h2.sidesIMap() { (s, i) => s.draw(2, Colours.rainbow.cycleGet(i)) }
  val h3d: PolygonDraw = HexParrY(231, 231, 0).draw(lineColour = DarkBlue)

  def hexGraphics(hr: HexReg, colour: Colour): GraphicElems =
  { val verts: GraphicElems = hr.vertsIFlatMap{ (pt, i) => pt.textArrowToward(hr.cen, "V" + i.str) }
    val sides: GraphicElems = hr.sidesIFlatMap{ (side, i) => side.midPt.textArrowAwayFrom(hr.cen, "Side" + i.str) }
    verts ++ sides +% hr.draw(lineColour = colour) +% TextGraphic(hr.str, 12, hr.cen, colour)
  }

  val h4: HexParrX = HexParrX(250, 200, 290)
  val h4d: GraphicElems = hexGraphics(h4, Green)

  val h5: HexParrY = HexParrY(250, -200, 290)
  val h5d: GraphicElems = hexGraphics(h5, DarkMagenta)

  val gap = 290

  val h6 = HexReg(220, Deg0, -gap, -270)
  val h6d = hexGraphics(h6, IndianRed)

  val h7 = HexReg(220, DegVec45, 0, -270)
  val h7d = hexGraphics(h7, Turquoise)

  val h8 = HexReg(220, DegVec90, gap, -270)
  val h8d = hexGraphics(h8, Colour.GoldenRod)

  repaint(htv ++ hts +% hd +% c1 +% c2 ++ hc +% h3d ++ h4d ++ h5d ++ h6d ++ h7d ++ h8d)
}