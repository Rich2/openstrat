/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** A homogeneous Product. The final class can be stored as an Array of primitive values. */
trait ProdHomo extends Any

/** An immutable Arr of homogeneous value products. Currently there is no compelling use case for heterogeneous value products, but the homogeneous
 * name is being used to avoid having to change the name if and when homogeneous value product Arrs are implemented. */
trait ArrProdHomo[A] extends Any with ArrValues[A]
{ type ThisT <: ArrProdHomo[A]
  def typeStr: String
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
  @deprecated def mapArrSeq[B <: AnyRef](f: A => B)(implicit ev: reflect.ClassTag[B]): ArrOld[B] =
  { val res = new Array[B](length)
    iForeach((a, i) => res(i) = f(a))
    res.toArrOld
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

/** ArrProdHomoBuild[B, BB] is a type class for the building of efficient compact Immutable Arrays of homogeneous Product elements. Instances for
 *  this typeclass for classes / traits you control should go in the companion object of B not the companion object of not BB. This is different from
 *  the related ArrProdHomoBinder[BB] typeclass where instance should go into the BB companion object.The Implicit instances that inherit from this
 *  trait will normally go in the companion object of type B, not the companion object of ArrT. */
trait ArrProdValueNBuild[B, ArrT <: ArrProdHomo[B]] extends ArrBuild[B, ArrT] with ArrFlatBuild[ArrT]
{ def elemSize: Int
}

trait BuffProdValueN[A] extends Any with ArrayLike[A]
{ type ArrT <: ArrProdHomo[A]
  def elemSize: Int
  def grow(newElem: A): Unit
  def grows(newElems: ArrT): Unit
  def toArr(implicit build: ArrBuild[A, ArrT]): ArrT = ???
}

abstract class ArrProdHomoPersist[A, M](val typeStr: String) extends PersistCompound[M]
{ /** Atomic Value type normally Double or Int. */
  type VT
  def appendtoBuffer(buf: Buff[VT], value: A): Unit
  def fromArray(value: Array[VT]): M
  def fromBuffer(buf: Buff[VT]): M
  def newBuffer: Buff[VT]
}