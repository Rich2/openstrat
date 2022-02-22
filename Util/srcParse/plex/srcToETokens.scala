/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex

/** Function object for creating an [[EMon]] of Refs of Token from a source. This internally uses a mutable ArrayBuffer, but the mutability is fully
 *  encapsulated. */
object srcToETokens
{
  def str(inp: String): EArr[Token] = apply(inp.toArray, "String")

  /** Max numbers for long and hexadecimal formats needs to be implemented. */
  def apply(charsIn: Array[Char], fileName: String): EArr[Token] =
  { implicit val charArr: Chars = new Chars(charsIn)
    val acc: Buff[Token] = Buff[Token]()

    implicit class E3Implicit (e3: EMon3[CharsOff, TextPosn, Token])
    { def appendLoop: EArr[Token] = e3.flatMap { (cOff, tp, token) =>
      acc.append (token)
      mainLoop (cOff, tp)
      }
    }

    def appendLoop(newToken: Token, charsOff: CharsOff, tp: TextPosn): EArr[Token] =
    { acc.append(newToken)
      mainLoop(charsOff, tp)
    }

    def mainLoop(rem: CharsOff, tp: TextPosn): EArr[Token] = rem match
    { case CharsOff0() => acc.goodRefs
      case CharsOff1Tail(';', tail) => appendLoop(SemicolonToken(tp), tail, tp.right1)
      case CharsOff1Tail(',', tail) => appendLoop(CommaToken(tp), tail, tp.right1)

      case CharsOff1Tail('(', tail) => appendLoop(ParenthOpenToken(tp), tail, tp.right1)
      case CharsOff1Tail(')', tail) => appendLoop(ParenthCloseToken(tp), tail, tp.right1)
      case CharsOff1Tail('[', tail) => appendLoop(SquareOpenToken(tp), tail, tp.right1)
      case CharsOff1Tail(']', tail) => appendLoop(SquareCloseToken(tp), tail, tp.right1)
      case CharsOff1Tail('{', tail) => appendLoop(CurlyOpenToken(tp), tail, tp.right1)
      case CharsOff1Tail('}', tail) => appendLoop(CurlyCloseToken(tp), tail, tp.right1)

      case CharsOffHead4('.', '.', '.', '.') => tp.right3.bad(".... is not an allowed character sequence.")
      case CharsOff3Tail('.', '.', '.', tail) => appendLoop(Dot3Token(tp), tail, tp.right3)
      case CharsOff2Tail('.', '.', tail) => appendLoop(Dot2Token(tp), tail, tp.right2)
      case CharsOff1Tail('.', tail) => appendLoop(DotToken(tp), tail, tp.right1)
      case CharsOff1Tail('\n', tail) => mainLoop(tail, tp.newLine)
      case CharsOff1Tail(h, tail) if h.isWhitespace => mainLoop(tail, tp.right1)

      case CharsOff2Tail('/', '/', tail) =>
      { val len = tail.notPredicateLength(_ == '\n')
        mainLoop(tail.drop(len), tp.right(len + 2))
      }

      case CharsOff3Tail('\'', c1, '\'', tail) => appendLoop(CharToken(tp, c1), tail, tp.right3)
      case CharsOff1Tail('\'', _) => tp.bad("Unclosed Character literal.")

      case CharsOff2Tail('/', '*', tail) =>
      {
        def loop(rem: CharsOff, tp: TextPosn): (CharsOff, TextPosn) = rem match
        { case CharsOff0() => (rem, tp)
          case CharsOff2Tail('*', '/', tail) => (tail, tp.right(2))
          case CharsOff1Tail(_, rem) => loop(rem, tp.right1)
        }
        loop(tail, tp.right2).f2(mainLoop)
      }

      case CharsOffHead('\"') => parseStringToken(rem, tp).appendLoop
      case CharsOffHead(LetterOrUnderscoreChar(_)) => parseIdentifierToken(rem, tp).appendLoop

      case CharsOffHead2('0', 'x') => Nat0xToken.parse(rem, tp).appendLoop
      case CharsOffHead2('0', 'y') => Nat0yToken.parse(rem, tp).appendLoop
      case CharsOff1Tail(DigitChar(d), tail) => parseRawNumberToken(tail, tp, d.toString, false).appendLoop
      case CharsOff2Tail('-', DigitChar(d), tail) => parseRawNumberToken(tail, tp, d.toString, true).appendLoop

      case CharsOffHead(c) if isOperator(c) => parseOperatorToken(rem, tp).appendLoop
      case CharsOffHead(c) => tp.bad("Unimplemented character in main loop: " + c.toString)
    }

    mainLoop(charArr.offsetter0, new TextPosn(fileName, 1, 1))
  }
}