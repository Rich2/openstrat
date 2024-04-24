/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** A [[Token]] that can be a member of a operator expression. A [[Clause]] member that is not an operator. An [[OperatorToken]] can be a component of an
 *  operator expression which can be a member of an operator expression, but an operator can not be an operator expression member directly. */
trait OpExprMemToken extends ClauseMemExprToken

/** A [[Char]] [[Token]]. */
case class CharToken(startPosn: TextPosn, char: Char) extends OpExprMemToken
{ def srcStr: String = char.toString.enquote1
  override def exprName: String = "CharToken"
}

/** A [[String]] [[Token]]. */
case class StringToken(startPosn: TextPosn, stringStr: String) extends OpExprMemToken
{ def srcStr: String = stringStr.enquote
  override def exprName: String = "StringToken"
}

/** File path token. */
case class PathToken(startPosn: TextPosn, arrayUnsafe: Array[String]) extends OpExprMemToken
{ override def exprName: String = "PathTokenExpr"
  override def srcStr: String = arrayUnsafe.foldLeft("")(_ + "/" + _)
}