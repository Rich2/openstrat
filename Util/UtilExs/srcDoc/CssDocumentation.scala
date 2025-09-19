/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, Colour.*

object CssDocumentation extends CssOpenstrat
{
  override def rules: RArr[CssRuleLike] = RArr(
    CssBody(CssBGColour(Ivory), DecFontSize(18.px)), CssH1(DecTextCentre, DecFontSize(44.px)), CssP(DecAlignJus),
    CssClassesRule("central", DecMaxWidth(68.em), DecMargLeftRight(CssAuto)),
    CssClassesRule("main", DecMaxWidth(68.em), DecMargLeftRight(CssAuto)),
    CssOl(DecPadLeft(1.em)), CssRule("ol li", CssMargTopBot(2.em)), CssRule("ul li", DecMarg(0.25.em)), CssRule("ol > li", CssMargTopBot(1.em)),
    CssClassesRule("lexical", DecColour(DarkBlue)),
    CssCode(DecFontSize(14.px), DecColour(DarkRed)),
    CssClassesRule("output", DecColour(Black)),
    CssClassesRule("sbt", DecColour(DarkGreen)),
    CssClassesRule("folder", DecColour(DarkBlue)),
    CssClassesRule("path", DecColour(DarkBlue), DecNoWrap),
    CssClassesRule("bash", DecColour(DarkRed)),
    CssClassesRule("bashprompt", DecColour(Black)),
    CssClassesRule("scalalines", DecColour(Black), DecNoWrap),
    CssClassesRule("scala", DecColour(DarkRed), DecNoWrap, DecBold),
    CssClassesRule("centreBlock", DispBlock, DecMargLeftRightAuto),
    CssRule("td th", DecPadRight(2.em), DecAlignLeft),
    minMed
  )
}