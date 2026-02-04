/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, Colour.*

/** CSS for openstrat documentation. */
object CssDocumentation extends CssOpenstrat
{
  val newRules: RArr[CssRuleLike] = RArr(
    CssBody(BGColourDec(Ivory), FontSizeDec(18.px)), CssH1(TextCentreDec, FontSizeDec(44.px)), CssP(DecAlignJus),
    CssClassesRule("main", MaxWidthDec(68.em), MarginLRAutoDec),
    CssClassesRule("lexical", BGColourDec(White), ColourDec(DarkBlue)),
    CssMultiRule("code", TagChildSel("code", "span"), TagChildSel("code", "div"))(FontSizeDec(14.px), BGColourDec(Black), ColourDec(White), PadBottomDec(0.1.em)),
    CssMultiRule(".output", ClassChildSel(".output", "div"))(BGColourDec(Black), ColourDec(Pink)),
    CssClassesRule("sbt", BGColourDec(Black), ColourDec(LightGreen)),
    CssClassesRule("folder", BGColourDec(Black), ColourDec(LightBlue)),
    CssClassesRule("path", BGColourDec(White), ColourDec(DarkBlue), NoWrapDec),
    CssClassesRule("bash", NoWrapDec),
    CssClassesRule("bashprompt", BGColourDec(Black), ColourDec(Pink)),
    CssClassesRule("scalalines", BGColourDec(Black), ColourDec(White), NoWrapDec),
    CssClassesRule("scala", BGColourDec(White), ColourDec(DarkRed), NoWrapDec, DecBold),
    CssClassesRule("centreBlock", BlockDec, MarginLRAutoDec),
    CssRule("td th", PadRightDec(2.em), DecAlignLeft),
    minMed
  )

  override def rules: RArr[CssRuleLike] = osweb.utilRules ++ newRules
}