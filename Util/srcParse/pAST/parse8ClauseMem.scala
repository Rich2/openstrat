/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST

/** Needs change of input from ColonOpMem => ClauseMem, Function object for parsing [[ColonOpMem]]s into [[ColonMemExpr]]. */
object parse8ClauseMem
{ /** Needs change of input from ColonOpMem => ClauseMem, Function apply method parsing [[ColonOpMem]]s into [[ColonMemExpr]]. */
  def apply(implicit seg: Arr[ColonOpMem]): EMon[ColonMemExpr] =
  {
    val acc: Buff[BlockMem] = Buff()

    def loop(rem: ArrOff[ColonOpMem]): EArr[BlockMem] = rem match
    { case ArrOff0() => parse9PrefixPlus(acc.toArr)

      case ArrOff2Tail(at: IdentifierToken, bb: BracketedStatements, t2) => {
        val abe = AlphaBracketExpr(at, Arr(bb))
        acc.append(abe)
        loop(t2)
      }

      case ArrOff1Tail(h, tail) => { acc.append(h); loop(tail) }
    }

    loop(seg.offset0).flatMap {
      case Arr1(e: ColonMemExpr) => Good(e)
      case arr if arr.forAll(_.isInstanceOf[ColonMemExpr]) => Good(SpacedExpr(arr.map(_.asInstanceOf[ColonMemExpr])))
      case s => bad1(s.head, "Unknown Expression sequence in getBlocks:" -- s.toString)
    }
  }
}