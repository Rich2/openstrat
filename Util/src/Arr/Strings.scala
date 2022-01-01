/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for Strings. */
class Strings(val arrayUnsafe: Array[String]) extends AnyVal with SeqImut[String]
{ override type ThisT = Strings
  override def typeStr: String = "Strings"
  override def unsafeSameSize(length: Int): Strings = new Strings(new Array[String](length))
  override def unsafeSetElem(i: Int, value: String): Unit = arrayUnsafe(i) = value
  override def fElemStr: String => String = s => s
  override def indexData(index: Int): String = arrayUnsafe(index)
  override def elemsNum: Int = arrayUnsafe.length

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
    if(a1.elemsNum != a2.elemsNum) false
    else
    { var i = 0
      var acc = true
      while (i < a1.elemsNum & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object StringsBuild extends ArrBuilder[String, Strings] with ArrFlatBuilder[Strings]
{ type BuffT = StringsBuff
  override def newArr(length: Int): Strings = new Strings(new Array[String](length))
  override def arrSet(arr: Strings, index: Int, value: String): Unit = arr.arrayUnsafe(index) = value
  override def newBuff(length: Int = 4): StringsBuff = new StringsBuff(new ArrayBuffer[String](length))
  override def buffGrow(buff: StringsBuff, value: String): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: StringsBuff, arr: Strings): Unit = buff.unsafeBuff.addAll(arr.arrayUnsafe)
  override def buffToBB(buff: StringsBuff): Strings = new Strings(buff.unsafeBuff.toArray)
}

class StringsBuff(val unsafeBuff: ArrayBuffer[String]) extends AnyVal with SeqGen[String]
{ override def typeStr: String = "Stringsbuff"
  override def indexData(index: Int): String = unsafeBuff(index)
  override def elemsNum: Int = unsafeBuff.length
  override def unsafeSetElem(i: Int, value: String): Unit = unsafeBuff(i) = value
  override def fElemStr: String => String = s => s
}