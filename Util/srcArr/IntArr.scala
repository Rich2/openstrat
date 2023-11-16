/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, pParse._

/** Immutable efficient [[Array]][[Int]] backed class for [[Int]]s. There are no concat methods, as Ints has no type parameter and can not be
 *  widened. */
final class IntArr(val unsafeArray: Array[Int]) extends AnyVal with ArrNoParam[Int]
{ type ThisT = IntArr
  override def typeStr: String = "Ints"

  override def unsafeSameSize(length: Int): IntArr = new IntArr(new Array[Int](length))
  override def length: Int = unsafeArray.length
  override def apply(index: Int): Int = unsafeArray(index)
  override def setElemUnsafe(i: Int, newElem: Int): Unit = unsafeArray(i) = newElem
  def unsafeArrayCopy(operand: Array[Int], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Int => String = _.toString
  override def reverse: IntArr = ???

  /** appendArr. Apends the operand [[IntArr]] to this [[IntArr]]. */
  @targetName("appendArr") override def ++(op: IntArr): IntArr = appendInts(op)

  /** append. Appends operand [[Int]] to this [[IntArr]]. */
  @targetName("append") override def +%(operand: Int): IntArr =
  { val newArray = new Array[Int](length + 1)
    unsafeArray.copyToArray(newArray)
    newArray(length) = operand
    new IntArr(newArray)
  }

  /** Functionally appends the operand Ints. Aliased by the ++ operator. */
  def appendInts(op: IntArr): IntArr =
  { val newArray = new Array[Int](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new IntArr(newArray)
  }

  override def drop(n: Int): IntArr =
  { val nn = n.max0
    val newArray = new Array[Int]((length - nn).max0)
    iUntilForeach(length - nn) { i => newArray(i) = unsafeArray(i + nn) }
    new IntArr(newArray)
  }

  /** Alias for prepend. Functionally appends the operand Int. */
  @inline def +:(op: Int): IntArr = prepend(op)
  /** Functionally prepends the operand [[Int]]. */
  def prepend(op: Int): IntArr =
  { val newArray = new Array[Int](length + 1)
    newArray(0) = op
    unsafeArray.copyToArray(newArray, 1)
    new IntArr(newArray)
  }

  /** Removes the Int element at the given index, will throw exception if out of range. */
  def removeIndex(index: Int): IntArr =
  { val newArray = new Array[Int](length - 1)
    iUntilForeach(index){i => newArray(i) = apply(i) }
    iUntilForeach(index + 1, length){i => newArray(i - 1) = apply(i) }
    new IntArr(newArray)
  }

  def take(n: Int): IntArr =
    if (n >= length) this
    else
    { val newArray = new Array[Int](n)
      unsafeArray.copyToArray(newArray)
      new IntArr(newArray)
    }
}

/** Companion object for the [[IntArr]] claas an immutable efficient [[Array]] backed sequence for class [[Int]]s. Contains apply factory method and
 * implicit type class instances. */
object IntArr
{ def apply(input: Int*): IntArr = new IntArr(input.toArray)

  implicit val eqImplicit: EqT[IntArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }

  implicit val showEv: ShowSequ[Int, IntArr] = ShowSequ[Int, IntArr]()

  implicit val unshowEv: Unshow[IntArr] = new Unshow[IntArr]
  { override def typeStr: String = "Seq"

    override def fromExpr(expr: Expr): EMon[IntArr] = expr match
    { case _: EmptyExprToken => Good(IntArr())

      case AlphaBracketExpr(id1,
      RArr2(BracketedStructure(RArr1(_), brs1, _, _),
      BracketedStructure(sts, brs2, _, _))) if (id1.srcStr == "Seq") && brs1 == SquareBraces && brs2 == Parentheses =>
        sts.mapEMon(s => Unshow.intEv.fromExpr(s.expr))(IntArrBuilder)

      case AlphaBracketExpr(id1, RArr1(BracketedStructure(sts, brs, _, _))) if (id1.srcStr == "Seq") && brs == Parentheses =>
        sts.mapEMon(s => Unshow.intEv.fromExpr(s.expr))(IntArrBuilder)

      case e => bad1(expr, expr.toString + " unknown Expression for Seq")
    }
  }
}

/** Builder object for [[IntArr]]. */
object IntArrBuilder extends BuilderArrMap[Int, IntArr] with BuilderArrFlat[IntArr]
{ type BuffT = IntBuff
  override def uninitialised(length: Int): IntArr = new IntArr(new Array[Int](length))
  override def indexSet(seqLike: IntArr, index: Int, elem: Int): Unit = seqLike.unsafeArray(index) = elem
  override def newBuff(length: Int = 4): IntBuff = new IntBuff(new ArrayBuffer[Int](length))
  override def buffGrow(buff: IntBuff, newElem: Int): Unit = buff.unsafeBuffer.append(newElem)
  override def buffToSeqLike(buff: IntBuff): IntArr = new IntArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: IntBuff, arr: IntArr): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
}

/** ArrayBuffer class for [[IntArr]]. End users should rarely have need to use this class */
class IntBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with BuffSequ[Int]
{ override type ThisT = IntBuff
  override def typeStr: String = "IntBuff"
  override def apply(index: Int): Int = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def setElemUnsafe(i: Int, newElem: Int): Unit = unsafeBuffer(i) = newElem
  override def fElemStr: Int => String = _.toString
  def grow(newElem: Int): Unit = unsafeBuffer.append(newElem)
  def growArray(operand: Array[Int]): Unit = unsafeBuffer.appendAll(operand)
  def toInts: IntArr = new IntArr(unsafeBuffer.toArray)
}

object IntBuff
{ /** apply factory method for [[IntBuff]] class. */
  def apply(startSize: Int = 4): IntBuff = new IntBuff(new ArrayBuffer[Int](startSize))
}