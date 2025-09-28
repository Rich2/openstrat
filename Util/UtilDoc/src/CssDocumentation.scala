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
    CssClassesRule("lexical", DecColour(DarkBlue)),
    CssListRule("code", ChildSel("code", "span"))(DecFontSize(14.px), DecBGColour(Black), DecColour(White)),
    CssClassesRule("output", DecColour(White)),
    CssClassesRule("sbt", DecColour(LightGreen)),
    CssClassesRule("folder", DecColour(LightBlue)),
    CssClassesRule("path", DecColour(DarkBlue), DecNoWrap),
    //CssClassesRule("bash", DecColour(Pink)),
    CssClassesRule("bashprompt", DecColour(Pink)),
    CssClassesRule("scalalines", DecColour(White), DecNoWrap),
    CssClassesRule("scala", DecColour(DarkRed), DecNoWrap, DecBold),
    CssClassesRule("centreBlock", DispBlock, DecMargLeftRightAuto),
    CssRule("td th", DecPadRight(2.em), DecAlignLeft),
    minMed
  )
}