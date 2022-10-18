/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for [[Any]]s. */
class AnyArr(val unsafeArray: Array[Any]) extends AnyVal with ArrCloneable[Any]
{ type ThisT = AnyArr

  /** Copy's the backing Array[[Any]] to a new Array[char]. End users should rarely have to use this method. */
  def unsafeArrayCopy(operand: Array[Any], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }

  override def typeStr: String = "Anys"
  override def unsafeSameSize(length: Int): AnyArr = new AnyArr(new Array[Any](length))
  override def length: Int = unsafeArray.length
  override def apply(index: Int): Any = unsafeArray(index)
  override def unsafeSetElem(i: Int, value: Any): Unit = unsafeArray(i) = value
  override def fElemStr: Any => String = _.toString

  def ++ (op: AnyArr): AnyArr =
  { val newArray = new Array[Any](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new AnyArr(newArray)
  }
}

object AnyArr
{ def apply(input: Any*): AnyArr = new AnyArr(input.toArray)

  implicit val EqImplicit: EqT[AnyArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object AnyArrBuild extends ArrBuilder[Any, AnyArr] with ArrFlatBuilder[AnyArr]
{ type BuffT = AnyBuff
  override def newArr(length: Int): AnyArr = new AnyArr(new Array[Any](length))
  override def arrSet(arr: AnyArr, index: Int, value: Any): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): AnyBuff = new AnyBuff(new ArrayBuffer[Any](length))
  override def buffGrow(buff: AnyBuff, value: Any): Unit = buff.unsafeBuffer.append(value)
  override def buffGrowArr(buff: AnyBuff, arr: AnyArr): Unit = buff.unsafeBuffer.addAll(arr.unsafeArray)
  override def buffToBB(buff: AnyBuff): AnyArr = new AnyArr(buff.unsafeBuffer.toArray)
}

class AnyBuff(val unsafeBuffer: ArrayBuffer[Any]) extends AnyVal with Sequ[Any]
{ override def typeStr: String = "AnysBuff"
  override def apply(index: Int): Any = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Any): Unit = unsafeBuffer(i) = value
  override def fElemStr: Any => String = _.toString

  /** The final type of this object. */
  override type ThisT = AnyBuff
}

object AnyArrHead
{ /** Extractor for the head of an Arr, immutable covariant Array based collection. The tail can be any length. */
  def unapply(arr: AnyArr): Option[Any] = ife(arr.nonEmpty, Some(arr(0)), None)
}

/** Extractor object for Arr[A] of length == 1. Arr[A] is an immutable covariant Array based collection. */
object AnyArr1
{ /** Extractor for [[AnyArr]] of length == 1. Arr[A] is an immutable covariant Array based collection. */
  def unapply(arr: AnyArr): Option[Any] = arr.length match
  { case 1 => Some(arr(0))
    case _ => None
  }
}