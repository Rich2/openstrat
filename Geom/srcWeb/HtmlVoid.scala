/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** trait for HTML Void elements such as br img and input. */
sealed trait HtmlVoid extends HtmlElem
{ final override def contents: Arr[XCon] = Arr()
}

/** HTML meta element. */
trait HtmlMeta extends HtmlVoid
{ override def tag: String = "meta"
  override def out(indent: Int, linePosn: Int, lineLen: Int): String = indent.spaces + openUnclosed
}

/** HTML charset='UTF-8' meta element. */
object HtmlUtf8 extends HtmlMeta
{ val utf8Attrib: XmlAtt = XmlAtt("charset", "UTF-8")
  override def attribs: Arr[XmlAtt] = Arr(utf8Attrib)
}

/** Creates the meta element <meta name="viewport" content="width=device-width,initial-scale=1.0" > */
object HtmlViewDevWidth extends HtmlMeta
{ val viewPort: XmlAtt = XmlAtt("name", "viewport")
  val content: XmlAtt = XmlAtt("content", "width=device-width,initial-scale=1.0")
  override def attribs: Arr[XmlAtt] = Arr(viewPort, content)
}