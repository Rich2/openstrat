/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._

/** Produces an HTML file documentation for the Util module. */
object UtilPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Util Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Util Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", list, HtmlH2("Tokeniser"), tokList, gen2, identList, lits, table1, cenStr1.xCon, astHeaders, cenStr2.xCon)

  def list: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("The Util module contains"), debug, gen, coll, errs, parse, persist)

  def debug: HtmlLi = HtmlLi("Some simple debug macros")
  def gen: HtmlLi = HtmlLi("Many useful functions and extension methods such as the iToForeach, iToMap, iToFlatMap, iUntilForeach, etc.")

  def coll: HtmlLi = HtmlLi("Powerful, fast, efficient Array based collections for primitive values and compound value classes. These work on" +
    " both the Java platform, the JVM and in the web browser when compiled to JavaScript.")

  def errs: HtmlLi = HtmlLi("Functional error system using the EMon trait and its Good and Bad sub classes.")

  def parse: HtmlLi = HtmlLi("Parser for RSON, Rich Succinct, Object Notation. Includes a lexar for Tokenisation and a parser for an AST," +
    " abstract syntax tree.")

  def persist: HtmlLi = HtmlLi("Persistence system for Show and UnShow, uses the previously mentioned RSON syntax.")

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

  val cenStr1: String = """
      |<br>
      |    <table>
      |    <tr><td>IdentifierToken</td>             <td>= letter | UnderscoreThenLetterOrDigit, { LetterOrDigitChar | UnderscoreThenLetterOrDigit }</td></tr>
      |    <tr><td>DeciLitToken</td>                <td>= '0' | (NonZeroDigit { DigitChar })</td></tr>
      |  </table>""".stripMargin

  def astSect = new HtmlSect {
    /** The attributes of this XML / HTML element. */
    override def attribs: RArr[XmlAtt] = ???

    /** The content of this XML / HTML element. */
    override def contents: RArr[XCon] = ???
  }

  def astHeaders = HtmlH2("Abstract Syntax Tree")

  def cenStr2 = """
      |  <p>So after the source has been tokenised it is parsed into an Abstract Syntax tree. the basic idea is that an RSON file can be three things.
      |  <ol>
      |    <li>An unStatemented file. It just contains an expression, without a semi colon at the end for example could just an Int or String.</li>
      |    <li>A Statemented file</li>
      |    <li>The empty file. It may contain comments but no expressions. The empty statement is a thing in itself but also a special case of a claused
      |    statement with with zero Clauses.</li>
      |  </ol>
      |
      |  <ol>
      |    <li>An unclaused Statement. It just contains an expression, without a comma at the end for example could just an Int or String.</li>
      |    <li>A Claused Statement</li>
      |    <li>The empty Statement It may contain comments but no expressions. The empty file is a thing in itslef but also a special case of a Statemented
      |     file with zero statements.</li>
      |  </ol>
      |    So there is currently some confusion as to where it is parsed into a series of statements or into an expression. Currently Statements and Clauses
      |    contain an expression but are not themselves an expression. This is causing a block to me coding at the moment.
      |  </p>
      |  <p>AST Precedence From lowest to highest after brace block parsing.</p>
      |    <ul>
      |      <li>SemicolonToken Delimits the end of a Statement. The last Statement of a block /file may have, but does not need a need a trailing Semicolon. A statement without commas is considered Unclaused.</li>
      |      <li>CommaToken Delimits the end of clause. The last Clause of a Statement may have, but does not need a trailing Comma, unless it is a single Clause Statment in which case it must have a trailing Comma to
      |      distinguish it from an Unclaused Statement.</li>
      |      <li>assignment operators. == and != are normal operators. Any other operators ending with a '=' character are assignment operators.</li>
      |      <li>The single Colon Token</li>
      |      <li>| From here on down the precedence is determined by the first character of the operator. An operator ending in a ':' will be expected to be an infix operator dispatch from its rhs.</li>
      |      <li>^</li>
      |      <li>&</li>
      |      <li>= !</li>
      |      <li>:</li>
      |      <li>+ -</li>
      |      <li>* / %</li>
      |      <li>All other special charachters</li>
      |      <li>Whitespace</li>
      |  </ul>
      |  </p>
      |  <h2>Hexadecimal and Base32</h2>
      |  <p>Hexadecimal is written with Uppercase letters. Base32 is written with the digits followed by the upper case letters A to W, with the letter 'O'
      |    unused.</p>
      |  <p>A 10, B 11, C 12, D 13, E 14, F 15, G 16, H 17, I 18, J 19, K 20, L 21, M 22, N 23, P 24, Q 25, R 26, S 27, T 28, U 29 V 30, W 31</p>
      |  <h2>Miscellaneous</h2>
      |  <p>A Lower case letter will be use after numerals in names.</p>
      |""".stripMargin
}