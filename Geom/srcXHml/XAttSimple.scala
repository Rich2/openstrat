/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait XAttSimple extends XAtt
{
  def valueStr: String
  
  override def valueOutLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines = TextLines(valueStr)
}

case class XmlAttGen(name: String, valueStr: String) extends XAttSimple

/** Creates for an "id" XML / HTML attribute." */
case class IdAtt(valueStr: String) extends XAttSimple
{ override def name: String = "id"
}

/** Creates for a "class" XML / HTML attribute." */
case class ClassAtt(valueStr: String) extends XAttSimple
{ override def name: String = "class"
}

case class HrefAtt(valueStr: String) extends XAttSimple
{ override def name: String = "href"
}

case class TypeAtt(valueStr: String) extends XAttSimple
{ override def name: String = "type"
}
/** Type attribute set to text. */
object TypeTextAtt extends TypeAtt("text")

/** Type attribute set to text/javascript. */
object TypeJsAtt extends TypeAtt("text/javascript")

/** Type attribute set to submit. */
object TypeSubmitAtt extends TypeAtt("submit")

case class SrcAtt(valueStr: String) extends XAttSimple
{ override def name: String = "src"
}

case class TextAnchorAtt(valueStr: String) extends XAttSimple
{ override def name: String = "text-anchor"
}

/** Creates for an HTML / XML content attribute." */
case class ContentAtt(valueStr: String) extends XAttSimple
{ override def name: String = "content"
}

/** Creates for an HTML http-equiv attribute." */
case class HttpEquivAtt(valueStr: String) extends XAttSimple
{ override def name: String = "http-equiv"
}

/** The XML value attribute. */
case class NameAtt(valueStr: String) extends XAttSimple
{ override def name: String = "name"
}

/** The XML value attribute. */
case class ValueAtt(valueStr: String) extends XAttSimple
{ override def name: String = "value"
}

/** The XML value attribute. */
case class VersionAtt(valueStr: String) extends XAttSimple
{ override def name: String = "version"
}

/** The XML xmlns namespace attribute. */
case class Xmlns(valueStr: String) extends XAttSimple
{ override def name: String = "xmlns"
}

/** The XML xmlns:xsi schema attribute. */
case class XmlNsXsi(valueStr: String) extends XAttSimple
{ override def name: String = "xmlns:xsi"
}

/** The XML xmlns:schemaLocation schema attribute. */
case class XsiSchemaLoc(valueStr: String) extends XAttSimple
{ override def name: String = "xsi:schemaLocation"
}

/** The Style attribute for inline CSS. */
class StyleAtt(decs: RArr[CssDec]) extends XAttSimple
{ override def name: String = "style"

  override def valueStr: String = decs.mkStr(_.out, " ")
}

object StyleAtt
{
  def apply(decs: RArr[CssDec]): StyleAtt = new StyleAtt(decs)
  def apply(decs: CssDec*): StyleAtt = new StyleAtt(decs.toArr)
}