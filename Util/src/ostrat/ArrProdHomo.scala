/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** A homogeneous Product. The final class can be stored as an Array of primitive values. */
trait ProdHomo extends Any

/** An immutable Arr of homogeneous value products. Currently there is no compelling use case for heterogeneous value products, but the homogeneous
 * name is being used to avoid having to change the name if and when homogeneous value product Arrs are implemented. */
trait ArrProdHomo[A] extends Any with ArrValues[A]
{ def typeStr: String
  def productSize: Int
  def arrLen: Int
  final def length: Int = arrLen / productSize

  def pMap[B, N <: ArrProdHomo[B]](f: A => B)(implicit factory: Int => N): N =
  { val res = factory(length)
    var count: Int = 0
    while (count < length) {
      val newValue: B = f(apply(count))
      res.unsafeSetElem(count, newValue)
      count += 1
    }
    res
  }

  /** Maps to ArrSeq of type B. */
  def mapArrSeq[B <: AnyRef](f: A => B)(implicit ev: reflect.ClassTag[B]): Arr[B] =
  { val res = new Array[B](length)
    iForeach((a, i) => res(i) = f(a))
    res.toArr
  }


  /** map every 2 elements of type A from this ProductValue Collection A to 1 element of B Product Value collection N[B]. */
  def by2PMap[B, N <: ArrProdHomo[B]](f: (A, A) => B)(implicit factory: Int => N): N =
  { val res = factory(length / 2)
    var count: Int = 0
    while (count < length) {
      val newValue: B = f(apply(count), apply(count + 1))
      res.unsafeSetElem(count, newValue)
      count += 2
    }
    res
  }

  /** map 2 values of A to 1 element of B in List. */
  def by2MapList[B](f: (A, A) => B): List[B] =
  { var count = 0
    var acc: List[B] = Nil
    while (count < length) {
      acc ::= f(apply(count), apply(count + 1)); count += 2
    }
    acc.reverse
  }

  /** Appends ProductValue collection with the same type of Elements to a new ValueProduct collection. Note the operand collection can have a different
   * type, although it shares the same element type. In such a case, the returned collection will have the type of the operand not this collection. */
  def ++[N <: ArrProdHomo[A]](operand: N)(implicit factory: Int => N): N =
  { val res = factory(length + operand.length)
    iForeach((elem, i) => res.unsafeSetElem(i, elem))
    operand.iForeach((elem, i) => res.unsafeSetElem(i + length, elem))
    res
  }

  /** Appends an element to a new ProductValue collection of type N with the same type of Elements. */
  def :+[N <: ArrProdHomo[A]](operand: A)(implicit factory: Int => N): N =
  { val res = factory(length + 1)
    iForeach((elem, i) => res.unsafeSetElem(i, elem))
    res.unsafeSetElem(length, operand)
    res
  }

  def foldWithPrevious[B](initPrevious: A, initAcc: B)(f: (B, A, A) => B): B =
  { var acc: B = initAcc
    var prev: A = initPrevious
    foreach { newA =>
      acc = f(acc, prev, newA)
      prev = newA
    }
    acc
  }
}

trait ArrBuffHomo[A, M <: ArrProdHomo[A]] extends Any
{ def unBuff: M
  def append(newElem: A): Unit
  def addAll(newElems: M): Unit
}

abstract class ArrProdHomoPersist[A, M](val typeStr: String) extends PersistCompound[M]
{ /** Atomic Value type normally Double or Int. */
  type VT
  def appendtoBuffer(buf: Buff[VT], value: A): Unit
  def fromArray(value: Array[VT]): M
  def fromBuffer(buf: Buff[VT]): M
  def newBuffer: Buff[VT]
}