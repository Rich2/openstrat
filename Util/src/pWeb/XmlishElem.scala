/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

/** An XML or an HTML element */
trait XmlishElem extends XCon
{ def tag: String
  def attribs: Arr[XmlAtt]
  def content: Arr[XCon]
  def openTag: String = "<" + tag + ">"
  def closeTag: String = "</" + tag + ">"
}

/** Content for XML and HTML. */
trait XCon
{
  def out: String

}

object XCon
{

}

/** XConStr is a wrapper to convert [[String]]s to XCon, XML Element content. */
case class XConStr(value: String) extends XCon
{
  override def out: String = value
}

object XConStr
{ implicit def StringToXConStr(value: String): XConStr = new XConStr(value)
}