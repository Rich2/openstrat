/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST

/** Function object to parse [[ColonExpr]] from [[ClauseMem]]s. */
object parse7Clause
{ /** Function apply method parses [[ColonExpr]] from [[ClauseMem]]s. */
  def apply(implicit seg: Arr[ClauseMem]): EMon[ClauseMemExpr] =
  {
    val leftAcc: Buff[ColonOpMem] = Buff()
    val rightAcc: Buff[ColonOpMem] = Buff()

    def rightLoop(rem: ArrOff[ClauseMem], ct: ColonToken, leftExpr: ColonMemExpr): EMon[ColonExpr] = rem match {
      case ArrOff0() => parse8ColonMem(rightAcc.toArr).map{r => ColonExpr(leftExpr, ct, r) }
      case ArrOff1Tail(ct2: ColonToken, tail) => bad1(ct2, "More than 1 Colon in expression.")
      case ArrOff1Tail(cm: ColonOpMem, tail) => { rightAcc.append(cm); rightLoop(tail, ct, leftExpr)}
    }

    def leftLoop(rem: ArrOff[ClauseMem]): EMon[ClauseMemExpr] = rem match {
      case ArrOff0() => parse8ColonMem(leftAcc.toArr)
      case ArrOff1Tail(ct: ColonToken, tail) => parse8ColonMem(leftAcc.toArr).flatMap{leftExpr => rightLoop(tail, ct, leftExpr) }
      case ArrOff1Tail(cm: ColonOpMem, tail) => { leftAcc.append(cm); leftLoop(tail) }
    }

    leftLoop(seg.offset0)
  }
}