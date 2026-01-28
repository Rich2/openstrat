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
  class CssSelGen(val out: String) extends SelListMem
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
trait CssAdultSel extends CssSelector

type SelAdultOrStr = SelListMem | String

/** CSS rule selector for HTML tag type. */
case class CssTagSel(out: String) extends CssAdultSel

/** CSS rule selector for a CSS class. */
case class CssClassSel(tailStr: String) extends CssAdultSel
{ override def out: String = "," + tailStr
}
/** CSS rule selector for a CSS ID. */
case class CssIdSel(tailStr: String) extends CssAdultSel
{ override def out: String = "#" + tailStr
}

trait SelListMem extends CssSelector
{ /** Returns CSS child selector. */
  //def > (child: SelListMem | String): CssSelector = ChildSel(this, child)
}

type SelMemOrStr = SelListMem | String

class ChildSel(parent: SelAdultOrStr, child: SelListMem | String) extends SelListMem
{
  override def out: String = parent.outStr -- ">" -- child.outStr
}