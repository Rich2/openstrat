/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ProductDouble2sCompanion[T <: ProdD2, ST <: ProductDouble2s[T]] 
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