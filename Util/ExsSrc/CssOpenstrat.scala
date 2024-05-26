/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._
import Colour._

object CssOpenstrat extends CssSpec
{
  override def rules: RArr[CssRuleLike] = RArr(
    CssBody(CssBGColour(Ivory), CssFontSize(18.px)), CssH1(CssTextCentre, CssFontSize(44.px)),
    CssClassesRule("central", DecMaxWidth(68.em) %: DecMargLeftRight(CssAuto)),
    CssClassesRule("main", DecMaxWidth(68.em) %: DecMargLeftRight(CssAuto)),
    CssOl(DecPadLeft(1.em)), CssRule("ol li", CssMargTopBot(2.em)), CssRule("ul li", DecMarg(0.25.em)), CssRule("ol > li", CssMargTopBot(1.em)),
    CssClassesRule("lexical", CssColour(DarkBlue)),
    CssCode(CssColour(DarkRed)), CssClassesRule("sbt", CssColour(DarkGreen)), CssClassesRule("folder", CssColour(DarkBlue)),
    CssClassesRule("path", CssColour(DarkBlue), CssNoWrap), CssClassesRule("bash", CssColour(DarkRed), CssNoWrap),
    CssClassesRule("scala", CssColour(Black), CssNoWrap, CssFontSize(10.px)), CssObjectRule("centreBlock", DispBlock %: DecMargLeftRightAuto),
    CssRule("td th", DecPadRight(2.em), DecAlignStart),
    minMed
  )

  def minMed: CssMedia = new CssMedia("min-width:50em")
  {
    override def rules: RArr[CssRule] = RArr(
      CssObjectRule("topmenu li", DispInBlock, CssBGColour(Colour(0xFFDDDDDD)), DecPad(0.2.em), DecBorder(CssSolid(Yellow))),
      CssObjectRule("topmenu", DecAlignCen, DecMaxWidth(100.em)),
      CssObjectRule("bottommenu", DispNone)
    )
  }

  override def endStr: String =
  """
  @media (min-width:50em)
  {
    #topmenu li
    { display: inline-block;
      background-color: #dddddd;
      padding: 0.2em;
      border: 0.2em solid yellow;
    }

    #topmenu { text-align: center; max-width: 100em;}
    #bottommenu {display: none;}
  }

  td, th {padding-right: 2.0em;
  text-align: start;
}"""
}