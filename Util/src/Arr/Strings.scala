/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

import scala.collection.mutable.ArrayBuffer

class Strings(val arrayUnsafe: Array[String]) extends AnyVal with ArrBase[String]
{ override type ThisT = Strings
  override def typeStr: String = "Strings"
  override def unsafeNew(length: Int): Strings = new Strings(new Array[String](length))
  override def unsafeSetElem(i: Int, value: String): Unit = arrayUnsafe(i) = value

  override def fElemStr: String => String = s => s

  override def apply(index: Int): String = arrayUnsafe(index)

  /** The number of elements in the collection. These collections use underlying mutable Arrays and ArrayBuffers. The length of the underlying
   * Array maybe longer by a multiple of this number. */
  override def elemsLen: Int = arrayUnsafe.length

  /** Make string with separator. */
  def mkStr(separator: String): String = if(empty) ""
  else {
    var acc = head
    foreachTail{ s => acc += separator + s }
    acc
  }
}

/** Companion object of ArrStrings class contains repeat parameter apply factor method. */
object Strings
{ /** Repeat parameter apply factor method. */
  def apply(input: String*): Strings = new Strings(input.toArray)
}

object StringsBuild extends ArrBuild[String, Strings] with ArrFlatBuild[Strings]
{ type BuffT = StringsBuff
  override def newArr(length: Int): Strings = new Strings(new Array[String](length))
  override def arrSet(arr: Strings, index: Int, value: String): Unit = arr.arrayUnsafe(index) = value
  override def newBuff(length: Int = 4): StringsBuff = new StringsBuff(new ArrayBuffer[String](length))
  override def buffGrow(buff: StringsBuff, value: String): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: StringsBuff, arr: Strings): Unit = buff.unsafeBuff.addAll(arr.arrayUnsafe)
  override def buffToArr(buff: StringsBuff): Strings = new Strings(buff.unsafeBuff.toArray)
}

class StringsBuff(val unsafeBuff: ArrayBuffer[String]) extends AnyVal with ArrayLike[String]
{ override def apply(index: Int): String = unsafeBuff(index)
  override def elemsLen: Int = unsafeBuff.length
}