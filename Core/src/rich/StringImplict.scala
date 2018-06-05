/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

class StringImplicit(val thisString: String) extends AnyVal //extends PersistStr
{
//   override def persistStr: String = thisString.foldLeft("")((accStr, head) => head match
//         {
//      case '\"' | '\'' | '\\' => accStr :+ '\\' :+ head
//      case '\n' =>  accStr :+ '\\' :+ 'n'
//      case '\b' => accStr :+ '\\' :+ 'b'
//      case '\t' => accStr :+ '\\' :+ 't'
//      case '\f' => accStr :+ '\\' :+ 'f'
//      case '\r' => accStr :+ '\\' :+ 'r'
//      case c => accStr :+ c
//         }).enqu
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
   {
      val (s1, s2) = thisString.span(_ != '.')         
      val (s2a, s2b) = s2.drop(1).span(_ != '.')
      s1 + "." + s2a
   }
   def parseTree: EMonSeq[Statement] = ParseTree.fromString(thisString)
   def strToTokens: EMonSeq[Token] = TokensGet.fromString(thisString)
   /** Appends strings with a comma and space seperator */
   def commaAppend(extraStrings: String*): String = extraStrings.foldLeft(thisString)(_ + ", " + _)
   def semicolonAppend(extraStrings: String*): String = extraStrings.foldLeft(thisString)(_ - "; " - _)
   def dotAppend(extraStrings: String*): String = extraStrings.foldLeft(thisString)(_ + "." + _)
   //def prependMinus: String = "-" - thisString
   def appendParenth(innerStrs: String*): String = thisString - innerStrs.semicolonFold.enParenth  
}
