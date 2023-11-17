/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST
import collection.mutable.ArrayBuffer

/** Function object, seems to parse prefix operators. */
object parse9PrefixPlus
{ /** Seems to parse prefix operators. Function object apply method. */
  def apply(implicit refs: RArr[ClauseMem]): EArr[ClauseMem] =
  {
    val acc: ArrayBuffer[ClauseMem] = Buffer()

    def loop(rem: ArrOff[ClauseMem]): EArr[ClauseMem] = rem match
    { case ArrOff0() => Good(acc).map(_.toArr)
      case ArrOff2Tail(pp: OperatorToken,  right: ClauseMemExpr, tail) => { acc.append(PreOpExpr(pp, right)); loop(tail) }
      case ArrOff3Tail(left: ClauseMemExpr, pp: OperatorToken,  right: ClauseMemExpr, tail) =>
      { acc.append(InfixOpExpr(left, pp, right));
        loop(tail)
      }
      case ArrOffHead(pp: OperatorToken) => bad1(pp, "Prefix operator not followed by expression")
      case ArrOff1Tail(h, tail) => { acc.append(h); loop(tail) }
    }
    loop(refs.offset0)
  }
}

object parseAlphaBrackets
{
  def apply(rem: ArrOff[ClauseMem], alpha: IdentifierToken, bracks1: BracketedStructure)(implicit refs: RArr[ClauseMem]):
  (AlphaBracketExpr, ArrOff[ClauseMem]) =
  { val acc: ArrayBuffer[BracketedStructure] = ArrayBuffer(bracks1)

    def loop(rem: ArrOff[ClauseMem]): (AlphaBracketExpr, ArrOff[ClauseMem]) = rem match {
      case ArrOff1Tail(bs: BracketedStructure, tail) => {
        acc.append(bs);
        loop(tail)
      }
      case _ => (AlphaBracketExpr(alpha, acc.toArr), rem)
    }

    loop(rem)
  }
}