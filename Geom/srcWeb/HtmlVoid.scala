/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** trait for HTML Void elements such as br img and input. */
sealed trait HtmlVoid extends HtmlElem
{ final override def contents: RArr[XCon] = RArr()
  override def out(indent: Int, maxLineLen: Int): String = indent.spaces + openUnclosed
}

/** HTML meta element. */
trait HtmlMeta extends HtmlVoid
{ override def tag: String = "meta"
}

/** HTML charset='UTF-8' meta element. */
object HtmlUtf8 extends HtmlMeta
{ val utf8Attrib: XmlAtt = XmlAtt("charset", "UTF-8")
  override def attribs: RArr[XmlAtt] = RArr(utf8Attrib)
}

/** Creates the meta element <meta name="viewport" content="width=device-width,initial-scale=1.0" > */
object HtmlViewDevWidth extends HtmlMeta
{ val viewPort: XmlAtt = XmlAtt("name", "viewport")
  val content: XmlAtt = XmlAtt("content", "width=device-width,initial-scale=1.0")
  override def attribs: RArr[XmlAtt] = RArr(viewPort, content)
}

/** HTML CSS link. */
class HtmlCssLink(val fullFileName: String) extends HtmlVoid
{ override def tag: String = "link"
  override def attribs: RArr[XmlAtt] = RArr(XmlAtt("rel", "stylesheet"), XmlAtt("type", "text/css"), XmlAtt("href", fullFileName))
}


/** Companion object for [[HtmlCssLink]] class, contains factory apply methid. */
object HtmlCssLink
{ /** Factory apply method for [[HtmlCssLink]] class form filename stem, adds the .css file ending. */
  def apply(fileNameStem: String): HtmlCssLink = new HtmlCssLink(fileNameStem + ".css")
}