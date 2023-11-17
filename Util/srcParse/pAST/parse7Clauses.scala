/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST
import collection.mutable.ArrayBuffer

/** Function object parses [[Clause]]s. */
object parse7Clauses
{
  /** Function apply method parses [[Clause]]s. Assumes input [[RArr]] is not empty. */
  def apply (implicit seg: RArr[ColonOpMem]): EMon[ColonMemExpr] = fromOffset(seg.offset0)

  def fromOffset(inp: ArrOff[ColonOpMem])(implicit seg: RArr[ColonOpMem]): EMon[ColonMemExpr] =
  {
    var subAcc: ArrayBuffer[ClauseMem] = Buffer()
    val acc: ArrayBuffer[Clause] = Buffer()

    def loop(rem: ArrOff[ColonOpMem]): EMon[ColonMemExpr] = rem match
    { case ArrOff0() if acc.isEmpty => parse8ClauseMem(subAcc.toArr)
      case ArrOff0() if subAcc.isEmpty => Good(ClausesExpr(acc.toArr))
      case ArrOff0() => parse8ClauseMem(subAcc.toArr).map{ e => ClausesExpr(acc.append(Clause(e, None)).toArr)}
      case ArrOff1Tail(ct: CommaToken, tail) if subAcc.isEmpty => { acc.append(EmptyClause(ct)); loop(tail) }

      case ArrOff1Tail(ct: CommaToken, tail) => parse8ClauseMem(subAcc.toArr).flatMap{ expr =>
        acc.append(Clause(expr, Some(ct)))
        subAcc = Buffer()
        loop(tail)
      }
      case ArrOff1Tail(cm: ClauseMem, tail) => { subAcc.append(cm); loop(tail)}
    }
    loop(inp)
  }
}