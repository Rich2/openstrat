package ostrat
package pParse

/** I believe this composes Blocks with their preceding identifiers. */
object composeBlocks
{
  def apply(implicit seg: Arr[ClauseMember]): EMon[Expr]=
  {
    val acc: Buff[BlockMember] = Buff()

    def sortBlocks(rem: RefsOff[ClauseMember]): ERefs[BlockMember] = rem match
    { case RefsOff0() => PrefixPlus(acc.toRefs)
      /*case (at: IdentifierLowerToken) :: (bb: BracketedStatements) :: t2 =>
      { //typedSpan needs removal
        val (blocks, tail) = t2.typedSpan[BracketedStatements](_.isInstanceOf[BracketedStatements])
        sortBlocks(tail, acc :+ AlphaBracketExpr(at, blocks.toImut.asInstanceOf[Refs[BracketedStatements]]))
      }*/
      case RefsOff1Tail(h, tail) => { acc.append(h); sortBlocks(tail) }
    }

    sortBlocks(seg.offset0).flatMap {
      case Arr1(e: Expr) => Good(e)
      case s => bad1(s.head, "Unknown Expression sequence in getBlocks:" -- s.toString)
    }
  }
}