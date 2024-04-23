/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Common trait for all tokens that can be valid hexadecimal natural numbers as well as valid base32 numbers. */
trait ValidBase32IntToken extends ValidIntToken
{
  def asBase32Int: Int =
  { var acc = 0
    implicit val chars: CharArr = digitsStr.toChars

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
trait ValidRawBase32IntToken extends ValidBase32IntToken

object ValidRawBase32IntToken
{
  def unapply(input: Any): Option[Int] = input match {
    case vrbit: ValidRawBase32IntToken => Some(vrbit.asBase32Int)
    case _ => None
  }
}

trait ValidRawBase32NatToken extends ValidRawBase32IntToken
{ def asBase32Nat: Int = asBase32Int
}

object ValidRawBase32NatToken
{
  def unapply(input: Any): Option[Int] = input match
  { case vrbit: ValidRawBase32NatToken => Some(vrbit.asBase32Int)
    case _ => None
  }
}

/** A raw natural Base32 integer token starting with a digit that is not a valid hexadecimal and hence is not a decimal number either. */
case class RawBase32NatToken(startPosn: TextPosn, srcStr: String) extends ValidRawBase32NatToken
{ override def exprName: String = "Base32Raw"
  override def digitsStr: String = srcStr
}

/** A raw natural Base32 integer token starting with a digit that is not a valid hexadecimal and hence is not a decimal number either. */
case class RawBase32NegToken(startPosn: TextPosn, srcStr: String) extends ValidRawBase32IntToken
{ override def exprName: String = "Base32Raw"
  override def digitsStr: String = srcStr
}