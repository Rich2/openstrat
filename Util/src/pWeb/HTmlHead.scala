/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML head element. */
case class HtmlHead(contents : Arr[XCon], attribs: Arr[XmlAtt] = Arr()) extends HtmlUnvoid
{ override def tag: String = "head"
  //override def contents: Arr[XCon] = Arr[XCon](HtmlTitle(titleStr))
  def out(indent: Int, linePosn: Int, lineLen: Int): String = openTag1 + contents.toStrsFold("\n", _.out(indent + 2, 0, 150)) + "\n" + closeTag
}  

/** Companion object for the [[HtmlHead]] case class. */
object HtmlHead
{ def title(titleStr: String): HtmlHead = new HtmlHead(Arr[XCon](HtmlTitle(titleStr), HtmlUtf8))
}