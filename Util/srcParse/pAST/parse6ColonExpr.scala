/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST
import collection.mutable.ArrayBuffer

/** Function object to parse [[ColonExpr]] from [[AssignMem]]s. */
object parse6ColonExpr
{ /** Function apply method parses [[ColonExpr]] from [[AssignMem]]s. */
  def apply(implicit seg: RArr[AssignMem]): EMon[AssignMemExpr] =
  {
    val leftAcc: ArrayBuffer[ColonOpMem] = Buff()
    val rightAcc: ArrayBuffer[ColonOpMem] = Buff()

    def rightLoop(rem: ArrOff[AssignMem], leftExpr: ColonMemExpr, ct: ColonToken): EMon[ColonExpr] = rem match {
      case ArrOff0() => parse7Clauses(rightAcc.toArr).map{ r => ColonExpr(leftExpr, ct, r) }
      case ArrOff1Tail(ct2: ColonToken, tail) => bad1(ct2, "More than 1 Colon in expression.")
      case ArrOff1Tail(cm: ColonOpMem, tail) => { rightAcc.append(cm); rightLoop(tail, leftExpr, ct) }
    }

    def leftLoop(rem: ArrOff[AssignMem]): EMon[AssignMemExpr] = rem match {
      case ArrOff0() => parse7Clauses(leftAcc.toArr)
      case ArrOff1Tail(ct: ColonToken, tail) => parse7Clauses(leftAcc.toArr).flatMap{ leftExpr => rightLoop(tail, leftExpr, ct) }
      case ArrOff1Tail(cm: ColonOpMem, tail) => { leftAcc.append(cm); leftLoop(tail) }
    }

    leftLoop(seg.offset0)
  }
}