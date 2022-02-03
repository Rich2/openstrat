/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Common trait for all tokens that can be valid hexadecimal natural numbers as well as valid base32 numbers. */
trait ValidNatBase32Token extends ValidIntToken
{
  def asBase32Int: Int =
  { var acc = 0
    implicit val chars: Chars = digitsStr.toChars

    def loop(rem: CharsOff): Int = rem match
    {
      case CharsOff0() => acc

      case CharsOff1Tail(DigitChar(d), tail) =>
      { acc = acc * 32 + d - '0'
        loop(tail)
      }

      case CharsOff1Tail(l, tail) if l >= 'A' && l <= 'N' => {
        val delta: Int = l - 'A' + 10
        acc = acc * 32 + delta
        loop(tail)
      }

      case CharsOff1Tail(l, tail) if l >= 'P' && l <= 'W' =>
      { val delta: Int = l - 'A' + 9
        acc = acc * 32 + delta
        loop(tail)
      }

      case _ => excep("Case not implemented")
    }
    loop(chars.offsetter0)
  }
}


/** Token for base 32 natural number. All DecimalTokens are also legal base32 tokens, as well as all raw hexadecimal tokens. Base32 , '0' .. '9',
 *  followed by 'A' .. 'W' with letter 'O' unused. Some Alpha numeric terms are valid TrigDualInts. */
trait ValidRawNatBase32Token extends ValidIntToken
{ /** Uses the digitStr to calculate the natural number from the digitStr. */
  def asbase32: Int = ???
}

/** A raw natural Base32 integer token starting with a digit that is not a valid hexadecimal and hence is not a decimal number either. */
case class RawNat32Token(startPosn: TextPosn, srcStr: String) extends ValidRawNatBase32Token
{ override def exprTypeStr: String = "Base32Raw"
  override def digitsStr: String = srcStr
}

/** An unambiguous base32 natural number token, starts with the 0z characters. */
case class Nat0tToken(startPosn: TextPosn, digitsStr: String) extends ValidRawNatBase32Token
{ override def exprTypeStr: String = "NatOt"
  override def srcStr: String = "0t" + digitsStr
}