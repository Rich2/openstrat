/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** Function object for creating an EMon of Refs of Token from a source. This internally uses a mutable ArrayBuffer, but the mutability is fully
 *  encapsulated. */
object srcToETokens
{
  /** Max numbers for long and hexadecimal formats needs to be implemented. */
  def apply(srcStr: String, fileName: String): ERefs[Token] =
  { val array: Array[Char] = srcStr.toCharArray
    implicit val charArr: Chars = new Chars(array)
    val acc: Buff[Token] = Buff[Token]()

    implicit class E3Implicit (e3: EMon3[CharsOff, TextPosn, Token])
    { def appendLoop: ERefs[Token] = e3.flatMap { (cOff, tp, token) =>
      acc.append (token)
      mainLoop (cOff, tp)
      }
    }

    def appendLoop(newToken: Token, charsOff: CharsOff, tp: TextPosn): ERefs[Token] =
    {
      acc.append(newToken)
      mainLoop(charsOff, tp)
    }

    def mainLoop(charsOff: CharsOff, tp: TextPosn): ERefs[Token] = charsOff match
    { case CharsOff0() => acc.goodRefs
      case CharsOff1Tail(';', tail) => appendLoop(SemicolonToken(tp), tail, tp.right1)
      case CharsOff1Tail(',', tail) => appendLoop(CommaToken(tp), tail, tp.right1)

      case CharsOff1Tail('(', tail) => appendLoop(ParenthOpen(tp), tail, tp.right1)
      case CharsOff1Tail(')', tail) => appendLoop(ParenthClose(tp), tail, tp.right1)
      case CharsOff1Tail('[', tail) => appendLoop(SquareOpen(tp), tail, tp.right1)
      case CharsOff1Tail(']', tail) => appendLoop(SquareClose(tp), tail, tp.right1)
      case CharsOff1Tail('{', tail) => appendLoop(CurlyOpen(tp), tail, tp.right1)
      case CharsOff1Tail('}', tail) => appendLoop(CurlyClose(tp), tail, tp.right1)

      case CharsOff4Plus('.', '.', '.', '.') => tp.right3.bad(".... is not an allowed character sequence.")
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

      case CharsOff1Plus(LetterUpper(l)) => parseLetterUpper(charsOff, tp).appendLoop
      case CharsOff1Plus(LetterOrUnderscoreChar(_)) => parseIdentifier(charsOff, tp).appendLoop
      case CharsOff2Tail('/', '*', tail) => parseMultiComment(tail, tp.right2).f2(mainLoop)
      case CharsOff1Plus('\"') => parseStringToken(charsOff, tp).appendLoop
      case CharsOff1Plus(DigitChar(d, _)) => parseNumberToken(charsOff, tp).appendLoop
      case CharsOff1Plus(c) if isOperator(c) => parseOperatorToken(charsOff, tp).appendLoop

      case CharsOff1Plus(c) => tp.bad("Unimplemented character in main loop: " + c.toString)
    }

    mainLoop(charArr.offsetter0, new TextPosn(fileName, 1, 1))
  }
}