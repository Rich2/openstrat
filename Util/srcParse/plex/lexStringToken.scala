/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex

/** Function object that parses String (with RSON syntax) searching for the String terminator. Returns error if end of file found first. */
object lexStringToken
{ /** Parses String (with RSON syntax) searching for the String terminator. Returns error if end of file found first. */
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, StringToken] =
  {
    val strAcc: StringBuilder = new StringBuilder()

    def loop(rem: CharsOff): EMon3[CharsOff, TextPosn, StringToken] = rem match
    { case CharsOff0() => tp.bad3("Unclosed String")
      case CharsOff1Tail('\"', tail2) => Good3(tail2, tp.right(strAcc.length + 2),  StringToken(tp, strAcc.mkString))
      case CharsOff1('\\') =>  tp.bad3("Unclosed String ending with unclosed escape Sequence")
      case CharsOff2Tail('\\', c2, tail) if Array('\"', '\n', '\b', '\t', '\f', '\r', '\'', '\\').contains(c2) => { strAcc.append(c2); loop(tail) }
      case CharsOffHead2('\\', c2) => tp.bad3("Unrecognised escape Sequence \\" + c2.toString)
      case CharsOff1Tail(h, tail2) => { strAcc.append(h); loop(tail2) }
    }
    rem match
    {
      case CharsOff1Tail('\"', tail) => loop(tail)
      case _ => tp.bad3("These characters do not begin with a String opening delimitter.")
    }

  }
}