/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._, Colour._

/** Produces an HTML file documentation for the Geom module. */
object GeomPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Geom Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Geom Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", list, Polygons)

  def list: HtmlOlWithLH = HtmlOlWithLH.h2("The Geom module contains",
    geomItme, colourItem, graphicItem, compound, trans, canv, svg, web, geom3, lessons)

  def geomItme: HtmlLi = HtmlLi("Geometry. Immutable classes for points, lines and shapes. These classes build on the Array based collections" +
    " from the Util module.")

  def colourItem: HtmlLi = HtmlLi("Colour class. A 32 bit integer class that can be built from rgba and named values.")

  def graphicItem: HtmlLi = HtmlLi("Graphic primitives. Immutable classes for fills, draws and active elements based on the geometry classes.")

  def compound: HtmlLi = HtmlLi("Compound Graphics. Again immutable classes. Useful for selection and placing.")

  def trans: HtmlLi = HtmlLi("Geometric transformations on both the geometric and graphical elements, preserving maximum type information.")

  def canv: HtmlLi = HtmlLi("An abstract canvas on which to display the graphic elements. Concrete implementations for JavaFx and HtmlCanvas," +
    " allowing applications to be created with minimal platform specific code. The abstract canvas api could be implemented on DirectX or OpenGL," +
    " but this would require significantly more work than for the ScalaFx canvas or the Html Canvas.")

  def svg: HtmlLi = HtmlLi("Conversion of Graphic classes into SVG, gving an alternative target and greater flexibility.")

  def web: HtmlLi = HtmlLi("Web library. Classes for XML, HTML, CSS and simple JavaScript functions. These pages have been generated using this.")

  def geom3: HtmlLi = HtmlLi("3D geometry as well as distance unit classes as opposed to scalars for 1D, 2D and 3D. Basic 3D Graphics will be" +
    " provided, but currently there is no attempt to provide any kind of 3D or physics engine, although a 3D implementation for canvas is entirely" +
    " possible.")

  def lessons: HtmlLi = HtmlLi("Series of lessons / tutorials in geometry and graphics.")

  object Polygons extends HtmlSection
  {
    override def contents: RArr[XCon] = RArr(HtmlH2("Polygons"), svg1, p1, svg2)

    val width: Int = 250
    val margin = 50
    val polyColour: Colour = DarkGreen
    val dodec1: DoDeclign = DoDeclign(width)
    val dodec2: SvgElem = dodec1.draw(polyColour).svgElem
    val circ: SvgElem = Circle(width * 2).draw().svgElem
    val verts: RArr[SvgElem] = dodec1.vertsIFlatMap{ (pt, i) => pt.textArrowToward(Pt2Z, "V" + i.str).map(_.svgElem) }
    val sides: RArr[SvgElem] = dodec1.sidesIFlatMap{ (sd, i) => sd.midPt.textArrowAwayFrom(Pt2Z, "Sd" + i.str, colour = polyColour).map(_.svgElem) }
    val cen = Pt2Z.textAt("Centre").svgElem

    val rect1 = Rect(400, 250)
    val rect2: SvgElem = rect1.draw(polyColour).svgElem
    val verts2: RArr[SvgElem] = rect1.vertsIFlatMap { (pt, i) => pt.textArrowToward(Pt2Z, "V" + i.str).map(_.svgElem) }
    val sides2: RArr[SvgElem] = rect1.sidesIFlatMap { (sd, i) => sd.midPt.textArrowAwayFrom(Pt2Z, "Sd" + i.str, colour = polyColour).map(_.svgElem) }

    val svg1: SvgSvgElem = SvgSvgElem.bounds(dodec1.boundingRect.addMargin(margin), RArr(dodec2, circ, cen) ++ verts ++ sides, RArr(CentreBlockAtt))
    val svg2: SvgSvgElem = SvgSvgElem.bounds(rect1.boundingRect.addMargin(margin), RArr(rect2, cen) ++ verts2 ++ sides2, RArr(CentreBlockAtt))


    def p1: HtmlP = HtmlP(
    """Polygons are used a lot in this module and in modules that use this module. So it is important to establish conventions or defaults. The
      | vertices of an N sided polygon are numbered from 0 to n - 1. With the 0th vertex appearing at 12 o'clock, unless there is no vertex at the 12
      |  o'clock position in which case it is the first vertex clockwise of 12 o'clock. The other vertices then follow clockwise. The last vertex
      |  being immediately anti clockwise of 12 o'clock.""".stripMargin)
  }
}