/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** Immutable Array based class for [[String]]s. */
final class StrArr(val unsafeArray: Array[String]) extends AnyVal with ArrNoParam[String]
{ override type ThisT = StrArr
  override def typeStr: String = "Strings"
  override def unsafeSameSize(length: Int): StrArr = new StrArr(new Array[String](length))
  override def unsafeSetElem(i: Int, newElem: String): Unit = unsafeArray(i) = newElem
  override def fElemStr: String => String = s => s
  override def apply(index: Int): String = unsafeArray(index)
  override def length: Int = unsafeArray.length

  /** Make 1 string with separator from this collection of strings. */
  def mkStr(separator: String): String = if(empty) ""
  else {
    var acc = head
    tailForeach{ s => acc += separator + s }
    acc
  }

  override def reverse: StrArr =
  { val newArray = new Array[String](length)
    iUntilForeach(0, length) { i => newArray(i) = unsafeArray(length - 1 - i) }
    new StrArr(newArray)
  }

  override def drop(n: Int): StrArr =
  { val nn = n.max0
    val newArray = new Array[String]((length - nn).max0)
    iUntilForeach(length - nn) { i => newArray(i) = unsafeArray(i + nn) }
    new StrArr(newArray)
  }

  @targetName("appendArr") override def ++(operand: StrArr): StrArr =
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
    new StrArr(newArray)
  }

  /** append. Functionally appends the operand [[String]]. */
  @targetName("append") @inline def +%(operand: String): StrArr =
  { val newArray = new Array[String](length + 1)
    unsafeArray.copyToArray(newArray)
    newArray(length) = operand
    new StrArr(newArray)
  }

  def appendOption(optElem: Option[String]): StrArr =
    optElem.fld(this, this +% _)

  def appendsOption(optElem: Option[StrArr]): StrArr =
    optElem.fld(this, this ++ _)

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
object StrArr
{ /** Repeat parameter apply factor method. */
  def apply(input: String*): StrArr = new StrArr(input.toArray)

  implicit val eqImplicit: EqT[StrArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object StringArrBuilder extends ArrMapBuilder[String, StrArr] with ArrFlatBuilder[StrArr]
{ type BuffT = StringBuff
  override def uninitialised(length: Int): StrArr = new StrArr(new Array[String](length))
  override def indexSet(seqLike: StrArr, index: Int, elem: String): Unit = seqLike.unsafeArray(index) = elem
  override def newBuff(length: Int = 4): StringBuff = new StringBuff(new ArrayBuffer[String](length))
  override def buffGrow(buff: StringBuff, newElem: String): Unit = buff.unsafeBuffer.append(newElem)
  override def buffToSeqLike(buff: StringBuff): StrArr = new StrArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: StringBuff, arr: StrArr): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
}

class StringBuff(val unsafeBuffer: ArrayBuffer[String]) extends AnyVal with Buff[String]
{   override type ThisT = StringBuff

  override def typeStr: String = "Stringsbuff"
  override def apply(index: Int): String = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, newElem: String): Unit = unsafeBuffer(i) = newElem
  override def fElemStr: String => String = s => s
  override def grow(newElem: String): Unit = unsafeBuffer.append(newElem)
}

object StringBuff
{ def apply(startSize: Int = 4): StringBuff = new StringBuff(new ArrayBuffer[String](startSize))
}