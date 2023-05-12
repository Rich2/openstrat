/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST
import collection.mutable.ArrayBuffer

/** Parses String (with RSON syntax) searching for the String terminator. Returns error if end of file found first. Function object to parse a raw
 *  Statement of statement members, where sub blocks have already been parsed into Statement Blocks. */
object parse4Statement
{
  /** Tries to parse a sequence of [[StatementMem]]s into a Statement. Statement members are either nonBracketTokens or parsed BracketBlocks.  */
  def apply(memsIn: RArr[StatementMem], optSemi: Option[SemicolonToken]): EMon[Statement] =
  {
    implicit val inp = memsIn
    val acc: ArrayBuffer[StatementMem] = Buffer()

    def loop(rem: ArrOff[StatementMem]): EMon[Statement] =
      rem.headFold(parse5AssignExpr(acc.toArr).map(g => StatementNoneEmpty(g, optSemi))){ (em, tail) =>
        acc.append(em)
        loop(tail)
      }


    loop(inp.offset0)
  }
}