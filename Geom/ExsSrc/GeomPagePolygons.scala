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
  val dodec2: SvgElem = dodec1.draw(polyColour).svgElem
  val circ: SvgElem = Circle(width * 2).draw().svgElem
  val verts: RArr[SvgElem] = dodec1.vertsIFlatMap { (pt, i) => pt.textArrowToward(Pt2Z, "V" + i.str).map(_.svgElem) }
  val sides: RArr[SvgElem] = dodec1.sidesIFlatMap { (sd, i) => sd.midPt.textArrowAwayFrom(Pt2Z, "Sd" + i.str, colour = polyColour).map(_.svgElem) }
  val cen: SvgElem = Pt2Z.textAt("Centre").svgElem
  val svg1: HtmlSvg = HtmlSvg.bounds(dodec1.boundingRect.addMargin(svgMargin), RArr(dodec2, circ, cen) ++ verts ++ sides, RArr(CentreBlockAtt))

  val rect1: Rect = Rect(400, 250)
  val rect2: SvgElem = rect1.draw(polyColour).svgElem
  val verts2: RArr[SvgElem] = rect1.vertsIFlatMap { (pt, i) => pt.textArrowToward(Pt2Z, "V" + i.str).map(_.svgElem) }
  val sides2: RArr[SvgElem] = rect1.sidesIFlatMap { (sd, i) => sd.midPt.textArrowAwayFrom(Pt2Z, "Sd" + i.str, colour = polyColour).map(_.svgElem) }
  val svg2: HtmlSvg = HtmlSvg.bounds(rect1.boundingRect.addMargin(svgMargin), RArr(rect2, cen) ++ verts2 ++ sides2, RArr(CentreBlockAtt))

  def p2: HtmlP = HtmlP(
  """I've included the Scala code below both for the above diagram and for the rectangle diagram below. If you check the html source code for this web
  | page you will see that it is pretty succinct compared with the generated SVG code.""".stripMargin)

  def code1: HtmlScala = HtmlScala(
    "val width: Int = 250<br>" ---
    "val polyColour: Colour = DarkGreen<br>" ---
    "val dodec1: DoDeclign = DoDeclign(width)<br>" ---
    "val dodec2: SvgElem = dodec1.draw(polyColour).svgElem<br>" ---
    "val circ: SvgElem = Circle(width * 2).draw().svgElem<br>" ---

    "val verts: RArr[SvgElem] =" ---
    """  dodec1.vertsIFlatMap{ (pt, i) => pt.textArrowToward(Pt2Z, "V" + i.str).map(_.svgElem) }<br>""" ---

    "val sides: RArr[SvgElem] =" ---
    """dodec1.sidesIFlatMap{ (sd, i) => sd.midPt.textArrowAwayFrom(Pt2Z, "Sd" + i.str, colour = polyColour).map(_.svgElem) }<br>""" ---
    """val cen: SvgElem = Pt2Z.textAt("Centre").svgElem<br>""" ---
      
    "val svg1: SvgSvgElem = SvgSvgElem.bounds(dodec1.boundingRect.addMargin(svgMargin), RArr(dodec2, circ, cen) ++ verts ++ sides, RArr(CentreBlockAtt))<br>" ---

    "val rect1: Rect = Rect(400, 250)<br>" ---
    "val rect2: SvgElem = rect1.draw(polyColour).svgElem<br>" ---
    """val verts2: RArr[SvgElem] = rect1.vertsIFlatMap { (pt, i) => pt.textArrowToward(Pt2Z, "V" + i.str).map(_.svgElem) }<br>""" ---
    """val sides2: RArr[SvgElem] = rect1.sidesIFlatMap { (sd, i) => sd.midPt.textArrowAwayFrom(Pt2Z, "Sd" + i.str, colour = polyColour).map(_.svgElem) }<br>""" ---

    "val svg2: SvgSvgElem = SvgSvgElem.bounds(rect1.boundingRect.addMargin(svgMargin), RArr(rect2, cen) ++ verts2 ++ sides2, RArr(CentreBlockAtt))"
  )


  def p3: HtmlP = HtmlP(
    """If there is no vertex at the 12 o'clock / 00 hundred hours postion as in the rectangle below vertex 0 is the first vertex clockwise of 12
      | o'clock. The other vertices then follow clockwise. The last vertex being immediately anti clockwise of 12 o'clock.""".stripMargin)
}