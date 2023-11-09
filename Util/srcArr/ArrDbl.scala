/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, pParse._

/** An immutable Array based class for Doubles. */
class ArrDbl(val unsafeArray: Array[Double]) extends AnyVal with ArrNoParam[Double]
{ type ThisT = ArrDbl
  override def typeStr: String = "Doubles"
  override def unsafeSameSize(length: Int): ArrDbl = new ArrDbl(new Array[Double](length))
  override def length: Int = unsafeArray.length
  override def apply(index: Int): Double = unsafeArray(index)
  override def setElemUnsafe(i: Int, newElem: Double): Unit = unsafeArray(i) = newElem
  def unsafeArrayCopy(operand: Array[Double], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Double => String = _.toString

  @targetName("appendArr") def ++ (op: ArrDbl): ArrDbl =
  { val newArray = new Array[Double](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new ArrDbl(newArray)
  }

  override def drop(n: Int): ArrDbl = ???

  /** Reverses the order of the elements of this sequence. */
  override def reverse: ArrDbl = ???

  /** append. appends element to this [[Arr]]. */
  @targetName("append") override def +%(operand: Double): ArrDbl = ???
}

/** Companion object for the Dbls Array based class for Doubles, contains a repeat parameter factory method. */
object ArrDbl
{ def apply(input: Double*): ArrDbl = new ArrDbl(input.toArray)

  implicit val eqImplicit: EqT[ArrDbl] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }

  /** Implicit method for creating [[IntArr]] instances. */
  implicit val unshowEv: Unshow[ArrDbl] = new Unshow[ArrDbl]
  { override def typeStr: String = "Seq" + "Dbl"

    override def fromExpr(expr: Expr): EMon[ArrDbl] = expr match
    { case _: EmptyExprToken => Good(ArrDbl())

      case AlphaBracketExpr(id1,
      RArr2(BracketedStatements(RArr1(_), brs1, _, _),
      BracketedStatements(sts, brs2, _, _))) if (id1.srcStr == "Seq") && brs1 == SquareBraces && brs2 == Parentheses =>
        sts.mapEMon(s => Unshow.doubleEv.fromExpr(s.expr))(DblArrBuilder)

      case AlphaBracketExpr(id1, RArr1(BracketedStatements(sts, brs, _, _))) if (id1.srcStr == "Seq") && brs == Parentheses =>
        sts.mapEMon(s => Unshow.doubleEv.fromExpr(s.expr))(DblArrBuilder)

      case e => bad1(expr, expr.toString + " unknown Expression for Seq")
    }
  }
}

object DblArrBuilder extends BuilderArrMap[Double, ArrDbl] with BuilderArrFlat[ArrDbl]
{ type BuffT = BuffDbl
  override def uninitialised(length: Int): ArrDbl = new ArrDbl(new Array[Double](length))
  override def indexSet(seqLike: ArrDbl, index: Int, elem: Double): Unit = seqLike.unsafeArray(index) = elem
  override def newBuff(length: Int = 4): BuffDbl = new BuffDbl(new ArrayBuffer[Double](length))
  override def buffGrow(buff: BuffDbl, newElem: Double): Unit = buff.unsafeBuffer.append(newElem)
  override def buffToSeqLike(buff: BuffDbl): ArrDbl = new ArrDbl(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BuffDbl, arr: ArrDbl): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
}

/** Compile time wrapped Buff class for [[Double]]s, used to build [[ArrDbl]]. */
class BuffDbl(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffSequ[Double]
{ override type ThisT = BuffDbl
  override def typeStr: String = "DblsBuff"
  override def apply(index: Int): Double = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def setElemUnsafe(i: Int, newElem: Double): Unit = unsafeBuffer(i) = newElem
  override def fElemStr: Double => String = _.toString
  override def grow(newElem: Double): Unit = unsafeBuffer.append(newElem)
}