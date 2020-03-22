package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for Ints. There are no concat methods, as Ints has no type parameter and can not be widened. */
final class Ints(val array: Array[Int]) extends AnyVal with ArrValues[Int]
{ type ThisT = Ints
  override def unsafeNew(length: Int): Ints = new Ints(new Array[Int](length))
  override def length: Int = array.length
  override def apply(index: Int): Int = array(index)
  override def unsafeSetElem(i: Int, value: Int): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Int], offset: Int, copyLength: Int): Unit = { array.copyToArray(array, offset, copyLength); () }

  /** Alias for appendInts. Functionally appends the operand Ints. */
  @inline def ++ (op: Ints): Ints = appendInts(op)
  /** Functionally appends the operand Ints. Aliased by the ++ operator. */
  def appendInts(op: Ints): Ints =
  { val newArray = new Array[Int](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Ints(newArray)
  }

  /** Alias for append. Functionally appends the operand Int. */
  @inline def :+(op: Int): Ints = append(op)
  /** Functionally appends the operand Int. This method by the :+ operator, rather than the +- operator alias used for append on Refs to avoid
   *  confusion with arithmetic operations. */
  def append(op: Int): Ints =
  { val newArray = new Array[Int](length + 1)
    array.copyToArray(newArray)
    newArray(length) = op
    new Ints(newArray)
  }

  /** Alias for prepend. Functionally appends the operand Int. */
  @inline def +:(op: Int): Ints = prepend(op)
  /** Functionally prepends the operand Int. This alphanumeric method is not aliased with an operator to avoid confusion with numeric operators. */
  def prepend(op: Int): Ints =
  { val newArray = new Array[Int](length + 1)
    newArray(0) = op
    array.copyToArray(newArray, 1)
    new Ints(newArray)
  }
}

object Ints
{ def apply(input: Int*): Ints = new Ints(input.toArray)

  implicit val showImplicit: Show[Ints] = ArrayLikeShow[Int, Ints](Show.intPersistImplicit)

  implicit val EqImplicit: Eq[Ints] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var count = 0
      var acc = true
      var continue = true

      while (count < a1.length & continue)
      { if (a1(count) == a2(count)) count += 1
      else {acc = false; continue = false}
      }
      acc
    }
}

object IntsBuild extends ArrBuild[Int, Ints] with ArrFlatBuild[Ints]
{ type BuffT = IntBuff
  override def imutNew(length: Int): Ints = new Ints(new Array[Int](length))
  override def imutSet(arr: Ints, index: Int, value: Int): Unit = arr.array(index) = value
  override def buffNew(length: Int = 4): IntBuff = new IntBuff(new ArrayBuffer[Int](length))
  override def buffGrow(buff: IntBuff, value: Int): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: IntBuff, arr: Ints): Unit = buff.unsafeBuff.addAll(arr.array)
  override def buffToArr(buff: IntBuff): Ints = new Ints(buff.unsafeBuff.toArray)
}

class IntBuff(val unsafeBuff: ArrayBuffer[Int]) extends AnyVal with ArrayLike[Int]
{ override def apply(index: Int): Int = unsafeBuff(index)
  override def length: Int = unsafeBuff.length
}