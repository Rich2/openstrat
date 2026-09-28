/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, Colour.*, wcode.*


object TopMenuAtt extends IdAtt("topmenu")
object BottomMenuAtt extends IdAtt("bottommenu")

/** Common trait for openstrat CSS. */
trait CssOpenstrat extends CssRulesFile
{  
  def minMed: CssMedia = new MediaMinWidth(50.em)
  {
    override def rules: RArr[CssRule] = RArr(
      CssRuleDescent(TopMenuAtt, LiHtml, InlineBlockDec, BGColourDec(Colour(0xFFDDDDDD)), PaddingDec(0.2.em), BorderDec(SolidCss(Yellow))),
      TopMenuAtt.rule(DecAlignCen, MaxWidthDec(100.em)),
      BottomMenuAtt.rule(DispNoneDec)
    )
  }
}

/** CSS file for application pages. */
object OnlyCss extends CssOpenstrat
{ override def fileStemStr: String = "only"
  
  /** The CSS rules. */
  override def rules: RArr[CssRuleLike] = RArr(BodyRule(DispFlexDec, DecMinHeight(98.vh), DecFlexDirnCol),
    ButtonRule(FontSizeDec(1.5.em)),
    BottomMenuAtt.rule(DecAlignCen, MarginDec(0.8.em), ColourDec(FireBrick)),
    CssRuleMulti(UlHtml, OlHtml, PHtml)(MaxWidthDec(68.em), MarginLRAutoDec),
    PRule(MarginTBDec(0.5.em)),
    H1Rule(DecAlignCen),
    CanvasRule(DecWidth(100.vw), DecHeight(100.vh), BlockDec), minMed, maxMed)

  def maxMed: CssMedia = new CssMedia("max-width: 50em")
  {
    override def rules: RArr[CssRule] = RArr(
      CssRule(TopMenuAtt, DispNoneDec),
      CssRuleDescent(BottomMenuAtt, LiHtml, InlineBlockDec, BGColourDec(Colour(0xFFDDDDDD)), PaddingDec(0.2.em), BorderDec("0.2em solid Green")),
    )
  }
}