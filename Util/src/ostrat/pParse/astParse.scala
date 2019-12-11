/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** Function object for getting an EMon of Statements from Tokens. */
object astParse
{
  /** Gets Statements from Tokens. All other methods in this object are private. */
  def apply(implicit tokens: Refs[Token]): ERefs[Statement] =
  {
    val acc: Buff[BlockMember] = Buff()
    /** The top level loop takes a token sequence input usually from a single source file stripping out the brackets and replacing them and the
     * intervening tokens with a Bracket Block. */
    def loop(rem: RefsOff[Token]): ERefs[Statement] = rem match
    {
      case RefsOff0() => statementsParse(acc.toRefs)
      case RefsOff1Tail(bo: BracketOpen, tail) => bracesParse(tail, bo).flatMap {(bracketBlock, remTokens) =>
        acc.append(bracketBlock)
        loop(remTokens)
      }

      case RefsOffHead(bc: BracketCloseToken) => bad1(bc, "Unexpected Closing Brace at top syntax level")
      case RefsOff1Tail(bm: BlockMember, tail) => { acc.append(bm); loop(tail) }
    }

    loop(tokens.offset0)
  }
}