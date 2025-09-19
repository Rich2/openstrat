/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** CSS selector */
trait CssSel
{
  def out: String
}

/** CSS selector or [[String]] that can be used for selector. */
type SelOrStr = CssSel | String

extension (inp: SelOrStr)
{
  def outStr: String = inp match {
    case cs: CssSel => cs.out
    case s: String => s
  }
}

trait SelListMem extends CssSel
{
  def > (child: SelListMem | String): CssSel = ChildSel(this, child)
}

type SelMemOrStr = SelListMem | String

object CssSel
{
  def apply(str: String): CssSel = new CssSelGen(str)
  class CssSelGen(val out: String) extends SelListMem
}

class ChildSel(parent: SelListMem | String, child: SelListMem | String) extends SelListMem
{
  override def out: String = parent.outStr -- ">" -- child.outStr
}

class CssListSel(elems: RArr[SelListMem | String]) extends CssSel
{
  override def out: String = elems.mkStr(_.outStr, ", ")
}