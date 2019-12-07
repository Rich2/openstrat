/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** Function object for getting an EMon of Statements from Tokens. */
object tokensToEStatements
{
  /** Gets Statements from Tokens. All other methods in this object are private. */
  def apply(implicit tokens: Refs[Token]): ERefs[Statement] =
  {
    /** The top level loop takes a token sequence input usually from a single source file stripping out the brackets and replacing them and the
     * intervening tokens with a Bracket Block. */
    def fileLoop(rem: RefsOff[Token], acc: List[BlockMember]): ERefs[Statement] = rem match
    { case RefsOff0() => blockMembersToEStatements(acc)
      case RefsOff1Tail(bo: BracketOpen, tail) => bracketLoop(tail, Nil, bo).flatMap { pair =>
        val (bracketBlock, remTokens) = pair
        fileLoop(remTokens, acc :+ bracketBlock)
      }

      case RefsOffHead(bc: BracketCloseToken) => bad1(bc, "Unexpected Closing Brace at top syntax level")
      case RefsOff1Tail(bm: BlockMember, tail) => fileLoop(tail, acc :+ bm)
    }

    /** Sorts tokens in to brace hierarchy. */
    def bracketLoop(rem: RefsOff[Token], acc: List[BlockMember], open: BracketOpen): EMon[(BracketBlock, RefsOff[Token])] = rem match
    { case RefsOff0() => bad1(open, "Unclosed Brace")

      //This case is where an inner BracketBlock starts wiithin the current BracketBlock
      case RefsOff1Tail(bo: BracketOpen, tail) => bracketLoop(tail, Nil, bo).flatMap { pair =>
        val (bracketBlock, remTokens) = pair
        bracketLoop(remTokens, acc :+ bracketBlock, open)
      }

      case RefsOff1Tail(bc: BracketCloseToken, tail) => if (bc.braces == open.braces)
        blockMembersToEStatements(acc).map(g => (BracketBlock(g, bc.braces, open.startPosn , bc.startPosn ), tail))
      else bad1(bc, "Unexpected Closing Parenthesis")

      case RefsOff1Tail(nbt: BlockMember, tail) => bracketLoop(tail, acc :+ nbt, open)
    }

    fileLoop(tokens.offset0, Nil)
  }
}