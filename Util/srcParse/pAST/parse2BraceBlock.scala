/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST
import collection.mutable.ArrayBuffer

/** Function object to parse a brace delineated block. */
object parse2BraceBlock
{ /** Function apply method parses input [[Token]]s into a brace syntax block. */
  def apply(rem: ArrOff[Token], open: BracketOpen)(implicit arr: RArr[Token]): ErrBi2[ExcAst, BracketedStructure, ArrOff[Token]] =
  {
    val acc: ArrayBuffer[BlockMem] = Buffer()
    def loop(rem: ArrOff[Token]): ErrBi2[ExcAst, BracketedStructure, ArrOff[Token]] = rem match
    {
      case ArrOff0() => open.startPosn.failAst("Unclosed Brace")

      //This case is where an inner BracketBlock starts within the current BracketBlock
      case ArrOff1Tail(bo: BracketOpen, tail) => parse2BraceBlock(tail, bo).t2FlatMap{ (bracketBlock, remTokens) =>
        acc.append(bracketBlock)
        loop(remTokens)
      }

      case ArrOff1Tail(bc: BracketCloseToken, tail) =>
        if (bc.braces == open.braces) blockMemsToStatements(acc.toArr).map { g =>
          val res = BracketedStructure(g, bc.braces, open.startPosn, bc.startPosn)
          (res, tail)
        }
        else bc.startPosn.failAst("Unexpected Closing Parenthesis")

      case ArrOff1Tail(nbt: BlockMem, tail) => { acc.append(nbt); loop(tail) }
      case _ => excep("Case not implemented")
    }

    loop(rem)
  }
}