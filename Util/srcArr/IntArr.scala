/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer, pParse.*

/** Immutable efficient [[Array]][[Int]] backed class for [[Int]]s. There are no concat methods, as Ints has no type parameter and can not be widened. */
final class IntArr(val arrayUnsafe: Array[Int]) extends AnyVal with ArrNoParam[Int]
{ type ThisT = IntArr
  override def typeStr: String = "IntArr"
  override def unsafeSameSize(length: Int): IntArr = new IntArr(new Array[Int](length))
  override def apply(index: Int): Int = arrayUnsafe(index)
  override def elem(index: Int): Int = arrayUnsafe(index)
  override def length: Int = arrayUnsafe.length
  override def numElems: Int = arrayUnsafe.length
  override def setElemUnsafe(index: Int, newElem: Int): Unit = arrayUnsafe(index) = newElem
  override def mutateElemUnsafe(index: Int, f: Int => Int): Unit = arrayUnsafe(index) = f(apply(index))
  def unsafeArrayCopy(operand: Array[Int], offset: Int, copyLength: Int): Unit = { arrayUnsafe.copyToArray(arrayUnsafe, offset, copyLength); () }
  override def fElemStr: Int => String = _.toString
  override def reverse: IntArr = ???

  /** appendArr. Appends the operand [[IntArr]] to this [[IntArr]]. */
  @targetName("append") override def ++(op: IntArr): IntArr = appendInts(op)

  /** append. Appends operand [[Int]] to this [[IntArr]]. */
  @targetName("appendElem") override def +%(operand: Int): IntArr =
  { val newArray = new Array[Int](length + 1)
    arrayUnsafe.copyToArray(newArray)
    newArray(length) = operand
    new IntArr(newArray)
  }

  /** Functionally appends the operand Ints. Aliased by the ++ operator. */
  def appendInts(op: IntArr): IntArr =
  { val newArray = new Array[Int](length + op.length)
    arrayUnsafe.copyToArray(newArray)
    op.arrayUnsafe.copyToArray(newArray, length)
    new IntArr(newArray)
  }

  override def drop(n: Int): IntArr =
  { val nn = n.max0
    val newArray = new Array[Int]((length - nn).max0)
    iUntilForeach(length - nn) { i => newArray(i) = arrayUnsafe(i + nn) }
    new IntArr(newArray)
  }

  /** Functionally prepends the operand [[Int]] returning a new object. Note the operators %: and +% are used rather than the conventional +: and :+ to ensure
   * that prepend takes precedence over append. */
  @inline @targetName("prepend") def %:(op: Int): IntArr =
  { val newArray = new Array[Int](length + 1)
    newArray(0) = op
    arrayUnsafe.copyToArray(newArray, 1)
    new IntArr(newArray)
  }

  /** Removes the Int element at the given index, will throw exception if out of range. */
  def removeIndex(index: Int): IntArr =
  { val newArray = new Array[Int](length - 1)
    iUntilForeach(index){i => newArray(i) = apply(i) }
    iUntilForeach(index + 1, length){i => newArray(i - 1) = apply(i) }
    new IntArr(newArray)
  }

  def take(n: Int): IntArr = if (n >= length) this else
  { val newArray = new Array[Int](n)
      arrayUnsafe.copyToArray(newArray)
      new IntArr(newArray)
  }

  /** Returns the index of the max value. */
  def indexOfMax: Int =
  { var index = -1
    var i = 0
    var acc = -1
    while(i < length)
    { val newVal = elem(i)
      if(newVal > acc)
      { index = i
        acc = newVal
      }
      i += 1
    }
    index
  }
}

/** Companion object for the [[IntArr]] claas an immutable efficient [[Array]] backed sequence for class [[Int]]s. Contains apply factory method and implicit
 * type class instances. */
object IntArr
{ def apply(input: Int*): IntArr = new IntArr(input.toArray)

  def uninitialised(length: Int): IntArr = new IntArr(new Array[Int](length))

  implicit val eqImplicit: EqT[IntArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }

  /** [[Show]] type class instance / evidence for [[IntArr]]. */
  implicit val showEv: ShowSequ[Int, IntArr] = ShowSequ[Int, IntArr]()

  /** [[Unshow]] type class instance / evidence for [[IntArr]]. */
  implicit val unshowEv: Unshow[IntArr] = new Unshow[IntArr]
  { override def typeStr: String = "Seq"

    override def fromExpr(expr: Expr): ExcMon[IntArr] = expr match
    { case _: EmptyExprToken => Succ(IntArr())

      case AlphaBracketExpr(id1, RArr2(BracketedStructure(RArr1(_), brs1, _, _),
      BracketedStructure(sts, brs2, _, _))) if (id1.srcStr == "Seq") && brs1 == SquareBraces && brs2 == Parentheses =>
        sts.mapErrBi(s => Unshow.intEv.fromExpr(s.expr))(IntArrBuilder)

      case AlphaBracketExpr(id1, RArr1(BracketedStructure(sts, brs, _, _))) if (id1.srcStr == "Seq") && brs == Parentheses =>
        sts.mapErrBi(s => Unshow.intEv.fromExpr(s.expr))(IntArrBuilder)

      case e => expr.failExc(expr.toString + " unknown Expression for Seq")
    }
  }
}

/** Builder object for [[IntArr]]. */
object IntArrBuilder extends BuilderMapArr[Int, IntArr] with BuilderFlatArr[IntArr]
{ type BuffT = IntBuff
  override def uninitialised(length: Int): IntArr = new IntArr(new Array[Int](length))
  override def indexSet(seqLike: IntArr, index: Int, newElem: Int): Unit = seqLike.arrayUnsafe(index) = newElem
  override def newBuff(length: Int = 4): IntBuff = new IntBuff(new ArrayBuffer[Int](length))
  override def buffGrow(buff: IntBuff, newElem: Int): Unit = buff.bufferUnsafe.append(newElem)
  override def buffToSeqLike(buff: IntBuff): IntArr = new IntArr(buff.bufferUnsafe.toArray)
  override def buffGrowArr(buff: IntBuff, arr: IntArr): Unit = arr.arrayUnsafe.foreach(el => buff.bufferUnsafe.append(el))
}

/** ArrayBuffer class for [[IntArr]]. End users should rarely have need to use this class */
class IntBuff(val bufferUnsafe: ArrayBuffer[Int]) extends AnyVal, Buff[Int]
{ override type ThisT = IntBuff
  override def typeStr: String = "IntBuff"
  override def apply(index: Int): Int = bufferUnsafe(index)
  override def elem(index: Int): Int = bufferUnsafe(index)
  override def length: Int = bufferUnsafe.length
  override def numElems: Int = bufferUnsafe.length
  override def setElemUnsafe(index: Int, newElem: Int): Unit = bufferUnsafe(index) = newElem
  override def fElemStr: Int => String = _.toString
  def grow(newElem: Int): Unit = bufferUnsafe.append(newElem)
  def growArray(operand: Array[Int]): Unit = bufferUnsafe.appendAll(operand)
  def toInts: IntArr = new IntArr(bufferUnsafe.toArray)
}

object IntBuff
{ /** apply factory method for [[IntBuff]] class. */
  def apply(startSize: Int = 4): IntBuff = new IntBuff(new ArrayBuffer[Int](startSize))
}