/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** CSS selector */
trait CssSelector
{ /** The CSS code output. */
  def out: String
}

object CssSelector
{
  //def apply(str: String): CssSelector = new CssSelGen(str)
//  class CssSelGen(val out: String) extends SelListMem
}

/** CSS selector or [[String]] that can be used for selector. */
type SelOrStr = CssSelector | String

extension (inp: SelOrStr)
{
  def outStr: String = inp match {
    case cs: CssSelector => cs.out
    case s: String => s
  }
}

/** CSS rule selector that is not a child or a descendent. */
trait CssSimpleSel extends CssSelector
{
  def > (child: SelSimpleOrStr): CssChildSel
}

type SelSimpleOrStr = CssSimpleSel | String

/** CSS rule selector for HTML tag type. */
case class CssTagSel(out: String) extends CssSimpleSel
{
  override def >(child: SelSimpleOrStr): TagChildSel = TagChildSel(this, child)
}

/** CSS rule selector for a CSS class. */
case class CssClassSel(tailStr: String) extends CssSimpleSel
{ override def out: String = "," + tailStr
  override def >(child: SelSimpleOrStr): ClassChildSel = ClassChildSel(this, child)
}
/** CSS rule selector for a CSS ID. */
case class CssIdSel(tailStr: String) extends CssSimpleSel
{ override def out: String = "#" + tailStr
  override def >(child: SelSimpleOrStr): IdChildSel = IdChildSel(this, child)
}

trait CssChildSel extends CssSelector
{ def parent: SelSimpleOrStr
  def child: SelSimpleOrStr
  override def out: String = parent.outStr -- ">" -- child.outStr
}

case class TagChildSel(parent: CssTagSel | String, child: SelSimpleOrStr) extends CssChildSel

case class ClassChildSel(parent: CssClassSel | String, child: SelSimpleOrStr) extends CssChildSel

case class IdChildSel(parent: CssIdSel | String, child: SelSimpleOrStr) extends CssChildSel