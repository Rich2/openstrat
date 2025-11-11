/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, Colour.*

/** Common trait for openstrat CSS. */
trait CssOpenstrat extends CssRulesHolder
{
  def minMed: CssMedia = new MediaMinWidth(50.em)
  {
    override def rules: RArr[CssRule] = RArr(
      CssIDRule("topmenu li", InlineBlockDec, BGColourDec(Colour(0xFFDDDDDD)), PaddingDec(0.2.em), BorderDec(CssSolid(Yellow))),
      CssIDRule("topmenu", DecAlignCen, MaxWidthDec(100.em)),
      CssIDRule("bottommenu", DispNoneDec)
    )
  }
}

/** CSS file for application pages. */
object OnlyCss extends CssOpenstrat
{ /** The CSS rules. */
  override def rules: RArr[CssRuleLike] = RArr(CssBody(DispFlexDec, DecMinHeight(98.vh), DecFlexDirnCol), CssButton(FontSizeDec(1.5.em)),
    CssIDRule("footer", DecAlignCen, MarginDec(0.8.em), ColourDec(FireBrick)), CssRule("ul, ol, p", MaxWidthDec(68.em), DecMargLeftRightAuto),
    CssP(CssMargTopBot(0.5.em)), CssH1(DecAlignCen), CssCanvas(DecWidth(100.vw), DecHeight(100.vh), BlockDec), minMed, maxMed)

  def maxMed: CssMedia = new CssMedia("max-width: 50em")
  {
    override def rules: RArr[CssRule] = RArr(
      CssIDRule("topmenu", DispNoneDec),
      CssIDRule("bottommenu li", InlineBlockDec, BGColourDec(Colour(0xFFDDDDDD)), PaddingDec(0.2.em), BorderDec("0.2em solid Green")),
    )
  }
}