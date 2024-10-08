/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Extension methods for String. Brought into scope by the stringToImplicit method in the package object. */
class ExtensionsString(val thisString: String) extends AnyVal
{ /** Substitutes the given value for empty [[String]]s or nulls. */
  def emptyMap(nullSubstitute: => String): String = ife(thisString == null || thisString == "", nullSubstitute, thisString)

  /** Parses this [[String]] into RSON tokens. */
  def parseTokensOld: EArr[Token] = plex.lexSrc(thisString.toCharArray, "String").toEMon

  /** Parses this [[String]] into RSON tokens. */
  def parseTokens: ErrBiArr[ExcParse, Token] = plex.lexSrc(thisString.toCharArray, "String")

  /** Parses this [[String]] into RSON statements. */
  def parseStatementsOld: EArr[Statement] = parseTokensOld.flatMap(pParse.tokensToStatementsOld(_))

  /** Parses this [[String]] into RSON statements. */
  def parseStatements: ExcMonRArr[Statement] = parseTokens.flatMap(pParse.tokensToStatements(_))

  /** Parses this [[String]] into an RSON expression. */
  def parseExprOld: EMonOld[Expr] = parseTokensOld.flatMap(pParse.tokensToExprOld(_))

  /** Parses this [[String]] into an RSON expression. */
  def parseExpr: ErrBi[ExcParse, Expr] = parseTokens.flatMap(pParse.tokensToExpr(_))

  /** Searches for Statement of type A. Can be a value of type A or a setting of a type A. */
  def findTypeOld[A](implicit ev: Unshow[A]): EMonOld[A] = thisString.parseStatementsOld.seqMapUniqueGood((st: Statement) => ev.fromStatementOld(st))

  /** Searches for Statement of type A. Can be a value of type A or a setting of a type A. */
  def findType[A](implicit ev: Unshow[A]): ErrBi[Exception, A] = thisString.parseStatements.flatMap{_.mapUniqueSucc((st: Statement) => ev.fromStatement(st)) }

  /** Finds Statement of type A and returns value or returns the elseValue if not found. */
  def findTypeElseOld[A: Unshow](elseValue: => A): A = findTypeOld[A].getElse(elseValue)

  /** Parses this [[String]] into EMon statements and tries to get the value from the Statement given by the index. */
  def typeAtStsIndexOld[A: Unshow](index: Int): EMonOld[A] = thisString.parseStatementsOld.flatMap(_.typeAtIndexOld[A](index))

  /** Parses this [[String]] into EMon statements and tries to get the value from the Statement given by the index. */
  //def typeAtStsIndex[A: Unshow](index: Int) = thisString.parseStatements.flatMap(_.typeAtIndex[A](index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Double]] value from the Statement given by the index. */
  def dblAtStsIndexOld(index: Int): EMonOld[Double] = thisString.parseStatementsOld.flatMap(_.dblAtIndexOld(index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Double]] value from the Statement given by the index. */
  def dblAtStsIndex(index: Int): ErrBi[Exception, Double] = thisString.parseStatements.flatMap(_.dblAtIndex(index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Int]] value from the Statement given by the index. */
  def intAtStsIndexOld(index: Int): EMonOld[Int] = thisString.parseStatementsOld.flatMap(_.intAtIndexOld(index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Int]] value from the Statement given by the index. */
  def intAtStsInde(index: Int): ErrBi[Exception, Int] = thisString.parseStatements.flatMap(_.intAtIndex(index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Int]] value from the Statement given by the index. */
  def natAtStsIndex(index: Int): EMonOld[Int] = thisString.parseStatementsOld.flatMap(_.natIntAtIndexOLd(index))

  /** Parses this [[String]] into EMon statements and tries to get a positive, non negative [[Double]] value from the Statement given by the index. */
  def posDblAtStsIndex(index: Int): EMonOld[Double] = thisString.parseStatementsOld.flatMap(_.posDblAtIndexOld(index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Boolean]] value from the Statement given by the index. */
  def boolAtStsIndex(index: Int): EMonOld[Boolean] = thisString.parseStatementsOld.flatMap(_.boolAtIndexOld(index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Long]] value from the Statement given by the index. */
  def longAtStsIndex(index: Int): EMonOld[Long] = thisString.parseStatementsOld.flatMap(_.longAtIndexOld(index))

  def findTypeDo[A: Unshow](f: A => Unit): Unit = findTypeOld[A].forGood(f)

  /** Attempts to parse this [[String]] into an RSON expression of the given type. */
  def asTypeOld[A](implicit ev: Unshow[A]): EMonOld[A] = parseExprOld.flatMap(g => ev.fromExprOld(g))

  /** Attempts to parse this [[String]] into an RSON expression of the given type. */
  def asType[A](implicit ev: Unshow[A]) = parseExpr.flatMap(ev.fromExpr(_))

