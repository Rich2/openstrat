package ostrat
package pParse

/** Not entirely sure what this does. */
object PrefixPlus
{
  def apply(rem: List[TokenOrBlock]): EMonArr[TokenOrBlock] =
  {
    val acc: Buff[TokenOrBlock] = Buff()

    def loop(rem: List[TokenOrBlock]): EMonArr[TokenOrBlock] = rem match
    {
      case Nil => Good(acc).map(_.toArr)
      case (pp: PrefixToken) :: (right: Expr) :: tail => { acc.append(PreOpExpr(pp, right)); loop(tail) }
      case (pp: PrefixToken) :: _ => bad1(pp, "Prefix operator not followed by expression")
      case h :: tail => { acc.append(h); loop(tail) }
    }
    loop(rem)
  }
}