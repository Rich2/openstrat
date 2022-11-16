/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, collection.immutable._, reflect.ClassTag

class StringPair[A2](val a1: String, val a2: A2) extends PairElem[String, A2]

class StringPairArr[A2](val a1Array: Array[String], val a2Array: Array[A2]) extends PairArr[String, StringArr, A2, StringPair[A2]]
{ override type ThisT = StringPairArr[A2]
  override def a1Arr: StringArr = new StringArr(a1Array)
  override def a1Index(index: Int): String = a1Array(index)
  override def unsafeSetA1(index: Int, value: String): Unit = a1Array(index) = value

  override def replaceA1Value(key: A2, newValue: String): StringPairArr[A2] = ???

  /** Returns a new uninitialised [[PairArr]] of the same final type. */
  override def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): StringPairArr[A2] = ???

  /** Accesses the defining sequence element by a 0 based index. */
  override def apply(index: Int): StringPair[A2] = ???

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  override def unsafeSetElem(i: Int, value: StringPair[A2]): Unit = ???

  override def fElemStr: StringPair[A2] => String = ???

  /** String specifying the type of this object. */
  override def typeStr: String = ???
}