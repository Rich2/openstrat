/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** not sure about comment tokens */
case class TokensFind(srcStr: String)
{
  val array: Array[Char] = srcStr.toCharArray
  implicit val charArr: Chars = new Chars(array)
  type ETokenList = EMon[Refs[Token]]
  /** Max numbers for long and hexadecimal formats needs to be implemented */
  def apply(fileName: String): ETokenList =
    mainLoop(charArr.charsOffsetter, new TextPosn(fileName, 1, 1), Buff[Token]())

  def fromString: ETokenList = apply("String")

  private def mainLoop(remOff: CharsOff, tp: TextPosn, acc: Buff[Token]): ETokenList = remOff match
  {
    case CharsOff0() => acc.goodRefs
    case CharsOff1Tail(';', tail) => mainLoop(tail, tp.nextChar, acc.append(SemicolonToken(tp)))
    case CharsOff1Tail(',', tail) => mainLoop(tail, tp.nextChar,  acc.append(CommaToken(tp)))
    case CharsOff1Tail('(', tail) => mainLoop(tail, tp.nextChar,  acc.append(ParenthOpen(tp)))
    case CharsOff1Tail(')', tail) => mainLoop(tail, tp.nextChar,  acc.append(ParenthClose(tp)))
    case CharsOff1Tail('[', tail) => mainLoop(tail, tp.nextChar,  acc.append(SquareOpen(tp)))
    case CharsOff1Tail(']', tail) => mainLoop(tail, tp.nextChar,  acc.append(SquareClose(tp)))
    case CharsOff1Tail('{', tail) => mainLoop(tail, tp.nextChar,  acc.append(CurlyOpen(tp)))
    case CharsOff1Tail('}', tail) => mainLoop(tail, tp.nextChar,  acc.append(CurlyClose(tp)))
    case CharsOff1Tail('.', tail) => mainLoop(tail, tp.nextChar,  acc.append(DotToken(tp)))
    case CharsOff1Tail('\n', tail) => mainLoop(tail, tp.newLine, acc)
    case CharsOff1Tail(h, tail) if h.isWhitespace => mainLoop(tail, tp.nextChar, acc)
    case CharsOff2Tail('/', '/', tail) => { val len = tail.notPredicateLength(_ == '\n'); mainLoop(tail.drop(len), tp.right(len), acc) }
    case CharsOff3Tail('\'', c1, '\'', tail) => mainLoop(tail, tp.right(3), acc.append(CharToken(tp, c1)))
    case CharsOff1Tail('\'', _)=> bad1(tp, "Unclosed Character literal.")

    case CharsOff1Tail(a, tail) if a.isLetter =>
    { val (alphaStr, finalTail) = remOff.span(a => a.isLetterOrDigit | a == '.')
      mainLoop(finalTail, tp.addChars(alphaStr.array), acc.append(AlphaToken(tp, alphaStr.mkString)))
    }

    case CharsOff2Tail('/', '*', remOff) =>
    {
      def loop(rem: CharsOff, tp: TextPosn): ETokenList = rem match
      { case CharsOff0() => acc.goodRefs
        case CharsOff2Tail('*', '/', rem) => mainLoop(rem, tp, acc)
        case CharsOff1Tail(_, rem) => loop(rem, tp.nextChar)
      }      
      loop(remOff, tp.addLinePosn(2))
    }
    case CharsOff2Plus('0', 'x')  => Hexadecimal(remOff, tp).flatMap{ case (co, tp, ht) => mainLoop(co, tp, acc.append(ht)) }
    case CharsOff1Tail('\"', tail) => ParseString(tail, tp).flatMap{case (cOff, tp, token) => mainLoop(cOff, tp, acc.append(token)) }
    case CharsOff1Tail(d, tail) if d.isDigit => DecimalNumber(tail, tp, d).flatMap{case (cOff, tp ,token) => mainLoop(cOff, tp, acc.append(token)) }
    case CharsOff1Plus(c) if isOperator(c) => ParseOperator(remOff, tp).flatMap{case (cOff, tp ,token) => mainLoop(cOff, tp, acc.append(token)) }
    case CharsOff1Plus(c) => bad1(tp, "Unimplemented character in main loop: " + c.toString)
  }
}