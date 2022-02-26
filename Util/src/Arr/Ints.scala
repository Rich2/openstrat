/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable efficient [[Array]][[Int]] backed class for [[Int]]s. There are no concat methods, as Ints has no type parameter and can not be
 *  widened. */
final class Ints(val unsafeArray: Array[Int]) extends AnyVal with SeqImut[Int]
{ type ThisT = Ints

  /** Copy's the backing Array[[Int]] to a new Array[Int]. End users should rarely have to use this method. */
  override def unsafeSameSize(length: Int): Ints = new Ints(new Array[Int](length))

  override def typeStr: String = "Ints"
  override def dataLength: Int = unsafeArray.length
  override def length: Int = unsafeArray.length
  override def indexData(index: Int): Int = unsafeArray(index)
  override def unsafeSetElem(i: Int, value: Int): Unit = unsafeArray(i) = value
  def unsafeArrayCopy(operand: Array[Int], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Int => String = _.toString

  /** Alias for appendInts. Functionally appends the operand Ints. */
  @inline def ++ (op: Ints): Ints = appendInts(op)

  /** Functionally appends the operand Ints. Aliased by the ++ operator. */
  def appendInts(op: Ints): Ints =
  { val newArray = new Array[Int](dataLength + op.dataLength)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, dataLength)
    new Ints(newArray)
  }

  /** Alias for append. Functionally appends the operand Int. */
  @inline def :+(op: Int): Ints = append(op)
  /** Functionally appends the operand Int. This method by the :+ operator, rather than the +- operator alias used for append on Refs to avoid
   *  confusion with arithmetic operations. */
  def append(op: Int): Ints =
  { val newArray = new Array[Int](dataLength + 1)
    unsafeArray.copyToArray(newArray)
    newArray(dataLength) = op
    new Ints(newArray)
  }

  /** Alias for prepend. Functionally appends the operand Int. */
  @inline def +:(op: Int): Ints = prepend(op)
  /** Functionally prepends the operand Int. This alphanumeric method is not aliased with an operator to avoid confusion with numeric operators. */
  def prepend(op: Int): Ints =
  { val newArray = new Array[Int](dataLength + 1)
    newArray(0) = op
    unsafeArray.copyToArray(newArray, 1)
    new Ints(newArray)
  }
}

/** Companion object for the [[Ints]] claas an immutable efficient [[Array]] backed sequence for class [[Int]]s. Contains apply factory method and
 * implicit type class instances. */
object Ints
{ def apply(input: Int*): Ints = new Ints(input.toArray)

  implicit val showImplicit: ShowT[Ints] = DataGenShowT[Int, Ints](ShowT.intPersistEv)

  implicit val eqImplicit: EqT[Ints] = (a1, a2) =>
    if(a1.dataLength != a2.dataLength) false
    else
    { var i = 0
      var acc = true
      while (i < a1.dataLength & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

/** Builder object for [[Ints]]. */
object IntsBuild extends ArrBuilder[Int, Ints] with ArrFlatBuilder[Ints]
{ type BuffT = IntBuff
  override def newArr(length: Int): Ints = new Ints(new Array[Int](length))
  override def arrSet(arr: Ints, index: Int, value: Int): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): IntBuff = new IntBuff(new ArrayBuffer[Int](length))
  override def buffGrow(buff: IntBuff, value: Int): Unit = buff.unsafeBuffer.append(value)
  override def buffGrowArr(buff: IntBuff, arr: Ints): Unit = buff.unsafeBuffer.addAll(arr.unsafeArray)
  override def buffToBB(buff: IntBuff): Ints = new Ints(buff.unsafeBuffer.toArray)
}

/** ArrayBuffer class for [[Ints]]. End users should rarely have need to use this class */
class IntBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with SeqGen[Int]
{ override def typeStr: String = "IntBuff"
  override def indexData(index: Int): Int = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def dataLength: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Int): Unit = unsafeBuffer(i) = value
  override def fElemStr: Int => String = _.toString
  def grow(newElem: Int): Unit = unsafeBuffer.append(newElem)
  def growArray(operand: Array[Int]): Unit = unsafeBuffer.appendAll(operand)
  def toInts: Ints = new Ints(unsafeBuffer.toArray)
}

object IntBuff
{ def apply(startSize: Int = 4): IntBuff = new IntBuff(new ArrayBuffer[Int](startSize))
}