/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._

object UtilTokenSection extends HtmlSection
{
  override def contents: RArr[XCon] = RArr(HtmlH2("Tokeniser"), tokList, gen2, identList, lits, table1, HtmlBr, table2)

  def tokList: HtmlUlWithLH = HtmlUlWithLH("The Tokeniser will create the following tokens",
    HtmlLi("""Keytokens <span class= lexical>_ ? ?? ???</ span >"""),
    HtmlLi("Identifiers alphanumeric tokens starting with a letter or underscore character."), HtmlLi("Operators"),
    HtmlLi("Numeric literals"), HtmlLi("Separators , . .. ... {} etc."), HtmlLi("String literals"), HtmlLi("Character literals"), HtmlLi("Comments"))

  def gen2 = HtmlP("KeyTokens, Identifiers, and literals are all expressions. Operators, separators and comments are not. Identifiers" --
    "includes lexemes such as <span class=lexical>if, IF true and TRUE</span>. There are no alphabetic keywords in RSON syntax. Consumers of" --
    "RSON syntax can of course treat what ever identifiers they want as keywords appropriate to their use case.")

  def identList = HtmlUlWithLH("Identifiers are categorised into 3 types.", HtmlLi("IdentUnder An identifer beginning with an underscore character."),
    HtmlLi("IdentLow And identifer beginning with a lower case alphabetic character."),
    HtmlLi("IdentUp An identifer beginning with an upper case alphabetic character. Some of these tokens will also be consider valid raw Base32" --
      "tokens, and a subset of these will also be considered valid raw Hexadecimals, however all the alphabetic characters must be lower case.")
  )


  def lits: HtmlOlWithLH = HtmlOlWithLH("Numerical literals come in 4 types.",
    HtmlLi("Floating point numbers <span class= lexical>6.02e23 6.02e-23 6.02e-23d</span>.Note this is the only case where a negative or dash" --
      "character is included as part of a token. Can have optional trailing lower case alphabetic characters at the end of the token."),
    HtmlLi("Explicit hexadecimals <span class= lexical>0x433A 0x2222 0xff000bc</span> Alphabetic characters must be all lower or all upper case."),
    HtmlLi("Explicit Base32 tokens <span class= lexical>0y433G 0x222C 0yWW000MP</span> Alphabetic characters must all be upper case.</li>"),
    digToks, negToks)

  def digToks = HtmlLi(RArr(digToksEl))

  def digToksEl = HtmlUlWithLH("DigitCode tokens. These are a sequence of one or more sequences of digits separated by decimal points, as well as" --
    "integer and fractional decimal numbers they can be used for version numbers, IP addresses and other codes. These can themsleves be further" --
    "divided into",
    HtmlLi("Valid natural integers <span class= lexical>0 21 567</span> These are valid raw hexademimal and raw Base32 tokens."),
    HtmlLi("Valid natural integers with trailing lower case alphabetic characters at the end of the token" --
      "<span class=lexical>0f 21snap 567d</span>. These may be valid raw hexadecimal or Base32 tokens if the alphabetic letter all fall within the" --
      "correct set of letters."),
    HtmlLi("Raw Hexadecimals that start with a digit, the alphabetic characters must be all lower or all upper case." --
      "<span class= lexical>3A 2d1e 567D</span>. These are also valid raw Base32 tokens."),
    HtmlLi("Raw Base32 tokens that start with a digit that are not valid raw Hexadecimals, the alphabetic characters must be all lower or all" --
      "upper case. <span class= lexical>3G 2d1s 567R</span>"),
    HtmlLi("Valid fractional decimal numbers, which may have trailing lower case alphabetic characters at the end of the token" --
      "<span class= lexical>0.0f 2084.4 3.1rc</span>"),
    HtmlLi("DigitCode Tokens with 3 or greater digit sequence parts <span class= lexical>2.13.4 0.0.3snap 192.168.1.1</span>")
  )

  def negToks = HtmlLi("There will be tokens for negative numbers. <span class= lexical>-5 -5.32 -5.87e7 -0xA434</span> will all be lexically" --
    "processed as single tokens. This means that raw hexidecimals and raw base32s can be processed as 1 or 2 tokens depending on whether they" --
    "start with a digit. This should not cause a problem as long as they are not combined with dot operators in dot expressions.")

  def table1: HtmlTable = HtmlTable(
    HtmlRow.strs2("LetterChar", "= unicode_letter | '_'"),
    HtmlRow.strs2("NonZeroDigit", "= '1' ... '9'"),
    HtmlRow.strs2("DigitChar", "= '0' | NonZeroDigit"),
    HtmlRow.strs2("HexaLowerChar", "= 'a' ... 'f'"),
    HtmlRow.strs2("HexaUpperChar", "= 'A' ... 'F'"),
    HtmlRow.strs2("HexaLetterChar", "= HexaLowerChar | 'a' ... 'f'"),
    HtmlRow.strs2("HexaChar", "= DigitChar | HexLetterChar"),
    HtmlRow.strs2("LetterOrDigitChar", "= LetterChar | DigitChar"),
    HtmlRow.strs2("LetterOrUnderscoreChar", "= LetterChar | '_'"),
    HtmlRow.strs2("UnderscoreThenLetterOrDigit", "= '_', LetterOrDigitChar"),
    HtmlRow.strs2("Dot3Token", "= \"...\""),
    HtmlRow.strs2("Dot2Token", "= \"..\""),
    HtmlRow.strs2("DotToken", "= '.'")
  )

  val table2 = HtmlTable(
    HtmlRow.strs2("IdentifierToken", "= letter | UnderscoreThenLetterOrDigit, { LetterOrDigitChar | UnderscoreThenLetterOrDigit }"),
    HtmlRow.strs2("DeciLitToken", "= '0' | (NonZeroDigit { DigitChar })")
  )
}