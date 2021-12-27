/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object to sort tokens in to brace hierarchy. */
object bracesParse
{ /** Sorts tokens in to brace hierarchy. */
  def apply(rem: ArrOff[Token], open: BracketOpen)(implicit arr: Arr[Token]): EMon2[BracketedStatements, ArrOff[Token]] =
  {
    val acc: Buff[BlockMem] = Buff()
    def loop(rem: ArrOff[Token]): EMon2[BracketedStatements, ArrOff[Token]] = rem match
    {
      case ArrOff0() => open.startPosn.bad2("Unclosed Brace")

      //This case is where an inner BracketBlock starts within the current BracketBlock
      case ArrOff1Tail(bo: BracketOpen, tail) => bracesParse(tail, bo).flatMap2{ (bracketBlock, remTokens) =>
        acc.append(bracketBlock)
        loop(remTokens)
      }

      case ArrOff1Tail(bc: BracketCloseToken, tail) =>
        if (bc.braces == open.braces) statementsParse(acc.toArr).flatMap2 { g =>
          val res = BracketedStatements(g, bc.braces, open.startPosn, bc.startPosn)
          Good2(res, tail)
        }
        else bc.startPosn.bad2("Unexpected Closing Parenthesis")

      case ArrOff1Tail(nbt: BlockMem, tail) => { acc.append(nbt);  loop(tail) }
      case _ => excep("Case not implemented")
    }

    loop(rem)
  }
}