/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** The fundamental expression trait. As it currently stands properly formed Statements either is empty or contains an expression or a sequence of
 *  clauses that contain each contain an expression. */
trait Expr extends TokenOrBlock with ExprMember
{ def exprParseErr[A](implicit ev: Persist[A]): EMon[A] = startPosn.bad(ev.typeStr -- "is not available from" -- exprName)
  def exprName: String
}

/** A compound expression. The traits sole purpose is to give an Expr, the start and end text positions from its first and last components. */
trait ExprCompound extends Expr with TextSpanCompound

trait ExprSeq extends ExprCompound
{ def exprs: Refs[Expr]
}

/** A Token that is an Expression. Most tokens are expressions, but some are not such as braces, commas and semicolons. */
trait ExprToken extends Expr with ExprMemberToken
{ def subTypeStr: String
  def exprName: String = subTypeStr + "Expr"
  final override def tokenTypeStr: String = subTypeStr + "Token"
  final override def toString: String = tokenTypeStr.appendParenthSemis(srcStr.toString, startPosn.lineNum.toString, startPosn.linePosn.toString)
}

trait BlockRaw
{ def statements: Refs[Statement]
 // def exprs: Refs[Expr] = statements.map(_.expr)
  def startMem = statements.head
  def endMem = statements.last
}

trait BlockStatements extends ExprSeq
{ def statements: Refs[Statement]
  def exprs: Refs[Expr] = statements.map(_.expr).asInstanceOf[Refs[Expr]]
  def startMem = statements.head
  def endMem = statements.last
}

case class FileStatements(statements: Refs[Statement]) extends BlockStatements
{ def exprName: String = "FileStatements"
//  def startPosn: TextPosn = statements.head.startPosn
  //def endPosn: TextPosn = statements.last.endPosn
}

case class StringStatements(statements: Refs[Statement]) extends BlockStatements
{ def exprName: String = "StringStatements"
  //def startPosn: TextPosn = statements.head.startPosn
  //def endPosn: TextPosn = statements.last.endPosn
}

case class ClausesExpr(exprs: Refs[Expr]) extends ExprSeq
{ def startMem = exprs.head
  def endMem = exprs.last
  override def exprName: String = "Claused Expr"
}

case class UnimplementedExpr(bMems: Refs[BlockMember]) extends ExprCompound
{ def startMem = bMems.head
  def endMem = bMems.last
  override def exprName: String = "UnimplementedExpr"
}

case class AlphaBracketExpr(name: IdentifierLowerToken, blocks: Refs[BracketedStatements]) extends ExprCompound
{ def startMem = name
  def endMem = blocks.last
  override def exprName: String = "AlphaBracketExpr"  
}

case class PreOpExpr(op: OperatorToken, right: Expr) extends ExprCompound
{ override def startMem = op
  override def endMem = right
  override def exprName: String = "PreOpExpr"
  def opStr = op.srcStr
}

case class AsignExpr(asToken: AsignToken, left: Expr, right : Expr) extends ExprCompound
{ override def startMem = left
  override def endMem = right
  override def exprName: String = "AsignExpr"  
}