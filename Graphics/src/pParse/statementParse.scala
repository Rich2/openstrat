/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Parses String (with RSON syntax) searching for the String terminator. Returns error if end of file found first. Function object to parse a raw
 *  Statement of statement members, where sub blocks have already been parsed into Statement Blocks. */
object statementParse
{
  /** Parses a sequence of Statement members into a Statement. Statement members are either nonBracketTokens or parsed BracketBlocks.  */
  def apply(memsIn: Arr[StatementMember], optSemi: OptRef[SemicolonToken]): EMon[Statement] =
  {
    implicit val inp = memsIn
    val acc: Buff[Clause] = Buff()
    val subAcc: Buff[ClauseMember] = Buff()

    def loop(rem: ArrOff[StatementMember]): EMon[Statement] = rem match
    { case ArrOff0() if acc.isEmpty => getExpr(subAcc.toArr).map(g => MonoStatement(g, optSemi))
      case ArrOff0() if subAcc.isEmpty => Good(ClausedStatement(acc.toArr, optSemi))
      case ArrOff0() => getExpr(subAcc.toArr).map(g => ClausedStatement(acc.append(Clause(g, NoRef)).toArr, optSemi))
      case ArrOff1Tail(ct: CommaToken, tail) if subAcc.isEmpty => { acc.append(EmptyClause(ct)); loop(tail) }
      case ArrOff1Tail(ct: CommaToken, tail) => getExpr(subAcc.toArr).flatMap{ g =>
        acc.append(Clause(g, OptRef(ct)))
        loop(tail)
      }
      case ArrOff1Tail(em: ClauseMember, tail) => { subAcc.append(em); loop(tail) }
    }

    loop(inp.offset0)
  }
}