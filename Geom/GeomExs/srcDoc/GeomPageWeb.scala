/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** HTML documentation for [[Polygon]]s for the [[GeomPage]]. */
object GeomPageWeb extends HtmlSection
{
  override def contents: RArr[XCon] = RArr(HtmlH2("XML, HTML and CSS"), p2)

  def p1 = HtmlOlWithLH("The package consists of a number of types of objects",
  HtmlLi.boldName("XHml elements", "Xml and Html elements with their opening and closing tags and HTML void elements with just an opening tag."),
    HtmlLi("XAtt attributes", "XML and HTML attributes"),
    HtmlLi.boldName("CSS", "CSS rules and declarations"),
    HtmlLi.boldName("SVG", "Scalable Vector Graphics. This is just a form of XML."),
    HtmlLi.boldName("XCon XML and HTML content", """This consists of XConElems and plain Strings. XCon can be divided into 3 types, depending on how it is
    |formatted as HTML code. Note this is different to how it is formatted in the browser."""))

  def p2 = HtmlOlWithLH.strs("""XML / HTML content is typed as XCon. XCon consists of """.stripMargin,

  """Inline elements, including plain Strings and spans. The elements continue on the same line following another inline element. White space distinctions
  |between new lines, tabs and spaces in the element are ignored and the white space is reformatted into single space and new lines to fit the indentations and
  |line lengths of the XML / HTML / CSS output.""".stripMargin,

  """OwnLine elements, including list items. These element may be encapsulated on a single line, but may not share a line with sibling content. They may
  |however be enclosed within parent tags on a single line, if it is the only child content of its parent element.""".stripMargin,

  """MultiLine elements, including Lists, Body, Section. These elements opening  and closing tags must appear on their own lines, separate from both the parent
  |element tags and their own content.""".stripMargin
  )
}