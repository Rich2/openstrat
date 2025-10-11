/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML Pre element. Unlike most other [[HtmlElem]]s this only takes a [[String]] as its content. */
class HtmlPre(val str: String, val attribs: RArr[XAtt]) extends HtmlTagLines
{ override def tagName: String = "pre"
  override def contents: RArr[XCon] = RArr(str)

  override def out: String = openTag(0, 0, MaxLineLen) + str + closeTag
  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines =
  { val res = openTag(indent, line1InputLen, maxLineLen) + str + closeTag
    TextLines(res)
  }
}

object HtmlPre
{ /** Factory apply method to create an HTML pre element. */
  def apply(str: String, attribs: XAtt*): HtmlPre = new HtmlPre(str, attribs.toRArr)
  
  /** Factory method to create an HTML pre element with an id attribute. */
  def idAtt(idStr: String, contentStr: String , otherAttribs: XAtt*): HtmlPre = new HtmlPre(idStr, IdAtt(idStr) %: otherAttribs.toRArr)
  
  /** Creates an HTML Pre element and registers the textContent with an HTML Text Input. */
  def inputText(input: InputUpdaterText)(f: String => String): HtmlPre =
  { def newId = input.next1Id(f)
    new HtmlPre(f(input.valueStr), RArr(newId))
  }
}