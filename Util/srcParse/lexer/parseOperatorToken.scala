/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package lexer

/** Function object to parse operator token. */
object parseOperatorToken
{
  /** Not sure if this is fully fixed. Parses an operator. Operators can have multiple charachters in RSON. */
  def apply(remOff: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    var acc: String = ""
    def loop(remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    {
      case CharsOffHead2('/', '/' | '*') => Good3(remOff, tp, sort)
      case CharsOff1Tail(OperatorChar(c), tail) => { acc :+= c; loop(tail, tp.right1) }
      case _ => Good3(remOff, tp, sort)
    }

    def sort: Token = acc.last match
    {
      case '=' => AsignToken(tp)
      case _ => OperatorToken(tp, acc)
    }
    loop(remOff, tp)
  }
}