/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable efficient [[Array]][[Int]] backed class for [[Int]]s. There are no concat methods, as Ints has no type parameter and can not be
 *  widened. */
final class IntArr(val unsafeArray: Array[Int]) extends AnyVal with SeqImut[Int]
{ type ThisT = IntArr

  /** Copy's the backing Array[[Int]] to a new Array[Int]. End users should rarely have to use this method. */
  override def unsafeSameSize(length: Int): IntArr = new IntArr(new Array[Int](length))

  override def typeStr: String = "Ints"
  override def sdLength: Int = unsafeArray.length
  override def length: Int = unsafeArray.length
  override def sdIndex(index: Int): Int = unsafeArray(index)
  override def unsafeSetElem(i: Int, value: Int): Unit = unsafeArray(i) = value
  def unsafeArrayCopy(operand: Array[Int], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Int => String = _.toString

  /** Alias for appendInts. Functionally appends the operand Ints. */
  @inline def ++ (op: IntArr): IntArr = appendInts(op)

  /** Functionally appends the operand Ints. Aliased by the ++ operator. */
  def appendInts(op: IntArr): IntArr =
  { val newArray = new Array[Int](sdLength + op.sdLength)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, sdLength)
    new IntArr(newArray)
  }

  /** Alias for append. Functionally appends the operand Int. */
  @inline def :+(op: Int): IntArr = append(op)
  /** Functionally appends the operand Int. This method by the :+ operator, rather than the +- operator alias used for append on Refs to avoid
   *  confusion with arithmetic operations. */
  def append(op: Int): IntArr =
  { val newArray = new Array[Int](sdLength + 1)
    unsafeArray.copyToArray(newArray)
    newArray(sdLength) = op
    new IntArr(newArray)
  }

  /** Alias for prepend. Functionally appends the operand Int. */
  @inline def +:(op: Int): IntArr = prepend(op)
  /** Functionally prepends the operand [[Int]]. */
  def prepend(op: Int): IntArr =
  { val newArray = new Array[Int](sdLength + 1)
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

  /** Drops the given number of elements from the head of this sequence. If n is greater than the length returns an empty [[IntArr] sequence. */
  def drop(n: Int): IntArr =
  { val newArray = new Array[Int]((length - n).max0)
    iUntilForeach(n, length){i => newArray(i - n.min(length)) = apply(i) }
    new IntArr(newArray)
  }

  /** Drops the the head of this sequence. If the seqeunce is already empty returns an empty [[IntArr] sequence. */
  inline def drop1: IntArr = drop(1)

  def take(n: Int): IntArr = if (n >= length) this
  else {
    val newArray = new Array[Int](n)
    unsafeArray.copyToArray(newArray)
    new IntArr(newArray)
  }
}

/** Companion object for the [[IntArr]] claas an immutable efficient [[Array]] backed sequence for class [[Int]]s. Contains apply factory method and
 * implicit type class instances. */
object IntArr
{ def apply(input: Int*): IntArr = new IntArr(input.toArray)

  implicit val showImplicit: ShowT[IntArr] = DataGenShowT[Int, IntArr](ShowT.intPersistEv)

  implicit val eqImplicit: EqT[IntArr] = (a1, a2) =>
    if(a1.sdLength != a2.sdLength) false
    else
    { var i = 0
      var acc = true
      while (i < a1.sdLength & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

/** Builder object for [[IntArr]]. */
object IntsBuild extends ArrBuilder[Int, IntArr] with ArrFlatBuilder[IntArr]
{ type BuffT = IntBuff
  override def newArr(length: Int): IntArr = new IntArr(new Array[Int](length))
  override def arrSet(arr: IntArr, index: Int, value: Int): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): IntBuff = new IntBuff(new ArrayBuffer[Int](length))
  override def buffGrow(buff: IntBuff, value: Int): Unit = buff.unsafeBuffer.append(value)
  override def buffGrowArr(buff: IntBuff, arr: IntArr): Unit = buff.unsafeBuffer.addAll(arr.unsafeArray)
  override def buffToBB(buff: IntBuff): IntArr = new IntArr(buff.unsafeBuffer.toArray)
}

/** ArrayBuffer class for [[IntArr]]. End users should rarely have need to use this class */
class IntBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with Sequ[Int]
{ override def typeStr: String = "IntBuff"
  override def sdIndex(index: Int): Int = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def sdLength: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Int): Unit = unsafeBuffer(i) = value
  override def fElemStr: Int => String = _.toString
  def grow(newElem: Int): Unit = unsafeBuffer.append(newElem)
  def growArray(operand: Array[Int]): Unit = unsafeBuffer.appendAll(operand)
  def toInts: IntArr = new IntArr(unsafeBuffer.toArray)
}

object IntBuff
{ /** apply factory method for [[IntBuff]] class. */
  def apply(startSize: Int = 4): IntBuff = new IntBuff(new ArrayBuffer[Int](startSize))
}