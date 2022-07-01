/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for Strings. */
class StringArr(val unsafeArray: Array[String]) extends AnyVal with SeqImut[String]
{ override type ThisT = StringArr
  override def typeStr: String = "Strings"
  override def unsafeSameSize(length: Int): StringArr = new StringArr(new Array[String](length))
  override def unsafeSetElem(i: Int, value: String): Unit = unsafeArray(i) = value
  override def fElemStr: String => String = s => s
  override def sdIndex(index: Int): String = unsafeArray(index)
  override def sdLength: Int = unsafeArray.length
  override def length: Int = unsafeArray.length

  /** Make 1 string with separator from this collection of strings. */
  def mkStr(separator: String): String = if(empty) ""
  else {
    var acc = head
    tailForeach{ s => acc += separator + s }
    acc
  }

  /** Append. */
  def ++ (operand: StringArr): StringArr =
  { val newArray: Array[String] = new Array[String](length + operand.length)
    var i = 0
    while (i < length) {
      newArray(i) = unsafeArray(i)
      i += 1
    }
    i = 0
    while (i < operand.length) {
      newArray(i + length) = unsafeArray(i)
      i += 1
    }
    new StringArr(newArray)
  }

  /** Alias for append. Functionally appends the operand [[String]]. */
  @inline def :+(op: String): StringArr = append(op)
  /** Functionally appends the operand [[String]]. This method by the :+ operator, rather than the +- operator alias used for append on [[Arr]] to
   *  avoid confusion with arithmetic operations. */
  def append(op: String): StringArr =
  { val newArray = new Array[String](sdLength + 1)
    unsafeArray.copyToArray(newArray)
    newArray(sdLength) = op
    new StringArr(newArray)
  }

  /** Finds the index of the first [[String]] element that fulfills the predicate parameter or returns -1. */
  def findIndex(f: String => Boolean): Int =
  {
    def loop(index: Int): Int = index match
    { case i if i == length => -1
      case i if f(apply(i)) => i
      case i => loop(i + 1)
    }
    loop(0)
  }
}

/** Companion object of ArrStrings class contains repeat parameter apply factor method. */
object StringArr
{ /** Repeat parameter apply factor method. */
  def apply(input: String*): StringArr = new StringArr(input.toArray)

  implicit val eqImplicit: EqT[StringArr] = (a1, a2) =>
    if(a1.sdLength != a2.sdLength) false
    else
    { var i = 0
      var acc = true
      while (i < a1.sdLength & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object StringsBuild extends ArrBuilder[String, StringArr] with ArrFlatBuilder[StringArr]
{ type BuffT = StringsBuff
  override def newArr(length: Int): StringArr = new StringArr(new Array[String](length))
  override def arrSet(arr: StringArr, index: Int, value: String): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): StringsBuff = new StringsBuff(new ArrayBuffer[String](length))
  override def buffGrow(buff: StringsBuff, value: String): Unit = buff.unsafeBuffer.append(value)
  override def buffGrowArr(buff: StringsBuff, arr: StringArr): Unit = buff.unsafeBuffer.addAll(arr.unsafeArray)
  override def buffToBB(buff: StringsBuff): StringArr = new StringArr(buff.unsafeBuffer.toArray)
}

class StringsBuff(val unsafeBuffer: ArrayBuffer[String]) extends AnyVal with SeqGen[String]
{ override def typeStr: String = "Stringsbuff"
  override def sdIndex(index: Int): String = unsafeBuffer(index)
  override def sdLength: Int = unsafeBuffer.length
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: String): Unit = unsafeBuffer(i) = value
  override def fElemStr: String => String = s => s
}

object StringsBuff
{ def apply(startSize: Int = 4): StringsBuff = new StringsBuff(new ArrayBuffer[String](startSize))
}