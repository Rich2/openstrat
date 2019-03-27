/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

trait Double2sMaker[T <: ProdD2, ST <: ProductDouble2s[T]] extends DoublesMaker[T, ST]
{
  val factory: Int => ST
  def apply(length: Int): ST = factory(length)
  def apply(elems: T*): ST =
  {
    val length = elems.length
    val res = factory(length)
    var count: Int = 0
    
    while (count < length)
    { res.arr(count * 2) = elems(count)._1         
      res.arr(count * 2 + 1) = elems(count)._2
      count += 1
    }
    res
  }
  
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: T): Unit =
  { buf += value._1
    buf += value._2
  }
   
  def doubles(elems: Double*): ST =
  {
    val arrLen: Int = elems.length
    val res = factory(elems.length / 2)
    var count: Int = 0
    
    while (count < arrLen)
    { res.arr(count) = elems(count)
       count += 1         
    }
    res
  }
   
  def fromList(list: List[T]): ST = 
  {
    val arrLen: Int = list.length * 2
    val res = factory(list.length)
    var count: Int = 0
    var rem = list
    
    while (count < arrLen)
    { res.arr(count) = rem.head._1
      count += 1
      res.arr(count) = rem.head._2
      count += 1
      rem = rem.tail
    }
    res
  }
}

abstract class PersistProductDouble2s[R <: ProductDouble2s[_]](typeSym: Symbol) extends PersistCompound[R](typeSym)
{
  import pParse._
  override def typeStr = typeSym.name
  override def syntaxDepth = 3
  override def persistSemi(thisColl: R): String = thisColl.mapPairs(_ + ", " + _ ).mkString("; ")
  override def persistComma(thisColl: R): String = persist(thisColl)
  //override def persist(thisColl: R): String = typeStr - persistSemi(thisColl).enParenth
  override def fromParameterStatements(sts: List[Statement]): EMon[R] = ???
  override def fromClauses(clauses: Seq[Clause]): EMon[R] = ???
}