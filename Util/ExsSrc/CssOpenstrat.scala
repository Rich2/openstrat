/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._, Colour._

object CssOpenstrat extends CssRules
{
  override def rules: RArr[CssRule] = RArr(body, h1, ol, olli, CssCode(CssColour(DarkRed)), CssClassesRule("sbt", CssColour(DarkGreen)), folder)

  def body: CssBody = CssBody(CssBGColour(Ivory), CssFontSize(18.px))
  def h1: CssH1 = CssH1(CssTextCentre, CssFontSize(44.px))
  def ol = CssOl(CssPadLt(1.em))
  val olli = CssRule("ol li", CssMargBot(2.em), CssMargTop(2.em))
  val folder: CssClassesRule = CssClassesRule("folder", CssColour(DarkBlue))

  override def endStr: String =
  """
  ul li {	margin: 0.25em; }

  ol > li
  { margin-top: 1em;
    margin-bottom: 1em;
  }

    .central
    { max-width: 68em;
      margin-left: auto;
      margin-right: auto;
    }

    .main
    { max-width: 68em;
      margin-left: auto;
      margin-right: auto;
    }

    .lexical { color: DarkBlue; }

    .sbt { color: DarkGreen; }
    .folder { color: DarkBlue; white-space: nowrap; }
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