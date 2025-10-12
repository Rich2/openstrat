/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML element that should not share a line with sibling content, but not a multiline element such as an OL or a UL, which will be multi line. */
trait HtmlOwnLine extends HtmlElem, XHmlOwnLine

/** An HTML element where the CSS display declaration in the style attribute is set to Block, as the element is not Block by default. These can be useful as
 * they can be placed inside an HTML p paragraph element, which is forbidden to elements that are block by default. */
trait HtmlOwnLineBlocked extends HtmlOwnLine
{ override def attribs: RArr[XAtt] = RArr(StyleAtt(BlockDec))
}

/** An HTML whose contents can be represented by a [[String]]. */
trait HtmlStrOwnLine extends HtmlOwnLine
{ def str: String
  override def contents: RArr[XCon] = RArr(str)
}

/** HTML content code that mey need BRs separate it from preceding and successive inline and other [[HtmlBrLine]] content */
trait HtmlBrLine extends HtmlOwnLineBlocked

/** An inline-block HTML eleement */
trait HtmlInlineBlock extends HtmlElem

trait HtmlInlineBlocked extends HtmlOwnLine
{ override def attribs: RArr[XAtt] = RArr(StyleAtt(BlockDec))
}