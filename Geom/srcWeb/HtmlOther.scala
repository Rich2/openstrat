/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

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
case class HtmlP(contents: RArr[XCon], attribs: RArr[XmlAtt]) extends HtmlUnvoid
{ def tag = "p"
  def text(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = lineLenDefault) = contents.foldLeft("")(_ + _.out(indent, line1InputLen, maxLineLen))
  override def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = lineLenDefault): String = "<p>" + text(indent, line1InputLen, maxLineLen) + "</p>"
  def textLen: String = text(0)
  override def toString: String = s"HtmlP $textLen characters, $attribsLen attributes"
}

/** Copied from old needs checking. */
object HtmlP
{ /** Factory apply method for creating HTML paragraphs. */
  def apply(strIn: String, attsIn: XmlAtt*): HtmlP = new HtmlP(RArr(strIn.xCon), attsIn.toRArr)
  def apply(contents: XCon*) : HtmlP = new HtmlP(contents.toRArr, RArr())
  /*{ def str: String = strIn
    def con1: XConText = str.xCon
    override val attribs: RArr[XmlAtt] = attsIn.toArr
    override def contents: RArr[XCon] = RArr(con1)

    override def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = lineLenDefault): String = str
    {
      val subt: TextLines = con1.outLines(indent + 2, openUnclosed(indent, line1InputLen, maxLineLen).length)
      val isps = indent.spaces
      val nli = "\n" + indent.spaces
      val nli2 = "\n" + (indent + 2).spaces
      subt.numLines match
      { case 1 if (indent + openTagMinLen + subt.firstLen + closeTagMinLen) <= maxLineLen => isps + openUnclosed + subt.text + closeTag
        case 1 if (indent + openTagMinLen + subt.firstLen) <= maxLineLen  => isps + openUnclosed + nli2 + subt.text + closeTag
        case 1 if (indent + subt.firstLen + 1) <= maxLineLen  => isps + openUnclosed + nli2 + subt.text + closeTag
        case 1 => isps + openUnclosed + nli2 + subt.text + nli + closeTag
        case 2 => isps + openUnclosed + subt.text + closeTag
        case _ => isps + openUnclosed + subt.text + nli + closeTag
      }
    }
  }*/
}

/** HTML noscript element. */
case class HtmlNoScript(contents: RArr[XCon], attribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag: String = "noscript"
}

object HtmlNoScript
{
  def apply(): HtmlNoScript = new HtmlNoScript(RArr("This page will not function properly without Javascript enabled".xCon))
}

/** HTML script element. */
case class HtmlScript(contents: RArr[XCon], attribs: RArr[XmlAtt]) extends HtmlInline
{ override def tag: String = "script"
}

/** Companion object for [[HtmlScript]] class, HTML script element Contains factory methods for creating the src and function call elements. */
object HtmlScript
{ /** Sets the link for a Javascript script file. */
  def jsSrc(src: String): HtmlScript = HtmlScript(RArr(), RArr(TypeJsAtt, SrcAtt(src)))

  /** Sets the function for an external JavaScript call. */
  def main(stem: String): HtmlScript = HtmlScript(RArr(XConText(stem + ".main()")), RArr(TypeJsAtt))
  
  def inlineJsStr(codeStr: String): HtmlScript = HtmlScript(RArr(codeStr.xCon), RArr(TypeJsAtt))
}

/** HTML style element. */
case class HtmlStyle(rules: RArr[CssRuleLike], attribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag: String = "style"
  override def contents: RArr[XCon] = RArr(rules.mkStr(_.out(), "; ").xCon)
}

object HtmlStyle
{
  def apply(rules: CssRuleLike*): HtmlStyle = new HtmlStyle(rules.toArr)
}

/** HTML bold element. */
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

class HtmlButton(val contents: RArr[XCon], val attribs: ostrat.RArr[XmlAtt] = RArr()) extends HtmlInline
{
  override def tag = "button"
}

object HtmlButton{
  def apply(inp: String): HtmlButton = new HtmlButton(RArr(inp.xCon), RArr(TypeSubmitAtt))
}