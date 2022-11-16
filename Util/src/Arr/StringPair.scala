/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag

class StringPair[A2](val a1: String, val a2: A2) extends PairElem[String, A2]

class StringPairArr[A2](val a1Array: Array[String], val a2Array: Array[A2]) extends PairArr[String, StringArr, A2, StringPair[A2]]
{ override type ThisT = StringPairArr[A2]
  override def typeStr: String = "StringPairArr"
  override def a1Arr: StringArr = new StringArr(a1Array)
  override def a1Index(index: Int): String = a1Array(index)
  override def unsafeSetA1(index: Int, value: String): Unit = a1Array(index) = value

  override def replaceA1Value(key: A2, newValue: String): StringPairArr[A2] =
  { val newA1s = new Array[String](length)
    a1Array.copyToArray(newA1s)
    var i = 0
    while (i < length) {
      if (key == a2Index(i)) a1Array(i) = newValue; i += 1
    }
    new StringPairArr[A2](newA1s, a2Array)
  }

  override def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): StringPairArr[A2] =
    new StringPairArr[A2](new Array[String](length), new Array[A2](length))

  override def apply(index: Int): StringPair[A2] = new StringPair[A2](a1Array(index), a2Array(index))
  override def unsafeSetElem(i: Int, value: StringPair[A2]): Unit = { a1Array(i) = value.a1; a2Array(i) = value.a2 }

  override def fElemStr: StringPair[A2] => String = _.toString
}