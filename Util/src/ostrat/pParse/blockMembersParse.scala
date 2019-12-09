package ostrat
package pParse

object blockMembersParse
{
  def apply(inp: List[BlockMember]): ERefs[Statement] =
  {
    def loop(rem: List[BlockMember], acc: Buff[Statement], subAcc: Buff[StatementMember]): ERefs[Statement] = rem match
    {
      case Nil if subAcc.isEmpty => Good(acc.toRefs)
      case Nil => statementParse(subAcc.toRefs, nullRef).map(acc :+ _).map(_.toRefs)

      case h :: tail => h match
      {
        case st: SemicolonToken if subAcc.isEmpty => loop(tail, acc :+ EmptyStatement(st), Buff())
        case st: SemicolonToken => statementParse(subAcc.toRefs, Opt(st)).flatMap(g => loop(tail, acc :+ g, Buff()))
        case sm: StatementMember => loop(tail, acc, subAcc :+ sm)
        case u => excep("Statement Loop, impossible case")
      }
    }
    loop(inp, Buff(), Buff())
  }
}

