/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex
import collection.mutable.ArrayBuffer

/** More imperative version of Function object for creating an [[ErrBi]] of [[Token]]s. This internally uses a mutable ArrayBuffer, but the mutability is fully
 * encapsulated. */
object lexSrc
{ /** lexes [[String]]. */
  def str(inp: String): ErrBiArr[ExcLexar, Token] = apply(inp.toArray, "String")

  /** Max numbers for long and hexadecimal formats needs to be implemented. */
  def apply(charsIn: Array[Char], fileName: String): ErrBiArr[ExcLexar, Token] =
  { implicit val charArr: CharArr = new CharArr(charsIn)
    val acc: ArrayBuffer[Token] = Buffer[Token]()
    var acc2: ErrBi[ExcLexar, ArrayBuffer[Token]] = Succ(acc)
    var rem: CharsOff = charArr.offsetter0
    var posn: TextPosn = new TextPosn(fileName, 1, 1)

    implicit class E3LexarImplicit(e3: ErrBi3[ExcLexar, CharsOff, TextPosn, Token])
    {
      def append3Loop: Unit = e3 match
      {
        case Succ3 (co, tp, token) =>
        { acc.append (token)
          rem = co
          posn = tp
        }
        case Fail(err) => acc2 = Fail(err)
        case eb => excep(s"$eb This case was unexpected")
      }
    }

    /** Before this was inlined getting stack overflows. */
    def appendLoop(newToken: Token, charsOff: CharsOff, tpNew: TextPosn): Unit =
    { acc.append(newToken)
      rem = charsOff
      posn = tpNew
    }

    while(rem.length > 0 && acc2.isSucc)  rem match
    { case CharsOff0() => acc.succRArr
      case CharsOff1Tail(';', tail) => appendLoop(SemicolonToken(posn), tail, posn.right1)
      case CharsOff1Tail(',', tail) => appendLoop(CommaToken(posn), tail, posn.right1)

      case CharsOff1Tail('(', tail) => appendLoop(ParenthOpenToken(posn), tail, posn.right1)
      case CharsOff1Tail(')', tail) => appendLoop(ParenthCloseToken(posn), tail, posn.right1)
      case CharsOff1Tail('[', tail) => appendLoop(SquareOpenToken(posn), tail, posn.right1)
      case CharsOff1Tail(']', tail) => appendLoop(SquareCloseToken(posn), tail, posn.right1)
      case CharsOff1Tail('{', tail) => appendLoop(CurlyOpenToken(posn), tail, posn.right1)
      case CharsOff1Tail('}', tail) => appendLoop(CurlyCloseToken(posn), tail, posn.right1)

      case CharsOffHead4('.', '.', '.', '.') => acc2 = posn.right(4).failLexar(".... is not an allowed character sequence.")
      case CharsOff3Tail('.', '.', '.', tail) => appendLoop(Dot3Token(posn), tail, posn.right3)
      case CharsOff2Tail('.', '.', tail) => appendLoop(Dot2Token(posn), tail, posn.right2)
      case CharsOff1Tail('.', tail) => appendLoop(DotToken(posn), tail, posn.right1)
      case CharsOff1Tail('\n', tail) => { rem = tail; posn = posn.newLine }
      case CharsOff1Tail(h, tail) if h.isWhitespace =>  { rem = tail; posn = posn.right1 }

      case CharsOff2Tail('/', '/', tail) =>
      { val len = tail.notPredicateLength(_ == '\n')
        rem = tail.drop(len)
        posn = posn.right(len + 2)
      }

      case CharsOff3Tail('\'', c1, '\'', tail) => appendLoop(CharToken(posn, c1), tail, posn.right3)
      case CharsOff1Tail('\'', _) => acc2 = posn.failLexar("Unclosed Character literal.")

      case CharsOff2Tail('/', '*', tail) =>
      {
        def loop(rem: CharsOff, tp: TextPosn): (CharsOff, TextPosn) = rem match
        { case CharsOff0() => (rem, tp)
          case CharsOff2Tail('*', '/', tail) => (tail, tp.right(2))
          case CharsOff1Tail(_, rem) => loop(rem, tp.right1)
        }
        val pair = loop(tail, posn.right2)
        rem = pair._1
        posn = pair._2
      }

      case CharsOffHead('\"') => lexStringToken(rem, posn).append3Loop
      case CharsOffHead(LetterOrUnderscoreChar(_)) => lexIdentifierToken(rem, posn).append3Loop

      case CharsOffHead2('0', 'x') => Nat0xToken.parse(rem, posn).append3Loop
      case CharsOffHead2('0', 'y') => Nat0yToken.parse(rem, posn).append3Loop
      case CharsOff1Tail(DigitChar(d), tail) => lexRawNumberToken(tail, posn, d.toString, false).append3Loop
      case CharsOff2Tail('-', DigitChar(d), tail) => lexRawNumberToken(tail, posn, d.toString, true).append3Loop
      case CharsOffHead2('/', LetterOrUnderscoreChar(_) ) => lexPathToken(rem, posn).append3Loop
      case CharsOffHead(c) if isOperator(c) => lexOperatorToken(rem, posn).append3Loop
      case CharsOffHead(c) => acc2 = posn.failLexar("Unimplemented character in main loop: " + c.toString)
    }
    acc2.map(_.toArr)
  }
}