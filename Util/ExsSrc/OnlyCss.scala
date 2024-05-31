/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._, Colour._

trait CssOpenstrat extends CssSpec
{
  def minMed: CssMedia = new CssMedia("min-width: 50em")
  {
    override def rules: RArr[CssRule] = RArr(
      CssObjectRule("topmenu li", DispInBlock, CssBGColour(Colour(0xFFDDDDDD)), DecPad(0.2.em), DecBorder(CssSolid(Yellow))),
      CssObjectRule("topmenu", DecAlignCen, DecMaxWidth(100.em)),
      CssObjectRule("bottommenu", DispNone)
    )
  }
}

object OnlyCss extends CssOpenstrat
{ /** The CSS rules. */
  override def rules: RArr[CssRuleLike] = RArr(CssBody(DispFlex, DecMinHeight(98.vh), DecFlexDirnCol), CssButton(DecFontSize(1.5.em)),
    CssObjectRule("footer", DecAlignCen, DecMarg(0.8.em), DecColour(FireBrick)), CssRule("ul, ol, p", DecMaxWidth(68.em), DecMargLeftRightAuto),
    CssP(CssMargTopBot(0.5.em)), CssH1(DecAlignCen), CssCanvas(DecWidth(100.vw), DecHeight(100.vh), DispBlock), minMed, maxMed)

  def maxMed: CssMedia = new CssMedia("max-width: 50em")
  {
    override def rules: RArr[CssRule] = RArr(
      CssObjectRule("topmenu", DispNone),
      CssObjectRule("bottommenu li", DispInBlock, CssBGColour(Colour(0xFFDDDDDD)), DecPad(0.2.em), DecBorder("0.2em solid Green")),
    )
  }

  override def endStr: String =
"""
  |@media (max-width:50em)
  |{
  |	#topmenu { display: none; }
  |	#bottommenu li
  |	{
  |	   display: inline-block;
  |	   background-color: #dddddd;
  |	   padding: 0.2em;
  |	   border: 0.2em solid green;
  |	}
  |}
  |""".stripMargin
}