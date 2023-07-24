/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._

/** Produces an HTML file documentation for the Util module. */
object UtilPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Util Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Util Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", list, UtilTokenSection, astSect, cenStr2.xCon, miscStr.xCon)

  def list: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("The Util module contains"), debug, gen, coll, errs, parse, persist)

  def debug: HtmlLi = HtmlLi("Some simple debug macros")
  def gen: HtmlLi = HtmlLi("Many useful functions and extension methods such as the iToForeach, iToMap, iToFlatMap, iUntilForeach, etc.")

  def coll: HtmlLi = HtmlLi("Powerful, fast, efficient Array based collections for primitive values and compound value classes. These work on" +
    " both the Java platform, the JVM and in the web browser when compiled to JavaScript.")

  def errs: HtmlLi = HtmlLi("Functional error system using the EMon trait and its Good and Bad sub classes.")

  def parse: HtmlLi = HtmlLi("Parser for RSON, Rich Succinct, Object Notation. Includes a lexar for Tokenisation and a parser for an AST," +
    " abstract syntax tree.")

  def persist: HtmlLi = HtmlLi("Persistence system for Show and UnShow, uses the previously mentioned RSON syntax.")

  def astSect = HtmlSect(RArr(HtmlH2("Abstract Syntax Tree"),
    HtmlP("So after the source has been tokenised it is parsed into an Abstract Syntax tree. the basic idea is that an RSON file can be three things.")
    ), RArr())

  def cenStr2 = """
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

      |""".stripMargin

  def miscStr ="""
      |<h2>Hexadecimal and Base32</h2>
      |  <p>Hexadecimal is written with Uppercase letters. Base32 is written with the digits followed by the upper case letters A to W, with the letter 'O'
      |    unused.</p>
      |  <p>A 10, B 11, C 12, D 13, E 14, F 15, G 16, H 17, I 18, J 19, K 20, L 21, M 22, N 23, P 24, Q 25, R 26, S 27, T 28, U 29 V 30, W 31</p>
      |  <h2>Miscellaneous</h2>
      |  <p>A Lower case letter will be use after numerals in names.</p>
      |""".stripMargin
}