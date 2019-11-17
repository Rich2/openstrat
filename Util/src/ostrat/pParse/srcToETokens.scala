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

    def mainLoop(charsOff: CharsOff, tp: TextPosn): ERefs[Token] = charsOff match
    { case CharsOff0() => acc.goodRefs
      case CharsOff1Tail(';', tail) => { acc.append(SemicolonToken(tp)); mainLoop(tail, tp.right1) }
      case CharsOff1Tail(',', tail) => { acc.append(CommaToken(tp)); mainLoop(tail, tp.right1) }
      case CharsOff1Tail('(', tail) => { acc.append(ParenthOpen(tp)); mainLoop(tail, tp.right1) }
      case CharsOff1Tail(')', tail) => { acc.append(ParenthClose(tp)); mainLoop(tail, tp.right1) }
      case CharsOff1Tail('[', tail) => { acc.append(SquareOpen(tp)); mainLoop(tail, tp.right1) }
      case CharsOff1Tail(']', tail) => { acc.append(SquareClose(tp)); mainLoop(tail, tp.right1) }
      case CharsOff1Tail('{', tail) => { acc.append(CurlyOpen(tp)); mainLoop(tail, tp.right1) }
      case CharsOff1Tail('}', tail) => { acc.append(CurlyClose(tp)); mainLoop(tail, tp.right1) }
      case CharsOff1Tail('.', tail) => { acc.append(DotToken(tp)); mainLoop(tail, tp.right1) }
      case CharsOff1Tail('\n', tail) => mainLoop(tail, tp.newLine)
      case CharsOff1Tail(h, tail) if h.isWhitespace => mainLoop(tail, tp.right1)

      case CharsOff2Tail('/', '/', tail) =>
      { val len = tail.notPredicateLength(_ == '\n')
        mainLoop(tail.drop(len), tp.right(len + 2))
      }

      case CharsOff3Tail('\'', c1, '\'', tail) => { acc.append(CharToken(tp, c1)); mainLoop(tail, tp.right3) }
      case CharsOff1Tail('\'', _) => tp.bad("Unclosed Character literal.")

      //Needs tests.
      case CharsOff1Plus(LetterOrUnderscoreChar(_)) => parseIdentifier(charsOff, tp).flatMap { (cOff, tp, token) =>
        acc.append(token)
        mainLoop(cOff, tp)
      }

      case CharsOff2Tail('/', '*', tail) => parseMultiComment(tail, tp.right2).f2(mainLoop)

      case CharsOff2Plus('0', 'x') => parseHexadecimal(charsOff, tp).flatMap { (co, tp, token) =>
        acc.append(token)
        mainLoop(co, tp)
      }

      //Passes the whole string for sub parsing including the initail Double quotation character.
      case CharsOff1Plus('\"') => parseStringToken(charsOff, tp).flatMap { (cOff, tp, token) =>
        acc.append(token)
        mainLoop(cOff, tp)
      }

      case CharsOff1Tail(DigitChar(d), tail) => parseDeciNumber(tail, tp, d).flatMap { (cOff, tp, token) =>
        acc.append(token)
        mainLoop(cOff, tp)
      }

      case CharsOff1Plus(c) if isOperator(c) => parseOperatorToken(charsOff, tp).flatMap { (cOff, tp, token) =>
        acc.append(token)
        mainLoop(cOff, tp)
      }

      case CharsOff1Plus(c) => tp.bad("Unimplemented character in main loop: " + c.toString)
    }

    mainLoop(charArr.offsetter0, new TextPosn(fileName, 1, 1))
  }
}