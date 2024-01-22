/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._, Colour._, GeomPage.svgMargin

object GeomPagePolygons extends HtmlSection
{
  override def contents: RArr[XCon] = RArr(HtmlH2("Polygons"), p1, svg1, p2, code1, p3, svg2)

  def p1: HtmlP = HtmlP(
  """Polygons are used a lot in this module and in modules that use this module. So it is important to establish conventions or defaults. The
  | vertices of an N sided polygon are numbered from 0 to n - 1. With the vertex 0 appearing at 12 o'clock or 00 hundred hours as in the
  | dodecahedron below. Vertex 1 appears at the 1 o'clock position, vertex 2 at the 2 o'clock position etc. The middle of side 0 is at 12.30 or
  | 00.30 hours, the middle of side 1 is at 01.30 hours etc.""".stripMargin)

  val width: Int = 250
  val polyColour: Colour = DarkGreen
  val dodec1: DoDeclign = DoDeclign(width)
  val dodec2: PolygonDraw = dodec1.draw(lineColour = polyColour)
  val circ: CircleDraw = Circle(width * 2).draw()
  val verts: RArr[GraphicSvgElem] = dodec1.verts.iFlatMap { (i, pt) => pt.textArrowToward(Pt2Z, "V" + i.str) }
  val sides: RArr[GraphicSvgElem] = dodec1.sides.iFlatMap { (i, sd) => sd.midPt.textArrowAwayFrom(Pt2Z, "Sd" + i.str, colour = polyColour) }
  val cen: TextFixed = Pt2Z.textAt("Centre")
  val clock: RArr[GraphicSvgElem] = RArr(dodec2, circ, cen) ++ verts ++ sides
  val svg1: HtmlSvg = HtmlSvg(dodec1.boundingRect.addMargin(svgMargin), clock, RArr(CentreBlockAtt))

  val rect1: Rect = Rect(400, 250)
  val rect2: RectDraw = rect1.draw()
  val verts2: RArr[GraphicSvgElem] = rect1.verts.iFlatMap { (i, pt) => pt.textArrowToward(Pt2Z, "V" + i.str) }
  val sides2: RArr[GraphicSvgElem] = rect1.sides.iFlatMap { (i, sd) => sd.midPt.textArrowAwayFrom(Pt2Z, "Sd" + i.str, colour = polyColour) }
  val svg2: HtmlSvg = HtmlSvg(rect1.boundingRect.addMargin(svgMargin), RArr(rect2, cen) ++ verts2 ++ sides2, RArr(CentreBlockAtt))

  def p2: HtmlP = HtmlP(
  """I've included the Scala code below both for the above diagram. If you check the html source code for this web page you will see that it is pretty
  | succinct compared with the generated SVG code. The code is in small font and is not type annotated so it is not intended as a tutorial, but just
  | to give an idea of possibilities. It is only the last line that creates the SVG. The rest of the code could be used in an HTML or a JavaFx
  | canvas.""".stripMargin)

  def code1: HtmlScala = HtmlScala(
    "val width: Int = 250<br>" ---
    "val polyColour: Colour = DarkGreen<br>" ---
    "val dodec1: DoDeclign = DoDeclign(width)<br>" ---
    "val dodec2 = dodec1.draw(polyColour)<br>" ---
    "val circ = Circle(width * 2).draw()<br>" ---

    """val verts = dodec1.vertsIFlatMap{ (pt, i) => pt.textArrowToward(Pt2Z, "V" + i.str) }<br>""" ---

    """val sides = dodec1.sidesIFlatMap{ (sd, i) => sd.midPt.textArrowAwayFrom(Pt2Z, "Sd" + i.str, colour = polyColour) }<br>""" ---
    """val cen = Pt2Z.textAt("Centre")<br>""" ---
    "val clock = RArr(dodec2, circ, cen) ++ verts ++ sides<br>" ---
    "val svg1 = HtmlSvg(dodec1.boundingRect.addMargin(svgMargin), clock, RArr(CentreBlockAtt))<br>"
  )

  def p3: HtmlP = HtmlP(
    """If there is no vertex at the 12 o'clock / 00 hundred hours postion as in the rectangle below vertex 0 is the first vertex clockwise of 12
      | o'clock. The other vertices then follow clockwise. The last vertex being immediately anti clockwise of 12 o'clock.""".stripMargin)
}