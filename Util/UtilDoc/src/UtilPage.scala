/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, HtmlStrExts.*

trait OSDocumentationPage extends PageFile
{ /** A title [[String]] is all that is needed to be implemented by the final class to complete the [[HeadHtml]]. */
  def titleStr: String

  override def head: HeadHtml = headFavCss("documentation")
  
  def dirsRel: DirsRel = DirsRel("Documentation")
}

/** Produces an HTML file documentation for the Util module. */
object UtilPage extends OSDocumentationPage
{ override val titleStr: String = "Util Module"
  override val fileNameStem: String = "util"
  override def body: BodyHtml = BodyHtml(titleStr.h1, central)
  def central: DivHtml = DivHtml.classAtt("central", list, UtilTokenSection, AstSection, base32, misc)

  def list: OlSection = OlSection(H2Html("The Util module contains"),
    LiHtml("Some simple debug macros"),
    LiHtml("iToForeach, iToMap, iToFlatMap, iUntilForeach, etc functions. A more succinct and expressive alternative to the Standard Library's" +
      "Range Class."),
    LiHtml("Many useful functions and extension methods."),
    coll,
    errs,
    parse,
    LiHtml("RSON: Rich Succint Object Notation. A persistence system to write objects to text and to read text back into memory as objects, using" --
      "consistent properly structured grammar heirachry, default values, Multiple values and repeat values."))

  def coll: LiHtml = LiHtml("Powerful, fast, efficient Array based collections for primitive values and compound value classes. These work on" +
    " both the Java platform, the JVM and in the web browser when compiled to JavaScript.")

  def errs: LiHtml = LiHtml("Functional error system using the EMon trait and its Good and Bad sub classes.")

  def parse: LiHtml = LiHtml("Parser for RSON, Rich Succinct, Object Notation. Includes a lexar for tokenisation and a parser for an AST," +
    " abstract syntax tree.")

  def persistSection: SectionHtml = SectionHtml(H2Html("Persistence"),
    PHtml("Persistence system for Show and UnShow, uses the previously mentioned RSON syntax. Show will be used for the" --
      "classic type class. This will be the default for classic product types. 'Tell' will be used for traits inherited directly by the type itself." --
      "And ShowTell will be used for Show type classes that use Tell functionality, to minimise their constructors. The show and tell methods" --
      "contain a style parameter, to indicate which information to show and precision parameters to specify the number of decimal places precision." --
      "The default max precision is set to -1, which indicates no limit."
    ))

  def base32: SectionHtml = SectionHtml(H2Html("Hexadecimal and Base32"),
    PHtml("Hexadecimal is written with Uppercase letters. Base32 is written with the digits followed by the upper case letters A to W, with the" --
      "letter 'O' unused"),
    PHtml("A 10, B 11, C 12, D 13, E 14, F 15, G 16, H 17, I 18, J 19, K 20, L 21, M 22, N 23, P 24, Q 25, R 26, S 27, T 28, U 29 V 30, W 31")
  )

  def misc: SectionHtml = SectionHtml(H2Html("Miscellaneous"), PHtml("A Lower case letter will be used after numerals in names."))
}

object AstSection extends SectionHtml
{
  override def contents: RArr[XConCompound] = RArr(H2Html("Abstract Syntax Tree"), file, statement, p1, prec)  

  def file: UlSection = UlSection("So after the source has been tokenised it is parsed into an Abstract Syntax tree. the basic idea" --
    "is that an RSON file can be three things.",
    LiHtml("An unStatemented file. It just contains an expression, without a semi colon at the end for example could just an Int or String."),
    LiHtml("A Statemented file"),
    LiHtml("The empty file. It may contain comments but no expressions. The empty statement is a thing in itself but also a special case of a" --
      "claused statement with with zero Clauses.")
  )

  def statement = UlSection("A statement can be 3 things",
    LiHtml("An unclaused Statement. It just contains an expression, without a comma at the end for example could just an Int or String."),
    LiHtml("A Claused Statement"),
    LiHtml("The empty Statement It may contain comments but no expressions. The empty file is a thing in itself but also a special case of a" --
      "Statemented file with zero statements.")
  )

  def p1: PHtml = PHtml("So there is currently some confusion as to where it is parsed into a series of statements or into an expression. Currently" --
    "Statements and Clauses contain an expression but are not themselves an expression.This is causing a block to me coding at the moment.")

  def prec: UlSection = UlSection("AST Precedence From lowest to highest after brace block parsing.",
    LiHtml("SemicolonToken Delimits the end of a Statement. The last Statement of a block / file may have, but does not need a need a trailing" --
      "Semicolon. A statement without commas is considered Unclaused."),
    LiHtml("CommaToken Delimits the end of clause. The last Clause of a Statement may have, but does not need a trailing Comma, unless it is a" --
      "single Clause Statment in which case it must have a trailing Comma to distinguish it from an Unclaused Statement."),
    LiHtml("assignment operators. == and != are normal operators. Any other operators ending with a '=' character are assignment operators."),
    LiHtml("The single Colon Token"),
    LiHtml("From here on down the precedence is determined by the first character of the operator. An operator ending in a ':' will be expected" --
      "to be an infix operator dispatch from its rhs."),
    LiHtml("^"),
    LiHtml("&"),
    LiHtml("= !"),
    LiHtml(":"),
    LiHtml("+ -"),
    LiHtml("* / %"),
    LiHtml("All other special characters"),
    LiHtml("Whitespace")
  )
}