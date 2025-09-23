/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** HTML documentation for [[Polygon]]s for the [[GeomPage]]. */
object GeomPageWeb extends HtmlSection
{
  override def contents: RArr[XCon] = RArr(HtmlH2("XML, HTML and CSS"), s1)

  def s1 = HtmlOlWithLH("The package consists of a number of types of objects",
    HtmlLi("XHml elements".htmlB, "Xml and Html elements with their opening and closing tags and HTML void elements with just an opening tag."),
    HtmlLi("XAtt attributes".htmlB,"""XML and HTML attributes. They are not called XHAtts, because in this regard HTML is consistent with XML and there is no
    |need to distinguish between XML and HTML attributes, as with elements, where XML and HTML has different rules.""".stripMargin),
    HtmlLi("CSS".htmlB, "CSS rules and declarations"),
    HtmlLi("SVG".htmlB, "Scalable Vector Graphics. This is just a form of XML."),
    HtmlLi("XCon XML and HTML content".htmlB, """This consists of XConElems and plain Strings. XConElems includes XML and HTML elements but also includes CSS
    |rules as they can be used as content in an HTML style element. XCon can be divided into 3 types, depending on how it is formatted as HTML code. Note this
    |is different to how it is formatted in the browser.""".stripMargin,
    HtmlUl(
      HtmlLi("Inline elements".htmlB, """Includes plain Strings and spans. The elements continue on the same line following another inline element. White
      |space distinctions between new lines, tabs and spaces in the element are ignored and the white space is reformatted into single space and new lines to
      |fit the indentations and line lengths of the XML / HTML / CSS output.""".stripMargin),
      HtmlLi("OwnLine elements".htmlB, """Includes list items. These element may be encapsulated on a single line, but may not share a line with sibling
      |content. They may however be enclosed within parent tags on a single line, if it is the only child content of its parent element.""".stripMargin),
      HtmlLi("MultiLine elements".htmlB, """Includes Lists, Body, Section. These elements opening  and closing tags must appear on their own lines, separate
      |from both the parent element tags and their own content.""".stripMargin))
    )
  )
}