/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML / HTML attribute, has a name and a value [[StrArr]]. */
trait XmlAtt
{ def name: String
  def valueStr: String
  def str: String = name + "=" + valueStr.enquote1
}

/** Companion object for the XML attribute [[XmlAtt]] trait. */
object XmlAtt
{ /** Factory apply method for [[XmlAtt]] trait. Often you may prefer to use the sub classes of [[XmlAtt]] where the name of the attribute has already
   * been set. */
  def apply(nameIn: String, valueStrIn: String): XmlAtt = new XmlAtt
  { override def name: String = nameIn
    override def valueStr: String = valueStrIn
  }
}

/** An Xml attribute that has a numeric value, allowing the value to be constructed with a
 *  [[Double]]. */
trait XAttNumeric extends XmlAtt
{ def value: Double
  override def valueStr: String = value.toString
}

/** /companion object for the [[XAttNumeric]] trait, an Xml attribute that has a numeric value, allowing the value to be constructed with a
 *  [[Double]]. */
object XAttNumeric
{
  /** Factory apply method for an Xml attribute that has a numeric value, allowing the value to be constructed with a [[Double]].  */
  def apply(nameIn: String, valueIn: Double): XAttNumeric = new XAttNumeric
  { override def name: String = nameIn
    override def value: Double = valueIn    
  }
}

/** Creates for an "id" XML / HTML attribute." */
case class IdAtt(valueStr: String) extends XmlAtt
{ override def name: String = "id"
}

/** Creates for a "class" XML / HTML attribute." */
case class ClassAtt(valueStr: String) extends XmlAtt
{ override def name: String = "class"
}

case class HrefAtt(valueStr: String) extends XmlAtt
{ override def name: String = "href"
}

case class TypeAtt(valueStr: String) extends XmlAtt
{ override def name: String = "type"
}

object TypeAtt{
  def js: TypeAtt = TypeAtt("text/javascript")
}

case class SrcAtt(valueStr: String) extends XmlAtt
{ override def name: String = "src"
}