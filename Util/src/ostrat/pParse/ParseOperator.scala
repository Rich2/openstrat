package ostrat
package pParse

object ParseOperator
{
  /** Not sure if this is fully fixed. */
  def apply(remOff: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon[(CharsOff, TextPosn, Token)] =
  {
    val (opChars, finalTail) = remOff.span(isOperator)
    val opStr = opChars.mkString

    val ot =  opChars.last match
    {
      //below makes no sense
      case '+' | '-' => finalTail match
      { //case CharsOff0() =>
        case CharsOff1Plus(h) if !h.isWhitespace => PrefixToken(tp, opStr)
        case _ => PlusInToken(tp, opStr)
      }
      case '=' => AsignToken(tp)
      case _ => OtherOperatorToken(tp, opStr)
    }
    Good3(finalTail, tp.addChars(opChars.toList),  ot)
  }


}


