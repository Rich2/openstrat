package ostrat
package pParse

object blockMembersToEStatements
{
  def apply(rem: List[BlockMember]): ERefs[Statement] =
  {
    def loop(rem: List[BlockMember], acc: Buff[Statement], subAcc: Buff[StatementMember]): ERefs[Statement] = rem match
    {
      case Nil if subAcc.isEmpty => Good(acc.toRefs)
      case Nil => statementParse(subAcc.toList, nullRef).map(acc :+ _).map(_.toRefs)

      case h :: tail => h match {
        case st: SemicolonToken if subAcc.isEmpty => loop(tail, acc :+ EmptyStatement(st), Buff())
        case st: SemicolonToken => statementParse(subAcc.toList, Opt(st)).flatMap(g => loop(tail, acc :+ g, Buff()))
        case sm: StatementMember => loop(tail, acc, subAcc :+ sm)
        case u => excep("Statement Loop, impossible case")
      }
    }
    loop(rem, Buff(), Buff())
  }
}