  /** Replaces newline characters into space characters. */
  def oneLine: String = thisString.map { case '\n' => ' '; case c => c }

  /** Tries to parse this String as a [[Double]] expression. */
  def asDbl: EMonOld[Double] = asTypeOld[Double]

  /** Tries to parse this String as a [[Double]] expression. */
  def asPosDbl: EMonOld[Double] = asTypeOld[Double](Unshow.posDoubleEv)

  /** Tries to parse this String as an [[Int]] expression. */
  def asInt: EMonOld[Int] = asTypeOld[Int]

  /** Tries to parse this String as a natural non-negative [[Int]] expression. */
  def asNat: EMonOld[Int] = asTypeOld[Int](Unshow.natEv)

  /** Tries to parse this String as an [[Int]] in hexadecimal format expression. */
  def asHexaInt: EMonOld[Int] = asTypeOld(Unshow.hexaIntEv)

  /** Tries to parse this String as a natural non negative [[Int]] in hexadecimal format expression. */
  def asHexaNat: EMonOld[Int] = asTypeOld(Unshow.hexaNatEv)

  /** Tries to parse this String as an [[Int]] in base32 format expression. */
  def asBase32Int: EMonOld[Int] = asTypeOld(Unshow.base32IntEv)

  /** Tries to parse this String as a natural non negative [[Int]] in base32 format expression. */
  def asBase32NatOld: EMonOld[Int] = asTypeOld(Unshow.base32NatEv)

  /** Tries to parse this String as a [[Boolean]] expression. */
  def asBoolOld: EMonOld[Boolean] = asTypeOld[Boolean]

  /** Tries to parse this String as a [[Long]] expression. */
  def asLong: EMonOld[Long] = asTypeOld[Long]

  def findIntArray: ErrBi[Exception, Array[Int]] = thisString.parseStatements.flatMap(_.findIntArray)

  /** Find setting of type T from this [[String]] extension method, parsing this String as RSON Statements. */
  def findSettingOld[T: Unshow](settingStr: String): EMonOld[T] = thisString.parseStatementsOld.flatMap(_.findSettingOld[T](settingStr))

  /** Find setting of type T from this [[String]] extension method, parsing this String as RSON Statements. */
  def findSetting[T: Unshow](settingStr: String): ExcMon[T] = thisString.parseStatements.flatMap(_.findSetting[T](settingStr))

  /** Find setting of type T, from this [[String]], or return the default value, extension method, parsing this String as RSON Statements. */
  def findSettingElse[T: Unshow](settingStr: String, elseValue: T): T = findSetting[T](settingStr).getElse(elseValue)

  /** Find setting of type Int from this [[String]] extension method, parsing this String as RSON Statements. */
  def findIntSetting(settingStr: String) = thisString.parseStatements.flatMap(_.findSettingInt(settingStr))

  /** Find setting of the given name and type [[Int]], from this [[String]], or return the default value, extension method, parsing this String as RSON
   * Statements. */
  def findIntSettingElse(settingStr: String, elseValue: Int): Int = findIntSetting(settingStr).getElse(elseValue)

  /** Find setting of the given name and type [[Double]], from this [[String]], or return the default value, extension method, parsing this String as RSON
   * Statements. */
  def findDblSetting(settingStr: String) = thisString.parseStatements.flatMap(_.findSettingDbl(settingStr))

  /** Find setting of the given name and type [[Double]], from this [[String]], or return the default value, extension method, parsing this String as
   * RSON Statements. */
  def findDblSettingElse(settingStr: String, elseValue: Double): Double = findDblSetting(settingStr).getElse(elseValue)

  /** Find setting of the given name and type [[Boolean]], from this [[String]], or return the default value, extension method, parsing this String as RSON
   * Statements. */
  def findBoolSetting(settingStr: String): ExcMon[Boolean] = thisString.parseStatements.flatMap(_.findSettingBool(settingStr))

  /** Find setting of the given name and type [[Boolean]], from this [[String]], or return the default value, extension method, parsing this String as
   * RSON Statements. */
  def findBoolSettingElse(settingStr: String, elseValue: Boolean): Boolean = findBoolSetting(settingStr).getElse(elseValue)

  /** Concatenates a space and then the other String. */
  def -- (other: String): String = thisString + " " + other
  
  /** Concaternates a newline character and then the other [String]]. */
  def --- (other: String): String = thisString + "\n" + other

  /** Concaternates 2 newline characters and then the other [String]]. */
  def ---- (other: String): String = thisString + "\n\n" + other
  
  /** Concatenates a newline special character followed by spaces to this string. */
  def nli(indent: Int): String = thisString + "\n" + indent.spaces

