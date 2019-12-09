package ostrat
package pParse

/** Function object to parse a raw Statement where sub blocks have already been parsed into a Statement. */
object blockMembersParse
{
  /** Parses a raw Statement where sub blocks have already been parsed into a Statement. */
  def apply(implicit inp: Refs[BlockMember]): ERefs[Statement] =
  {
    val acc: Buff[Statement] = Buff()
    val subAcc: Buff[StatementMember] = Buff()

    def loop(rem: RefsOff[BlockMember]): ERefs[Statement] = rem match
    {
      case RefsOff0() if subAcc.isEmpty => Good(acc.toRefs)
      case RefsOff0() => statementParse(subAcc.toRefs, nullRef).map(acc :+ _).map(_.toRefs)
      case RefsOff1Tail(st: SemicolonToken, tail) if subAcc.isEmpty => { acc.append(EmptyStatement(st)); loop(tail) }

      case RefsOff1Tail(st: SemicolonToken, tail) => statementParse(subAcc.toRefs, Opt(st)).flatMap{g =>
          acc.append(g)
          loop(tail)
        }

      case RefsOff1Tail(sm: StatementMember, tail) => { subAcc.append(sm); loop(tail) }
      case u => excep("Statement Loop, impossible case")
    }

    loop(inp.offset0)
  }
}

