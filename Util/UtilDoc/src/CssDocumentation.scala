/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, Colour.*

/** CSS for openstrat documentation. */
object CssDocumentation extends CssOpenstrat
{
  override def rules: RArr[CssRuleLike] = RArr(
    CssBody(BGColourDec(Ivory), FontSizeDec(18.px)), CssH1(TextCentreDec, FontSizeDec(44.px)), CssP(DecAlignJus),
    CssClassesRule("central", MaxWidthDec(68.em), DecMargLeftRight(CssAuto)),
    CssClassesRule("main", MaxWidthDec(68.em), DecMargLeftRight(CssAuto)),
    CssOl(DecPadLeft(1.em)), CssRule("ol li", CssMargTopBot(2.em)), CssRule("ul li", DecMarg(0.25.em)), CssRule("ol > li", CssMargTopBot(1.em)),
    CssClassesRule("lexical", BGColourDec(White), ColourDec(DarkBlue)),
    CssListRule("code", ChildSel("code", "span"), ChildSel("code", "div"))(FontSizeDec(14.px), BGColourDec(Black), ColourDec(White)),
    CssClassesRule("output", BGColourDec(Black), ColourDec(Pink)),
    CssClassesRule("sbt", BGColourDec(Black), ColourDec(LightGreen)),
    CssClassesRule("folder", BGColourDec(Black), ColourDec(LightBlue)),
    CssClassesRule("path", BGColourDec(White), ColourDec(DarkBlue), NoWrapDec),
    CssClassesRule("bashprompt", BGColourDec(Black), ColourDec(Pink)),
    CssClassesRule("scalalines", BGColourDec(Black), ColourDec(White), NoWrapDec),
    CssClassesRule("scala", BGColourDec(White), ColourDec(DarkRed), NoWrapDec, DecBold),
    CssClassesRule("centreBlock", BlockDec, DecMargLeftRightAuto),
    CssRule("td th", DecPadRight(2.em), DecAlignLeft),
    minMed
  )
}