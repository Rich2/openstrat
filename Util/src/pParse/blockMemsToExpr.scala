/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object parses blockMems to an expression. */
object blockMemsToExpr
{
  /** Tries to Parses a sequence of block members to an [[Expr]]. Returns an [[EMon]][Expr]. So an original String of "4' will essentially return
   * a Good natural integer expression. but "4;" will return a Good Statement sequence of one statment. */
  def apply(implicit inp: Arr[BlockMem]): EMon[Expr] =
  {
    val acc: Buff[Statement] = Buff()
    var subAcc: Buff[StatementMem] = Buff()

    def loop(rem: ArrOff[BlockMem]): EMon[Expr] = rem match
    {
      case ArrOff0() if subAcc.isEmpty => Good(StringStatements(acc.toArr))
      case ArrOff0() if acc.isEmpty => getExpr(subAcc.toArr)
      case ArrOff0() => statementParse(subAcc.toArr, NoRef).map(acc :+ _).map(g => StringStatements(g.toArr))
      case ArrOff1Tail(st: SemicolonToken, tail) if subAcc.isEmpty => { acc.append(EmptyStatement(st)); loop(tail) }

      case ArrOff1Tail(st: SemicolonToken, tail) => statementParse(subAcc.toArr, OptRef(st)).flatMap{ g =>
        acc.append(g)
        subAcc = Buff()
        loop(tail)
      }

      case ArrOff1Tail(sm: StatementMem, tail) => { subAcc.append(sm); loop(tail) }
      case u => excep("Statement Loop, impossible case")
    }

    loop(inp.offset0)
  }
}