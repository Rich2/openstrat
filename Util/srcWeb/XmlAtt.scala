/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML / HTML attribute, has a name and a value [[StrArr]]. */
trait XmlAtt
{ def name: String
  def valueStr: String
  def str: String = name + "=" + valueStr.enquote1
}

/** Companion object for the XML attribute [[XmlAtt]] trait. */
object XmlAtt
{
  def apply(nameIn: String, valueStrIn: String): XmlAtt = new XmlAtt
  { override def name: String = nameIn
    override def valueStr: String = valueStrIn
  }
}

/** An Xml numeric attribute. */
trait XANumeric extends XmlAtt
{ def value: Double
  override def valueStr: String = value.toString
}

object XANumeric
{
  def apply(nameIn: String, valueIn: Double): XANumeric = new XANumeric
  { override def name: String = nameIn
    override def value: Double = valueIn    
  }
}

case class IdAtt(valueStr: String) extends XmlAtt
{ override def name: String = "id"
}

case class HrefAtt(valueStr: String) extends XmlAtt
{ override def name: String = "href"
}