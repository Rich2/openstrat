/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Sequence of point separated number sequences. Includes fixed point decimal numbers, IPv4 notation nad version nunbers. */
trait DigitSeqsCode extends ExprToken
{ /** The digit sequences of the code. For example "2.13.7" gives Strings("2", "13", "7"). */
   def digitSeqs: Strings

  def trail: String
}

/** Decimal fractional fixed point token. */
case class DeciFracToken(startPosn: TextPosn, dgs1: String, dgs2: String, trail: String) extends DigitSeqsCode
{ override def digitSeqs: Strings = Strings(dgs1, dgs2)
  override def srcStr: String = dgs1 + "." + dgs2 + trail
  override def exprTypeStr: String = "DeciFrac"
}