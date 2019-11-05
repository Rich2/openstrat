package ostrat
package pParse

object ParseString
{
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def loop(rem: CharsOff, strAcc: StringBuilder): EMon3[CharsOff, TextPosn, Token] = rem match
    {
      case CharsOff0() => bad3(tp, "Unclosed String")
      case CharsOff1Tail('\"', tail2) => Good3(tail2, tp.addLinePosn(strAcc.length + 2),  StringToken(tp, strAcc.mkString))

      case CharsOff1Tail('\\', tail2) => tail2 match
      {
        case CharsOff0() =>  bad3(tp, "Unclosed String ending with unclosed escape Sequence")

        case CharsOff1Tail(c2, tail3) => c2 match
        {
          case '\"' | '\n' | '\b' | '\t' | '\f' | '\r' | '\'' | '\\' => loop(tail3, strAcc.append(c2))
          case c2 => bad3(tp, "Unrecognised escape Sequence \\" + c2.toString)
        }
      }
      case CharsOff1Tail(h, tail2) => loop(tail2, strAcc.append(h))
    }
    loop(rem, new StringBuilder())
  }
}
