/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** An alpha-numeric token beginning with an alphabetic character that represents a name of something, that identifies something. */
trait IdentifierToken extends ExprToken

/** An alpha-numeric token beginning with an uppercase letter that represents a name of something, that identifies something. */
case class IdentifierUpToken(startPosn: TextPosn, srcStr: String) extends IdentifierToken
{ override def subTypeStr: String = "IdentifierUpper"
}

/** A valid identifier beginning with a lowercase letter or an underscore character. */
case class IdentifierLwToken(startPosn: TextPosn, srcStr: String) extends IdentifierToken
{ override def subTypeStr: String = "IdentifierLower"
}

/** The purpose of this token is for use at the beginning of a file, to make the the rest of the Statements, sub-statements. As if they were the
 *  statements inside parenthesis. */
case class HashAlphaToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def subTypeStr: String = "HashAlpha"
}

case class UnderscoreToken(startPosn: TextPosn) extends EmptyExprToken with StatementMember
{ def srcStr = "_"
  override def exprName: String = "EmptyClauseExpr"
  override def subTypeStr: String = "Underscore"
}