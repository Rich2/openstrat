/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, reflect.ClassTag

/** These classes are for use in [[ArrPairFinalA1]]s. The A1 type is finalised in the instantiable classes */
trait PairFinalA1Elem[A1, A2] extends Any, PairElem[A1, A2]

/** [[ArrPair]] where the A1 type is finalised in the instantiable classes. */
trait ArrPairFinalA1[A1, A1Arr <: Arr[A1], A2, A <: PairFinalA1Elem[A1, A2]] extends ArrPair[A1, A1Arr, A2, A]
{ type ThisT <: ArrPairFinalA1[A1, A1Arr, A2, A]

  /** Returns a copy of this [[ArrPairFinalA1]] where the A1 component is replaced for any pairs where the A2 value matches the given parameter. this method
   * treats the [[ArrPairFinalA1]] as a Scala [[Map]] class with the A2s as the keys and the A1s as the values. */
  def replaceA1byA2(key: A2, newValue: A1): ThisT

  def replaceA1byA2OrAppend(key: A2, newValue: A1)(implicit classTag: ClassTag[A2]): ThisT =
    if (a2Exists(key)) replaceA1byA2(key, newValue) else appendPair(newValue, key)

  /** Returns a new uninitialised [[ArrPairFinalA1]] of the same final type. */
  def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): ThisT

  @targetName("append") def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT

  def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT
}