/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML Pre element. Unlike most other [[HtmlElem]]s this only takes a [[String]] as its content. */
class PreHtml(val str: String, val attribs: RArr[XAtt]) extends HtmlTagLines
{ override def tagName: String = "pre"
  override def contents: RArr[XCon] = RArr(str)

  override def out: String = openTag(0, 0, MaxLineLen) + str + closeTag
  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines =
  { val res = openTag(indent, line1InputLen, maxLineLen) + str + closeTag
    TextLines(res)
  }
}

object PreHtml
{ /** Factory apply method to create an HTML pre element. */
  def apply(str: String, attribs: XAtt*): PreHtml = new PreHtml(str, attribs.toRArr)
  
  /** Factory method to create an HTML pre element with an id attribute. */
  def idAtt(idStr: String, contentStr: String , otherAttribs: XAtt*): PreHtml = new PreHtml(idStr, IdAtt(idStr) %: otherAttribs.toRArr)
  
  /** Creates an HTML Pre element and registers the textContent with an HTML Text Input. */
  def inputText(input: InputUpdaterText)(f: String => String, otherAttribs: XAtt*): PreHtml =
  { def targetId = input.next1Id(f)
    new PreHtml(f(input.valueStr), targetId %: otherAttribs.toRArr)
  }

  /** Creates an HTML Pre element and registers the textContent with 2 HTML Text Inputs. */
  def input2Text(input1: InputUpdaterText, input2: InputUpdaterText, otherAttribs: XAtt*)(f: (String, String) => String): PreHtml =
  { def targetId = input1.next2Id1(input2.idStr, f)
    input2.next2Id2(targetId.valueStr, input1.idStr, f)
    new PreHtml(f(input1.valueStr, input2.valueStr), targetId %: otherAttribs.toRArr)
  }
}