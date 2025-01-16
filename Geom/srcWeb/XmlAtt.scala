/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML / HTML attribute, has a name and a value [[StrArr]]. */
trait XmlAtt
{ def name: String
  def valueStr: String
  def out: String = name + "=" + valueStr.enquote1
}

/** Companion object for the XML attribute [[XmlAtt]] trait. */
object XmlAtt
{ /** Factory apply method for [[XmlAtt]] trait. Often you may prefer to use the subclasses of [[XmlAtt]] where the name of the attribute has already
   * been set. */
  def apply(name: String, valueStr: String): XmlAtt = XmlAttGen(name, valueStr)

  implicit class rArrExtensions(val thisArr: RArr[XmlAtt])
  { def explicitFill: RArr[XmlAtt] = ife(thisArr.exists(_.name == "fill"), thisArr, thisArr +% FillAttrib.none)
  }  
}

case class XmlAttGen(name: String, valueStr: String) extends XmlAtt

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

/** Type attribute set to text/javascript. */
object TypeJsAtt extends TypeAtt("text/javascript")

/** Type attribute set to submit. */
object TypeSubmitAtt extends TypeAtt("submit")

case class SrcAtt(valueStr: String) extends XmlAtt
{ override def name: String = "src"
}

case class TextAnchorAtt(valueStr: String) extends XmlAtt
{ override def name: String = "text-anchor"
}

/** Creates for an HTML / XML content attribute." */
case class ContentAtt(valueStr: String) extends XmlAtt
{ override def name: String = "content"
}

/** Creates for an HTML http-equiv attribute." */
case class HttpEquivAtt(valueStr: String) extends XmlAtt
{ override def name: String = "http-equiv"
}

/** The XML value attribute. */
case class NameAtt(valueStr: String) extends XmlAtt
{ override def name: String = "name"
}

/** The XML value attribute. */
case class ValueAtt(valueStr: String) extends XmlAtt
{ override def name: String = "value"
}

/** The XML value attribute. */
case class VersionAtt(valueStr: String) extends XmlAtt
{ override def name: String = "version"
}

/** The XML xmlns namespace attribute. */
case class Xmlns(valueStr: String) extends XmlAtt
{ override def name: String = "xmlns"
}

/** The XML xmlns:xsi schema attribute. */
case class XmlNsXsi(valueStr: String) extends XmlAtt
{ override def name: String = "xmlns:xsi"
}

/** The XML xmlns:schemaLocation schema attribute. */
case class XsiSchemaLoc(valueStr: String) extends XmlAtt
{ override def name: String = "xsi:schemaLocation"
}