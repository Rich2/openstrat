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

  implicit class rArrExtensions(val thisArr: RArr[XmlAtt])
  {
    def explicitFill: RArr[XmlAtt] = ife(thisArr.exists(_.name == "fill"), thisArr, thisArr +% FillAttrib.none)
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

object TypeAtt
{ def js: TypeAtt = TypeAtt("text/javascript")
}

case class SrcAtt(valueStr: String) extends XmlAtt
{ override def name: String = "src"
}