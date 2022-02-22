/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex

/** Function object to parse identifier token. If successful it passes back, the remaining chars pointer, the test position and the token. */
object parseIdentifierToken
{ /** Function apply method to parse identifier token. If successful it passes back, the remaining chars pointer, the test position and the token. */
  def apply(remOff: CharsOff, tpStart: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    //val acc: StringBuffer = new StringBuffer()

    def upperHexaLoop(acc: String, remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    { case CharsOff0() => Good3(remOff, tp, IdentUpperHexaToken(tpStart, acc))
      case CharsOffHead2('_', LetterOrDigitChar(_))  => upperLoop(acc + '_', remOff.drop1, tp.right1)
      case CharsOffHead2('_', '_') => tp.right1.bad3("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail(HexaUpperChar(c), tail) => upperHexaLoop(acc + c, tail, tp.right1)
      case CharsOff1Tail(DigitChar(c), tail) => upperHexaLoop(acc + c, tail, tp.right1)
      case CharsOff1Tail(LetterChar(c), tail) => upperLoop(acc + c, tail, tp.right1)
      case CharsOffHead(_) => Good3(remOff, tp, IdentUpperHexaToken(tpStart, acc))
    }

    def lowerHexaLoop(acc: String, remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    { case CharsOff0() => Good3(remOff, tp, IdentLowerHexaToken(tpStart, acc))
      case CharsOffHead2('_', LetterOrDigitChar(_))  => lowerLoop(acc + '_', remOff.drop1, tp.right1)
      case CharsOffHead2('_', '_') => tp.right1.bad3("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail(HexaLowerChar(c), tail) => lowerHexaLoop(acc + c, tail, tp.right1)
      case CharsOff1Tail(LetterOrDigitChar(c), tail) => lowerLoop(acc + c, tail, tp.right1)
      case CharsOffHead(_) => Good3(remOff, tp, IdentLowerHexaToken(tpStart, acc))
    }

    def upperBase32Loop(acc: String, remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match {
      case CharsOff0() => Good3(remOff, tp, IdentUpperBase32OnlyToken(tpStart, acc))
      case CharsOffHead2('_', LetterOrDigitChar(_)) => upperLoop(acc + "_", remOff.drop1, tp.right1)
      case CharsOffHead2('_', '_') => tp.right1.bad3("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail(Base32UpperChar(c), tail) => upperBase32Loop(acc + c, tail, tp.right1)
      case CharsOff1Tail(DigitChar(d), tail) => upperBase32Loop(acc + d, tail, tp.right1)
      case CharsOff1Tail(LetterChar(c), tail) => upperLoop(acc + c, tail, tp.right1)
      case CharsOffHead(_) => Good3(remOff, tp, IdentUpperBase32OnlyToken(tpStart, acc))
    }


    def lowerBase32Loop(acc: String, remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    { case CharsOff0() => Good3(remOff, tp, IdentLowerBase32OnlyToken(tpStart, acc))
      case CharsOffHead2('_', LetterOrDigitChar(_))  => lowerLoop(acc + '_', remOff.drop1, tp.right1)
      case CharsOffHead2('_', '_') => tp.right1.bad3("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail(Base32LowerChar(c), tail) => lowerBase32Loop(acc + c, tail, tp.right1)
      case CharsOff1Tail(LetterOrDigitChar(c), tail) => lowerLoop(acc + c, tail, tp.right1)
      case CharsOffHead(_) => Good3(remOff, tp, IdentLowerOnlyToken(tpStart, acc))
    }

    def underLoop(acc: String, remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    { case CharsOff0() => Good3(remOff, tp, IdentUnderToken(tpStart, acc))
      case CharsOffHead2('_', LetterOrDigitChar(_))  => underLoop(acc + '_', remOff.drop1, tp.right1)
      case CharsOffHead2('_', '_') => tp.right1.bad3("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail(LetterOrDigitChar(c), tail) => underLoop(acc + c, tail, tp.right1)
      case CharsOffHead(_) => Good3(remOff, tp, IdentUnderToken(tpStart, acc))
    }

    def lowerLoop(acc: String, remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    { case CharsOff0() => Good3(remOff, tp, IdentLowerOnlyToken(tpStart, acc))
      case CharsOffHead2('_', LetterOrDigitChar(_))  => lowerLoop(acc + '_', remOff.drop1, tp.right1)
      case CharsOffHead2('_', '_') => tp.right1.bad3("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail(LetterOrDigitChar(c), tail) => lowerLoop(acc + c, tail, tp.right1)
      case CharsOffHead(_) => Good3(remOff, tp, IdentLowerOnlyToken(tpStart, acc))
    }


    def upperLoop(acc: String, remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    { case CharsOff0() => Good3(remOff, tp, IdentUpperOnlyToken(tpStart, acc))
      case CharsOffHead2('_', LetterOrDigitChar(_))  => upperLoop(acc + '_', remOff.drop1, tp.right1)
      case CharsOffHead2('_', '_') => tp.right1.bad3("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail(LetterOrDigitChar(c), tail) => upperLoop(acc + c, tail, tp.right1)
      case CharsOffHead(_) => Good3(remOff, tp, IdentUpperOnlyToken(tpStart, acc))
    }

    remOff match
    {
      case CharsOff0() => tpStart.bad3("Can not return Token from empty Char Array.")
      case CharsOffHead2('_', '_') => tpStart.right1.bad3("Consecutive underscores in Identifier not allowed.")
      case CharsOffHead2('_', LetterOrDigitChar(_)) => underLoop("_", remOff.drop1, tpStart.right1)
      case CharsOff1Tail('_', tail)  => Good3(tail, tpStart.right1, UnderscoreToken(tpStart))

      case CharsOff1Tail(HexaUpperChar(c1), tail) => upperHexaLoop(c1.toString, tail, tpStart.right1)
      case CharsOff1Tail(Base32UpperChar(c1), tail) => upperBase32Loop(c1.toString(), tail, tpStart.right1)
      case CharsOff1Tail(LetterUpper(c1), tail)  => upperLoop(c1.toString, tail, tpStart.right1)

      case CharsOff1Tail(HexaLowerChar(c1), tail) => lowerHexaLoop(c1.toString, tail, tpStart.right1)
      case CharsOff1Tail(Base32LowerChar(c1), tail) => lowerBase32Loop(c1.toString, tail, tpStart.right1)
      case CharsOff1Tail(LetterLower(c1), tail) => lowerLoop(c1.toString, tail, tpStart.right1)

      case CharsOffHead(c) => tpStart.bad3(c.toString + " is not a valid start to an identifier.")
    }
  }
}