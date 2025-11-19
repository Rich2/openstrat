/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import geom.*, pWeb.*, wcode.*, Colour.*

/** Produces an HTML file documentation for the Geom module. */
object GeomPage extends HtmlPage
{ override def head: HtmlHead = HtmlHead.titleCss("Geom Module", "documentation")
  override def body: HtmlBody = HtmlBody(HtmlH1("Geom Module"), central)
  def central: HtmlDiv = HtmlDiv.classAtt("central", HtmlP(intro), GeomPage2D, list, GeomPagePolygons, Ellipses, LinePathNames, LessonLists, GeomPageWeb)

  def intro = """The Geom module contains 2D geometry and graphics. These can currently be output to JavaFx canvas, Html canvas and Svg. It also contains other
  | geometries including 3D with associated graphics. Development of targets for 3d graphics is still rudimentary. The 2D and 3D can also be defined in length
  | units such as metres, miles and picometres. There is also 2D geometry and graphics that can be defined in latitudes and longitudes.""".stripMargin

  val list: HtmlOlWithLH =
    HtmlOlWithLH.h2("The Geom module contains", geomItem, colourItem, graphicItem, compound, trans, canv, svg, web, geom3, lessons, earth)

  def geomItem: HtmlLi =
    HtmlLi("Geometry. Immutable classes for points, lines and shapes. These classes build on the Array based collections from the Util module.")

  def colourItem: HtmlLi = HtmlLi("Colour class. A 32 bit integer class that can be built from rgba and named defues.")

  def graphicItem: HtmlLi = HtmlLi("Graphic primitives. Immutable classes for fills, draws and active elements based on the geometry classes.")

  def compound: HtmlLi = HtmlLi("Compound Graphics. Again immutable classes. Useful for selection and placing.")

  def trans: HtmlLi = HtmlLi("Geometric transformations on both the geometric and graphical elements, preserving maximum type information.")

  def canv: HtmlLi = HtmlLi("""An abstract canvas on which to display the graphic elements. Concrete implementations for JavaFx and HtmlCanvas, allowing
  | applications to be created with minimal platform specific code. The abstract canvas api could be implemented on DirectX or OpenGL, but this would require
  | significantly more work than for the ScalaFx canvas or the Html Canvas.""".stripMargin)

  def svg: HtmlLi = HtmlLi("Conversion of Graphic classes into SVG, giving an alternative target and greater flexibility.")

  def web: HtmlLi = HtmlLi("Web library. Classes for XML, HTML, CSS and simple JavaScript functions. These pages have been generated using this.")

  def geom3: HtmlLi = HtmlLi("""3D geometry as well as distance unit classes as opposed to scalars for 1D, 2D and 3D. Basic 3D Graphics will be provided, but
  | currently there is no attempt to provide any kind of 3D or physics engine, although a 3D implementation for canvas is entirely possible.""".stripMargin)

  def lessons: HtmlLi = HtmlLi("Series of lessons / tutorials in geometry and graphics.")

  def earth: HtmlLi = HtmlLi("""Earth geometry. This is for Earth maps. Allows the manipulation of latitude and longitude allowing free conversion between them
  | and 2D and 3D coordinates.""".stripMargin)

  val svgMargin = 50

  object Ellipses extends HtmlSection
  { override def contents: RArr[XConCompound] = RArr(HtmlH2("Circles and Ellipses"), svgs1, svgs2)

    val circ2: Circle = Circle.d(200)
    val circ1: Circle = circ2.slateX(-200)
    val circ3: Circle = circ2.slateX(200)
    val cg1 = circ1.fill(Orange)
    val cg2 = circ2.fillDraw(Turquoise)
    val cg3 = circ3.draw(lineColour = Orchid)
    val bounds1: Rect = circ1.boundingRect.||(circ3.boundingRect).addMargin(svgMargin)
    val svgs1 = SvgSvgRel(bounds1, RArr(cg1, cg3, cg2), RArr(CentreBlockAtt))

    val rad1 = 125
    val elipse2 = Ellipse(rad1, rad1 / 2)
    val ellipse1 = elipse2.slateX(-rad1 * 2)
    val ellipse3 = elipse2.slateX(rad1 * 2)
    val eg1 = ellipse1.fill(Red)
    val eg2 = elipse2.fillDraw(Pink)
    val eg3 = ellipse3.draw(lineColour = DarkBlue)
    val bounds2: Rect = ellipse1.boundingRect.||(ellipse3.boundingRect).addMargin(svgMargin)
    val svgs2 = SvgSvgRel(bounds2, RArr(eg1, eg2, eg3), RArr(CentreBlockAtt))
  }
}

