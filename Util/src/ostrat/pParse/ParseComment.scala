package ostrat
package pParse

object ParseComment
{ /** Searches for comment terminator or end of comment, discards comment and returns the new Chars offset and the new TextPosn*/
  def apply(remOff: CharsOff, tp: TextPosn)(implicit charArr: Chars): (CharsOff, TextPosn) =
  {
    def loop(rem: CharsOff, tp: TextPosn): (CharsOff, TextPosn) = rem match
    { case CharsOff0() => (rem, tp)
      case CharsOff2Tail('*', '/', tail) => (tail, tp.right(2))
      case CharsOff1Tail(_, rem) => loop(rem, tp.right1)
    }
    loop(remOff, tp)
  }
}
