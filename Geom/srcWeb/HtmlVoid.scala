/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** trait for HTML Void elements such as br img and input. */
sealed trait HtmlVoid extends HtmlInline
{ final override def contents: RArr[XConElem] = RArr()
  override def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = 150): String = indent.spaces + openUnclosed(indent + 2, 0)
}

/** An Html br line break element. */
object HtmlBr extends HtmlVoid
{ override def tag: String = "br"
  override def attribs: RArr[XAtt] = RArr()
}


/** HTML meta element. */
trait HtmlMeta extends HtmlVoid
{ override def tag: String = "meta"
}

/** HTML charset='UTF-8' meta element. */
object HtmlUtf8 extends HtmlMeta
{ val utf8Attrib: XAtt = XAtt("charset", "UTF-8")
  override def attribs: RArr[XAtt] = RArr(utf8Attrib)
}

/** Creates the meta element no-cache. */
object HtmlNoCache extends HtmlMeta
{ val equiv: XAtt = HttpEquivAtt("cache-Control")
  val content: XAtt = ContentAtt("no-cache")

  override def attribs: RArr[XAtt] = RArr(equiv, content)
}

/** Creates the meta element name="viewport" content="width=device-width,initial-scale=1.0" */
object HtmlViewDevWidth extends HtmlMeta
{ val viewPort: XAtt = XAtt("name", "viewport")
  val content: XAtt = ContentAtt("width=device-width,initial-scale=1.0")
  override def attribs: RArr[XAtt] = RArr(viewPort, content)
}

/** HTML CSS link. */
class HtmlCssLink(val fullFileName: String) extends HtmlVoid
{ override def tag: String = "link"
  override def attribs: RArr[XAtt] = RArr(XAtt("rel", "stylesheet"), XAtt("type", "text/css"), XAtt("href", fullFileName))
}


/** Companion object for [[HtmlCssLink]] class, contains factory apply methid. */
object HtmlCssLink
{ /** Factory apply method for [[HtmlCssLink]] class form filename stem, adds the .css file ending. */
  def apply(fileNameStem: String): HtmlCssLink = new HtmlCssLink(fileNameStem + ".css")
}

/** An HTML input element. */
trait HtmlInput extends HtmlVoid
{ def typeAtt: TypeAtt
  override def tag: String = "input"
}