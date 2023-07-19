/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML code element. */
case class HtmlCode(contentStr: String, attribs: RArr[XmlAtt] = RArr()) extends HtmlUnvoid
{ override def tag: String = "code"
  override def contents: RArr[XCon] = RArr(contentStr.xCon)
  override def out(indent: Int = 0, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String = openUnclosed + contentStr + closeTag
}

/** HTML A anchor element. */
class HtmlA(val link: String, val contents: RArr[XCon], otherAttribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag: String = "a"
  override val attribs: RArr[XmlAtt] = RArr(HrefAtt(link)) ++ otherAttribs
}

object HtmlA
{ /** Factory apply method for [[HtmlA]] class. */
  def apply(link: String, label: String): HtmlA = new HtmlA(link, RArr(label.xCon))
}

trait HtmlP extends HtmlUnvoid
{ def tag = "p"
}

/** Copied from old needs checking. */
object HtmlP
{
  def apply(strIn: String, attsIn: XmlAtt*): HtmlP = new HtmlP
  { def str: String = strIn
    def con1: XConText = str.xCon
    override val attribs: RArr[XmlAtt] = attsIn.toArr
    override def contents: RArr[XCon] = RArr(con1)
    override def out(indent: Int, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String = con1.outLines(indent + 2, openUnclosed.length) match
    { case TextIn1Line(text, _) => indent.spaces + openUnclosed + text + closeTag
      case TextIn2Line(text, _) => indent.spaces + openUnclosed + text + closeTag
      case TextInMultiLines(text, _) => indent.spaces + openUnclosed + text --- indent.spaces + closeTag
    }
  }
}

/** Html LI, list item element. */
case class HtmlLi(contents: RArr[XCon], attribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag: String = "li"
}

/** Companion object for HTML LI list element class, contains multiple methods fpr their construction. */
object HtmlLi
{ /** Factory apply method for HTML LI list element [[HtmlLi]] class. */
  def apply(contents: XCon*): HtmlLi = new HtmlLi(contents.toArr)

  /** An HTML list item element that has a link as its sole content. */
  def a(link: String, label: String, attribs: XmlAtt*): HtmlLi = new HtmlLi(RArr( new HtmlA(link, RArr(label.xCon))), attribs.toArr)

  /** An HTML list item element that has a link, followed by some text as its sole contents. */
  def linkAndText(link: String, label: String,otherText: String, attribs: XmlAtt*): HtmlLi =
    new HtmlLi(RArr(new HtmlA(link, RArr(label.xCon)), otherText.xCon), attribs.toArr)

  def str(text: String): HtmlLi = HtmlLi(RArr(text.xCon))
}

/** Html UL unordered list element. */
case class HtmlUl(val contents: RArr[XCon], val attribs: RArr[XmlAtt] = RArr()) extends HtmlMultiLine
{ override def tag: String = "ul"
}

/** Html OL ordered list element. */
case class HtmlOl(val contents: RArr[XCon], val attribs: RArr[XmlAtt] = RArr()) extends HtmlMultiLine
{ override def tag: String = "ol"
}

object HtmlOl
{ /** Factory apply method for HTML OL orderd list. */
  def apply(contents: XCon*): HtmlOl = new HtmlOl(contents.toArr)
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
  def main(stem: String): HtmlScript = HtmlScript(RArr(XConText(stem + ".main()")), RArr(TypeAtt.js))
}

case class HtmlB(str: String) extends HtmlInline{
  /** The XML /HTML tag String. A tag is a markup construct that begins with < and ends with > */
  override def tag: String = "b"

  /** The attributes of this XML / HTML element. */
  override def attribs: RArr[XmlAtt] = RArr()

  /** The content of this XML / HTML element. */
  override def contents: RArr[XCon] = RArr(str.xCon)
}

/** Html H1 header element. */
case class HtmlH1(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ override def tag = "h1"
}

/** Html H2 header element. */
case class HtmlH2(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ def tag = "h2"
}

/** Html H3 header element. */
case class HtmlH3(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ def tag = "h3"
}

/** Html H4 header element. */
case class HtmlH4(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ def tag = "h4"
}