/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Extension methods for String. Brought into scope by the stringToImplicit method in the package object. */
class StringImplicit(val thisString: String) extends AnyVal //extends PersistStr
{ def findType[A: Persist]: EMon[A] = pParse.stringToStatements(thisString).flatMap(_.findType[A])
  def findInt: EMon[Int] = pParse.stringToStatements(thisString).flatMap(_.findInt)
  def findDouble: EMon[Double] = pParse.stringToStatements(thisString).flatMap(_.findDouble)
  def findTypeIndex[A: Persist](index: Int): EMon[A] = pParse.stringToStatements(thisString).flatMap(_.findTypeIndex[A](index))
  def findTypeElse[A: Persist](elseValue: A): A = findType[A].getElse(elseValue)
  def findTypeDo[A: Persist](f: A => Unit): Unit = findType[A].foreach(f)
  def findSetting[A: Persist](settingSym: Symbol) = pParse.stringToStatements(thisString).flatMap(_.findSetting[A](settingSym))
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
