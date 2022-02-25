/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST

/** Function object to parse [[ColonExpr]] from [[ClauseMem]]s. */
object parse7ColonExpr
{ /** Function apply method parses [[ColonExpr]] from [[ClauseMem]]s. */
  def apply(implicit seg: Arr[ClauseMem]): EMon[ClauseMemExpr] =
  {
    val leftAcc: Buff[ColonOpMem] = Buff()
    val rightAcc: Buff[ColonOpMem] = Buff()

    def rightLoop(rem: ArrOff[ClauseMem], leftExpr: ColonMemExpr, ct: ColonToken): EMon[ColonExpr] = rem match {
      case ArrOff0() => parse8ClauseMem(rightAcc.toArr).map{ r => ColonExpr(leftExpr, ct, r) }
      case ArrOff1Tail(ct2: ColonToken, tail) => bad1(ct2, "More than 1 Colon in expression.")
      case ArrOff1Tail(cm: ColonOpMem, tail) => { rightAcc.append(cm); rightLoop(tail, leftExpr, ct) }
    }

    def leftLoop(rem: ArrOff[ClauseMem]): EMon[ClauseMemExpr] = rem match {
      case ArrOff0() => parse8ClauseMem(leftAcc.toArr)
      case ArrOff1Tail(ct: ColonToken, tail) => parse8ClauseMem(leftAcc.toArr).flatMap{ leftExpr => rightLoop(tail, leftExpr, ct) }
      case ArrOff1Tail(cm: ColonOpMem, tail) => { leftAcc.append(cm); leftLoop(tail) }
    }

    leftLoop(seg.offset0)
  }
}