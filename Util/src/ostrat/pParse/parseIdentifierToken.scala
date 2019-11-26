package ostrat
package pParse

object parseIdentifierToken
{
  def apply(remOff: CharsOff, tpStart: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    val acc: StringBuffer = new StringBuffer()
    def loop(remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    {
      case CharsOff0() => Good3(remOff, tp, IdentifierLowerOnlyToken(tpStart, acc.toString))
      case CharsOff1Tail(LetterOrDigitChar(c), tail) => { acc.append(c); loop(tail, tp.right1) }
      case CharsOff2Plus('_', LetterOrDigitChar(_))  => { acc.append('_'); loop(remOff.drop1, tp.right1) }
      case CharsOff2Plus('_', '_') => tp.right1.bad("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Plus(_) => Good3(remOff, tp, IdentifierLowerOnlyToken(tpStart, acc.toString))
    }

    remOff match
    {
      //case CharsOff1Plus(LetterUpper(l)) => parseLetterUpper(charsOff, tp).appendLoop

      case CharsOff0() => tpStart.bad("Can not return Token form empty Char Array.")
      case CharsOff2Plus(LetterOrUnderscoreChar(c1), LetterOrDigitChar(_)) => { acc.append(c1); loop(remOff.drop1, tpStart.right1) }
      case CharsOff2Plus(LetterChar(c1), '_') => { acc.append(c1); loop(remOff.drop1, tpStart.right1) }
      case CharsOff2Plus('_', '_') => tpStart.right1.bad("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail('_', tail)  => Good3(tail, tpStart.right1, UnderscoreToken(tpStart))
      case CharsOff1Tail(LetterChar(l), tail) => { acc.append(l); loop(tail, tpStart.right1) }
      case CharsOff1Plus(c) => tpStart.bad(c.toString + " is not a valid start to an identifer.")
    }
  }
}
