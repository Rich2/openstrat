/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

class ArrStrings(val arrayUnsafe: Array[String]) extends AnyVal with ArrBase[String]
{ override type ThisT = ArrStrings
  override def typeStr: String = "Strings"
  override def unsafeNew(length: Int): ArrStrings = new ArrStrings(new Array[String](length))
  override def unsafeSetElem(i: Int, value: String): Unit = arrayUnsafe(i) = value

  override def fElemStr: String => String = s => s

  override def apply(index: Int): String = arrayUnsafe(index)

  /** The number of elements in the collection. These collections use underlying mutable Arrays and ArrayBuffers. The length of the underlying
   * Array maybe longer by a multiple of this number. */
  override def elemsLen: Int = arrayUnsafe.length
}

/** Companion object of ArrStrings class contains repeat parameter apply factor method. */
object ArrStrings
{ /** Repeat parameter apply factor method. */
  def apply(input: String*): ArrStrings = new ArrStrings(input.toArray)
}