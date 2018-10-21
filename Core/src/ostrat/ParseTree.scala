/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

object ParseTree
{
  /** Returns an EMon of a sequence of Statements from a file. This uses the fromString method. Non fatal exceptions or if the file doesn't exist will
   *  be returned as errors.*/
  def fromFile(input: String, fileName: String): EMonSeq[Statement] = TokensGet(input, fileName).flatMap(fromTokens(_))
  /** Returns an EMon of a sequence of Statements from a String. */
  def fromString(input: String): EMonSeq[Statement] = TokensGet.fromString(input).flatMap(fromTokens(_))
  def fromTokens(tokens: Seq[Token]): EMonSeq[Statement] = fileLoop(tokens, Nil)
   
  /** The top level loop takes a token sequence input usually from a single source file stripping out the brackets and replacing them and the intervening
   *  tokens with a Bracket Block. */
  def fileLoop(rem: Seq[Token], acc: Seq[BlockMember]): EMonSeq[Statement] = rem match
  { case Seq() => statementLoop(acc, Nil, Nil)
    case Seq(bo: BracketOpen, tail @ _*) => bracketLoop(tail, Nil, bo).flat2Map((bracketBlock, remTokens) => fileLoop(remTokens, acc :+ bracketBlock))               
    case Seq(bc: BracketClose, _*) => bad1(bc, "Unexpected Closing Brace at top syntax level")
    case Seq(bm: BlockMember, tail @ _*) => fileLoop(tail, acc :+ bm)
  }
   
  /** Sorts tokens in to brace hierarchy. */ 
  def bracketLoop(rem: Seq[Token], acc: Seq[BlockMember], open: BracketOpen): EMon[(BracketBlock, Seq[Token])] = rem match
  {
    case Seq() => bad1(open, "Unclosed Brace")
    case Seq(bo: BracketOpen, tail @ _*) =>
      bracketLoop(tail, Nil, bo).flat2Map((bracketBlock, remTokens) => bracketLoop(remTokens, acc :+ bracketBlock, open))
    
    case Seq(bc: BracketClose, tail @ _*) => open.matchingBracket(bc) match
    { case false => bad1(bc, "Unexpected Closing Parenthesis")
      case true => statementLoop(acc, Nil, Nil).map(g => (open.newBracketBlock(bc, g), tail))            
    }
    
    case Seq(nbt: BlockMember, tail @ _*) => bracketLoop(tail, acc :+ nbt, open)               
  }      
   
  def statementLoop(rem: Seq[BlockMember], acc: Seq[Statement], subAcc: Seq[StatementMember]): EMon[Seq[Statement]] = rem match
  {
    case Seq() if subAcc.isEmpty => Good(acc)
    case Seq () => getStatement(subAcc, nullRef).map(acc :+ _)      
    
    case Seq(h, tail @ _*) => h match
    {
      case st: SemicolonToken if subAcc.isEmpty => statementLoop(tail, acc :+ EmptyStatement(st), Nil) 
      case st: SemicolonToken => getStatement(subAcc, Opt(st)).flatMap(g => statementLoop(tail, acc :+ g, Seq()))         
      case sm: StatementMember => statementLoop(tail, acc, subAcc :+ sm)
      case u => excep("Statement Loop, impossible case")
    }
  }
   
  def getStatement(statement: Seq[StatementMember], optSemi: Opt[SemicolonToken]): EMon[Statement] =
  {
    def loop(rem: Seq[StatementMember], acc: Seq[Clause], subAcc: Seq[ExprMember]): EMon[Statement] = rem match
    {
      case Seq() if acc.isEmpty => getExpr(subAcc).map(g => MonoStatement(g, optSemi))
      case Seq() if subAcc.isEmpty => Good(ClausedStatement(acc, optSemi))
      case Seq() => getExpr(subAcc).map(g => ClausedStatement(acc :+ Clause(g, nullRef), optSemi))        
      case Seq(ct: CommaToken, tail @ _*) if subAcc.isEmpty=> loop(tail, acc :+ EmptyClause(ct), Nil)
      case Seq(ct: CommaToken, tail @ _*) => getExpr(subAcc).flatMap(g => loop(tail, acc :+ Clause(g , Opt(ct)), Nil))
      case Seq(em: ExprMember, tail @ _*) => loop(tail, acc, subAcc :+ em)
    }
    loop(statement, Seq(), Seq())
  }
   
  def getExpr(seg: Seq[ExprMember]): EMon[Expr] = sortBlocks(seg ,Nil).flatMap{
    case Seq(e: Expr) => Good(e)      
    case s => bad1(s.head, "Unknown Expression sequence:" -- s.toString) 
  }
   
  def sortBlocks(rem: Seq[ExprMember], acc: Seq[ExprTran]): EMon[Seq[ExprTran]] = rem match
  {
    case Seq() => prefixPlus(acc, Nil)
    case Seq(at: AlphaToken, bb: BracketBlock, _*) =>
    {//typedSpan needs removal */
      val (blocks, tail) = rem.tail.typedSpan[BracketBlock](_.isInstanceOf[BracketBlock])
      sortBlocks(tail, acc :+ AlphaBracketExpr(at, blocks))               
    }
    case Seq(h, tail @ _*) => sortBlocks(tail, acc :+ h)
  }
      
  def prefixPlus(rem: Seq[ExprTran], acc: Seq[ExprTran]): EMon[Seq[ExprTran]] = rem match
  {
    case Seq() => Good(acc)
    case Seq(pp: PlusPreToken, right: Expr, tail @ _*) => prefixPlus(tail, acc :+ PreOpExpr(pp, right))
    case Seq(pp: PlusPreToken, _*) => bad1(pp, "Prefix operator not fillowed by expression")
    case Seq(h, tail @ _*) => prefixPlus(tail, acc :+ h)
  }
}
