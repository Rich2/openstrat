/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST
import collection.mutable.ArrayBuffer

/** Function object for parsing [[ClauseMem]]s into [[ClauseMemExpr]]. */
object parse8ClauseMem
{ /** Function apply method parsing [[ClauseMem]]s into [[ClauseMemExpr]]. */
  def apply(implicit inp: RArr[ClauseMem]): ErrBi[ExcAst, ClauseMemExpr] =
  {
    val acc: ArrayBuffer[ClauseMem] = Buffer()

    def loop(rem: ArrOff[ClauseMem]): ErrBiArr[ExcAst, ClauseMem] = rem match
    { case ArrOff0() => parse9PrefixPlus(using acc.toArr)

      case ArrOff2Tail(at: IdentifierToken, bb: BracketedStructure, t2) =>
      { val (newExpr, newRem) = parseAlphaBrackets(t2, at, bb)
        acc.append(newExpr)
        loop(newRem)
      }
      
      /*{ deb("parse8ClauseMem => AlphaBracketExpr")
        val (bks, tail3) = t2.partitionT[BracketedStructure]
        val abe = AlphaBracketExpr(at, bb %: bks)
        acc.append(abe)
        loop(tail3)
      }*/

      case ArrOff1Tail(h, tail) => { acc.append(h); loop(tail) }
    }

    loop(inp.offset0).flatMap{
      case Arr1(e: ClauseMemExpr) => Succ(e)
      case arr if arr.forAll(_.isInstanceOf[ColonMemExpr]) => Succ(SpacedExpr(arr.map(_.asInstanceOf[ColonMemExpr])))
      case s => s.head.startPosn.failAst("Unknown Expression sequence in getBlocks:" -- s.toString)
    }
  }
}