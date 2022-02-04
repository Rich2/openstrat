/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object. Not entirely sure what this does. */
object prefixPlus
{
  def apply(implicit refs: Arr[BlockMem]): EArr[BlockMem] =
  {
    val acc: Buff[BlockMem] = Buff()

    def loop(rem: ArrOff[BlockMem]): EArr[BlockMem] = rem match
    { case ArrOff0() => Good(acc).map(_.toArr)
      //case RefsOff2Tail(pp: PrefixToken,  right: Expr, tail) => { acc.append(PreOpExpr(pp, right)); loop(tail) }
      //case RefsOffHead(pp: PrefixToken) => bad1(pp, "Prefix operator not followed by expression")
      case ArrOff1Tail(h, tail) => { acc.append(h); loop(tail) }
    }
    loop(refs.offset0)
  }
}

/** Function object that seeks to get a valid expression from a Mono Statement or clause. */
object getExpr
{ /** Seeks to get a valid expression from a Mono Statement or clause. */
  def apply (implicit seg: Arr[StatementMem]): EMon[Expr] = fromOffset(seg.offset0)

  def fromOffset(inp: ArrOff[StatementMem])(implicit seg: Arr[StatementMem]): EMon[Expr] =
  {
    val acc: Buff[AssignMem] = Buff()

    def loop(rem: ArrOff[StatementMem]): EMon[Expr] = rem match
    { case ArrOff0() => getClauses(acc.toArr)

      case ArrOff1Tail(at @ AsignToken(_), tail) => getClauses(acc.toArr).flatMap(gLs => rightExpr(tail).map { gRs =>
          AsignExpr(gLs, at, gRs)
        })

      case ArrOff1Tail(h: AssignMemExpr, tail) => { acc.append(h); loop(tail) }
      case ArrOff1Tail(h, tail) => debexc(h.toString + " is not AssignMemExpr.")
    }
    loop(inp)
  }

  def rightExpr(inp: ArrOff[StatementMem])(implicit seg: Arr[StatementMem]): EMon[AssignMemExpr] =
  {
    val acc: Buff[AssignMem] = Buff()
    def loop(rem: ArrOff[StatementMem]): EMon[AssignMemExpr] = rem match {
      case ArrOff0() => getClauses(acc.toArr)
      case ArrOffHead(at: AsignToken) => bad1(at, "Prefix operator not followed by expression")
      case ArrOff1Tail(am: AssignMem, tail) => { acc.append(am); loop(tail)}
    }
    loop(inp)
  }
}

object getClauses
{
  /** This assumes this is not empty */
  def apply (implicit seg: Arr[AssignMem]): EMon[AssignMemExpr] = fromOffset(seg.offset0)

  def fromOffset(inp: ArrOff[AssignMem])(implicit seg: Arr[AssignMem]): EMon[AssignMemExpr] =
  {
    var subAcc: Buff[ClauseMem] = Buff()
    val acc: Buff[Clause] = Buff()
    def loop(rem: ArrOff[AssignMem]): EMon[AssignMemExpr] = rem match {

      case ArrOff0() if acc.isEmpty => parseClause(subAcc.toArr)
      case ArrOff0() if subAcc.isEmpty => Good(ClausesExpr(acc.toArr))
      case ArrOff0() => parseClause(subAcc.toArr).map{e => ClausesExpr(acc.append(Clause(e, NoRef)).toArr)}
      case ArrOff1Tail(ct: CommaToken, tail) if subAcc.isEmpty => { acc.append(EmptyClause(ct)); loop(tail) }

      case ArrOff1Tail(ct: CommaToken, tail) => parseClause(subAcc.toArr).flatMap{ expr =>
        acc.append(Clause(expr, OptRef(ct)))
        subAcc = Buff()
        loop(tail)
      }
      case ArrOff1Tail(cm: ClauseMem, tail) => { subAcc.append(cm); loop(tail)}
    }
    loop(inp)
  }
}