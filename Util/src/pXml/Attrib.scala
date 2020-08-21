/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pXml

/** An XML attribute. */
trait Attrib
{ def name: String
  def valueStr: String
  def str: String = name + "=" + valueStr.enquote
}

case class NumericAttrib(name: String, num: Double) extends Attrib
{ override def valueStr: String = num.toString
}
