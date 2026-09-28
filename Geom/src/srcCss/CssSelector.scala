/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** CSS selector */
trait CssSelector
{ /** The CSS code output. */
  def cssOut: String

  final def > (child: CssSelector): CssChildSel = CssChildSel(this, child)
  
  final def rule(decs: RArr[CssDecBase]): CssRule = CssRule(RArr(this), decs)

  final def rule(decs: CssDecBase*): CssRule = CssRule(RArr(this), decs.toRArr)
}

object CssSelector
{
  //def apply(str: String): CssSelector = new CssSelGen(str)
//  class CssSelGen(val out: String) extends SelListMem
}

/** CSS rule selector that is not a child or a descendent. */
trait CssSimpleSel extends CssSelector
{
  
}

trait HtmlTag extends CssSelector
{ /** The HTML tag. */
  def tag: String
  
  override def cssOut: String = tag
}

case class CssChildSel(parent: CssSelector, child: CssSelector) extends CssSelector
{ override def cssOut: String = parent.cssOut -- ">" -- child.cssOut
}

case class CssDescentSel(ancestor: CssSelector, descendent: CssSelector) extends CssSelector
{ override def cssOut: String = ancestor.cssOut -- descendent.cssOut
}