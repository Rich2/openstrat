package ostrat
package pParse

/** Not entirely sure what this does. */
object PrefixPlus
{
  def apply(implicit refs: Refs[BlockMember]): ERefs[BlockMember] =
  {
    val acc: Buff[BlockMember] = Buff()

    def loop(rem: RefsOff[BlockMember]): ERefs[BlockMember] = rem match
    { case RefsOff0() => Good(acc).map(_.toRefs)
      case RefsOff2Tail(pp: PrefixToken,  right: Expr, tail) => { acc.append(PreOpExpr(pp, right)); loop(tail) }
      case RefsOffHead(pp: PrefixToken) => bad1(pp, "Prefix operator not followed by expression")
      case RefsOff1Tail(h, tail) => { acc.append(h); loop(tail) }
    }
    loop(refs.offset0)
  }
}

/** Needs Testing. */
object getExpr
{
  def apply (implicit seg: Refs[ClauseMember]): EMon[Expr] =
  {
    val acc: Buff[ClauseMember] = Buff()

    def loop(rem: RefsOff[ClauseMember]): EMon[Expr] = rem match
    { case RefsOff0() => composeBlocks(acc.toRefs)

      case RefsOff1Tail(at: AsignToken, tail) => for
      { gLs <- composeBlocks(acc.toRefs);
        gRs <- loop(tail) //This has been altered. I think its correct now with no altering to acc
      } yield AsignExpr(at, gLs, gRs)

      case RefsOff1Tail(h, tail) => { acc.append(h); loop(tail) }
    }

    loop(seg.offset0)
  }
}