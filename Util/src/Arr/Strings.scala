/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for Strings. */
class Strings(val unsafeArray: Array[String]) extends AnyVal with SeqImut[String]
{ override type ThisT = Strings
  override def typeStr: String = "Strings"
  override def unsafeSameSize(length: Int): Strings = new Strings(new Array[String](length))
  override def unsafeSetElem(i: Int, value: String): Unit = unsafeArray(i) = value
  override def fElemStr: String => String = s => s
  override def indexData(index: Int): String = unsafeArray(index)
  override def dataLength: Int = unsafeArray.length
  override def length: Int = unsafeArray.length

  /** Make 1 string with separator from this collection of strings. */
  def mkStr(separator: String): String = if(empty) ""
  else {
    var acc = head
    tailForeach{ s => acc += separator + s }
    acc
  }
}

/** Companion object of ArrStrings class contains repeat parameter apply factor method. */
object Strings
{ /** Repeat parameter apply factor method. */
  def apply(input: String*): Strings = new Strings(input.toArray)

  implicit val eqImplicit: EqT[Strings] = (a1, a2) =>
    if(a1.dataLength != a2.dataLength) false
    else
    { var i = 0
      var acc = true
      while (i < a1.dataLength & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object StringsBuild extends ArrBuilder[String, Strings] with ArrFlatBuilder[Strings]
{ type BuffT = StringsBuff
  override def newArr(length: Int): Strings = new Strings(new Array[String](length))
  override def arrSet(arr: Strings, index: Int, value: String): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): StringsBuff = new StringsBuff(new ArrayBuffer[String](length))
  override def buffGrow(buff: StringsBuff, value: String): Unit = buff.unsafeBuffer.append(value)
  override def buffGrowArr(buff: StringsBuff, arr: Strings): Unit = buff.unsafeBuffer.addAll(arr.unsafeArray)
  override def buffToBB(buff: StringsBuff): Strings = new Strings(buff.unsafeBuffer.toArray)
}

class StringsBuff(val unsafeBuffer: ArrayBuffer[String]) extends AnyVal with SeqGen[String]
{ override def typeStr: String = "Stringsbuff"
  override def indexData(index: Int): String = unsafeBuffer(index)
  override def dataLength: Int = unsafeBuffer.length
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: String): Unit = unsafeBuffer(i) = value
  override def fElemStr: String => String = s => s
}