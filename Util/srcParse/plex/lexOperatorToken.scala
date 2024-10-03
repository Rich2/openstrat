/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex

/** Function object to parse operator token. */
object lexOperatorToken
{
  def apply(remOff: CharsOff, tp: TextPosn)(implicit charArr: CharArr): ErrBi3[ExcLexar, CharsOff, TextPosn, Token] =
  { var acc: String = ""

    def loop(remOff: CharsOff, tp: TextPosn): ErrBi3[ExcLexar, CharsOff, TextPosn, Token] = remOff match {
      case CharsOffHead2('/', '/' | '*') => Succ3(remOff, tp, sort)
      case CharsOff1Tail(OperatorChar(c), tail) =>
      { acc :+= c;
        loop(tail, tp.right1)
      }
      case _ => Succ3(remOff, tp, sort)
    }

    def sort: Token = acc.last match
    { case '=' => AsignToken(tp)
      case ':' => ColonToken(tp)
      case _ if acc == "/" => SlashToken(tp)
      case _ => acc.head match
      { case '|' | '^' | '&' | '=' | '!' | '<' | '>' | ':' | '+' | '-' | '*' | '/' | '%' => OperatorPrec1Token(tp, acc)
        case _ => OperatorPrec0Token(tp, acc)
      }
    }

    loop(remOff, tp)
  }
}