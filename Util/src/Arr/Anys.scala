/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for [[Any]]s. */
class Anys(val unsafeArray: Array[Any]) extends AnyVal with SeqImut[Any]
{ type ThisT = Anys

  /** Copy's the backing Array[[Any]] to a new Array[char]. End users should rarely have to use this method. */
  def unsafeArrayCopy(operand: Array[Any], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }

  override def typeStr: String = "Anys"
  override def unsafeSameSize(length: Int): Anys = new Anys(new Array[Any](length))
  override def dataLength: Int = unsafeArray.length
  override def length: Int = unsafeArray.length
  override def indexData(index: Int): Any = unsafeArray(index)
  override def unsafeSetElem(i: Int, value: Any): Unit = unsafeArray(i) = value
  override def fElemStr: Any => String = _.toString

  def ++ (op: Anys): Anys =
  { val newArray = new Array[Any](dataLength + op.dataLength)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, dataLength)
    new Anys(newArray)
  }
}

object Anys
{ def apply(input: Any*): Anys = new Anys(input.toArray)

  implicit val EqImplicit: EqT[Anys] = (a1, a2) =>
    if(a1.dataLength != a2.dataLength) false
    else
    { var i = 0
      var acc = true
      while (i < a1.dataLength & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object AnysBuild extends ArrBuilder[Any, Anys] with ArrFlatBuilder[Anys]
{ type BuffT = AnysBuff
  override def newArr(length: Int): Anys = new Anys(new Array[Any](length))
  override def arrSet(arr: Anys, index: Int, value: Any): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): AnysBuff = new AnysBuff(new ArrayBuffer[Any](length))
  override def buffGrow(buff: AnysBuff, value: Any): Unit = buff.unsafeBuffer.append(value)
  override def buffGrowArr(buff: AnysBuff, arr: Anys): Unit = buff.unsafeBuffer.addAll(arr.unsafeArray)
  override def buffToBB(buff: AnysBuff): Anys = new Anys(buff.unsafeBuffer.toArray)
}

class AnysBuff(val unsafeBuffer: ArrayBuffer[Any]) extends AnyVal with SeqGen[Any]
{ override def typeStr: String = "AnysBuff"
  override def indexData(index: Int): Any = unsafeBuffer(index)
  override def dataLength: Int = unsafeBuffer.length
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Any): Unit = unsafeBuffer(i) = value
  override def fElemStr: Any => String = _.toString
}

object AnysHead
{ /** Extractor for the head of an Arr, immutable covariant Array based collection. The tail can be any length. */
  def unapply(arr: Anys): Option[Any] = ife(arr.nonEmpty, Some(arr(0)), None)
}