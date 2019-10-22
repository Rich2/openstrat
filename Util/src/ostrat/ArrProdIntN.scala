package ostrat
import collection.mutable.ArrayBuffer

/** An immutable collection of Elements that inherit from a Product of an Atomic value: Double, Int, Long or Float. They are stored with a backing
 * Array[Int] They are named ProductInts rather than ProductIs because that name can easlily be confused with ProductI1s. */
trait ArrProdIntN[A] extends Any with ArrProdHomo[A]
{ type ThisT <: ArrProdIntN[A]
  def array: Array[Int]
  def unsafeFromArray(array: Array[Int]): ThisT
  final override def buildThis(length: Int): ThisT = unsafeFromArray(new Array[Int](length * productSize))
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

/** A mutable collection of Elements that inherit from a Product of an Atomic value: Double, Int, Long or Float. They are stored with a backing
 * ArrayBuffer[Int] They are named ProductIntsBuff rather than ProductIsBuff because that name can easlily be confused with ProductI1sBuff. */
trait ArrBuffHomoInts[A, M <: ArrProdIntN[A]] extends Any with ArrBuffHomo[A, M]
{ def buffer: ArrayBuffer[Int]
  def toArray: Array[Int] = buffer.toArray[Int]
  def unBuff: M
  def append(newElem: A): Unit
  def addAll(newElems: M): Unit = { buffer.addAll(newElems.array); () }
}

abstract class ProductIntsBuilder[A, M <: ArrProdIntN[A]](typeStr: String) extends ArrHomoBuilder[A, M](typeStr)
{ type VT = Int
  override def fromBuffer(buf: Buff[Int]): M = fromArray(buf.toArray)
  override def newBuffer: Buff[Int] = Buff[Int](0)
}

trait ProductIntsCompanion[M]
{ def fromBuffer(buff: Buff[Int]): M = fromArray(buff.toArray[Int])
  val factory: Int => M
  def fromArray(array: Array[Int]): M
}


