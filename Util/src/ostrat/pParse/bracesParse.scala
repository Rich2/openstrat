package ostrat
package pParse

/** Sorts tokens in to brace hierarchy. */
object bracesParse
{
  def apply(rem: RefsOff[Token], acc: List[BlockMember], open: BracketOpen)(implicit arr: Refs[Token]): EMon[(BracketedStatements, RefsOff[Token] )] =
    rem match
  {
    case RefsOff0 () => bad1 (open, "Unclosed Brace")

    //This case is where an inner BracketBlock starts wiithin the current BracketBlock
    case RefsOff1Tail (bo: BracketOpen, tail) => bracesParse (tail, Nil, bo).flatMap { pair =>
      val (bracketBlock, remTokens) = pair
      bracesParse (remTokens, acc :+ bracketBlock, open)
    }

    case RefsOff1Tail (bc: BracketCloseToken, tail) => if (bc.braces == open.braces)
      blockMembersToEStatements (acc).map (g => (BracketedStatements (g, bc.braces, open.startPosn, bc.startPosn), tail) )
      else bad1 (bc, "Unexpected Closing Parenthesis")

    case RefsOff1Tail (nbt: BlockMember, tail) => bracesParse (tail, acc :+ nbt, open)
  }
}