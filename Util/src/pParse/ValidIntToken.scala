/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** The base trait for all integer tokens. A Natural (non negative) number Token. It contains a single property, the digitStr. The digitStr depending
* on the class may be interpreted in 1 to 3 ways, as a normal decimal number, a hexadecimal number, or a trigdual (base 32) number. */
trait ValidIntToken extends ClauseMemExprToken
{ /** The digit chars used to calculate the integer. */
  def digitsStr: String
}

/** A valid fractional number token. Could be an integer or a fractional number. */
trait ValidFracToken extends ClauseMemExprToken
{ def doubleValue: Double
}

/** A valid non negative fractional number token */
trait ValidPosFracToken extends ClauseMemExprToken
{ def posDoubleValue: Double
}

/** Common trait for [[RawIntDeciToken]], [[NatOxToken]] and [[NatOyToken]] has the getIntStd method. This is the trait you would use in general purpose
 * programming language, where raw hexadecimal and raw Bse32 numbers are disallowed. */
trait IntStdToken extends ValidIntToken with ValidFracToken
{ /** Returns an integer value for the [[Token]] using the standard decimal format unless it is an 0x or 0y Token. */
  def getIntStd: Int

  override def doubleValue: Double = getIntStd
}

/** Companion object for the [[IntStdToken]] trait, only contains an unapply method. */
object IntStdToken
{ /** Factory unapply method for the [[IntStdToken]] trait. */
  def unapply(inp: Token): Option[Int] = inp match {
    case idt: IntStdToken => Some(idt.getIntStd)
    case _ => None
  }
}

/** Common trait for [[RawIntDeciToken]], [[NatOxToken]] and [[NatOyToken]] has the getIntStd method. This is the trait you would use in general purpose
 * programming language, where raw hexadecimal and raw Bse32 numbers are disallowed. */
trait NatStdToken extends IntStdToken with ValidPosFracToken
{ def getNatStd: Int = getIntStd
  inline override def posDoubleValue: Double = getNatStd
}

/** Companion object for the [[NatStdToken]] trait, only contains an unapply method. */
object NatStdToken
{ /** Factory unapply method for the [[NatStdToken]] trait. */
  def unapply(inp: Token): Option[Int] = inp match {
    case idt: NatStdToken => Some(idt.getNatStd)
    case _ => None
  }
}


/** A raw integer token could be negative. */
trait RawIntDeciToken extends IntStdToken with ValidRawHexaIntToken
{
  /** gets the natural integer value part from this token interpreting it as a standard Base10 notation. */
  def getAbsoluteIntStd: Int =
  { var acc = 0
    implicit val chars: Chars = digitsStr.toChars

    def loop(rem: CharsOff): Int = rem match
    { case CharsOff0() => acc
      case CharsOff1Tail(DigitCharNum(i), tail)  => { acc = acc * 10 + i; loop(tail) }
    }

    loop(chars.offsetter0)
  }
}

/** A 64 bit natural number token in standard decimal format, but which can be inferred to be a raw Hexadecimal. It can be used for standard 32 bit
 *  Ints and 64 bit Longs, as well as less used integer formats such as Byte. This is in accord with the principle that RSON at the Token and AST
 *  (Abstract Syntax Tree) levels stores data not code, although of course at the higher semantic levels it can be used very well for programming
 *  languages. */
case class NatDeciToken(startPosn: TextPosn, srcStr: String) extends ValidRawHexaNatToken with RawIntDeciToken with NatStdToken with DigitSeqsCode
{ override def exprTypeStr: String = "Decimal"
  override def digitsStr: String = srcStr
  override def digitSeqs: Strings = Strings(digitsStr)
  inline override def getIntStd: Int = getAbsoluteIntStd

  override def trail: String = ???
}

/** Negative natural number token. There must be no space between the '-' character and the digits. */
case class NegDeciToken(startPosn: TextPosn, digitsStr: String) extends RawIntDeciToken with ValidRawHexaNegToken
{ override def exprTypeStr: String = "IntNeg"
  override def srcStr: String = "-" + digitsStr
  override def getIntStd: Int = -super.getAbsoluteIntStd
}