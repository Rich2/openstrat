/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST
import collection.mutable.ArrayBuffer

/** Function object for parsing [[blockMem]]s to an [[Expr]]. */
object parse3Statements
{
  /** Tries to parse a sequence of block members to an [[Expr]]. So an original String of "4' will return a [[Good]] natural integer expression. but "4;" will
   * return a [[Good]] [[Statement]] sequence of one [[Statement]]. */
  def apply(implicit inp: RArr[BlockMem]): ErrBi[ExcAst, Expr] =
  { val acc: ArrayBuffer[Statement] = Buffer()
    var subAcc: ArrayBuffer[StatementMem] = Buffer()

    def loop(rem: ArrOff[BlockMem]): ErrBi[ExcAst, Expr] = rem match
    { case ArrOff0() if subAcc.isEmpty => Succ(StringStatements(acc.toArr))
      case ArrOff0() if acc.isEmpty => parse5AssignExpr(using subAcc.toArr)
      case ArrOff0() => parse4Statement(subAcc.toArr, None).map(acc :+ _).map(g => StringStatements(g.toArr))
      case ArrOff1Tail(st: SemicolonToken, tail) if subAcc.isEmpty => { acc.append(StatementEmpty(st)); loop(tail) }

      case ArrOff1Tail(st: SemicolonToken, tail) => parse4Statement(subAcc.toArr, Some(st)).flatMap{ g =>
        acc.append(g)
        subAcc = Buffer()
        loop(tail)
      }

      case ArrOff1Tail(sm: StatementMem, tail) => { subAcc.append(sm); loop(tail) }
      case u => excep("Statement Loop, impossible case")
    }

    loop(inp.offset0)
  }
}