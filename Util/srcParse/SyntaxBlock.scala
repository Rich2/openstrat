/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** This trait includes all the tokens except braces plus the Bracket syntactic blocks. The Block in the name block member is either the top level statements in
 * the file or the statements with in a bracket block. Conceptually the source file is considered a special case of bracket block where the beginning of
 * substitutes for the opening bracket and the end of file substitutes for the closing bracket. BlockMember has only two sub traits StatementMember and
 * SemiToken. So a sequence of TokenOrBlocks is simply a series of tokens which has been parsed into Blocks. */
trait BlockMem extends TextSpan

/** A statement member is a Token except the semicolon, which is the statement delimiter and the braces, plus it includes blocks, As blocks contain a sequence
 * of Statements but can themselves be part of a statement. */
trait StatementMem extends BlockMem

/** An Assignment member can appear in the expressions either side of an assignment operator. */
trait AssignMem extends StatementMem

/** I think its helpful to have an expression member trait for syntax that is not expressions. So I don't think it would be helpful if say an opening brace was
 * an expression. All Expressions are Expression members. */
trait ColonOpMem extends AssignMem

/** Can be a member of either side of a ColonExpr */
trait ClauseMem extends ColonOpMem

sealed trait BracketToken extends Token
{ def braces: Braces
}

/** An opening Brace, '{', '[' or '('.  */
sealed trait BracketOpen extends BracketToken
{ final override def tokenTypeStr = "Open" + braces.name + "BraceToken"
  final override def srcStr: String = braces.openChar.toString
}

/** A closing bracket Token, The ']', ']' or ')' characters are BracketCloseTokens. */
sealed trait BracketCloseToken extends BracketToken
{ final override def tokenTypeStr = "Close" + braces.name + "BraceToken"
  final override def srcStr: String = braces.closeChar.toString
}

/** Parenthesis open token. */
case class ParenthOpenToken(startPosn: TextPosn) extends BracketOpen
{ override def braces: Braces = Parentheses
}

/** Parenthesis close token. */
case class ParenthCloseToken(startPosn: TextPosn) extends BracketCloseToken
{ override def braces: Braces = Parentheses
}

/** Square bracket open token. */
case class SquareOpenToken(startPosn: TextPosn) extends BracketOpen
{ override def braces: Braces = SquareBraces
}

/** Square bracket close token. */
case class SquareCloseToken(startPosn: TextPosn) extends BracketCloseToken
{ override def braces: Braces = SquareBraces
}

/** Curly brace open token. */
case class CurlyOpenToken(startPosn: TextPosn) extends BracketOpen
{ override def braces: Braces = CurlyBraces
}

/** Curly brace close token. */
case class CurlyCloseToken(startPosn: TextPosn) extends BracketCloseToken
{ override def braces: Braces = CurlyBraces
}

/** Defines the types of braces and the opening and closing brace character. Eg '(' and ')' for the [[Parentheses]] object. */
sealed trait Braces
{ def openChar: Char
  def closeChar: Char
  def name: String
}

/** Parentheses, holds the open and close characters. */
case object Parentheses extends Braces
{ override def openChar: Char = '('
  override def closeChar: Char = ')'
  override def name: String =  "Round"
}

/** Square braces, holds the open and close characters. */
case object SquareBraces extends Braces
{ override def openChar: Char = '['
  override def closeChar: Char = ']'
  override def name: String = "Square"
}

/** Curly braces, holds the open and close characters. */
case object CurlyBraces extends Braces
{ override def openChar: Char = '{'
  override def closeChar: Char = '}'
  override def name: String = "Curly"
}

case class BracketedRaws(statements: RArr[TextSpan], braces: Braces, startBracket: TextPosn, endBracket: TextPosn)
{ def exprName: String = braces.name + "BlockExpr"
}

/** Syntactic structure consisting of a pair of matching brackets and the sequence of statements they encapsulate." */
case class BracketedStructure(statements: RArr[Statement], braces: Braces, startBracket: TextPosn, endBracket: TextPosn) extends BlockStatements
{ def exprName: String = braces.name + "BlockExpr"
  def memExprs: RArr[Expr] = statements.map(_.expr)
}

object ParenthBlock
{ def unapply(inp: AnyRef): Option[(RArr[Statement], TextPosn, TextPosn)] = inp match
  { case BracketedStructure(sts, Parentheses, sp, ep) => Some((sts, sp, ep))
    case _ => None
  }
}

object SquareBlock
{ def unapply(inp: AnyRef): Option[(RArr[Statement], TextPosn, TextPosn)] = inp match
  { case BracketedStructure(sts, SquareBraces, sp, ep) => Some((sts, sp, ep))
    case _ => None
  }
}

object CurlyBlock
{ def unapply(inp: AnyRef): Option[(RArr[Statement], TextPosn, TextPosn)] = inp match
  { case BracketedStructure(sts, CurlyBraces, sp, ep) => Some((sts, sp, ep))
    case _ => None
  }
}

/** Extractor object for an [[AlphaBracketExpr]] with a square brackets followed by a single parentheses block. */
object AlphaSquareParenth
{ /** Extractor unapply method for an [[AlphaBracketExpr]] with a square brackets block followed by a single parentheses block. */
  def unapply(expr: ColonMemExpr): Option[(String, RArr[Statement], RArr[Statement])] = expr match
  { case AlphaBracketExpr(IdentifierToken(name), Arr2(SquareBlock(sts1, _, _) , ParenthBlock(sts2, _, _))) => Some((name, sts1, sts2))
    case _ => None
  }
}

/** Extractor object for an [[AlphaBracketExpr]] with a single parentheses block. */
object AlphaParenth
{ /** Extractor unapply method for an [[AlphaBracketExpr]] with a single parentheses block. */
  def unapply(expr: ColonMemExpr): Option[(String, RArr[Statement])] = expr match
  { case AlphaBracketExpr(IdentifierToken(name), Arr1(ParenthBlock(sts, _, _))) => Some((name, sts))
    case _ => None
  }
}

/** Extractor object for an [[AlphaBracketExpr]] with or without a square brackets followed by a single parentheses block. */
object AlphaMaybeSquareParenth
{ /** Extractor unapply method for an [[AlphaBracketExpr]] with a square brackets block followed by a single parentheses block. */
  def unapply(expr: ColonMemExpr): Option[(String, RArr[Statement])] = expr match
  { case AlphaBracketExpr(IdentifierToken(name), Arr2(SquareBlock(_, _, _) , ParenthBlock(sts2, _, _))) => Some((name, sts2))
    case AlphaBracketExpr(IdentifierToken(name), Arr1(ParenthBlock(sts, _, _))) => Some((name, sts))
    case _ => None
  }
}

/** Extractor object for an [[Expre]] sequence with a name to be applied to ALphaBracket expressions. */
class NamedExprSeq(val name: String)
{
  def unapply(expr: Expr): Option[RArr[Expr]] = expr match{
    case AlphaBracketExpr(IdentifierToken(`name`), Arr2(SquareBlock(_, _, _), ParenthBlock(sts2, _, _))) => Some(sts2.map(_.expr))
    case AlphaBracketExpr(IdentifierToken(`name`), Arr1(ParenthBlock(sts, _, _))) => Some(sts.map(_.expr))
    case ExprSeqNonEmpty(exprs) => Some(exprs)
    case _ => None
  }
}

object NamedExprSeq
{
  def apply(name: String): NamedExprSeq = new NamedExprSeq(name)
}