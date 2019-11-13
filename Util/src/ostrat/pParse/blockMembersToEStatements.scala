package ostrat
package pParse

object blockMembersToEStatements
{
  def apply(rem: List[BlockMember]): ERefs[Statement] =
  {
    def loop(rem: List[BlockMember], acc: Buff[Statement], subAcc: Buff[StatementMember]): ERefs[Statement] = rem match
    {
      case Nil if subAcc.isEmpty => Good(acc.toRefs)
      case Nil => blockMembersToEStatement(subAcc.toList, nullRef).map(acc :+ _).map(_.toRefs)

      case h :: tail => h match {
        case st: SemicolonToken if subAcc.isEmpty => loop(tail, acc :+ EmptyStatement(st), Buff())
        case st: SemicolonToken => blockMembersToEStatement(subAcc.toList, Opt(st)).flatMap(g => loop(tail, acc :+ g, Buff()))
        case sm: StatementMember => loop(tail, acc, subAcc :+ sm)
        case u => excep("Statement Loop, impossible case")
      }
    }
    loop(rem, Buff(), Buff())
  }
}

object blockMembersToEStatement
{
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