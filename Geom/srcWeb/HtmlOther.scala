/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML code element. */
trait HtmlCode/*(contentStr: String, attribs: RArr[XmlAtt] = RArr())*/ extends HtmlUnvoid
{ override def tag: String = "code"

  //override def contents: RArr[XCon] = RArr(contentStr.xCon)
  //override def out(indent: Int = 0, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String = openUnclosed + contentStr + closeTag
}

trait HtmlCodeInline extends HtmlCode with HtmlInline

trait HtmlSbt extends HtmlCode
{
  def classAtt: ClassAtt = ClassAtt("sbt")

  override def attribs: RArr[XmlAtt] = RArr(classAtt)
}

trait HtmlSbtInline extends HtmlSbt with HtmlCodeInline

object HtmlSbtInline
{
  def apply(str: String): HtmlSbtInline = new HtmlSbtInline
  { override def contents: RArr[XCon] = RArr(str.xCon)
  }
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

/** HTML P paragraph element. */
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

case class HtmlB(str: String) extends HtmlInline
{ override def tag: String = "b"

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