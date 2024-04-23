/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** A [[Token]] that begins with a digit. */
trait DigitHeadToken extends OpExprMemToken

trait DigitHeadAlphaToken extends DigitHeadToken

class DigitHeadAlphaTokenGen(val startPosn: TextPosn, val numStr: String, val alphaStr: String) extends DigitHeadAlphaToken
{
  override def exprName: String = "DigitHeadAlpha"

  override def srcStr: String = numStr + alphaStr
}