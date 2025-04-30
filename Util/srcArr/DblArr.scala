/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer, pParse.*

/** An immutable Array based class for [[Double]]s. Note the convention that for final classes The "Arr" part of the name is placed second, as opposed
 * to for example the non instantiable [[ArrDbl1]] and [[ArrDbl2]] traits. */
class DblArr(val arrayUnsafe: Array[Double]) extends AnyVal, ArrNoParam[Double]
{ type ThisT = DblArr
  override def typeStr: String = "Doubles"
  override def unsafeSameSize(length: Int): DblArr = new DblArr(new Array[Double](length))
  override def apply(index: Int): Double = arrayUnsafe(index)
  override def elem(index: Int): Double = arrayUnsafe(index)
  override def length: Int = arrayUnsafe.length
  override def numElems: Int = arrayUnsafe.length
  override def setElemUnsafe(index: Int, newElem: Double): Unit = arrayUnsafe(index) = newElem
  def unsafeArrayCopy(operand: Array[Double], offset: Int, copyLength: Int): Unit = { arrayUnsafe.copyToArray(arrayUnsafe, offset, copyLength); () }
  override def fElemStr: Double => String = _.toString

  @targetName("append") def ++ (op: DblArr): DblArr =
  { val newArray = new Array[Double](length + op.length)
    arrayUnsafe.copyToArray(newArray)
    op.arrayUnsafe.copyToArray(newArray, length)
    new DblArr(newArray)
  }

  override def drop(n: Int): DblArr = ???

  /** Reverses the order of the elements of this sequence. */
  override def reverse: DblArr = ???

  /** append. appends element to this [[Arr]]. */
  @targetName("appendElem") override def +%(operand: Double): DblArr = ???
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

    override def fromExpr(expr: Expr): ExcMon[DblArr] = expr match
    { case _: EmptyExprToken => Succ(DblArr())

      case AlphaBracketExpr(id1, RArr2(BracketedStructure(RArr1(_), brs1, _, _), BracketedStructure(sts, brs2, _, _)))
        if (id1.srcStr == "Seq") && brs1 == SquareBraces && brs2 == Parentheses =>  sts.mapErrBi(s => Unshow.doubleEv.fromExpr(s.expr))(DblArrBuilder)

      case AlphaBracketExpr(id1, RArr1(BracketedStructure(sts, brs, _, _))) if (id1.srcStr == "Seq") && brs == Parentheses =>
        sts.mapErrBi(s => Unshow.doubleEv.fromExpr(s.expr))(DblArrBuilder)

      case e => expr.failExc(expr.toString + " unknown Expression for Seq")
    }
  }
}

object DblArrBuilder extends BuilderArrMap[Double, DblArr], BuilderArrFlat[DblArr]
{ type BuffT = BuffDbl
  override def uninitialised(length: Int): DblArr = new DblArr(new Array[Double](length))
  override def indexSet(seqLike: DblArr, index: Int, newElem: Double): Unit = seqLike.arrayUnsafe(index) = newElem
  override def newBuff(length: Int = 4): BuffDbl = new BuffDbl(new ArrayBuffer[Double](length))
  override def buffGrow(buff: BuffDbl, newElem: Double): Unit = buff.bufferUnsafe.append(newElem)
  override def buffToSeqLike(buff: BuffDbl): DblArr = new DblArr(buff.bufferUnsafe.toArray)
  override def buffGrowArr(buff: BuffDbl, arr: DblArr): Unit = arr.arrayUnsafe.foreach(el => buff.bufferUnsafe.append(el))
}

/** Compile time wrapped Buff class for [[Double]]s, used to build [[DblArr]]. */
class BuffDbl(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal, Buff[Double]
{ override type ThisT = BuffDbl
  override def typeStr: String = "DblsBuff"
  override def apply(index: Int): Double = bufferUnsafe(index)
  override def elem(index: Int): Double = bufferUnsafe(index)
  override def length: Int = bufferUnsafe.length
  override def numElems: Int = bufferUnsafe.length
  override def setElemUnsafe(index: Int, newElem: Double): Unit = bufferUnsafe(index) = newElem
  override def fElemStr: Double => String = _.toString
  override def grow(newElem: Double): Unit = bufferUnsafe.append(newElem)
  override def mutateElemUnsafe(index: Int, f: Double => Double): Unit = { bufferUnsafe(index) = f(bufferUnsafe(index)) }
}