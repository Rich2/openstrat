/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

/** An XML or an HTML element */
trait XmlishElem extends XCon
{ def tag: String
  def attribs: Arr[XmlAtt]
  def content: Arr[XCon]
  def out: String
}

/** Content for XML and HTML. */
trait XCon
{
  implicit class StringExtension(thisString: String)
  {
    def xCon: XConStr = XConStr(thisString)
  }
}

/** XConStr is a wrapper to convert [[String]]s to XCon, XML Element content. */
case class XConStr(value: String) extends XCon

object XConStr
{ implicit def StringToXConStr(value: String): XConStr = new XConStr(value)
}