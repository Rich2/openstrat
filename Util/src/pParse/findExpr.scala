/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object. Not entirely sure what this does. */
object prefixPlus
{
  def apply(implicit refs: Arr[BlockMember]): ERefs[BlockMember] =
  {
    val acc: Buff[BlockMember] = Buff()

    def loop(rem: ArrOff[BlockMember]): ERefs[BlockMember] = rem match
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
  def apply (implicit seg: Arr[StatementMember]): EMon[Expr] = fromOffset(seg.offset0)

  def fromOffset(inp: ArrOff[StatementMember])(implicit seg: Arr[StatementMember]): EMon[Expr] =
  {
    val acc: Buff[StatementMember] = Buff()

    def loop(rem: ArrOff[StatementMember]): EMon[Expr] = { rem match
    { case ArrOff0() => composeBlocks(acc.toArr)

      case ArrOff1Tail(at @ AsignToken(_), tail) =>
        composeBlocks(acc.toArr).flatMap(gLs => fromOffset(tail).map(gRs => AsignExpr(gLs, at, gRs)))
      /*{
        val eA = for {
          gLs <- composeBlocks(acc.toRefs)
          gRs <- fromOffset(tail) //This has been altered. I think its correct now with no altering to acc
        } yield AsignExpr(gLs, at, gRs)

        eA
      }*/
      case ArrOff1Tail(h, tail) => { acc.append(h); loop(tail) }
    } }
    loop(inp)
  }
}