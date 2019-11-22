package ostrat
package pParse

class IdentNum {

}

/** An Alphanumeric Token. It contains a symbol rather than a String to represent the AlphaNumeric token as commonly used Symbols have better
 *  better performance than the equivalent Strings. */
case class IdentifierToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def exprName: String = "AlphaTokenExpr"
  override def toString: String =  "AlphaToken".appendParenthSemis(srcStr, startPosn.lineNum.toString, startPosn.linePosn.toString)
  override def tokenTypeStr: String = "AlphaToken"
}

/** A Double Floating point token. */
case class FloatToken(startPosn: TextPosn, srcStr: String, floatValue: Double) extends ExprToken
{ override def exprName: String = "FloatTokenExpr"
  override def tokenTypeStr: String = "FloatToken"
}
/** The purpose of this token is for use at the beginning of a file, to make the the rest of the Statements, sub-statements. As if they were the
 *  statements inside parenthesis. */
case class HashAlphaToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def exprName: String = "HashAlphaTokenExpr"
  override def tokenTypeStr: String = "HashAlphaToken"
}

case class UnderscoreToken(startPosn: TextPosn) extends EmptyExprToken with StatementMember
{ def srcStr = "_"
  override def exprName: String = "EmptyClauseExpr"
  override def tokenTypeStr: String = "CommaToken"
}


