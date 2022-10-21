/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST
import collection.mutable.ArrayBuffer

/** Function object to parse assignment expressions. */
object parse5AssignExpr
{ /** Function apply method parses assignment expressions. */
  def apply (implicit inp: RArr[StatementMem]): EMon[Expr] =
  {
    val leftAcc: ArrayBuffer[AssignMem] = Buffer()
    val rightAcc: ArrayBuffer[AssignMem] = Buffer()

    def leftLoop(rem: ArrOff[StatementMem]): EMon[Expr] = rem match
    { case ArrOff0() => parse6ColonExpr(leftAcc.toArr)

      case ArrOff1Tail(at@AsignToken(_), tail) => parse6ColonExpr(leftAcc.toArr).flatMap(gLs => rightLoop(tail).map { gRs =>
        AsignExpr(gLs, at, gRs)
      })

      case ArrOff1Tail(h: AssignMem, tail) => { leftAcc.append(h); leftLoop(tail) }
      case ArrOff1Tail(h, tail) => debexc(h.toString + " is not AssignMemExpr.")
    }

    def rightLoop(rem: ArrOff[StatementMem])(implicit seg: RArr[StatementMem]): EMon[AssignMemExpr] = rem match
    { case ArrOff0() => parse6ColonExpr(rightAcc.toArr)
      case ArrOffHead(at: AsignToken) => bad1(at, "Prefix operator not followed by expression")
      case ArrOff1Tail(am: AssignMem, tail) => { rightAcc.append(am); rightLoop(tail) }
    }

    leftLoop(inp.offset0)
  }
}