/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait HtmlPre extends HtmlTagLines
{
  override def tagName: String = "pre"
}

object HtmlPre
{
  /** Creates an HTML Pre element and registers the textContent with an HTML Text Input. */
  def inputText(input: InputUpdaterText)(f: String => String): HtmlPre =
  { def newId = input.next1Id(f)
    new HtmlPreGen(f(input.valueStr), RArr(newId))
  }
  
  case class HtmlPreGen(str: String, attribs: RArr[XAtt]) extends HtmlPre
  { override def contents: RArr[XCon] = RArr(str)
  }
}