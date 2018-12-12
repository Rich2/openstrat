package ostrat

package object pParse
{
  /** Returns an EMon of a sequence of Statements from a file. This uses the fromString method. Non fatal exceptions or if the file doesn't exist
   *   will be returned as errors.*/
  def getStatements(input: String, inputSourceName: String): EMonList[Statement] = TokensFind(input, inputSourceName).flatMap(GetStatements(_))
  /** Returns an EMon of a sequence of Statements from a String. */
  def stringToStatements(input: String): EMonList[Statement] = stringToTokens(input).flatMap(GetStatements(_))
  /** Max numbers for long and hexidecimal formats needs to be implemented */
  def stringToTokens(srcStr: String): EMon[List[Token]] = TokensFind(srcStr, "From string")  
}