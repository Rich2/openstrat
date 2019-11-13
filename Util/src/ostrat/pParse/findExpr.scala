package ostrat
package pParse

/** Not entirely sure what this does. */
object PrefixPlus
{
  def apply(implicit refs: Refs[TokenOrBlock]): EMonArr[TokenOrBlock] =
  {
    val acc: Buff[TokenOrBlock] = Buff()

    def loop(rem: RefsOff[TokenOrBlock]): EMonArr[TokenOrBlock] = rem match
    { case RefsOff0() => Good(acc).map(_.toArr)
      case RefsOff2Tail(pp: PrefixToken,  right: Expr, tail) => { acc.append(PreOpExpr(pp, right)); loop(tail) }
      case RefsOff1Plus(pp: PrefixToken) => bad1(pp, "Prefix operator not followed by expression")
      case RefsOff1Tail(h, tail) => { acc.append(h); loop(tail) }
    }
    loop(refs.refsOffsetter)
  }
}

/** Not sure what this does. */
object GetBlocks
{
  def apply(seg: Arr[ExprMember]): EMon[Expr]= sortBlocks(seg.toList, Buff()).flatMap {
    case Seq(e: Expr) => Good(e)
    case s => bad1(s.head, "Unknown Expression sequence:" -- s.toString)
  }

  def sortBlocks(rem: List[ExprMember], acc: Buff[TokenOrBlock]): EMonArr[TokenOrBlock] = rem match
  { case Nil => PrefixPlus(acc.toRefs)
    case (at: AlphaToken) :: (bb: BracketBlock) :: t2 =>
    { //typedSpan needs removal
      val (blocks, tail) = rem.tail.typedSpan[BracketBlock](_.isInstanceOf[BracketBlock])
      sortBlocks(tail, acc :+ AlphaBracketExpr(at, blocks.toImut.asInstanceOf[Refs[BracketBlock]]))
    }
    case h :: tail => sortBlocks(tail, acc :+ h)
  }
}

/** Needs Testing. */
object getExpr
{
  def apply (implicit seg: Refs[ExprMember]): EMon[Expr] =
  {
    val acc: Buff[ExprMember] = Buff()

    def loop(rem: RefsOff[ExprMember]): EMon[Expr] = rem match
    { case RefsOff0() => GetBlocks(acc.toArr)

      case RefsOff1Tail(at: AsignToken, tail) => for {
        gLs <- GetBlocks(acc.toArr);
        gRs <- loop(tail) //This has been altered. I think its correct now with no altering to acc
      } yield AsignExpr(at, gLs, gRs)

      case RefsOff1Tail(h, tail) => { acc.append(h) ;loop(tail) }
    }

    loop(seg.refsOffsetter)
  }
}

object getStatement
{
  def apply(statement: List[StatementMember], optSemi: Opt[SemicolonToken]): EMon[Statement] =
  {
    def loop(rem: List[StatementMember], acc: Buff[Clause], subAcc: List[ExprMember]): EMon[Statement] = rem match {
      case Nil if acc.isEmpty => getExpr(subAcc.toRefs).map(g => MonoStatement(g, optSemi))
      case Nil if subAcc.isEmpty => Good(ClausedStatement(acc.toRefs, optSemi))
      case Nil => getExpr(subAcc.toRefs).map(g => ClausedStatement(acc.append(Clause(g, nullRef)).toRefs, optSemi))
      case (ct: CommaToken) :: tail if subAcc.isEmpty => loop(tail, acc :+ EmptyClause(ct), Nil)
      case (ct: CommaToken) :: tail => getExpr(subAcc.toRefs).flatMap(g => loop(tail, acc :+ Clause(g, Opt(ct)), Nil))
      case (em: ExprMember) :: tail => loop(tail, acc, subAcc :+ em)
    }

    loop(statement, Buff(), Nil)
  }
}