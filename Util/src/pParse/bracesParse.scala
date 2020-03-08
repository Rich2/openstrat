package ostrat
package pParse

/** Sorts tokens in to brace hierarchy. */
object bracesParse
{
  def apply(rem: RefsOff[Token], open: BracketOpen)(implicit arr: Refs[Token]): EMon2[BracketedStatements, RefsOff[Token]] =
  {
    val acc: Buff[BlockMember] = Buff()
    def loop(rem: RefsOff[Token]): EMon2[BracketedStatements, RefsOff[Token]] = rem match
    {
      case RefsOff0() => open.startPosn.bad2("Unclosed Brace")

      //This case is where an inner BracketBlock starts within the current BracketBlock
      case RefsOff1Tail(bo: BracketOpen, tail) => bracesParse(tail, bo).flatMap2{(bracketBlock, remTokens) =>
        acc.append(bracketBlock)
        loop(remTokens)
      }

      case RefsOff1Tail(bc: BracketCloseToken, tail) =>
        if (bc.braces == open.braces) statementsParse(acc.toRefs).flatMap2 { g =>
          val res = BracketedStatements(g, bc.braces, open.startPosn, bc.startPosn)
          Good2(res, tail)
        }
        else bc.startPosn.bad2("Unexpected Closing Parenthesis")

      case RefsOff1Tail(nbt: BlockMember, tail) => { acc.append(nbt);  loop(tail) }
    }

    loop(rem)
  }
}