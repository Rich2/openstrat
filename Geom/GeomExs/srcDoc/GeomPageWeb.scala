/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import geom.*, pWeb.*, Colour.*

/** HTML documentation for [[Polygon]]s for the [[GeomPage]]. */
object GeomPageWeb extends HtmlSection
{
  override def contents: RArr[XCon] = RArr(HtmlH2("XML, HTML and CSS"), p1)

  def p1 = HtmlOlWithLH.strs("""XML / HTML content is typed as XCon. XCon consists of XConElems and plain Strings. XCon can be divided into 3 types, depending on how
  |it is formatted as HTML code. Note this is different to how it is formatted in the browser.""".stripMargin,

  """Inline elements, including plain Strings and spans. The elements continue on the same line following another inline element. White space distinctions
  |between new lines, tabs and spaces in the element are ignored and the white space is reformatted into single space and new lines to fit the indentations and
  |line lengths of the XML / HTML / CSS output.""".stripMargin,

  """OwnLine elements, including list items. These element may be encapsulated on a single line, but may not share a line with sibling content. They may
  |however be enclosed within parent tags on a single line, if it is the only child content of its parent element.""".stripMargin,

  """MultiLine elements, incliding Lists, Body, Section. These elements opening  and closing tags must appear on their own lines, spearate from both the parent
  |element tags and the content.""".stripMargin
  )
}