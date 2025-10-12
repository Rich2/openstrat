/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, Colour.*

/** Common trait for openstrat CSS. */
trait CssOpenstrat extends CssRulesHolder
{
  def minMed: CssMedia = new MediaMinWidth(50.em)
  {
    override def rules: RArr[CssRule] = RArr(
      CssIDRule("topmenu li", InlineBlockDec, DecBGColour(Colour(0xFFDDDDDD)), DecPad(0.2.em), DecBorder(CssSolid(Yellow))),
      CssIDRule("topmenu", DecAlignCen, DecMaxWidth(100.em)),
      CssIDRule("bottommenu", DispNone)
    )
  }
}

/** CSS file for application pages. */
object OnlyCss extends CssOpenstrat
{ /** The CSS rules. */
  override def rules: RArr[CssRuleLike] = RArr(CssBody(DispFlex, DecMinHeight(98.vh), DecFlexDirnCol), CssButton(DecFontSize(1.5.em)),
    CssIDRule("footer", DecAlignCen, DecMarg(0.8.em), DecColour(FireBrick)), CssRule("ul, ol, p", DecMaxWidth(68.em), DecMargLeftRightAuto),
    CssP(CssMargTopBot(0.5.em)), CssH1(DecAlignCen), CssCanvas(DecWidth(100.vw), DecHeight(100.vh), BlockDec), minMed, maxMed)

  def maxMed: CssMedia = new CssMedia("max-width: 50em")
  {
    override def rules: RArr[CssRule] = RArr(
      CssIDRule("topmenu", DispNone),
      CssIDRule("bottommenu li", InlineBlockDec, DecBGColour(Colour(0xFFDDDDDD)), DecPad(0.2.em), DecBorder("0.2em solid Green")),
    )
  }
}