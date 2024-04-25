/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Sequence of point separated number sequences. Includes fixed point decimal numbers, IPv4 notation and version nunbers. */
trait DigitSeqsCode extends ClauseMemExprToken
{ /** The digit sequences of the code. For example "2.13.7" gives Strings("2", "13", "7"). */
  def digitSeqs: StrArr

  /** Lower case trailing letter sequence. */
  def trail: String
}

/** Decimal fractional fixed point token. */
trait DeciFracToken extends ValidFracToken
{ def dgs1: String
  def dgs2: String
  def trail: String
  def wholeNum: Long = dgs1.unsafeDigitsToLong
  def fractionalValue: Double = dgs2.unsafeDigitsToLong.toDouble / 10.power(dgs2.length)
  def fixedValue: Double = wholeNum.toDouble + fractionalValue
  override def srcStr: String = dgs1 + "." + dgs2 + trail
}

/** Positive Decimal fractional fixed point token. */
final case class DeciFracPosToken(startPosn: TextPosn, dgs1: String, dgs2: String, trail: String) extends DeciFracToken with ValidPosFracToken with DigitSeqsCode
{ override def exprName: String = "DeciFrac"
  override def digitSeqs: StrArr = StrArr(dgs1, dgs2)
  inline override def doubleValue: Double = fixedValue
  override def posDoubleValue: Double = doubleValue
}

/** Negative Decimal fractional fixed point number [[token]]. */
final case class DeciFracNegToken(startPosn: TextPosn, dgs1: String, dgs2: String, trail: String) extends DeciFracToken
{ override def srcStr: String = "-" + super.srcStr
  override def exprName: String = "DeciFrac"
  inline override def doubleValue: Double = -fixedValue
}

trait FloatPtToken extends DeciFracToken
{ def expStr: String
  def expPos: Boolean
  def expAbs: Int = expStr.tail.foldLeft[Int](expStr.head - '0'){ (acc, el) => acc * 10 + el - '0' }
  def exp: Int = ife(expPos, expAbs, -expAbs)
}

/** Positive Floating point fractional number token. */
final case class FloatPtPosToken(startPosn: TextPosn, dgs1: String, dgs2: String, expPos: Boolean, expStr: String, trail: String) extends FloatPtToken
{ override def srcStr: String = dgs1 + "." + dgs2 + "e" + expStr
  override def exprName: String = "FloatPtPos"
  override def doubleValue: Double = fixedValue * 10.power(exp)
}

/** Negative Floating point fractional number token. */
final case class FloatPtNegToken(startPosn: TextPosn, dgs1: String, dgs2: String, expPos: Boolean, expStr: String, trail: String) extends FloatPtToken
{ override def srcStr: String = "-" + dgs1 + "." + dgs2 + "e" + expStr
  override def exprName: String = "PloatPtNeg"
  override def doubleValue: Double = fixedValue * 10.power(exp)
}