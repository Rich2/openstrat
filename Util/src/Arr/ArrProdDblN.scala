/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

trait ArrayDblBased extends Any
{ def array: Array[Double]
}

/** Base trait for Array[Double] based collections of Products of Doubles. */
trait ArrProdDblN[A] extends Any with ArrProdHomo[A] with ArrayDblBased
{ type ThisT <: ArrProdDblN[A]

  def unsafeFromArray(array: Array[Double]): ThisT
  final override def unsafeNew(length: Int): ThisT = unsafeFromArray(new Array[Double](length * productSize))
  def unsafeCopyFromArray(opArray: Array[Double], offset: Int = 0): Unit = { opArray.copyToArray(array, offset * productSize); () }
  def arrLen = array.length

  def foreachArr(f: ArrOld[Double] => Unit): Unit

  override def toString: String =
  { var body = ""
    var start = true
    foreachArr { arr =>
      val el = arr.toStrsCommaNoSpaceFold(_.toString)
      if(start == true) {body = el; start = false}
      else  body = body + ";  " + el
    }
    typeStr + body.enParenth
  }

  /** Alias for append. Functionally appends a second Refs[A] to ArrImut. Returned value has the same type as the dispatching Refs. Both operator and
   *  alphanumeric names are overloaded. */
 /* @inline def :+ (op: A): ThisT = append(op)

  /** Functionally appends element to ArrImut. Returned value has the same type as the dispatching Refs. This method is aliased by the ++ operator. */
  def append(op: A): ThisT =
  { val newArray = new Array[Double]((length + 1) * elemLen)
    array.copyToArray(newArray)
    newArray(length) = op
    new Refs(newArray)
  }*/

  /** Builder helper method that provides a longer array, with the underlying array copied into the new extended Array.  */
  def appendArray(appendProductsLength: Int): Array[Double] =
  {
    val acc = new Array[Double](arrLen + appendProductsLength * productSize)
    array.copyToArray(acc)
    acc
  }
}

/** ArrProdDblNBuild[B, BB] is a type class for the building of efficient compact Immutable Arrays of Dbl Product elements. ArrT uses a compile time
 *  wrapped underlying Array[Double]. Instances for this typeclass for classes / traits you control should go in the companion object of B not the
 *  companion object of not BB. This is different from the related ArrProdDblNBinder[BB] typeclass where instance should go into the BB companion
 *  object.The Implicit instances that inherit from this trait will normally go in the companion object of type B, not the companion object of ArrT.
 *  */
trait ArrProdDblNBuild[B, ArrT <: ArrProdDblN[B]] extends ArrProdHomoBuild[B, ArrT]
{ type BuffT <: BuffProdDblN[B]
  def fromDblArray(array: Array[Double]): ArrT
  def fromDblBuffer(inp: ArrayBuffer[Double]): BuffT
  final override def buffNew(length: Int = 4): BuffT = fromDblBuffer(new ArrayBuffer[Double](length * elemSize))
  final override def imutNew(length: Int): ArrT = fromDblArray(new Array[Double](length * elemSize))
  final override def buffToArr(buff: BuffT): ArrT = fromDblArray(buff.buffer.toArray)
}

/** A mutable and resizable Array Buffer for collections of elements that are products of Double sub-elements. */
trait BuffProdDblN[A] extends Any with ArrBuffHomo[A]
{ type ArrT <: ArrProdDblN[A]
  def buffer: ArrayBuffer[Double]

  def length: Int = buffer.length / elemSize
  def toArray: Array[Double] = buffer.toArray[Double]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { buffer.addAll(newElems.array); () }
}

trait ProdDblNsCompanion[T,  ST <: ArrProdDblN[T]]
{ def prodLen: Int
  implicit val persistImplicit: ArrProdDblNPersist[T, ST]
  implicit val factory: Int => ST = len => persistImplicit.fromArray(new Array[Double](len * prodLen))
}

/** Persists and assists in building ArrProdDblN */
abstract class ArrProdDblNPersist[A, M <: ArrProdDblN[A]](typeStr: String) extends ArrProdHomoPersist[A, M](typeStr) with Eq[M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
  override def eqv(m1: M, m2: M): Boolean = m1.array equ m2.array
}