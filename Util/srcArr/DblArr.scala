/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, pParse._

/** An immutable Array based class for Doubles. */
class DblArr(val unsafeArray: Array[Double]) extends AnyVal with ArrNoParam[Double]
{ type ThisT = DblArr
  override def typeStr: String = "Doubles"
  override def unsafeSameSize(length: Int): DblArr = new DblArr(new Array[Double](length))
  override def length: Int = unsafeArray.length
  override def apply(index: Int): Double = unsafeArray(index)
  override def setElemUnsafe(i: Int, newElem: Double): Unit = unsafeArray(i) = newElem
  def unsafeArrayCopy(operand: Array[Double], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Double => String = _.toString

  @targetName("appendArr") def ++ (op: DblArr): DblArr =
  { val newArray = new Array[Double](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new DblArr(newArray)
  }

  override def drop(n: Int): DblArr = ???

  /** Reverses the order of the elements of this sequence. */
  override def reverse: DblArr = ???

  /** append. appends element to this [[Arr]]. */
  @targetName("append") override def +%(operand: Double): DblArr = ???
}

/** Companion object for the Dbls Array based class for Doubles, contains a repeat parameter factory method. */
object DblArr
{ def apply(input: Double*): DblArr = new DblArr(input.toArray)

  implicit val eqImplicit: EqT[DblArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }

  /** Implicit method for creating [[IntArr]] instances. */
  implicit val unshowEv: Unshow[DblArr] = new Unshow[DblArr]
  { override def typeStr: String = "Seq" + "Dbl"

    override def fromExpr(expr: Expr): EMon[DblArr] = expr match
    { case _: EmptyExprToken => Good(DblArr())

      case AlphaBracketExpr(id1,
      RArr2(BracketedStatements(RArr1(_), brs1, _, _),
      BracketedStatements(sts, brs2, _, _))) if (id1.srcStr == "Seq") && brs1 == SquareBraces && brs2 == Parenthesis =>
        sts.eMapLike(s => Unshow.doubleEv.fromExpr(s.expr))(DblArrBuilder)

      case AlphaBracketExpr(id1, RArr1(BracketedStatements(sts, brs, _, _))) if (id1.srcStr == "Seq") && brs == Parenthesis =>
        sts.eMapLike(s => Unshow.doubleEv.fromExpr(s.expr))(DblArrBuilder)

      case e => bad1(expr, expr.toString + " unknown Expression for Seq")
    }
  }
}

object DblArrBuilder extends ArrMapBuilder[Double, DblArr] with ArrFlatBuilder[DblArr]
{ type BuffT = DblBuff
  override def uninitialised(length: Int): DblArr = new DblArr(new Array[Double](length))
  override def indexSet(seqLike: DblArr, index: Int, elem: Double): Unit = seqLike.unsafeArray(index) = elem
  override def newBuff(length: Int = 4): DblBuff = new DblBuff(new ArrayBuffer[Double](length))
  override def buffGrow(buff: DblBuff, newElem: Double): Unit = buff.unsafeBuffer.append(newElem)
  override def buffToSeqLike(buff: DblBuff): DblArr = new DblArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: DblBuff, arr: DblArr): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
}

/** Compile time wrapped Buff class for [[Double]]s, used to build [[DblArr]]. */
class DblBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Buff[Double]
{ override type ThisT = DblBuff
  override def typeStr: String = "DblsBuff"
  override def apply(index: Int): Double = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def setElemUnsafe(i: Int, newElem: Double): Unit = unsafeBuffer(i) = newElem
  override def fElemStr: Double => String = _.toString
  override def grow(newElem: Double): Unit = unsafeBuffer.append(newElem)
}