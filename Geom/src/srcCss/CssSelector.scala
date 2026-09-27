/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** CSS selector */
trait CssSelector
{ /** The CSS code output. */
  def cssOut: String
}

object CssSelector
{
  //def apply(str: String): CssSelector = new CssSelGen(str)
//  class CssSelGen(val out: String) extends SelListMem
}

/** CSS rule selector that is not a child or a descendent. */
trait CssSimpleSel extends CssSelector
{
  def > (child: SelSimpleOrStr): CssChildSel
}

trait HtmlTag extends CssSelector
{
  def tag: String
  override def cssOut: String = tag
}

/** CSS selector or [[String]] that can be used for selector. */
type SelOrStr = CssSelector | String

extension (inp: SelOrStr)
{
  def outStr: String = inp match {
    case cs: CssSelector => cs.cssOut
    case s: String => s
  }
}

type SelSimpleOrStr = CssSimpleSel | String

/** CSS rule selector for HTML tag type. */
case class CssTagSel(cssOut: String) extends CssSimpleSel
{
  override def >(child: SelSimpleOrStr): TagChildSel = TagChildSel(this, child)
}

/** CSS rule selector for a CSS class. */
/*case class CssClassSel(tailStr: String) extends CssSimpleSel
{ override def cssOut: String = "," + tailStr
  override def >(child: SelSimpleOrStr): ClassChildSel = ClassChildSel(this, child)
}*/

/** CSS rule selector for a CSS ID. */
case class CssIdSel(tailStr: String) extends CssSimpleSel
{ override def cssOut: String = "#" + tailStr
  override def >(child: SelSimpleOrStr): IdChildSel = IdChildSel(this, child)
}

trait CssChildSel extends CssSelector
{ def parent: SelSimpleOrStr
  def child: SelSimpleOrStr
  override def cssOut: String = parent.outStr -- ">" -- child.outStr
}

case class TagChildSel(parent: CssTagSel | String, child: SelSimpleOrStr) extends CssChildSel

case class ClassChildSel(parent: /*CssClassSel |*/ String, child: SelSimpleOrStr) extends CssChildSel

case class IdChildSel(parent: CssIdSel | String, child: SelSimpleOrStr) extends CssChildSel