/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** Function object for creating an EMon of Refs of Token from a source. This internally uses a mutable ArrayBuffer, but the mutability is fully
 *  encapsulated. */
object srcETokens
{
  /** Max numbers for long and hexadecimal formats needs to be implemented */
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
      case CharsOff1Tail('\'', _) => bad1(tp, "Unclosed Character literal.")

      //Needs attention.
      case CharsOff1Plus(LetterChar(a)) =>
      { val (alphaStr, finalTail) = charsOff.span(a => a.isLetterOrDigit | a == '.')
        acc.append(AlphaToken(tp, alphaStr.mkString))
        mainLoop(finalTail, tp.addChars(alphaStr.array))
      }

      case CharsOff2Tail('/', '*', tail) => parseMultiComment(tail, tp.right2).f2(mainLoop)

      case CharsOff2Plus('0', 'x') => Hexadecimal(charsOff, tp).flatMap { (co, tp, token) =>
        acc.append(token)
        mainLoop(co, tp)
      }

      case CharsOff1Tail('\"', tail) => parseStringToken(tail, tp).flatMap { (cOff, tp, token) =>
        acc.append(token)
        mainLoop(cOff, tp)
      }

      case CharsOff1Tail(DigitChar(d), tail) => DecimalNumber(tail, tp, d).flatMap { (cOff, tp, token) =>
        acc.append(token)
        mainLoop(cOff, tp)
      }

      case CharsOff1Plus(c) if isOperator(c) => parseOperator(charsOff, tp).flatMap { (cOff, tp, token) =>
        acc.append(token)
        mainLoop(cOff, tp)
      }

      case CharsOff1Plus(c) => bad1(tp, "Unimplemented character in main loop: " + c.toString)
    }

    mainLoop(charArr.charsOffsetter, new TextPosn(fileName, 1, 1))
  }
}
