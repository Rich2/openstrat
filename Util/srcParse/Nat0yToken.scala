/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** A hexadecimal token with a leading "0y", that can be used for standard 32 bit Ints, 64 bit Longs, as well as less used integer
 *  formats such as BigInteger and Byte. This is in accord with the principle that RSON at the Token and AST (Abstract Syntax Tree) levels stores data not code,
 *  although of course at the higher semantic levels it can be used very well for programming languages. */
case class Nat0yToken(startPosn: TextPosn, digitsStr: String) extends IntStdToken with ValidBase32IntToken
{ override def srcStr: String = "0y" + digitsStr
  override def exprName: String = "Nat0y"
  override def getIntStd: Int = asBase32Int
}

/** Function for parsing explicit Hexadecimal Token, one that begins with the charchters '0y'. */
object Nat0yToken
{
  /** Function for parsing explicit Hexadecimal Token, one that begins with the charchters '0y'. */
  def parse(rem: CharsOff, tp: TextPosn)(implicit charArr: CharArr): ErrBi3[ExcLexar, CharsOff, TextPosn, Token] =
  {
    def loop(rem: CharsOff, strAcc: String): ErrBi3[ExcLexar, CharsOff, TextPosn, Nat0yToken] = rem match
    { case CharsOff0() => Succ3(rem, tp.right(strAcc.length + 2), Nat0yToken(tp, strAcc))
      case CharsOff1Tail(DigitChar(c), tail) => loop(tail, strAcc + c)
      case CharsOff1Tail(Base32UpperChar(c), tail) => upperLoop(tail, strAcc + c)
      case CharsOff1Tail(Base32LowerChar(c), tail) => lowerLoop(tail, strAcc + c)
      case CharsOffHead(LetterChar(_)) => tp.failLexar("Badly formed hexadecimal")
      case _ => Succ3(rem, tp.addStr(strAcc), Nat0yToken(tp, strAcc))
    }

    def upperLoop(rem: CharsOff, strAcc: String): ErrBi3[ExcLexar, CharsOff, TextPosn, Nat0yToken] = rem match
    { case CharsOff0() => Succ3(rem, tp.right(strAcc.length + 2), Nat0yToken(tp, strAcc))
      case CharsOff1Tail(DigitChar(c), tail) => upperLoop(tail, strAcc + c)
      case CharsOff1Tail(Base32UpperChar(c), tail) => upperLoop(tail, strAcc + c)
      case CharsOffHead(Base32LowerChar(c)) => tp.failLexar("Can't mix upper and lower case letters in 0y Base32 token.")
      case CharsOffHead(LetterChar(_)) => tp.failLexar("Badly formed hexadecimal")
      case _ => Succ3(rem, tp.addStr(strAcc), Nat0yToken(tp, strAcc))
    }

    def lowerLoop(rem: CharsOff, strAcc: String): ErrBi3[ExcLexar, CharsOff, TextPosn, Nat0yToken] = rem match
    { case CharsOff0() => Succ3(rem, tp.right(strAcc.length + 2), Nat0yToken(tp, strAcc))
      case CharsOff1Tail(DigitChar(c), tail) => lowerLoop(tail, strAcc + c)
      case CharsOff1Tail(Base32LowerChar(c), tail) => lowerLoop(tail, strAcc + c)
      case CharsOffHead(Base32UpperChar(c)) => tp.failLexar("Can't mix upper and lower case letters in 0y Base32 token.")
      case CharsOffHead(LetterChar(_)) => tp.failLexar("Badly formed hexadecimal")
      case _ => Succ3(rem, tp.addStr(strAcc), Nat0yToken(tp, strAcc))
    }

    rem match
    { case CharsOff3Tail('0', 'y', DigitChar(c), tail) => loop (tail, c.toString)
      case CharsOff3Tail('0', 'y', Base32UpperChar(c), tail) => upperLoop (tail, c.toString)
      case CharsOff3Tail('0', 'y', Base32LowerChar(c), tail) => lowerLoop (tail, c.toString)
      case CharsOffHead3('0', 'y', WhitespaceChar(_)) => tp.failLexar("Empty hexademicmal token.")
      case CharsOffHead3('0', 'y', c) => tp.failLexar("Badly formed hexademicmal token.")
      case CharsOff2('0', 'x') => tp.failLexar("Unclosed hexadecimal token")
      case _ => tp.failLexar("Badly formed explicit Hexadecimal literal")
    }
  }
}