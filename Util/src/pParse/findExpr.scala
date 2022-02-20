/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object, seems to parse prefix operators. */
object parsePrefixPlus
{ /** Seems to parse prefix operators. Function object apply method. */
  def apply(implicit refs: Arr[BlockMem]): EArr[BlockMem] =
  {
    val acc: Buff[BlockMem] = Buff()

    def loop(rem: ArrOff[BlockMem]): EArr[BlockMem] = rem match
    { case ArrOff0() => Good(acc).map(_.toArr)
      case ArrOff2Tail(pp: OperatorToken,  right: ClauseMemExpr, tail) => { acc.append(PreOpExpr(pp, right)); loop(tail) }
      case ArrOffHead(pp: OperatorToken) => bad1(pp, "Prefix operator not followed by expression")
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
    { case ArrOff0() => parseClauses(acc.toArr)

      case ArrOff1Tail(at @ AsignToken(_), tail) => parseClauses(acc.toArr).flatMap(gLs => rightExpr(tail).map { gRs =>
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
      case ArrOff0() => parseClauses(acc.toArr)
      case ArrOffHead(at: AsignToken) => bad1(at, "Prefix operator not followed by expression")
      case ArrOff1Tail(am: AssignMem, tail) => { acc.append(am); loop(tail)}
    }
    loop(inp)
  }
}

/** Function object parses [[Clause]]s. */
object parseClauses
{
  /** parses [[Clause]]s. Assumess input [[Arr]] is not empty. */
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