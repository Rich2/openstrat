package ostrat
import collection.mutable.ArrayBuffer

/** An immutable collection of Elements that inherit from a Product of an Atomic value: Double, Int, Long or Float. They are stored with a backing
 * Array[Int] They are named ProductInts rather than ProductIs because that name can easlily be confused with ProductI1s. */
trait ArrProdIntN[A] extends Any with ArrProdHomo[A]
{ type ThisT <: ArrProdIntN[A]
  def array: Array[Int]
  def unsafeFromArray(array: Array[Int]): ThisT
  final override def unsafeNew(length: Int): ThisT = unsafeFromArray(new Array[Int](length * productSize))
  def arrLen = array.length
  def toArrs: Arr[Arr[Int]]
  def foreachArr(f: Arr[Int] => Unit): Unit

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
}

/** ArrProdIntlNBuild[B, BB] is a type class for the building of efficient compact Immutable Arrays of Dbl Product elements. ArrT uses a compile time
 *  wrapped underlying Array[Int]. Instances for this typeclass for classes / traits you control should go in the companion object of B not the
 *  companion object of not BB. This is different from the related ArrProdIntNFlatBuild[BB] typeclass where instance should go into the BB companion
 *  object.The Implicit instances that inherit from this trait will normally go in the companion object of type B, not the companion object of ArrT.
 *  */
trait ArrProdIntNBuild[B, ArrT <: ArrProdIntN[B]] extends ArrProdHomoBuild[B, ArrT]
{ type BuffT <:  BuffProdHomoInts[B]
  def fromIntArray(inp: Array[Int]): ArrT
  def fromIntBuffer(inp: ArrayBuffer[Int]): BuffT
  final override def imutNew(length: Int): ArrT = fromIntArray(new Array[Int](length * elemSize))
  final override def buffNew(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemSize))
  final override def buffToArr(buff: BuffT): ArrT = fromIntArray(buff.buffer.toArray)
}

/** A mutable collection of Elements that inherit from a Product of an Atomic value: Double, Int, Long or Float. They are stored with a backing
 * ArrayBuffer[Int] They are named ProductIntsBuff rather than ProductIsBuff because that name can easlily be confused with ProductI1sBuff. */
trait BuffProdHomoInts[A] extends Any with ArrBuffHomo[A]
{ type ArrT <: ArrProdIntN[A]
  def buffer: ArrayBuffer[Int]
  def toArray: Array[Int] = buffer.toArray[Int]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { buffer.addAll(newElems.array); () }
}

abstract class ProductIntsBuilder[A, M <: ArrProdIntN[A]](typeStr: String) extends ArrProdHomoPersist[A, M](typeStr)
{ type VT = Int
  override def fromBuffer(buf: Buff[Int]): M = fromArray(buf.toArray)
  override def newBuffer: Buff[Int] = Buff[Int](0)
}

trait ProductIntsCompanion[M]
{ def fromBuffer(buff: Buff[Int]): M = fromArray(buff.toArray[Int])
  val factory: Int => M
  def fromArray(array: Array[Int]): M
}


