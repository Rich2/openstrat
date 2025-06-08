/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Extension methods for String. Brought into scope by the stringToImplicit method in the package object. */
class ExtensionsString(val thisString: String) extends AnyVal
{ /** Substitutes the given value for empty [[String]]s or nulls. */
  def emptyMap(nullSubstitute: => String): String = ife(thisString == null || thisString == "", nullSubstitute, thisString)

  /** Parses this [[String]] into RSON tokens. */
  def parseTokens: ErrBiArr[ExcParse, Token] = plex.lexSrc(thisString.toCharArray, "String")

  /** Parses this [[String]] into RSON statements. */
  def parseStatements: ExcMonRArr[Statement] = parseTokens.flatMap(pParse.tokensToStatements(_))

  /** Parses this [[String]] into an RSON expression. */
  def parseExpr: ErrBi[ExcParse, Expr] = parseTokens.flatMap(pParse.tokensToExpr(_))

  /** Searches for Statement of type A. Can be a value of type A or a setting of a type A. */
  def findType[A](implicit ev: Unshow[A]): ErrBi[Exception, A] = thisString.parseStatements.flatMap{_.mapUniqueSucc((st: Statement) => ev.fromStatement(st)) }

  /** Finds Statement of type A and returns value or returns the elseValue if not found. */
  def findTypeElse[A: Unshow](elseValue: => A): A = findType[A].getElse(elseValue)

  /** Parses this [[String]] into EMon statements and tries to get the value from the Statement given by the index. */
  def typeAtStsIndex[A: Unshow](index: Int) = thisString.parseStatements.flatMap(_.typeAtIndex[A](index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Double]] value from the Statement given by the index. */
  def dblAtStsIndex(index: Int): ErrBi[Exception, Double] = thisString.parseStatements.flatMap(_.dblAtIndex(index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Int]] value from the Statement given by the index. */
  def intAtStsIndex(index: Int): ErrBi[Exception, Int] = thisString.parseStatements.flatMap(_.intAtIndex(index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Int]] value from the Statement given by the index. */
  def natAtStsIndex(index: Int): ErrBi[Exception, Int] = thisString.parseStatements.flatMap(_.natIntAtIndex(index))

  /** Parses this [[String]] into EMon statements and tries to get a positive, non-negative [[Double]] value from the Statement given by the index. */
  def posDblAtStsIndex(index: Int): ErrBi[Exception, Double] = thisString.parseStatements.flatMap(_.posDblAtIndex(index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Boolean]] value from the Statement given by the index. */
  def boolAtStsIndex(index: Int): ErrBi[Exception, Boolean] = thisString.parseStatements.flatMap(_.boolAtIndex(index))

  /** Parses this [[String]] into EMon statements and tries to get a [[Long]] value from the Statement given by the index. */
  def longAtStsIndex(index: Int): ErrBi[Exception, Long] = thisString.parseStatements.flatMap(_.longAtIndex(index))

  /** Find type from this [[String]] parsed as a sequence of RSON statements and if succssful run the sdie effecting proceedure on the value.  */
  def findTypeDo[A: Unshow](f: A => Unit): Unit = findType[A].forSucc(f)

  /** Attempts to parse this [[String]] into an RSON expression of the given type. */
  def asType[A](implicit ev: Unshow[A]) = parseExpr.flatMap(ev.fromExpr(_))

  /** Replaces newline characters into space characters. */
  def oneLine: String = thisString.map { case '\n' => ' '; case c => c }

  /** Tries to parse this String as a [[Double]] expression. */
  def asDbl: ErrBi[Exception, Double] = asType[Double]

  /** Tries to parse this String as a [[Double]] expression. */
  def asPosDbl = asType[Double](using Unshow.posDoubleEv)

  /** Tries to parse this String as an [[Int]] expression. */
  def asInt: ErrBi[Exception, Int] = asType[Int]

  /** Tries to parse this String as an [[Int]] expression, if fails returns the elseValue with a defualt of 0. */
  def asIntElse(elseValue: Int = 0): Int = asType[Int].getElse(elseValue)

