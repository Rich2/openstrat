package ostrat
import collection.mutable.ArrayBuffer

/** An immutable collection of Elements that inherit from a Product of an Atomic value: Double, Long, Long or Float. They are stored with a backing
 * Array[Long] They are named ProductLongs rather than ProductIs because that name can easlily be confused with ProductI1s. */
trait ArrProdLongN[A] extends Any with ArrProdHomo[A]
{ def array: Array[Long]
  def arrLen = array.length
  def toArrs: ArrOld[ArrOld[Long]]
  def foreachArr(f: ArrOld[Long] => Unit): Unit

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

/** A mutable collection of Elements that inherit from a Product of an Atomic value: Double, Long, Long or Float. They are stored with a backing
 * ArrayBuffer[Long] They are named ProductLongsBuff rather than ProductIsBuff because that name can easlily be confused with ProductI1sBuff. */
trait ProductLongsBuff[A] extends Any with BuffProdHomo[A]
{ def buffer: ArrayBuffer[Long]
  def toArray: Array[Long] = buffer.toArray[Long]
//  def unBuff: M
  def grow(newElem: A): Unit
//  def addAll(newElems: M): Unit = { buffer.addAll(newElems.array); () }
}

abstract class ProductLongsBuilder[A, M <: ArrProdLongN[A]](typeStr: String) extends ArrProdHomoPersist[A, M](typeStr)
{ type VT = Long
  override def fromBuffer(buf: Buff[Long]): M = fromArray(buf.toArray)
  override def newBuffer: Buff[Long] = Buff[Long](0)
}

trait ProductLongsCompanion[M]
{ def fromBuffer(buff: Buff[Long]): M = fromArray(buff.toArray[Long])
  val factory: Long => M
  def fromArray(array: Array[Long]): M
}