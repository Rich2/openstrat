/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** trait for short attributes that should never require multiple lines in the editor. */
trait XAttShort extends XAtt
{ /** The [[String]] for the attribute value. */
  def valueStr: String
  
  override def valueOutLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines = TextLines(valueStr)
}

/** Will probably change name to XAttSimple. */
case class XmlAttGen(name: String, valueStr: String) extends XAttShort

/** Trait for XML /HTML attributes that take an integer value. */
trait XAttInt extends XAttShort
{ /** The integer value of this attribute. */
  def num: Int

  override def valueStr: String = num.str
}

object XAttInt
{ /** Factory apply method for XML / HTML  attribute that takes an integer value. */
  def apply(name: String, num: Int): XAttInt = new XAttIntGen(name, num)
  
  /** General implementation class for attribute that takes an integer value. */
  case class XAttIntGen(name: String, num: Int) extends XAttInt
}

/** Creates for an "id" XML / HTML attribute." */
case class IdAtt(valueStr: String) extends XAttShort
{ override def name: String = "id"
}

/** Creates for a "class" XML / HTML attribute." */
class ClassAtt(val valueStr: String) extends XAttShort
{ override def name: String = "class"
}

object ClassAtt
{ /** Factory apply method for HTML class attribute. */
  def apply(classStrs: String*): ClassAtt = new ClassAtt(classStrs.mkString(" "))
}

/** rel XML /HTML attribute */
case class RelAtt(valueStr: String) extends XAttShort
{ override def name: String = "rel"
}

/** Relative stylesheet HTML attribute. */
object RelStylesheet extends RelAtt("stylesheet")

/** href XML /HTML attribute */
case class HrefAtt(valueStr: String) extends XAttShort
{ override def name: String = "href"
}

/** SVG favicon href attribute." */
object FaviconSvgHref extends HrefAtt("/images/favicon.svg")

case class TypeAtt(valueStr: String) extends XAttShort
{ override def name: String = "type"
}

/** Type attribute set to text. */
object TypeTextAtt extends TypeAtt("text")

/** The CSS type attribute */
object TypeCssAtt extends TypeAtt("text/css")

/** The SVG type attribute */
object TypeSvgAtt extends TypeAtt("image/svg+xml")

/** Type attribute set to number. */
object TypeNumberAtt extends TypeAtt("number")

/** Type attribute set to text/javascript. */
object TypeJsAtt extends TypeAtt("text/javascript")

/** Type attribute set to submit. */
object TypeSubmitAtt extends TypeAtt("submit")

case class SrcAtt(valueStr: String) extends XAttShort
{ override def name: String = "src"
}

case class TextAnchorAtt(valueStr: String) extends XAttShort
{ override def name: String = "text-anchor"
}

/** Creates for an HTML / XML content attribute." */
case class ContentAtt(valueStr: String) extends XAttShort
{ override def name: String = "content"
}

/** Creates for an HTML http-equiv attribute." */
case class HttpEquivAtt(valueStr: String) extends XAttShort
{ override def name: String = "http-equiv"
}

/** The XML value attribute. */
case class NameAtt(valueStr: String) extends XAttShort
{ override def name: String = "name"
}

/** The XML value attribute. */
case class ValueAtt(valueStr: String) extends XAttShort
{ override def name: String = "value"
}

/** The XML value attribute. */
case class VersionAtt(valueStr: String) extends XAttShort
{ override def name: String = "version"
}

/** The XML xmlns namespace attribute. */
case class XmlnsAtt(valueStr: String) extends XAttShort
{ override def name: String = "xmlns"
}

/** The XML xmlns:xsi schema attribute. */
case class XmlNsXsi(valueStr: String) extends XAttShort
{ override def name: String = "xmlns:xsi"
}

/** The XML xmlns:schemaLocation schema attribute. */
case class XsiSchemaLoc(valueStr: String) extends XAttShort
{ override def name: String = "xsi:schemaLocation"
}