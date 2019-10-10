package ostrat
import collection.mutable.ArrayBuffer

trait ProductInts[A] extends Any with ProductVals[A]
{ def array: Array[Int]
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

trait ProductIntsBuff[A, M <: ProductInts[A]] extends Any
{ def buffer: ArrayBuffer[Int]
  def toArray: Array[Int] = buffer.toArray[Int]
  def toProductInts: M
  def append(newElem: A): Unit
  def addAll(newElems: M): Unit = buffer.addAll(newElems.array)
}

abstract class ProductIntsBuilder[A, M <: ProductInts[A]](typeStr: String) extends ProductValsBuilder[A, M](typeStr)
{ type VT = Int
  override def fromBuffer(buf: Buff[Int]): M = fromArray(buf.toArray)
  override def newBuffer: Buff[Int] = Buff[Int](0)
}

trait ProductIntsCompanion[M]
{ def fromBuffer(buff: Buff[Int]): M = fromArray(buff.toArray[Int])
  val factory: Int => M
  def fromArray(array: Array[Int]): M
}


