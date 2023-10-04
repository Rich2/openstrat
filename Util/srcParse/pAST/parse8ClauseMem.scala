/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST
import collection.mutable.ArrayBuffer

/** Function object for parsing [[ClauseMem]]s into [[ClauseMemExpr]]. */
object parse8ClauseMem
{ /** Function apply method parsing [[ClauseMem]]s into [[ClauseMemExpr]]. */
  def apply(implicit inp: RArr[ClauseMem]): EMon[ClauseMemExpr] =
  {
    val acc: ArrayBuffer[ClauseMem] = Buffer()

    def loop(rem: ArrOff[ClauseMem]): EArr[ClauseMem] = rem match
    { case ArrOff0() => parse9PrefixPlus(acc.toArr)

      case ArrOff2Tail(at: IdentifierToken, bb: BracketedStatements, t2) =>
      { val (bks, tail3) = t2.partitionT[BracketedStatements]
        val abe = AlphaBracketExpr(at, bb %: bks)
        acc.append(abe)
        loop(tail3)
      }

      case ArrOff1Tail(h, tail) => { acc.append(h); loop(tail) }
    }

    loop(inp.offset0).flatMap {
      case Arr1(e: ClauseMemExpr) => Good(e)
      case arr if arr.forAll(_.isInstanceOf[ColonMemExpr]) => Good(SpacedExpr(arr.map(_.asInstanceOf[ColonMemExpr])))
      case s => bad1(s.head, "Unknown Expression sequence in getBlocks:" -- s.toString)
    }
  }
}