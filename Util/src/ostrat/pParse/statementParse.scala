package ostrat
package pParse

/** Function object to parse a sequence of Statement members into a Statement. Statement members are either nonBracketTokens or parsed
 *  BracketBlocks. */
object statementParse
{
  /** Parses a sequence of Statement members into a Statement. Statement members are either nonBracketTokens or parsed BracketBlocks.  */
  def apply(statement: List[StatementMember], optSemi: Opt[SemicolonToken]): EMon[Statement] =
  {
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
}
