/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
class PathToken(val startPosn: TextPosn, val arrayUnsafe: Array[String]) extends OpExprMemToken
{ override def exprName: String = "PathTokenExpr"
  override def srcStr: String = arrayUnsafe.foldLeft("")(_ + "/" + _)

  override def equals(that: Any): Boolean = that match{
    case op: PathToken if startPosn == op.startPosn && arrayUnsafe.sameElements(op.arrayUnsafe) => true
    case _ => false
  }

  override def hashCode: Int = arrayUnsafe.foldLeft(0)(_ + _.hashCode)
}

object PathToken
{
  def unapply(inp: Any): Option[(TextPosn, Array[String])] = inp match{
    case pt: PathToken => Some((pt.startPosn, pt.arrayUnsafe))
    case _ => None
  }

  /** Implicit [[EqT]] instance / evidence for [[PathToken]]. */
  implicit val eqTEv: EqT[PathToken] = (pt1, pt2) => pt1.startPosn == pt2.startPosn && pt1.arrayUnsafe.sameElements(pt2.arrayUnsafe)
}