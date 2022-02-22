/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

object tokensToBlockMems
{
  /** Gets Statements from Tokens. All other methods in this object are private. */
  def apply(implicit tokens: Arr[Token]): EArr[BlockMem] =
  {
    val acc: Buff[BlockMem] = Buff()

    /** The top level loop takes a token sequence input usually from a single source file stripping out the brackets and replacing them and the
     * intervening tokens with a Bracket Block. */
    def loop(rem: ArrOff[Token]): EArr[BlockMem] = rem match
    {
      case ArrOff0() => Good(acc.toArr)
      case ArrOff1Tail(bo: BracketOpen, tail) => bracesParse(tail, bo).flatMap { (bracketBlock, remTokens) =>
        acc.append(bracketBlock)
        loop(remTokens)
      }

      case ArrOffHead(bc: BracketCloseToken) => bc.startPosn.bad("Unexpected Closing Brace at top syntax level")
      case ArrOff1Tail(bm: BlockMem, tail) => { acc.append(bm); loop(tail) }
      case _ => excep("Case not implemented")
    }

    loop(tokens.offset0)
  }
}