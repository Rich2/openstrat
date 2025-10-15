/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An element that outputs a String. This could be XML HTML other HTTP content */
trait OutElem
{ /** The output [[String]]. Includes HTML and CSS code. */
  def out: String
}

/** Content for XML and HTML elements. You can use plain [[String]]s, however other content classes must implement the out and outLines interfaces. The outLines
 * method allows the parent element to the content to format itself according to the number of lines and the length of the first and last lines. it is not the
 * responsibility of the content to indent its first line or provided a newline before its first line, or to provide a new line after its last line. This is the
 * responsibility of the parent element. Note in the case of HTML elements we are referring to the formatting of the HTML file in a text editor, not how it will
 * be displayed in a browser. */
trait XConElem extends OutElem
{ /** Returns the XML / HTML source code, formatted according to the input. This allows the XML to be indented according to its context. This will generally use
   * the outLines method for its implementation. */
  def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String

  /** This method returns the HTML output code, but also information for the parent XML / HTML element. The class should not add any indentation to its first
   * line. This is the responsibility of the parent element. */
  def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines = TextLines(out(indent, line1InputLen, maxLineLen))
}

/** An [[XConElem]] that has simple unindented output */
trait XConSimple extends XConElem
{ override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = out
}

/** An [[XConElem]] that has a compound, potentially multi line output */
trait XConCompound extends XConElem
{ override def out: String = out(0, 0, MaxLineLen)
}

/** XML / HTML just stored as a [[String]]. This is not desirable, except as a temporary expedient. */
case class XmlAsString(out: String) extends XConSimple
{ //override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines = TextLines(out)
}

/** An XML / HTML comment */
case class XComment(str: String) extends XConSimple
{ override def out: String = "<!-- " + str -- "-->"
}