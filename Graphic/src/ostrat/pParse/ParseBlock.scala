/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse
/** This trait includes all the tokens except braces plus the Bracket syntactic blocks. The Block in the name block member is either the top level
 *  statements in the file or the statements with in a bracket block. Conceptually the source file is considered a special case of bracket block where 
 *  the beginning of substitutes for the opening bracket and the end of file substitutes for the closing bracket. BlockMember has only two sub traits
 *  StatementMember and SemiToken. So a sequence of TokenOrBlocks is simply a series of tokens which has been parsed into Blocks. */
trait TokenOrBlock extends FileSpan

trait BlockMember extends TokenOrBlock
trait StatementMember extends BlockMember
trait ExprMember extends StatementMember
trait ExprMemberToken extends BlockMemberToken with ExprMember


sealed trait BracketToken extends Token
sealed trait BracketOpen extends BracketToken
{
   def matchingBracket(bc: BracketClose): Boolean
   def newBracketBlock(cb: BracketClose, statements: List[Statement]): BracketBlock
}
sealed trait BracketClose extends BracketToken

case class ParenthOpen(startPosn: FilePosn) extends BracketOpen  
{ def str = "("
  override def matchingBracket(bc: BracketClose): Boolean = bc.isInstanceOf[ParenthClose]
  override def newBracketBlock(cb: BracketClose, statements: List[Statement]): BracketBlock = (ParenthBlock(statements, this, cb))
}

case class ParenthClose(startPosn: FilePosn) extends BracketClose { def str = ")" }

case class SquareOpen(startPosn: FilePosn) extends BracketOpen
{ def str = "["
  override def matchingBracket(bc: BracketClose): Boolean = bc.isInstanceOf[SquareClose]
  override def newBracketBlock(cb: BracketClose, statements: List[Statement]): BracketBlock = (SquareBlock(statements, this, cb))   
}

case class SquareClose(startPosn: FilePosn) extends BracketClose  { def str = "]" }

case class CurlyOpen(startPosn: FilePosn) extends BracketOpen
{ def str = "{"  
  override def matchingBracket(bc: BracketClose): Boolean = bc.isInstanceOf[CurlyClose]
  override def newBracketBlock(cb: BracketClose, statements: List[Statement]): BracketBlock = (CurlyBlock(statements, this, cb))
}
case class CurlyClose(startPosn: FilePosn) extends BracketClose { def str = "}" }

sealed trait BracketBlock extends ExprMember
{
   def startBracket: BracketOpen
   def endBracket: BracketClose
   def statements: List[Statement]
   def startPosn: FilePosn = startBracket.startPosn
   def endPosn: FilePosn = endBracket.endPosn
}

case class ParenthBlock(statements: List[Statement], startBracket: BracketOpen, endBracket: BracketClose) extends BracketBlock
case class SquareBlock(statements: List[Statement], startBracket: BracketOpen, endBracket: BracketClose) extends BracketBlock
case class CurlyBlock(statements: List[Statement], startBracket: BracketOpen, endBracket: BracketClose) extends BracketBlock

