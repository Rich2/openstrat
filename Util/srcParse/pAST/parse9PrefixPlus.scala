/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST

/** Function object, seems to parse prefix operators. */
object parse9PrefixPlus
{ /** Seems to parse prefix operators. Function object apply method. */
  def apply(implicit refs: Arr[ClauseMem]): EArr[ClauseMem] =
  {
    val acc: Buff[ClauseMem] = Buff()

    def loop(rem: ArrOff[ClauseMem]): EArr[ClauseMem] = rem match
    { case ArrOff0() => Good(acc).map(_.toArr)
      case ArrOff2Tail(pp: OperatorToken,  right: ColonMemExpr, tail) => { acc.append(PreOpExpr(pp, right)); loop(tail) }
      case ArrOffHead(pp: OperatorToken) => bad1(pp, "Prefix operator not followed by expression")
      case ArrOff1Tail(h, tail) => { acc.append(h); loop(tail) }
    }
    loop(refs.offset0)
  }
}