/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST

/** Function object parses [[Clause]]s. */
object parse6Clauses
{
  /** Function apply method parses [[Clause]]s. Assumes input [[Arr]] is not empty. */
  def apply (implicit seg: Arr[AssignMem]): EMon[AssignMemExpr] = fromOffset(seg.offset0)

  def fromOffset(inp: ArrOff[AssignMem])(implicit seg: Arr[AssignMem]): EMon[AssignMemExpr] =
  {
    var subAcc: Buff[ClauseMem] = Buff()
    val acc: Buff[Clause] = Buff()
    def loop(rem: ArrOff[AssignMem]): EMon[AssignMemExpr] = rem match {

      case ArrOff0() if acc.isEmpty => parse7ColonExpr(subAcc.toArr)
      case ArrOff0() if subAcc.isEmpty => Good(ClausesExpr(acc.toArr))
      case ArrOff0() => parse7ColonExpr(subAcc.toArr).map{ e => ClausesExpr(acc.append(Clause(e, NoRef)).toArr)}
      case ArrOff1Tail(ct: CommaToken, tail) if subAcc.isEmpty => { acc.append(EmptyClause(ct)); loop(tail) }

      case ArrOff1Tail(ct: CommaToken, tail) => parse7ColonExpr(subAcc.toArr).flatMap{ expr =>
        acc.append(Clause(expr, OptRef(ct)))
        subAcc = Buff()
        loop(tail)
      }
      case ArrOff1Tail(cm: ClauseMem, tail) => { subAcc.append(cm); loop(tail)}
    }
    loop(inp)
  }
}