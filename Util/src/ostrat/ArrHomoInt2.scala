/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

trait ArrProdInt2[A <: HomoInt2] extends Any with ArrProdIntN[A]
{ 
  override def productSize: Int = 2  
  def newElem(i1: Int, i2: Int): A
  final override def apply(index: Int): A = newElem(array(2 * index), array(2 * index + 1))

  final override def unsafeSetElem(index: Int, elem: A): Unit = { array(2 * index) = elem._1; array(2 * index + 1) = elem._2 }

  def head1: Int = array(0)
  def head2: Int = array(1)
  
  def mapBy2[B](f: (Int, Int) => B)(implicit m: scala.reflect.ClassTag[B]): Array[B] =
  {
    val newArr = new Array[B](length)
    var count = 0
    while (count < length) 
    { newArr(count) = f(array(count * 2), array(count * 2 + 1))
      count += 1
    }
    newArr
   }
  def toArrs: Arr[Arr[Int]] = mapArrSeq(el => Arr(el._1, el._2))
  def foreachArr(f: Arr[Int] => Unit): Unit = foreach(el => f(Arr(el._1, el._2)))
}

trait ProductI2sBuff[A <: HomoInt2, M <: ArrProdInt2[A]] extends Any with ArrBuffHomoInts[A, M]
{ override def append(newElem: A): Unit = { buffer.append(newElem._1).append(newElem._2); () }
}

abstract class ProductI2sCompanion[A <: HomoInt2, M <: ArrProdInt2[A]] extends ProductIntsCompanion[M]
{
  implicit val factory: Int => M = i => fromArray(new Array[Int](i * 2))
  def buff(initialSize: Int): ProductI2sBuff[A, M]

  def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 2
    val res = factory(elems.length)
    var count: Int = 0
    while (count < arrLen)
    {
      res.array(count) = elems(count / 2)._1
      count += 1
      res.array(count) = elems(count / 2)._2
      count += 1
    }
    res
  }
}

/** Persistence and Builder class for collections of Int products: ProdI2s. */
abstract class ProductI2sBuilder[A <: HomoInt2, M <: ArrProdInt2[A]](typeStr: String) extends ProductIntsBuilder[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value._1
    buf += value._2
  }

  import pParse._
  override def syntaxDepth = 3
  /** Not sure about this implementation. */
  override def showSemi(thisColl: M): String = thisColl.mapBy2(_.str + ", " + _.str).mkString("; ")
  override def showComma(thisColl: M): String = show(thisColl)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[M] = ???
  override def fromClauses(clauses: Arr[Clause]): EMon[M] = ???
}