  /** Tries to parse this String as a natural non-negative [[Int]] expression. */
  def asNat: ErrBi[Exception, Int] = asType[Int](using Unshow.natEv)

  /** Tries to parse this String as an [[Int]] in hexadecimal format expression. */
  def asHexaInt: ErrBi[Exception, Int] = asType(using Unshow.hexaIntEv)

  /** Tries to parse this String as a natural non-negative [[Int]] in hexadecimal format expression. */
  def asHexaNat: ErrBi[Exception, Int] = asType(using Unshow.hexaNatEv)

  /** Tries to parse this String as an [[Int]] in base32 format expression. */
  def asBase32Int: ErrBi[Exception, Int] = asType(using Unshow.base32IntEv)

  /** Tries to parse this String as a natural non-negative [[Int]] in base32 format expression. */
  def asBase32Nat: ErrBi[Exception, Int] = asType(using Unshow.base32NatEv)

  /** Tries to parse this String as a [[Boolean]] expression. */
  def asBool: ErrBi[Exception, Boolean] = asType[Boolean]

  /** Tries to parse this String as a [[Long]] expression. */
  def asLong: ErrBi[Exception, Long] = asType[Long]

  def dropRightWhile(f: Char => Boolean): String =
  { val arr = thisString.toCharArray
    var count = 0
    var i = arr.length - 1
    while (i >= 0) if(f(arr(i))){ count += 1; i -= 1 } else { i = -1 }
    thisString.dropRight(count)
  }

  def findIntArray: ErrBi[Exception, Array[Int]] = thisString.parseStatements.flatMap(_.findIntArray)

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

  /** Encloses this [[String]] within double quote characters. */
  def enquote: String = "\"" + thisString + "\""

  /** Encloses this [[String]] within single quotation characters */
  def enquote1: String = "'" + thisString + "'"
  
  /** encloses string in parentheses */
  def enParenth: String = "(" + thisString + ")"
  
  /** encloses string in Square brackets */
  def enSquare: String = "[" + thisString + "]"
  
  /** encloses string in Curly brackets */
  def enCurly: String = "{" + thisString + "}"

  /** encloses string in Curly brackets */
  def enCurlyNLs: String = "{\n" + thisString + "\n}"

  /** Splits this [[String]] by white space into [[Array]] of words. */
  def words: StrArr = new StrArr(thisString.split("\\s+"))

  /** Partitions this [[String]] into alphabetic [[String]] and a natural number. If no digits found returns 0. */
  def alphaNatPartition: (String, Int) =
  { val s1 = thisString.takeWhile(c => c.isLetter || c.isWhitespace)
    val n1 = thisString.dropWhile(!_.isDigit).takeWhile(_.isDigit)
    val n2 = n1.asIntElse()
    (s1, n2)
  }
  
  def remove2ndDot: String =
  { val (s1, s2) = thisString.span(_ != '.')         
    val (s2a, s2b) = s2.drop(1).span(_ != '.')
    s1 + "." + s2a
  }

  /** Extension method. Try to parse this [[String]] into RSON [[Token]]s. */
  def toTokens: ErrBiArr[ExcLexar, Token] = pParse.stringToTokens(thisString)

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

  /** Append parethesis containing the paremter [[String]]s, separated by semicolons. */
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
    { case Succ(i) if p1.length > 0 => Some(p1, i)
      case _ => None
    }
  }

  def longestLineLen: Int =
  { var res = 0
    var curr = 0
    var i = 0
    val array = thisString.toCharArray
    while (i < array.length)
    { array(i) match
      { case '\n' => { curr = 0 }
        case _ => { curr += 1; res = res.max(curr)  }
      }
      i += 1
    }
    res
  }
}

/** Extraction object for extracting a sequence of letters followed by an [[Int]]. */
object AlphaInt
{ /** Extractor for extracting a sequence of letters followed by an [[Int]]. */
  def unapply(inp: String): Option[(String, Int)] = inp.findAlphaInt
}