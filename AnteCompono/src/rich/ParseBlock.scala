/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

/** This trait includes all the tokens except braces plus the Bracket syntactic blocks. The Block in the name block member is either the top level
 *  statements in the file or the statements with in a bracket block. Conceptually the source file is considered a special case of bracket block where 
 *  the beginning of substitutes for the opening bracket and the end of file substitutes for the closing bracket. BlockMember has only two sub traits
 *  StatementMember and SemiToken */
trait ExprTran extends FileSpan

trait BlockMember extends ExprTran
trait StatementMember extends BlockMember
trait ExprMember extends StatementMember
trait ExprMemberToken extends BlockMemberToken with ExprMember


sealed trait BracketToken extends Token
sealed trait BracketOpen extends BracketToken
{
   def matchingBracket(bc: BracketClose): Boolean
   def newBracketBlock(cb: BracketClose, statements: Seq[Statement]): BracketBlock
}
sealed trait BracketClose extends BracketToken
case class ParenthOpen(startPosn: FilePosn) extends BracketOpen  
{
   def str = "("
   override def matchingBracket(bc: BracketClose): Boolean = bc.isInstanceOf[ParenthClose]
   override def newBracketBlock(cb: BracketClose, statements: Seq[Statement]): BracketBlock = (ParenthBlock(statements, this, cb))
}
case class ParenthClose(startPosn: FilePosn) extends BracketClose  { def str = ")" }
case class SquareOpen(startPosn: FilePosn) extends BracketOpen
{
   def str = "["
   override def matchingBracket(bc: BracketClose): Boolean = bc.isInstanceOf[SquareClose]
   override def newBracketBlock(cb: BracketClose, statements: Seq[Statement]): BracketBlock = (SquareBlock(statements, this, cb))
   
}
case class SquareClose(startPosn: FilePosn) extends BracketClose  { def str = "]" }
case class CurlyOpen(startPosn: FilePosn) extends BracketOpen
{
   def str = "{"
   override def matchingBracket(bc: BracketClose): Boolean = bc.isInstanceOf[CurlyClose]
   override def newBracketBlock(cb: BracketClose, statements: Seq[Statement]): BracketBlock = (CurlyBlock(statements, this, cb))
}
case class CurlyClose(startPosn: FilePosn) extends BracketClose { def str = "}" }

sealed trait BracketBlock extends ExprMember
{
   def startBracket: BracketOpen
   def endBracket: BracketClose
   def statements: Seq[Statement]
   def startPosn: FilePosn = startBracket.startPosn
   def endPosn: FilePosn = endBracket.endPosn
}

case class ParenthBlock(statements: Seq[Statement], startBracket: BracketOpen, endBracket: BracketClose) extends BracketBlock
case class SquareBlock(statements: Seq[Statement], startBracket: BracketOpen, endBracket: BracketClose) extends BracketBlock
case class CurlyBlock(statements: Seq[Statement], startBracket: BracketOpen, endBracket: BracketClose) extends BracketBlock

