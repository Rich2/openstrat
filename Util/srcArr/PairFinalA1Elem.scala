/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** These classes are for use in [[PairArrFinalA1]]s. The A1 type is finalised in the instantiable classes */
trait PairFinalA1Elem[A1, A2] extends Any with PairElem[A1, A2]

/** [[ArrPair]] where the A1 type is finalised in the instantiable classes. */
trait PairArrFinalA1[A1, A1Arr <: Arr[A1], A2, A <: PairFinalA1Elem[A1, A2]] extends ArrPair[A1, A1Arr, A2, A]
{ type ThisT <: PairArrFinalA1[A1, A1Arr, A2, A]

  /** Returns a copy of this [[PairArrFinalA1]] where the A1 component is replaced for any pairs where the A2 value matches the given parameter. this method
   * treats the [[PairArrFinalA1]] as a Scala [[Map]] class with the A2s as the keys and the A1s as the values. */
  def replaceA1byA2(key: A2, newValue: A1): ThisT

  def replaceA1byA2OrAppend(key: A2, newValue: A1)(implicit classTag: ClassTag[A2]): ThisT =
    if (a2Exists(key)) replaceA1byA2(key, newValue) else appendPair(newValue, key)

  /** Returns a new uninitialised [[PairArrFinalA1]] of the same final type. */
  def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): ThisT

  @targetName("append") def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT

  def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT
}

/** An efficient [[BuffSequ]] for [[PairFinalA1Elem]]s where the components are stored in separate buffers. The type parameter B, along with B1 and B2 are used
 * because these [[BuffSequ]]s will normally be used with map(f: A => B) and flatMap(f: A => M[B]) type methods. */
trait BuffPair[B1, B2, B <: PairElem[B1, B2]] extends Any with BuffSequ[B]
{ /** ArrayBuffer for the B2 components of the pairs. */
  def b2Buffer: ArrayBuffer[B2]

  /** Grows a [[BuffPair]] by adding the elements of the pair to the b1 and b2 buffers. */
  def pairGrow(b1: B1, b2: B2): Unit

  override def length: Int = b2Buffer.length
  override def fElemStr: B => String = _.toString
}