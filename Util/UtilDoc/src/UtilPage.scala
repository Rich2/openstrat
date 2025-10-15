/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

trait OSDocumentationPage extends HtmlPage
{ /** A title [[String]] is all that is needed to be implemented by the final class to complete the [[HtmlHead]]. */
  def titleStr: String

  override def head: HtmlHead = HtmlHead.titleCss(titleStr, "documentation")
  
  def dirsRel: DirsRel = DirsRel("Documentation")
}

/** Produces an HTML file documentation for the Util module. */
object UtilPage extends OSDocumentationPage
{ override def titleStr: String = "Util Module"
  override def body: HtmlBody = HtmlBody(HtmlH1("Util Module"), central)
  def central: HtmlDiv = HtmlDiv.classAtt("central", list, UtilTokenSection, AstSection, base32, misc)

  def list: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("The Util module contains"),
    HtmlLi("Some simple debug macros"),
    HtmlLi("iToForeach, iToMap, iToFlatMap, iUntilForeach, etc functions. A more succinct and expressive alternative to the Standard Library's" +
      "Range Class."),
    HtmlLi("Many useful functions and extension methods."),
    coll,
    errs,
    parse,
    HtmlLi("RSON: Rich Succint Object Notation. A persistence system to write objects to text and to read text back into memory as objects, using" --
      "consistent properly structured grammar heirachry, default values, Multiple values and repeat values."))

  def coll: HtmlLi = HtmlLi("Powerful, fast, efficient Array based collections for primitive values and compound value classes. These work on" +
    " both the Java platform, the JVM and in the web browser when compiled to JavaScript.")

  def errs: HtmlLi = HtmlLi("Functional error system using the EMon trait and its Good and Bad sub classes.")

  def parse: HtmlLi = HtmlLi("Parser for RSON, Rich Succinct, Object Notation. Includes a lexar for tokenisation and a parser for an AST," +
    " abstract syntax tree.")

  def persistSection: HtmlSection = HtmlSection(HtmlH2("Persistence"),
    HtmlP("Persistence system for Show and UnShow, uses the previously mentioned RSON syntax. Show will be used for the" --
      "classic type class. This will be the default for classic product types. 'Tell' will be used for traits inherited directly by the type itself." --
      "And ShowTell will be used for Show type classes that use Tell functionality, to minimise their constructors. The show and tell methods" --
      "contain a style parameter, to indicate which information to show and precision parameters to specify the number of decimal places precision." --
      "The default max precision is set to -1, which indicates no limit."
    ))

  def base32: HtmlSection = HtmlSection(HtmlH2("Hexadecimal and Base32"),
    HtmlP("Hexadecimal is written with Uppercase letters. Base32 is written with the digits followed by the upper case letters A to W, with the" --
      "letter 'O' unused"),
    HtmlP("A 10, B 11, C 12, D 13, E 14, F 15, G 16, H 17, I 18, J 19, K 20, L 21, M 22, N 23, P 24, Q 25, R 26, S 27, T 28, U 29 V 30, W 31")
  )

  def misc: HtmlSection = HtmlSection(HtmlH2("Miscellaneous"), HtmlP("A Lower case letter will be used after numerals in names."))
}

object AstSection extends HtmlSection
{
  override def contents: RArr[XConCompound] = RArr(HtmlH2("Abstract Syntax Tree"), file, statement, p1, prec)

  def file: HtmlUlWithLH = HtmlUlWithLH("So after the source has been tokenised it is parsed into an Abstract Syntax tree. the basic idea" --
    "is that an RSON file can be three things.",
    HtmlLi("An unStatemented file. It just contains an expression, without a semi colon at the end for example could just an Int or String."),
    HtmlLi("A Statemented file"),
    HtmlLi("The empty file. It may contain comments but no expressions. The empty statement is a thing in itself but also a special case of a" --
      "claused statement with with zero Clauses.")
  )

  def statement = HtmlUlWithLH("A statement can be 3 things",
    HtmlLi("An unclaused Statement. It just contains an expression, without a comma at the end for example could just an Int or String."),
    HtmlLi("A Claused Statement"),
    HtmlLi("The empty Statement It may contain comments but no expressions. The empty file is a thing in itself but also a special case of a" --
      "Statemented file with zero statements.")
  )

  def p1: HtmlP = HtmlP("So there is currently some confusion as to where it is parsed into a series of statements or into an expression. Currently" --
    "Statements and Clauses contain an expression but are not themselves an expression.This is causing a block to me coding at the moment.")

  def prec: HtmlUlWithLH = HtmlUlWithLH("AST Precedence From lowest to highest after brace block parsing.",
    HtmlLi("SemicolonToken Delimits the end of a Statement. The last Statement of a block / file may have, but does not need a need a trailing" --
      "Semicolon. A statement without commas is considered Unclaused."),
    HtmlLi("CommaToken Delimits the end of clause. The last Clause of a Statement may have, but does not need a trailing Comma, unless it is a" --
      "single Clause Statment in which case it must have a trailing Comma to distinguish it from an Unclaused Statement."),
    HtmlLi("assignment operators. == and != are normal operators. Any other operators ending with a '=' character are assignment operators."),
    HtmlLi("The single Colon Token"),
    HtmlLi("From here on down the precedence is determined by the first character of the operator. An operator ending in a ':' will be expected" --
      "to be an infix operator dispatch from its rhs."),
    HtmlLi("^"),
    HtmlLi("&"),
    HtmlLi("= !"),
    HtmlLi(":"),
    HtmlLi("+ -"),
    HtmlLi("* / %"),
    HtmlLi("All other special characters"),
    HtmlLi("Whitespace")
  )
}