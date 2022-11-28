/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, reflect.ClassTag

final class AnyPairElem[A1, A2](val a1: A1, val a2: A2) extends PairElem[A1, A2]

final class AnyPairArr[A1, A2](val a1Array: Array[A1], val a2Array: Array[A2]) extends PairArr[A1, RArr[A1], A2, AnyPairElem[A1, A2]]
{ override type ThisT = AnyPairArr[A1, A2]
  override def a1Arr: RArr[A1] = new RArr[A1](a1Array)
  override def a1Index(index: Int): A1 = a1Array(index)
  override def unsafeSetA1(index: Int, value: A1): Unit = a1Array(index) = value
  override def apply(index: Int): AnyPairElem[A1, A2] = new AnyPairElem[A1, A2](a1Array(index), a2Array(index))

  /** Returns a copy of this [[PairArr]] where the A1 component is replaced for any pairs where the A2 value matches the given parameter. this method
   * treats the [[PairArr]] as a Scala [[Map]] class with the A2s as the keys and the A1s as the values. */
  override def replaceA1byA2(key: A2, newValue: A1): AnyPairArr[A1, A2] = ???

  /** Returns a new uninitialised [[PairArr]] of the same final type. */
  override def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): AnyPairArr[A1, A2] = ???

  @targetName("append") override def +%(operand: AnyPairElem[A1, A2])(implicit ct: ClassTag[A2]): AnyPairArr[A1, A2] = ???

  override def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): AnyPairArr[A1, A2] = ???



  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  override def unsafeSetElem(i: Int, value: AnyPairElem[A1, A2]): Unit = ???

  override def fElemStr: AnyPairElem[A1, A2] => String = ???

  /** String specifying the type of this object. */
  override def typeStr: String = ???
}