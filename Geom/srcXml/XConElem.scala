/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Content for XML and HTML elements. You can use plain [[String]]s, however other content classes must implement the out and outLines interfaces. The outLines
 * method allows the parent element to the content to format itself according to the number of lines and the length of the first and last lines. it is not the
 * responsibility of the content to indent its first line or provided a newline before its first line, or to provide a new line after its last line. This is the
 * responsibility of the parent element. Note in the case of HTML elements we are refering to the formatting of the HTML file in a text editor, not how it will
 * be displayed in a browser. */
trait XConElem
{ /** Returns the XML / HTML source code, formatted according to the input. This allows the XML to be indented according to its context. This will generally use
   * the outLines method for its implementation. */
  def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = MaxLineLen): String

  /** This method returns the HTML output code, but also information for the parent XML / HTML element. The class should not add any indentation to its first
   * line. This is the responsibility of the parent element. */
  def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines = TextLines(out(indent, maxLineLen), 3, 30, 30)
}

/** XML / HTML just stored as a [[String]]. This is not desirable, except as a temporary expedient. */
case class XmlAsString(value: String) extends XConElem
{ override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = value
  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines = TextLines(value, 1, value.length, value.length)
}

/** Not sure about this trait. It is intended for short pieces of text that should be kept on the same line. */
trait XmlConStr extends XHmlOwnLine
{ def str: String
  override def contents: RArr[XCon] = RArr(str)
}