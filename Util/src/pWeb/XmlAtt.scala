/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

/** An XML attribute. */
trait XmlAtt
{ def name: String
  def valueStr: String
  def str: String = name + "=" + valueStr.enquote
}

/** An Xml numeric attribute. */
case class XANumeric(name: String, num: Double) extends XmlAtt
{ override def valueStr: String = num.toString
}