  /** Prepends the operand [[String]] if the predicate is true. */
  def ifPrepend(pred: Boolean, operand: => String): String = if(pred) operand + thisString else thisString
  
  /** Concatenates a '/' character and then the other String. Useful for constructing directory/ folder paths on the Web, Linux and Unix */      
  def /(other: String): String = thisString + "/" + other

  /** Appends a colon character, a space then the operand String wit */
  def -:-(other: String): String = thisString + ": " + other

  def optAppend (optionOther: Option[String]): String = optionOther.fold(thisString)(string2 => thisString + " " + string2)
  def enquote: String = "\"" + thisString + "\""
  def enquote1: String = "'" + thisString + "'"
  def addEnqu(s2: String): String = thisString + s2.enquote
  
  /** encloses string in parentheses */
  def enParenth: String = "(" + thisString + ")"
  
  /** encloses string in Square brackets */
  def enSquare: String = "[" + thisString + "]"
  
  /** encloses string in Curly brackets */
  def enCurly: String = "{" + thisString + "}"

  /** encloses string in Curly brackets */
  def enCurlyNLs: String = "{\n" + thisString + "\n}"
  
  def words: Array[String] = thisString.split("\\s+")
  
  def remove2ndDot: String =
  { val (s1, s2) = thisString.span(_ != '.')         
    val (s2a, s2b) = s2.drop(1).span(_ != '.')
    s1 + "." + s2a
  }
  
  def toTokens: EMonOld[RArr[pParse.Token]] = pParse.stringToTokensOld(thisString)

  /** Appends strings with a comma and space separator */
  def appendCommas(extraStrings: String*): String = extraStrings.foldLeft(thisString)(_ + ", " + _)

  /** Appends extra Strings to thisString separated by " ;". */
  def appendSemicolons(extraStrings: String*): String =
  { val v1 = extraStrings.foldLeft(thisString)(_ + "; " + _)
    extraStrings.length match
    { case 0 => ife(thisString == "", v1 + ";", v1)
      case _ => ife(extraStrings.last == "", v1 + ";", v1)
    }
  }

  def commaInts(ints: Int*): String = ints.foldLeft(thisString)(_ + ", " + _.toString)

  def dotAppend(extraStrings: String*): String = extraStrings.foldLeft(thisString)(_ + "." + _)  
  def appendParenthSemis(innerStrs: String*): String = thisString + innerStrs.mkSemiParenth
  def appendParenth(innerStr: String): String = thisString + innerStr.enParenth

  def prependIndefiniteArticle = thisString.find(!_.isWhitespace) match
  { case Some(ch) => ch.toLower match
    { case 'a' | 'e' | 'i' | 'o' | 'u' => "an " + thisString
      case _ => "a " + thisString
    }
    case _ => "a " + thisString
  }

  def lengthFix(newLenIn: Int = 3, packChar: Char = ' '): String = 
  { val newLen = newLenIn.min(1).max(9)
    (newLen - thisString.length) match {
      case l if l < 0 => thisString.take(newLen)
      case 0 => thisString
      case l if l.isEven => packChar.timesString(l / 2) + thisString + packChar.timesString(l / 2)
      case l => packChar.timesString(l / 2) + thisString + packChar.timesString(l / 2 + 1)
    }
  }
  
  /** Converts this String to an immutable Array based collection of [[CharArr]]. */
  def toChars: CharArr = new CharArr(thisString.toCharArray)
  
  /** Replaces the reserved HTML characters with their corresponding entities, in order to display XML code as text. Eg '>' is replaced by "&gt;". */
  def htmlReservedSubstitute: String = toChars.foldLeft(""){ (acc, el) => acc + el.htmlReservedSubstituion }

  def unsafeDigitsToLong: Long =
  { var acc: Long = thisString.head - '0'
    var i = 1
    while (i < thisString.length)
    { acc *= 10
      acc += thisString(i) - '0'
      i += 1
    }
    acc
  }

  /** Returns the number of [[Char]]s from the first that match from this [[String]] to the operand [[String]]. */
  def compareChars(operand: String): Int =
  { var res = 0
    var i = 0
    val len = thisString.length.min(operand.length)
    var continue = true
    while (i < len & res < len)
    { if (thisString(i) == operand(i)) res += 1 else { continue = false }
      i += 1
    }
    res
  }

  def findAlphaInt: Option[(String, Int)] =
  { val (p1, p2) = thisString.span(_.isLetter)
    p2.asInt match
    { case Good(i) if p1.length > 0 => Some(p1, i)
      case _ => None
    }
  }
}

/** Extraction object for extracting a sequence of letters followed by an [[Int]]. */
object AlphaInt
{ /** Extractor for extracting a sequence of letters followed by an [[Int]]. */
  def unapply(inp: String): Option[(String, Int)] = inp.findAlphaInt
}