object LinePathNames extends HtmlSection
{ override def contents: RArr[XConCompound] = RArr(HtmlH2("Line Paths"), p1, list)
  val p1 = HtmlP("Operator naming conventions for sequences and line paths.")

  /** Line path and [[Arr]] operator list. Note Triple [[String]] quotes can be problematic */
  val list = HtmlUl(
  HtmlLi("++ append".htmlScala, "This is a standard scala operator name for appending the adding the operand sequence to the end of this sequence. Example",
  "intArr1 ++ intArr2".htmlScala, "returns a new", "IntArr".htmlScala, ". For the RArr class type widening is allowed. So catsRArr ++ dogsRArr",
  " dogsRArr might return a new RArr[Animal]."),

  HtmlLi("++ append Add the operand line path to the end of this line path returning a new line path."),
  HtmlLi("++ append Add the operand line path to this line path returning a new line path."),
  HtmlLi("|++| appendToPolygon Adds a line path to the end of this line path and closes it into a Polygon."),

  HtmlLi("""%: prepend This is a non standard scala operator name for prepending an element to a sequence The '%' character has been chosen because of left
  | right operator precedence, it makes for better combination with the append element method""".stripMargin),

  HtmlLi("%: prepend Adds a point to the beginning of a line path, returning a new line path"),
  HtmlLi("%<: prependReverse Adds a point to the beginning of the reverse of a line path, returning a new line path"),
  HtmlLi("+% appendElem Adds an element to the end of this sequence. returning a new sequence."),
  HtmlLi("+% appendPt Adds an point to the end of this line path."),
  HtmlLi("|+%| appendPt Adds an point to the end of this line path and close it into a Polygon."),  

  HtmlLi("""+-+ appendTail Add the tail of the operand to the end of this line path returning a new line path. The - between the + characters indicates to drop
  | the first point of the operand.""".stripMargin),

  HtmlLi("|+-+| appendTailToPolygon Add the tail of the operand to the end of this line path closing to a polygon. The - between the + characters indicates" --
    "to drop the first point of the operand."),

  HtmlLi("""++< appendReverse Append the reverse of a line path to a line path returning a new line path. The < after the ++ indicates that it is the operand
  | that is reversed""".stripMargin),

  HtmlLi("|++<| appendReverseToPolygon Append the reverse of a line path to a line path closing it into a polygon. The < character after the ++ indicates" --
    "that it is the operand to be reversed."),

  HtmlLi("""+<+ reverseAppend Reverse this line path and append the operand line path, returning a new line path. The < between the + characters indicates that
  | it is this line segment that is reversed""".stripMargin),

  HtmlLi("|+<+| reverseAppendToPolygon Reverse this line path and then append the operand line path, closing it into a polygon. The < character between the" --
    "+ characters indicates that it is this the first line path that is reversed."),  

  HtmlLi("""+<+< reverseAppendReverse Reverse this line path and append the reverse of the operand line path, returning a new line path. The < between the +
  | characters indicates that this line segement is reversed. The < character after the 2nd + charcters indicates that the operand is also
  | reversed""".stripMargin)
  )
}

object LessonLists extends HtmlSection
{
  import learn.*
  val aList = LessonsLaunch.aList.iMap((i, ls) => HtmlLi("A" + (i + 1).str -- ls.title))
  val bList = LessonsLaunch.bList.iMap((i, ls) => HtmlLi("B" + (i + 1).str -- ls.title))
  val cList = LessonsLaunch.cList.iMap((i, ls) => HtmlLi("C" + (i + 1).str -- ls.title))
  val dList = LessonsLaunch.dList.iMap((i, ls) => HtmlLi("D" + (i + 1).str -- ls.title))
  val eList = LessonsLaunch.eList.iMap((i, ls) => HtmlLi("E" + (i + 1).str -- ls.title))
  override val contents: RArr[XConCompound] = RArr(HtmlUl(aList), HtmlUl(bList), HtmlUl(cList), HtmlUl(dList), HtmlUl(eList))
}