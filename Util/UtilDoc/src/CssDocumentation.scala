/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, Colour.*, wcode.*

/** CSS for openstrat documentation. */
object CssDocumentation extends CssOpenstrat
{ override def fileStemStr: String = "documentation"
  
  object LexicalAtt extends ClassAtt("lexical")

  val newRules: RArr[CssRuleLike] = RArr(
    BodyRule(BGColourDec(Ivory), FontSizeDec(18.px)),
    H1Rule(TextCentreDec, FontSizeDec(44.px)),
    PRule(DecAlignJus),
    MainBlockAtt.rule(MaxWidthDec(68.em), MarginLRAutoDec),
    LexicalAtt.rule(BGColourDec(White), ColourDec(DarkBlue)),
    CssRuleMulti(CodeHtml, CssChildSel(CodeHtml, SpanHtml), CssChildSel(CodeHtml, DivHtml))(FontSizeDec(14.px), BGColourDec(Black), ColourDec(White),
      PadBottomDec(0.1.em)),
    CssRuleMulti(CodeOutputAtt, CssChildSel(CodeOutputAtt, DivHtml))(BGColourDec(Black), ColourDec(Pink)),
    SbtAtt.rule(BGColourDec(Black), ColourDec(LightGreen)),
    DirPathAtt.rule(BGColourDec(Black), ColourDec(LightBlue)),
    FilePathAtt.rule(BGColourDec(White), ColourDec(DarkBlue), NoWrapDec),
    CodeRule(MarginTBDecs(0.25.rem)),
    BashPromptRule(BGColourDec(Black), ColourDec(Pink)),
    PsqlPromptRule(BGColourDec(Black), ColourDec(LightGreen)),
    ScalaLinesAtt.rule(BGColourDec(Black), ColourDec(White), NoWrapDec),
    ScalaAtt.rule(BGColourDec(White), ColourDec(DarkRed), NoWrapDec, DecBold),
    CssRuleDescent(TdHtml, ThHtml, PadRightDec(2.em), DecAlignLeft),
    CssRuleMulti(H1Html, H2Html, H3Html, H4Html, H5Html, H6Html)(MarginTBDec(0.67.em)),
    minMed
  )

  override def rules: RArr[CssRuleLike] = osweb.utilRules ++ newRules
}