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
    {
      case RefsOff0() => blockMembersToEStatements(acc)
      case RefsOff1Tail(bo: BracketOpen, tail) => bracesParse(tail, bo).flatMap {(bracketBlock, remTokens) =>
        fileLoop(remTokens, acc :+ bracketBlock)
      }

      case RefsOffHead(bc: BracketCloseToken) => bad1(bc, "Unexpected Closing Brace at top syntax level")
      case RefsOff1Tail(bm: BlockMember, tail) => fileLoop(tail, acc :+ bm)
    }

    fileLoop(tokens.offset0, Nil)
  }
}