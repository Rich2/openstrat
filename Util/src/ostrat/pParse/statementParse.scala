package ostrat
package pParse

/** Function object to parse a sequence of Statement members into a Statement. Statement members are either nonBracketTokens or parsed
 *  BracketBlocks. */
object statementParse
{
  /** Parses a sequence of Statement members into a Statement. Statement members are either nonBracketTokens or parsed BracketBlocks.  */
  def apply(memsIn: Refs[StatementMember], optSemi: Opt[SemicolonToken]): EMon[Statement] =
  {
    implicit val inp = memsIn
    def loop(rem: RefsOff[StatementMember], acc: Buff[Clause], subAcc: List[ExprMember]): EMon[Statement] = rem match {
      case RefsOff0() if acc.isEmpty => getExpr(subAcc.toRefs).map(g => MonoStatement(g, optSemi))
      case RefsOff0() if subAcc.isEmpty => Good(ClausedStatement(acc.toRefs, optSemi))
      case RefsOff0() => getExpr(subAcc.toRefs).map(g => ClausedStatement(acc.append(Clause(g, nullRef)).toRefs, optSemi))
      case RefsOff1Tail(ct: CommaToken, tail) if subAcc.isEmpty => loop(tail, acc :+ EmptyClause(ct), Nil)
      case RefsOff1Tail(ct: CommaToken, tail) => getExpr(subAcc.toRefs).flatMap(g => loop(tail, acc :+ Clause(g, Opt(ct)), Nil))
      case RefsOff1Tail(em: ExprMember, tail) => loop(tail, acc, subAcc :+ em)
    }

    loop(inp.offset0, Buff(), Nil)
  }
}
