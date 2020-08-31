/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

/** An XML or an HTML element */
trait XmlishElem extends XCon
{ def tag: String
  def attribs: Arr[XmlAtt]
  def content: Arr[XCon]
  def openTag: String = "<" + tag + ">"
  
  def openAtts: String = "<" + tag -- attribs.toStrsFold(" ", _.str) + " "
  def openUnclosed: String = openAtts + ">"
  def openTag1: String = openTag + "\n"
  def openTag2: String = openTag + "\n\n"
  def closeTag: String = "</" + tag + ">"
  def n1CloseTag: String = "\n" + closeTag
  def n2CloseTag: String = "\n\n" + closeTag
}

trait XmlElem extends XmlishElem
{
  def openVoid: String = openAtts + "/>"
}

/** Content for XML and HTML. */
trait XCon
{ /** Returns the XML source code, formatted according to the input. */
  def out(indent: Int, linePosn: Int, lineLen: Int): String
}

/** XConStr is a wrapper to convert [[String]]s to XCon, XML Element content. */
case class XConStr(value: String) extends XCon
{
  override def out(indent: Int, linePosn: Int, lineLen: Int): String = value
}

object XConStr
{ implicit def StringToXConStr(value: String): XConStr = new XConStr(value)
}