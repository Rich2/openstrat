/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST

/** Function object that seeks to get a valid expression from a Mono Statement or clause. */
object parse5Assignments
{ /** Seeks to get a valid expression from a Mono Statement or clause. */
  def apply (implicit seg: Arr[StatementMem]): EMon[Expr] = fromOffset(seg.offset0)

  def fromOffset(inp: ArrOff[StatementMem])(implicit seg: Arr[StatementMem]): EMon[Expr] =
  {
    val acc: Buff[AssignMem] = Buff()

    def loop(rem: ArrOff[StatementMem]): EMon[Expr] = rem match
    { case ArrOff0() => parse6Clauses(acc.toArr)

      case ArrOff1Tail(at @ AsignToken(_), tail) => parse6Clauses(acc.toArr).flatMap(gLs => rightExpr(tail).map { gRs =>
          AsignExpr(gLs, at, gRs)
        })

      case ArrOff1Tail(h: AssignMem, tail) => { acc.append(h); loop(tail) }
      case ArrOff1Tail(h, tail) => debexc(h.toString + " is not AssignMemExpr.")
    }
    loop(inp)
  }

  def rightExpr(inp: ArrOff[StatementMem])(implicit seg: Arr[StatementMem]): EMon[AssignMemExpr] =
  {
    val acc: Buff[AssignMem] = Buff()
    def loop(rem: ArrOff[StatementMem]): EMon[AssignMemExpr] = rem match {
      case ArrOff0() => parse6Clauses(acc.toArr)
      case ArrOffHead(at: AsignToken) => bad1(at, "Prefix operator not followed by expression")
      case ArrOff1Tail(am: AssignMem, tail) => { acc.append(am); loop(tail)}
    }
    loop(inp)
  }
}