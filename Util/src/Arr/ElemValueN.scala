/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A class that can be constructed from a fixed number of homogeneous primitive values such as Ints, Doubles or Longs. The final class can be stored
 *  as *  an Array of primitive values. Note the classes that extend this trait do not extend [[Product]] or its numbered sub traits, because the
 *  logical size of the product may not be the same as the number of primitive values, for example a LineSeg is a product of 2 [[Pt2]]s, but is
 *  composed from 4 [[Double]] values. */
trait ElemValueN extends Any with SpecialT

/** An immutable trait defined by  a collection of homogeneous value products. The underlying array is Array[Double], Array[Int] etc. The descendant
 *  classes include both [[ValueNscollection]]s and classes like polygons and lines. */
trait DataValueNs[A <: ElemValueN] extends Any with DataImut[A]
{ type ThisT <: DataValueNs[A]

  /** The number of atomic values, Ints, Doubles, Longs etc that specify / construct an element of this immutable flat Array based collection
   *  class. */
  def elemProdSize: Int

  /** The total  number of atomic values, Ints, Doubles, Longs etc in the backing Array. */
  def arrLen: Int

  def reverseData: ThisT

  /** The number of product elements in this collection. For example in a [[PolygonImp], this is the number of [[Pt2]]s in the [[Polygon]] */
  final override def elemsNum: Int = arrLen / elemProdSize

  /** Performs a side effecting function on each specifying data element of this ValueNsData in order. */
  /*def dataForeach[U](f: A => U): Unit =
  { var count = 0
    while(count < elemsNum)
    { f(indexData(count))
      count = count + 1
    }
  }*/

  /** Performs a side effecting function on each element of this sequence with an index starting at 0. */
  def dataIForeach[U](f: (A, Int) => U): Unit =
  { var count = 0
    var i: Int = 0
    while(count < elemsNum )
    { f(indexData(count), i)
      count+= 1
      i += 1
    }
  }

  /** Maps the dat elements that specify the final class. */
  def dataMap[B <: ElemValueN, N <: DataValueNs[B]](f: A => B)(implicit factory: Int => N): N =
  { val res = factory(elemsNum)
    var count: Int = 0
    while (count < elemsNum) {
      val newValue: B = f(indexData(count))
      res.unsafeSetElem(count, newValue)
      count += 1
    }
    res
  }
}

/** An immutable Arr of homogeneous value products. Currently there is no compelling use case for heterogeneous value products, but the homogeneous
 * name is being used to avoid having to change the name if and when homogeneous value product Arrs are implemented. */
trait ArrValueNs[A <: ElemValueN] extends Any with ArrBase[A] with DataValueNs[A]
{ type ThisT <: ArrValueNs[A]

  /** Appends ProductValue collection with the same type of Elements to a new ValueProduct collection. Note the operand collection can have a different
   * type, although it shares the same element type. In such a case, the returned collection will have the type of the operand not this collection. */
  def ++[N <: ArrValueNs[A]](operand: N)(implicit factory: Int => N): N =
  { val res = factory(elemsNum + operand.elemsNum)
    iForeach((elem, i) => res.unsafeSetElem(i, elem))
    operand.iForeach((elem, i) => res.unsafeSetElem(i + elemsNum, elem))
    res
  }

  /** Appends an element to a new ProductValue collection of type N with the same type of Elements. */
  def :+[N <: ArrValueNs[A]](operand: A)(implicit factory: Int => N): N =
  { val res = factory(elemsNum + 1)
    iForeach((elem, i) => res.unsafeSetElem(i, elem))
    res.unsafeSetElem(elemsNum, operand)
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

/** Trait for creating the ArrTBuilder. Instances for the [[ArrBuilder]] type class, for classes / traits you control, should go in the companion
 *  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait ArrValueNsBuilder[B <: ElemValueN, ArrB <: ArrValueNs[B]] extends ArrBuilder[B, ArrB]
{ def elemProdSize: Int
}

/** Trait for creating the ArrTFlatBuilder type class instances for [[ArrValueNs]] final classes. Instances for the [[ArrFlatBuilder] should go in
 *  the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB```
 *  function. */
trait ArrValueNsFlatBuilder[B <: ElemValueN, ArrB <: ArrValueNs[B]] extends ArrFlatBuilder[ArrB]
{ def elemProdSize: Int
}

/** Specialised flat arraybuffer based collection class, where the underlying ArrayBuffer element is an atomic value like [[Int]], [[Double]] or
 *  [[Long]]. */
trait BuffValueNs[A <: ElemValueN] extends Any with SeqGen[A]
{ type ArrT <: ArrValueNs[A]
  def elemProdSize: Int
  def grow(newElem: A): Unit
  def grows(newElems: ArrT): Unit
  def toArr(implicit build: ArrBuilder[A, ArrT]): ArrT = ???
  override def fElemStr: A => String = _.toString
}

/** Class to Persist specialised flat Array[Value] type based collections. */
abstract class DataValueNsPersist[A, M](val typeStr: String) extends PersistCompound[M]
{ /** Atomic Value type normally Double or Int. */
  type VT
  def appendtoBuffer(buf: Buff[VT], value: A): Unit
  def fromArray(value: Array[VT]): M
  def fromBuffer(buf: Buff[VT]): M
  def newBuffer: Buff[VT]
}

/** Helper trait for companion objects of [[DataValueNs]] classes. These are flat Array[Int], Array[Double] etc, flat collection classes. */
trait DataValueNsCompanion[A <: ElemValueN, ArrA <: DataValueNs[A]]
{ /** returns a collection class of type ArrA, whose backing Array is uninitialised. */
  implicit def uninitialised(length: Int): ArrA

  /** the product size of the ValueNsArr type's elements. */
  def elemProdSize: Int

  /** This method allows you to map from a DataGen to the ArrA type. */
  final def fromDataGenMap[T](alb: DataGen[T])(f: T => A): ArrA = {
    val res = uninitialised(alb.elemsNum)
    var count = 0
    alb.dataForeach { t =>
      res.unsafeSetElem(count, f(t))
      count += 1
    }
    res
  }
}