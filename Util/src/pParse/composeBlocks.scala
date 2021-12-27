/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** I believe this composes Blocks with their preceding identifiers. */
object composeBlocks
{
  def apply(implicit seg: Arr[StatementMember]): EMon[Expr]=
  {
    val acc: Buff[BlockMember] = Buff()

    def sortBlocks(rem: ArrOff[StatementMember]): ERefs[BlockMember] = rem match
    { case ArrOff0() => prefixPlus(acc.toArr)
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
      case Arr1(e: Expr) => Good(e)
      case arr if arr.forAll(_.isInstanceOf[Expr]) => Good(SpacedExpr(arr.map(_.asInstanceOf[Expr])))
      case s => bad1(s.head, "Unknown Expression sequence in getBlocks:" -- s.toString)
    }
  }
}