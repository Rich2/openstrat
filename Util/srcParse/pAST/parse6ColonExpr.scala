/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST
import collection.mutable.ArrayBuffer

/** Function object to parse [[ColonExpr]] from [[AssignMem]]s. */
object parse6ColonExpr
{ /** Function apply method parses [[ColonExpr]] from [[AssignMem]]s. */
  def apply(implicit seg: RArr[AssignMem]): ErrBi[ExcAst, AssignMemExpr] =
  { val leftAcc: ArrayBuffer[ColonOpMem] = Buffer()
    val rightAcc: ArrayBuffer[ColonOpMem] = Buffer()

    def rightLoop(rem: ArrOff[AssignMem], leftExpr: ColonMemExpr, ct: ColonToken): ErrBi[ExcAst, ColonExpr] = rem match
    { case ArrOff0() => parse7Clauses(using rightAcc.toArr).map{ r => ColonExpr(leftExpr, ct, r) }
      case ArrOff1Tail(ct2: ColonToken, tail) => ct2.startPosn.failAst("More than 1 Colon in expression.")
      case ArrOff1Tail(cm: ColonOpMem, tail) => { rightAcc.append(cm); rightLoop(tail, leftExpr, ct) }
    }

    def leftLoop(rem: ArrOff[AssignMem]): ErrBi[ExcAst, AssignMemExpr] = rem match
    { case ArrOff0() => parse7Clauses(using leftAcc.toArr)
      case ArrOff1Tail(ct: ColonToken, tail) => parse7Clauses(using leftAcc.toArr).flatMap{ leftExpr => rightLoop(tail, leftExpr, ct) }
      case ArrOff1Tail(cm: ColonOpMem, tail) => { leftAcc.append(cm); leftLoop(tail) }
    }

    leftLoop(seg.offset0)
  }
}