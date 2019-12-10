package ostrat
package pParse

/** I believe this composes Blocks with their preceding identifiers. */
object composeBlocks
{
  def apply(seg: Arr[ClauseMember]): EMon[Expr]= sortBlocks(seg.toList, Buff()).flatMap {
    case Arr(e: Expr) => Good(e)
    case s => bad1(s.head, "Unknown Expression sequence in getBlocks:" -- s.toString)
  }

  private[this] def sortBlocks(rem: List[ClauseMember], acc: Buff[BlockMember]): EMonArr[BlockMember] = rem match
  { case Nil => PrefixPlus(acc.toRefs)
    /*case (at: IdentifierLowerToken) :: (bb: BracketedStatements) :: t2 =>
    { //typedSpan needs removal
      val (blocks, tail) = t2.typedSpan[BracketedStatements](_.isInstanceOf[BracketedStatements])
      sortBlocks(tail, acc :+ AlphaBracketExpr(at, blocks.toImut.asInstanceOf[Refs[BracketedStatements]]))
    }*/
    case h :: tail => sortBlocks(tail, acc :+ h)
  }
}