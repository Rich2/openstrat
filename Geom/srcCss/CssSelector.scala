/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** CSS selector */
trait CssSelector
{ /** The CSS code output. */
  def out: String
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

trait SelListMem extends CssSelector
{ /** Returns CSS child selector. */
  def > (child: SelListMem | String): CssSelector = ChildSel(this, child)
}

type SelMemOrStr = SelListMem | String

object CssSelector
{
  def apply(str: String): CssSelector = new CssSelGen(str)
  class CssSelGen(val out: String) extends SelListMem
}

class ChildSel(parent: SelListMem | String, child: SelListMem | String) extends SelListMem
{
  override def out: String = parent.outStr -- ">" -- child.outStr
}

class CssListSel(elems: RArr[SelListMem | String]) extends CssSelector
{
  override def out: String = elems.mkStr(_.outStr, ", ")
}