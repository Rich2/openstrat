/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

/** Homogeneous Product2[Int, Int] with Stringer. These are used in ArrHomoInt2s Array[Int] based collections. */
trait ProdInt2 extends Any with Product2[Int, Int] with ProdHomo

trait ArrProdInt2sBuild[A <: ProdInt2, ArrT <: ArrProdInt2[A]] extends ArrProdIntNBuild[A, ArrT]
{ type BuffT <: BuffProdInt2[A, ArrT]

  final override def elemSize: Int = 2
  def newArray(length: Int): Array[Int] = new Array[Int](length * 2)
  final override def imutSet(arr: ArrT, index: Int, value: A): Unit = { arr.array(index * 2) = value._1; arr.array(index * 2 + 1) = value._2}
  override def buffGrow(buff: BuffT, value: A): Unit = ??? //{ buff.append(value._1,) ??? //buff.buffer.append(value)
}

trait ArrProdInt2[A <: ProdInt2] extends Any with ArrProdIntN[A]
{ 
  override def productSize: Int = 2  
  def newElem(i1: Int, i2: Int): A
  final override def apply(index: Int): A = newElem(array(2 * index), array(2 * index + 1))

  final override def unsafeSetElem(index: Int, elem: A): Unit = { array(2 * index) = elem._1; array(2 * index + 1) = elem._2 }

  def head1: Int = array(0)
  def head2: Int = array(1)

  //def toArrs: ArrOld[ArrOld[Int]] = mapArrSeq(el => ArrOld(el._1, el._2))
  def foreachArrOld(f: ArrOld[Int] => Unit): Unit = foreach(el => f(ArrOld(el._1, el._2)))
}

trait BuffProdInt2[A <: ProdInt2, M <: ArrProdInt2[A]] extends Any with BuffProdHomoInts[A]
{ type ArrT <: ArrProdInt2[A]
  override def elemSize: Int = 2
  override def grow(newElem: A): Unit = { buffer.append(newElem._1).append(newElem._2); () }
}

abstract class ProductI2sCompanion[A <: ProdInt2, M <: ArrProdInt2[A]] extends ProductIntsCompanion[M]
{
  implicit val factory: Int => M = i => fromArray(new Array[Int](i * 2))
  def buff(initialSize: Int): BuffProdInt2[A, M]

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
abstract class ProdInt2sBuilder[A <: ProdInt2, M <: ArrProdInt2[A]](typeStr: String) extends ProductIntsBuilder[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value._1
    buf += value._2
  }

  import pParse._
  override def syntaxDepth = 3
  /** Not sure about this implementation. */
  override def showSemi(thisColl: M): String = thisColl.map2To1(_.toString + ", " + _.toString).mkString("; ")
  override def showComma(thisColl: M): String = show(thisColl)
  override def fromParameterStatements(sts: Refs[Statement]): EMon[M] = ???
  override def fromClauses(clauses: Refs[Clause]): EMon[M] = ???
}

