/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** Function object for getting an EMon of Statements from Tokens. */
object tokensToEStatements
{
  /** Gets Statements from Tokens. All other methods in this object are private. */
  def apply(implicit tokens: Refs[Token]): ERefs[Statement] = {
    /** The top level loop takes a token sequence input usually from a single source file stripping out the brackets and replacing them and the
     * intervening tokens with a Bracket Block. */
    def fileLoop(rem: RefsOff[Token], acc: List[BlockMember]): ERefs[Statement] = rem match
    { case RefsOff0() => statementLoop(acc, Buff(), Buff())
      case RefsOff1Tail(bo: BracketOpen, tail) => bracketLoop(tail, Nil, bo).flatMap { pair =>
        val (bracketBlock, remTokens) = pair
        fileLoop(remTokens, acc :+ bracketBlock)
      }

      case RefsOff1Plus(bc: BracketClose) => bad1(bc, "Unexpected Closing Brace at top syntax level")
      case RefsOff1Tail(bm: BlockMember, tail) => fileLoop(tail, acc :+ bm)
    }

    /** Sorts tokens in to brace hierarchy. */
    def bracketLoop(rem: RefsOff[Token], acc: List[BlockMember], open: BracketOpen): EMon[(BracketBlock, RefsOff[Token])] = rem match
    { case RefsOff0() => bad1(open, "Unclosed Brace")
      case RefsOff1Tail(bo: BracketOpen, tail) => bracketLoop(tail, Nil, bo).flatMap { pair =>
        val (bracketBlock, remTokens) = pair
        bracketLoop(remTokens, acc :+ bracketBlock, open)
      }

      case RefsOff1Tail(bc: BracketClose, tail) => open.matchingBracket(bc) match
      { case false => bad1(bc, "Unexpected Closing Parenthesis")
        case true => statementLoop(acc, Buff(), Buff()).map(g =>
          (open.newBracketBlock(bc, g), tail)
        )
      }

      case RefsOff1Tail(nbt: BlockMember, tail) => bracketLoop(tail, acc :+ nbt, open)
    }

    def statementLoop(rem: List[BlockMember], acc: Buff[Statement], subAcc: Buff[StatementMember]): ERefs[Statement] = rem match
    { case Nil if subAcc.isEmpty => Good(acc.toRefs)
      case Nil => getStatement(subAcc.toList, nullRef).map(acc :+ _).map(_.toRefs)

      case h :: tail => h match
      { case st: SemicolonToken if subAcc.isEmpty => statementLoop(tail, acc :+ EmptyStatement(st), Buff())
        case st: SemicolonToken => getStatement(subAcc.toList, Opt(st)).flatMap(g => statementLoop(tail, acc :+ g, Buff()))
        case sm: StatementMember => statementLoop(tail, acc, subAcc :+ sm)
        case u => excep("Statement Loop, impossible case")
      }
    }

    def getStatement(statement: List[StatementMember], optSemi: Opt[SemicolonToken]): EMon[Statement] = {
      def loop(rem: List[StatementMember], acc: Buff[Clause], subAcc: List[ExprMember]): EMon[Statement] = rem match {
        case Nil if acc.isEmpty => getExpr(subAcc.toRefs).map(g => MonoStatement(g, optSemi))
        case Nil if subAcc.isEmpty => Good(ClausedStatement(acc.toRefs, optSemi))
        case Nil => getExpr(subAcc.toRefs).map(g => ClausedStatement(acc.append(Clause(g, nullRef)).toRefs, optSemi))
        case (ct: CommaToken) :: tail if subAcc.isEmpty => loop(tail, acc :+ EmptyClause(ct), Nil)
        case (ct: CommaToken) :: tail => getExpr(subAcc.toRefs).flatMap(g => loop(tail, acc :+ Clause(g, Opt(ct)), Nil))
        case (em: ExprMember) :: tail => loop(tail, acc, subAcc :+ em)
      }

      loop(statement, Buff(), Nil)
    }

    fileLoop(tokens.refsOffsetter, Nil)
  }
}
