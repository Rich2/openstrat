/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, Colour.*, wcode.*

/** CSS for openstrat documentation. */
object CssDocumentation extends CssOpenstrat
{ override def fileStemStr: String = "documentation"
  
  object LexicalAtt extends ClassAtt("lexical")

  val newRules: RArr[CssRuleLike] = RArr(
    BodyRule(BGColourDec(Ivory), FontSizeDec(18.px)), CssH1(TextCentreDec, FontSizeDec(44.px)), CssP(DecAlignJus),
    MainBlockAtt.rule(MaxWidthDec(68.em), MarginLRAutoDec),
    LexicalAtt.rule(BGColourDec(White), ColourDec(DarkBlue)),
    CssMultiRule("code", TagChildSel("code", "span"), TagChildSel("code", "div"))(FontSizeDec(14.px), BGColourDec(Black), ColourDec(White),
      PadBottomDec(0.1.em)),
    CssMultiRule(".output", ClassChildSel(".output", "div"))(BGColourDec(Black), ColourDec(Pink)),
    SbtAtt.rule(BGColourDec(Black), ColourDec(LightGreen)),
    DirPathAtt.rule(BGColourDec(Black), ColourDec(LightBlue)),
    FilePathAtt.rule(BGColourDec(White), ColourDec(DarkBlue), NoWrapDec),
    CssCode(MarginTBDecs(0.25.rem)),
    BashPromptCssRule(BGColourDec(Black), ColourDec(Pink)),
    PsqlPromptCssRule(BGColourDec(Black), ColourDec(LightGreen)),
    ScalaLinesAtt.rule(BGColourDec(Black), ColourDec(White), NoWrapDec),
    ScalaAtt.rule(BGColourDec(White), ColourDec(DarkRed), NoWrapDec, DecBold),
    CssRule("td th", PadRightDec(2.em), DecAlignLeft),
    CssRule("h1, h2. h3, h4, h5, h6", MarginTBDec(0.67.em)),
    minMed
  )

  override def rules: RArr[CssRuleLike] = osweb.utilRules ++ newRules
}