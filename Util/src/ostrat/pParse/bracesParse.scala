package ostrat
package pParse

/** Sorts tokens in to brace hierarchy. */
object bracesParse
{
  def apply(rem: RefsOff[Token], open: BracketOpen)(implicit arr: Refs[Token]): EMon2[BracketedStatements, RefsOff[Token]] =
  {
    def loop(rem: RefsOff[Token], acc: List[BlockMember]): EMon2[BracketedStatements, RefsOff[Token]] = rem match
    {
      case RefsOff0() => open.startPosn.bad("Unclosed Brace")

      //This case is where an inner BracketBlock starts within the current BracketBlock
      case RefsOff1Tail(bo: BracketOpen, tail) => bracesParse(tail, bo).flatMap2(
        (bracketBlock, remTokens) =>
        loop(remTokens, acc :+ bracketBlock))

      case RefsOff1Tail(bc: BracketCloseToken, tail) =>
        if (bc.braces == open.braces) blockMembersToEStatements(acc).flatMap2 { g =>
          val res = BracketedStatements(g, bc.braces, open.startPosn, bc.startPosn)
          Good2(res, tail)
        }
        else bc.startPosn.bad("Unexpected Closing Parenthesis")

      case RefsOff1Tail(nbt: BlockMember, tail) => loop(tail, acc :+ nbt)
    }

    loop(rem, Nil)
  }
}