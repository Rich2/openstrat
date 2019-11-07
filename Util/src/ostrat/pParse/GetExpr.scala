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