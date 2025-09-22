/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** The Style attribute for inline CSS. */
class StyleAtt(decs: RArr[CssDec]) extends XAttSimple
{ override def name: String = "style"

  override def valueStr: String = decs.mkStr(_.out, " ")
}

object StyleAtt
{
  def apply(decs: RArr[CssDec]): StyleAtt = new StyleAtt(decs)
  def apply(decs: CssDec*): StyleAtt = new StyleAtt(decs.toArr)
}

/** Css Display declaration set to inline-block. */
object DispInBlock extends DecDisplay(CssInBlock)

/** Css Display declaration set to block. */
object DispBlock extends DecDisplay(CssBlock)

/** Css Display declaration set to none. */
object DispNone extends DecDisplay(CssNone)

/** Css Display declaration set to flex. */
object DispFlex extends DecDisplay(CssFlex)