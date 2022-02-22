/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object to parse ColonExpr. */
object parseClauseMem
{ /** Function object to parse ColonExpr. */
  def apply(implicit seg: Arr[ClauseMem]): EMon[ClauseMemExpr] =
  {
    val leftAcc: Buff[ColonOpMem] = Buff()
    val rightAcc: Buff[ColonOpMem] = Buff()

    def rightLoop(rem: ArrOff[ClauseMem], ct: ColonToken, leftExpr: ColonMemExpr): EMon[ColonExpr] = rem match {
      case ArrOff0() => parseColonMem(rightAcc.toArr).map{r => ColonExpr(leftExpr, ct, r) }
      case ArrOff1Tail(ct2: ColonToken, tail) => bad1(ct2, "More than 1 Colon in expression.")
      case ArrOff1Tail(cm: ColonOpMem, tail) => { rightAcc.append(cm); rightLoop(tail, ct, leftExpr)}
    }

    def leftLoop(rem: ArrOff[ClauseMem]): EMon[ClauseMemExpr] = rem match {
      case ArrOff0() => parseColonMem(leftAcc.toArr)
      case ArrOff1Tail(ct: ColonToken, tail) => parseColonMem(leftAcc.toArr).flatMap{leftExpr => rightLoop(tail, ct, leftExpr) }
      case ArrOff1Tail(cm: ColonOpMem, tail) => { rightAcc.append(cm); leftLoop(tail) }
    }

    leftLoop(seg.offset0)
  }
}

/** Function object for parsing clause members into ClauseMemExpr. */
object parseColonMem
{ /** parsing clause members into ClauseMemExpr */
  def apply(implicit seg: Arr[ColonOpMem]): EMon[ColonMemExpr] =
  {
    val acc: Buff[BlockMem] = Buff()

    def sortBlocks(rem: ArrOff[ColonOpMem]): EArr[BlockMem] = rem match
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
      case Arr1(e: ColonMemExpr) => Good(e)
      case arr if arr.forAll(_.isInstanceOf[ColonMemExpr]) => Good(SpacedExpr(arr.map(_.asInstanceOf[ColonMemExpr])))
      case s => bad1(s.head, "Unknown Expression sequence in getBlocks:" -- s.toString)
    }
  }
}