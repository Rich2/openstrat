/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

object UtilPom extends XmlElem
{
  /** The XML /HTML tag String. A tag is a markup construct that begins with < and ends with > */
  override def tag: String = ???

  /** The attributes of this XML / HTML element. */
  override def attribs: RArr[XmlAtt] = ???

  /** The content of this XML / HTML element. */
  override def contents: RArr[XCon] = ???

  /** Returns the XML / HTML source code, formatted according to the input. This allows the XML to be indented according to its context. */
  override def out(indent: Int, line1Delta: Int, maxLineLen: Int): String = ???
}