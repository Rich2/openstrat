package ostrat
package pParse

object parseIdentifierToken
{
  def apply(remOff: CharsOff, tpStart: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    val acc: StringBuffer = new StringBuffer()

    def maybeTrigLoop(remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    { case CharsOff0() => Good3(remOff, tp, IdentifierMaybeTrigToken(tpStart, acc.toString))
      case CharsOffHead2('_', LetterOrDigitChar(_))  => { acc.append('_'); lowerLoop(remOff.drop1, tp.right1) }
      case CharsOffHead2('_', '_') => tp.right1.bad("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail(TrigdualChar(c), tail) => { acc.append(c); maybeTrigLoop(tail, tp.right1) }
      case CharsOff1Tail(LetterOrDigitChar(c), tail) => { acc.append(c); lowerLoop(tail, tp.right1) }
      case CharsOffHead(_) => Good3(remOff, tp, IdentifierMaybeTrigToken(tpStart, acc.toString))
    }

    def maybeHexaLoop(remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    { case CharsOff0() => Good3(remOff, tp, IdentifierMaybeHexaToken(tpStart, acc.toString))
      case CharsOffHead2('_', LetterOrDigitChar(_))  => { acc.append('_'); upperLoop(remOff.drop1, tp.right1) }
      case CharsOffHead2('_', '_') => tp.right1.bad("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail(HexaLetterChar(c), tail) => { acc.append(c); maybeHexaLoop(tail, tp.right1) }
      case CharsOff1Tail(LetterOrDigitChar(c), tail) => { acc.append(c); upperLoop(tail, tp.right1) }
      case CharsOffHead(_) => Good3(remOff, tp, IdentifierMaybeHexaToken(tpStart, acc.toString))
    }

    def lowerLoop(remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    { case CharsOff0() => Good3(remOff, tp, IdentifierLowerOnlyToken(tpStart, acc.toString))
      case CharsOffHead2('_', LetterOrDigitChar(_))  => { acc.append('_'); lowerLoop(remOff.drop1, tp.right1) }
      case CharsOffHead2('_', '_') => tp.right1.bad("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail(LetterOrDigitChar(c), tail) => { acc.append(c); lowerLoop(tail, tp.right1) }
      case CharsOffHead(_) => Good3(remOff, tp, IdentifierLowerOnlyToken(tpStart, acc.toString))
    }

    def upperLoop(remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    { case CharsOff0() => Good3(remOff, tp, IdentifierUpperOnlyToken(tpStart, acc.toString))
      case CharsOffHead2('_', LetterOrDigitChar(_))  => { acc.append('_'); upperLoop(remOff.drop1, tp.right1) }
      case CharsOffHead2('_', '_') => tp.right1.bad("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail(LetterOrDigitChar(c), tail) => { acc.append(c); upperLoop(tail, tp.right1) }
      case CharsOffHead(_) => Good3(remOff, tp, IdentifierUpperOnlyToken(tpStart, acc.toString))
    }

    remOff match
    {
      //case CharsOff1Plus(LetterUpper(l)) => parseLetterUpper(charsOff, tp).appendLoop

      case CharsOff0() => tpStart.bad("Can not return Token from empty Char Array.")
      case CharsOffHead2('_', '_') => tpStart.right1.bad("Consecutive underscores in Identifier not allowed.")
      case CharsOffHead2('_', LetterOrDigitChar(_)) => { acc.append('_'); lowerLoop(remOff.drop1, tpStart.right1) }
      case CharsOff1Tail('_', tail)  => Good3(tail, tpStart.right1, UnderscoreToken(tpStart))

      case CharsOff1Tail(TrigdualChar(c1), tail) => { acc.append(c1); maybeTrigLoop(tail, tpStart.right1) }
      case CharsOff1Tail(LetterLower(c1), tail) => { acc.append(c1); lowerLoop(tail, tpStart.right1) }

      case CharsOff1Tail(HexaLetterChar(c1), tail) => { acc.append(c1); maybeHexaLoop(tail, tpStart.right1) }
      case CharsOff1Tail(LetterUpper(c1), tail)  => { acc.append(c1); upperLoop(tail, tpStart.right1) }

      case CharsOffHead(c) => tpStart.bad(c.toString + " is not a valid start to an identifer.")
    }
  }
}
