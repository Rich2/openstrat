/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML element that we may wish to inline such as an LI list item, as opposed to a OL or a UL, which will be multi line. */
trait HtmlOwnLine extends HtmlElem, XHmlOwnLine

/** An HTML whose contents can be represented by a [[String]]. */
trait HtmlStrOwnLine extends HtmlOwnLine
{ def str: String
  override def contents: RArr[XCon] = RArr(str)
}

/** HTML content code that mey need BRs separate it from preceding and sucessive inline and other [[HtmlBrLine]] content */
trait HtmlBrLine extends HtmlOwnLine