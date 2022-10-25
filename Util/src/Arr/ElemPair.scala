/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** These classes are for use in [[PairArr]]s. */
trait ElemPair[A1, A2] extends Any
{ def a1: A1
  def a2: A2
}

/** An [[Arr]] of pairs [[ElemPair]]. These classees allow convenient methods to map and filter on just one component of the pair. They and their
 *  associated [[PairArrBuilder]] and [[PairBuff]] classes also allow for efficient storage by using 2 Arrays of the components of the pairs rather
 *  than one array of the pairs. It is particularly designed for efficient maoOnA1 operations, where we want to map over the first part of the pair
 *  while leaving the second component of the oair unchanged. So sub traits and classes specialise on a1 the first component of the pair. There are no
 *  filterMap methods. You must map then filter. */
trait PairArr[A1, A1Arr <: Arr[A1], A2, A <: ElemPair[A1, A2]] extends Arr[A]
{ def a1Arr: A1Arr
  def a2Array: Array[A2]
  def a2Arr: RArr[A2] = new RArr[A2](a2Array)
  override final def length: Int = a2Array.length

  /** Maps the first component of the pairs, dropping the second. */
  def a1Map[B, ArrB <: Arr[B]](f: A1 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = a1Arr.map(f)

  /** Maps the second component of the pairs, dropping the first. */
  def a2Map[B, ArrB <: Arr[B]](f: A2 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = a2Arr.map(f)

  /** Needs rewriting. */
  def pairMap[B, ArrB <: Arr[B]](f: (A1, A2) => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = map(p => f(p.a1, p.a2))

  def a1Index(index: Int): A1
  def a2Index(index: Int): A2 = a2Array(index)

  def mapOnA1[B1, ArrB1 <: Arr[B1], B <: ElemPair[B1, A2], ArrB <: PairArr[B1, ArrB1, A2, B]](f: A1 => B1)(implicit
    build: PairArrBuilder[B1, ArrB1, A2, B, ArrB]): ArrB =
  { val b1Arr: ArrB1 = a1Arr.map(f)(build.b1ArrBuilder)
    build.pairArrBuilder(b1Arr, a2Array)
  }

  def optMapOnA1[B1, ArrB1 <: Arr[B1], B <: ElemPair[B1, A2], ArrB <: PairArr[B1, ArrB1, A2, B]](f: A1 => Option[B1])(implicit
    build: PairArrBuilder[B1, ArrB1, A2, B, ArrB], ct: ClassTag[A2]): ArrB =
  {
    val a1Buff = build.newB1Buff()
    val a2Buff = new ArrayBuffer[A2]()
    foreach{ pair =>
      f(pair.a1).foreach{ poly =>
        build.b1BuffGrow(a1Buff, poly)
        a2Buff.append(pair.a2)
      }
    }
    build.fromBuffs(a1Buff, a2Buff)
  }
}

trait PairBuff[A1, A2, A <: ElemPair[A1, A2]] extends Any with Buff[A]
{ def a2Buffer: ArrayBuffer[A2]
  override def length: Int = a2Buffer.length
  override def fElemStr: A => String = _.toString
}

/** Builder for [[ElemPair]]s. As with all builders, we use B as the type parameter, because builders are nearly always used with some kind of map /
 * flatMap methods where B corresponds to the map function f: A => B. */
trait PairArrBuilder[B1, ArrB1 <: Arr[B1], B2, B <: ElemPair[B1, B2], ArrB <: Arr[B]] extends ArrBuilder[B, ArrB]
{ type BuffT <: PairBuff[B1, B2, B]

  /** Builder for an Arr of the first element of the pair. */
  def b1ArrBuilder: ArrBuilder[B1, ArrB1]

  implicit def b2ClassTag: ClassTag[B2]

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  def pairArrBuilder(b1Arr: ArrB1, b2s: Array[B2]): ArrB

  type B1BuffT <: Buff[B1]

  def newB1Buff(): B1BuffT

  def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit

  def fromBuffs(a1Buff : B1BuffT, b2s: ArrayBuffer[B2]): ArrB
}