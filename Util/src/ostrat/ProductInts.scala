package ostrat

trait ProductInts[A] extends Any with ProductVals[A]
{ def arr: Array[Int]
  def arrLen = arr.length
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
abstract class ProductIntsBuilder[A, M <: ProductInts[A]](typeStr: String) extends ProductValsBuilder[A, M](typeStr)
{
  type VT = Int
  override def fromBuffer(buf: Buff[Int]): M = fromArray(buf.toArray)
  override def newBuffer: Buff[Int] = Buff[Int](0)
}


