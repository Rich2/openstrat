/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._, Colour._

object CssOpenstrat extends CssRules
{
  override def rules: RArr[CssRule] = RArr(
    CssBody(CssBGColour(Ivory), CssFontSize(18.px)), CssH1(CssTextCentre, CssFontSize(44.px)),
    CssClassesRule("central", CssMaxWidth(68.em) %: CssMargLeftRight(CssAuto)),
    CssClassesRule("main", CssMaxWidth(68.em) %: CssMargLeftRight(CssAuto)),
    CssOl(CssPadLt(1.em)), CssRule("ol li", CssMargTopBot(2.em)), CssRule("ul li", CssMarg(0.25.em)), CssRule("ol > li", CssMargTopBot(1.em)),
    CssClassesRule("lexical", CssColour(DarkBlue)),
    CssCode(CssColour(DarkRed)), CssClassesRule("sbt", CssColour(DarkGreen)), CssClassesRule("folder", CssColour(DarkBlue)),
    CssClassesRule("path", CssColour(DarkBlue), CssNoWrap), CssClassesRule("bash", CssColour(DarkRed), CssNoWrap)
  )

  override def endStr: String =
  """
    .path { color: DarkBlue; white-space: nowrap; }
    .bash { color: DarkRed; white-space: nowrap; }
    .scala { color: Black; white-space: nowrap; font-size:10px; }

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
}
  .centreBlock
  { display: block;
    margin-left: auto;
    margin-right: auto;
  }"""
}