package ostrat
package pParse

/** Function object to parse a sequence of Statement members into a  sequence of Statements. Statement members are either nonBracketTokens
 *  or parsed BracketBlocks. */
object statementsParse
{
  /** Parses a sequence of block members raw Statement where bracket blocks have already been parsed into a sequence of Statements. */
  def apply(implicit inp: Refs[BlockMember]): ERefs[Statement] =
  {
    val acc: Buff[Statement] = Buff()
    var subAcc: Buff[StatementMember] = Buff()

    def loop(rem: RefsOff[BlockMember]): ERefs[Statement] = rem match
    {
      case RefsOff0() if subAcc.isEmpty => Good(acc.toRefs)
      case RefsOff0() => statementParse(subAcc.toRefs, nullRef).mapOld(acc :+ _).mapOld(_.toRefs)
      case RefsOff1Tail(st: SemicolonToken, tail) if subAcc.isEmpty => { acc.append(EmptyStatement(st)); loop(tail) }

      case RefsOff1Tail(st: SemicolonToken, tail) =>statementParse(subAcc.toRefs, OptOldRef(st)).flatMapOld{ g =>
          acc.append(g)
          subAcc = Buff()
          loop(tail)
        }

      case RefsOff1Tail(sm: StatementMember, tail) => { subAcc.append(sm); loop(tail) }
      case u => excep("Statement Loop, impossible case")
    }

    loop(inp.offset0)
  }
}

/*object parseSemicolons
{
  def apply(implicit inp: Refs[BlockMember]): ERefs[Refs[StatementMember]] =
  {
    ???
  }
}*/

