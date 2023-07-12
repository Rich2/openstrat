/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML code element. */
case class HtmlCode(contentStr: String, attribs: RArr[XmlAtt] = RArr()) extends HtmlUnvoid
{ override def tag: String = "code"
  override def contents: RArr[XCon] = RArr(contentStr.xCon)
  override def out(indent: Int = 0, maxLineLen: Int = 150): String = openUnclosed + contentStr + closeTag
}

/** HTML anchor. */
class HtmlA(val link: String, val contents: RArr[XCon], otherAttribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag: String = "a"
  override val attribs: RArr[XmlAtt] = RArr(HrefAtt(link)) ++ otherAttribs
}

object HtmlA
{ def apply(link: String, label: String): HtmlA = new HtmlA(link, RArr(label.xCon))
}

/** Html li, list item element. */
case class HtmlLi(contents: RArr[XCon], attribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag: String = "li"
}

object HtmlLi
{
  def a(link: String, label: String, attribs: XmlAtt*): HtmlLi = new HtmlLi(RArr( new HtmlA(link, RArr(label.xCon))), attribs.toArr)

  def linkAndText(link: String, label: String,otherText: String, attribs: XmlAtt*): HtmlLi =
    new HtmlLi(RArr(new HtmlA(link, RArr(label.xCon)), otherText.xCon), attribs.toArr)
}

/** Html ul unordered list element. */
case class HtmlUl(val contents: RArr[XCon], val attribs: RArr[XmlAtt] = RArr()) extends HtmlMultiLine
{ override def tag: String = "ul"
}

/** Html ul unordered list element. */
case class HtmlOl(val contents: RArr[XCon], val attribs: RArr[XmlAtt] = RArr()) extends HtmlMultiLine
{ override def tag: String = "ol"
}

/** HTML script element. */
case class HtmlScript(val contents: RArr[XCon], val attribs: RArr[XmlAtt]) extends HtmlInline
{ override def tag: String = "script"
}

/** Companon object for [[HtmlScript]] class, HTML script element Contains factory methods for creating the src and function call elements. */
object HtmlScript
{ /** Sets the link for a Javascript script file. */
  def jsSrc(src: String): HtmlScript = HtmlScript(RArr(), RArr(TypeAtt.js, SrcAtt(src)))

  /** Sets the function for an external JavaScript call. */
  def main(stem: String): HtmlScript = HtmlScript(RArr(XConStr(stem + ".main()")), RArr(TypeAtt.js))
}

/** Html h1 header element. */
case class HtmlH1(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ override def tag = "h1"
}

/** Html h2 header element. */
case class HtmlH2(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ def tag = "h2"
}

/** Html h3 header element. */
case class HtmlH3(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ def tag = "h3"
}