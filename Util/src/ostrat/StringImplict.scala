/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Extension methods for String. Brought into scope by the stringToImplicit method in the package object. */
class StringImplicit(val thisString: String) extends AnyVal //extends PersistStr
{
  import pParse.{stringToStatements => stss }
  def findType[A: Persist]: EMon[A] = stss(thisString).flatMap(_.findType[A])
  def findTypeElse[A: Persist](elseValue: => A): A = findType[A].getOrElse(elseValue)
  def findInt: EMon[Int] = stss(thisString).flatMap(_.findInt)
  def findDouble: EMon[Double] = stss(thisString).flatMap(_.findDouble)
  def findBoolean: EMon[Boolean] = stss(thisString).flatMap(_.findBoolean)
  def findTypeIndex[A: Persist](index: Int): EMon[A] = stss(thisString).flatMap(_.findTypeIndex[A](index))  
  def findTypeDo[A: Persist](f: A => Unit): Unit = findType[A].foreach(f)
  def findSett[A: Persist](settingSym: Symbol): EMon[A] = stss(thisString).flatMap(_.findSett[A](settingSym))
  def findSettElse[A: Persist](settingSym: Symbol, elseValue: A): A = findSett[A](settingSym).getOrElse(elseValue)
  def findIntSett(settingSym: Symbol): EMon[Int] = stss(thisString).flatMap(_.findIntSett(settingSym))
  def findIntSettElse(settingSym: Symbol, elseValue: Int): Int = findIntSett(settingSym).getOrElse(elseValue)  
  def findDoubleSett(settingSym: Symbol): EMon[Double] = stss(thisString).flatMap(_.findDoubleSett(settingSym))
  def findDoubleSettElse(settingSym: Symbol, elseValue: Double): Double = findDoubleSett(settingSym).getOrElse(elseValue)
  def findBooleanSett(settingSym: Symbol): EMon[Boolean] = stss(thisString).flatMap(_.findBooleanSett(settingSym))
  def findBooleanSettElse(settingSym: Symbol, elseValue: Boolean): Boolean = findBooleanSett(settingSym).getOrElse(elseValue)
  
  def - (other: String): String = thisString + other
  /** Concatenates a space and then the other String */
  def -- (other: String): String = thisString + " " + other
  /** appends a newline special character to this String */
  def nl: String = thisString - "\n"   
  /** Concatenates a newline special character followed by spaces to this string */
  def nl(indent: Int): String = thisString - "\n" - indent.toSpaces   
  /** prepends a newline special character and spaces to this string */
  def preNl(indent: Int): String = thisString - "\n" - indent.toSpaces
  /** Prepends a newline special character to this String */
  def preNl: String = "\n" - thisString
  /** Prepends 2 spaces to string */   
  def ind2: String = "  " - thisString
  /** Prepends 4 spaces to string */
  def ind4: String = "    " - thisString
  /** Concatenates a '/' character and then the other String. Useful for constructing directory/ folder paths on the Web, Linux and Unix */      
  def / (other: String): String = thisString + "/" + other
  def :- (other: String): String = thisString + ": " + other 
  def optAppend (optionOther: Option[String]): String = optionOther.fold(thisString)(string2 => thisString + " " + string2)
  def enqu: String = "\"" + thisString + "\""
  def enqu1: String = "'" + thisString + "'"
  def addEnqu(s2: String): String = thisString + s2.enqu
  /** encloses string in parentheses */
  def enParenth: String = "(" + thisString + ")"
  /** encloses string in Square brackets */
  def enSquare: String = "[" + thisString + "]"
  /** encloses string in Curly brackets */
  def enCurly: String = "{" + thisString + "}"      
  def words: Array[String] = thisString.split("\\s+")
  def toLowerWords: Array[String] = thisString.toLowerCase.words
  def remove2ndDot: String =
  { val (s1, s2) = thisString.span(_ != '.')         
    val (s2a, s2b) = s2.drop(1).span(_ != '.')
    s1 + "." + s2a
  }
  
  def toTokens: EMonList[pParse.Token] = pParse.stringToTokens(thisString)
  /** Appends strings with a comma and space seperator */
  def commaAppend(extraStrings: String*): String = extraStrings.foldLeft(thisString)(_ + ", " + _)
  def semicolonAppend(extraStrings: String*): String = extraStrings.foldLeft(thisString)(_ - "; " - _)
  def dotAppend(extraStrings: String*): String = extraStrings.foldLeft(thisString)(_ + "." + _)  
  def appendParenth(innerStrs: String*): String = thisString - innerStrs.semicolonFold.enParenth  
}
