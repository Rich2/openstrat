/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, Colour.*

/** CSS for openstrat documentation. */
object CssDocumentation extends CssOpenstrat
{
  override def rules: RArr[CssRuleLike] = RArr(
    CssBody(DecBGColour(Ivory), DecFontSize(18.px)), CssH1(DecTextCentre, DecFontSize(44.px)), CssP(DecAlignJus),
    CssClassesRule("central", DecMaxWidth(68.em), DecMargLeftRight(CssAuto)),
    CssClassesRule("main", DecMaxWidth(68.em), DecMargLeftRight(CssAuto)),
    CssOl(DecPadLeft(1.em)), CssRule("ol li", CssMargTopBot(2.em)), CssRule("ul li", DecMarg(0.25.em)), CssRule("ol > li", CssMargTopBot(1.em)),
    CssClassesRule("lexical", DecBGColour(White), DecColour(DarkBlue)),
    CssListRule("code", ChildSel("code", "span"))(DecFontSize(14.px), DecBGColour(Black), DecColour(White)),
    CssClassesRule("output", DecBGColour(Black), DecColour(White)),
    CssClassesRule("sbt", DecBGColour(Black), DecColour(LightGreen)),
    CssClassesRule("folder", DecBGColour(Black), DecColour(LightBlue)),
    CssClassesRule("path", DecBGColour(White), DecColour(DarkBlue), DecNoWrap),
    CssClassesRule("bashprompt", DecBGColour(Black), DecColour(Pink)),
    CssClassesRule("scalalines", DecBGColour(Black), DecColour(White), DecNoWrap),
    CssClassesRule("scala", DecBGColour(White), DecColour(DarkRed), DecNoWrap, DecBold),
    CssClassesRule("centreBlock", BlockDec, DecMargLeftRightAuto),
    CssRule("td th", DecPadRight(2.em), DecAlignLeft),
    minMed
  )
}