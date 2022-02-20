/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object for parsing clause members into ClauseMemExpr. */
object parseClause
{ /** parsing clause members into ClauseMemExpr */
  def apply(implicit seg: Arr[ClauseMem]): EMon[ClauseMemExpr] =
  {
    val acc: Buff[BlockMem] = Buff()

    def sortBlocks(rem: ArrOff[ClauseMem]): EArr[BlockMem] = rem match
    { case ArrOff0() => parsePrefixPlus(acc.toArr)
      case ArrOff2Tail(at: IdentifierToken, bb: BracketedStatements, t2) => {
        val abe = AlphaBracketExpr(at, Arr(bb))
        acc.append(abe)
        sortBlocks(t2)
      }
      /*{ //typedSpan needs removal
        val (blocks, tail) = t2.typedSpan[BracketedStatements](_.isInstanceOf[BracketedStatements])
        sortBlocks(tail, acc :+ AlphaBracketExpr(at, blocks.toImut.asInstanceOf[Refs[BracketedStatements]]))
      }*/
      case ArrOff1Tail(h, tail) => { acc.append(h); sortBlocks(tail) }
    }

    sortBlocks(seg.offset0).flatMap {
      case Arr1(e: ClauseMemExpr) => Good(e)
      case arr if arr.forAll(_.isInstanceOf[ClauseMemExpr]) => Good(SpacedExpr(arr.map(_.asInstanceOf[ClauseMemExpr])))
      case s => bad1(s.head, "Unknown Expression sequence in getBlocks:" -- s.toString)
    }
  }
}