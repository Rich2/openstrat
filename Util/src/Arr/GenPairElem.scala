/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, reflect.ClassTag

final class GenPairElem[A1, A2](val a1: A1, val a2: A2) extends PairElem[A1, A2]

final class GenPairArr[A1, A2](val a1Array: Array[A1], val a2Array: Array[A2]) extends PairArr[A1, RArr[A1], A2, GenPairElem[A1, A2]]
{ override type ThisT = GenPairArr[A1, A2]
  override def a1Arr: RArr[A1] = new RArr[A1](a1Array)
  override def a1Index(index: Int): A1 = a1Array(index)
  override def unsafeSetA1(index: Int, value: A1): Unit = a1Array(index) = value
  override def apply(index: Int): GenPairElem[A1, A2] = new GenPairElem[A1, A2](a1Array(index), a2Array(index))

  def replaceA1byA2(key: A2, newValue: A1)(implicit ct1: ClassTag[A1]): GenPairArr[A1, A2] =
  { val newA1Array = new Array[A1](length)
    var i = 0
    while(i < length){
      newA1Array(i) = ife(a2Array(i) == key, newValue, a1Array(i))
      i += 1
    }
    new GenPairArr[A1, A2](newA1Array, a2Array)
  }

  /** Returns a new uninitialised [[GenPairArr]]. */
  def uninitialised(length: Int)(implicit cl1: ClassTag[A1], ct2: ClassTag[A2]): GenPairArr[A1, A2] =
    new GenPairArr[A1, A2](new Array[A1](length), new Array[A2](length))

  @targetName("append") def +%(operand: GenPairElem[A1, A2])(implicit ct1: ClassTag[A1], ct2: ClassTag[A2]): GenPairArr[A1, A2] = ???

  def appendPair(a1: A1, a2: A2)(implicit ct1: ClassTag[A1], ct2: ClassTag[A2]): GenPairArr[A1, A2] = ???

  override def unsafeSetElem(i: Int, newValue: GenPairElem[A1, A2]): Unit = { a1Array(i) = newValue.a1; a2Array(i) = newValue.a2 }

  override def fElemStr: GenPairElem[A1, A2] => String = ???

  /** String specifying the type of this object. */
  override def typeStr: String = ???
}