/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ProductDouble7s[A <: ProdD7] extends Any with ProductValues[A]
{ def productSize: Int = 7
  def arr: Array[Double]
  def arrLen: Int = arr.length
  def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double, d7: Double): A
  
  def apply(index: Int): A =
  { val offset = 7 * index
    newElem(arr(offset), arr(offset + 1), arr(offset + 2), arr(offset + 3), arr(offset + 4), arr(offset + 5), arr(offset + 6))
  }
   
  def setElem(index: Int, elem: A): Unit =
  { val offset = 7 * index;
    arr(offset) = elem._1; arr(offset + 1) = elem._2; arr(offset + 2) = elem._3; arr(offset + 3) = elem._4; arr(offset + 4) = elem._5
    arr(offset + 5) = elem._6; arr(offset + 6) = elem._7
  }
   
  def head1: Double = arr(0); def head2: Double = arr(1); def head3: Double = arr(2); def head4: Double = arr(3); def head5: Double = arr(4)
  def head6: Double = arr(5); def head7: Double = arr(6)
}

abstract class Double7sMaker[T <: ProdD7, ST <: ProductDouble7s[T]]
{ val factory: Int => ST
  def apply(length: Int): ST = factory(length)
  def apply(elems: T*): ST =
  { val length = elems.length
    val res = factory(length)
    var count: Int = 0
      
    while (count < length)
    { val offset = count * 7
      res.arr(offset) = elems(count)._1; res.arr(offset + 1) = elems(count)._2; res.arr(offset + 2) = elems(count)._3
      res.arr(offset + 3) = elems(count)._4; res.arr(offset + 4) = elems(count)._5; res.arr(offset + 5) = elems(count)._6
      res.arr(offset + 6) = elems(count)._7
      count += 1
    }
    res
  }
   
  def doubles(elems: Double*): ST =
  { val arrLen: Int = elems.length
    val res = factory(elems.length / 7)
    var count: Int = 0
    while (count < arrLen) { res.arr(count) = elems(count); count += 1 }
    res
  }
   
   def fromList(list: List[T]): ST = 
   { val res = factory(list.length)
     var count: Int = 0
     var rem = list
     
     while (count < list.length)
     { val offset = count * 7      
       res.arr(offset) = rem.head._1; res.arr(offset +  1) = rem.head._2; res.arr(offset +  2) = rem.head._3; res.arr(offset + 3) = rem.head._4
       res.arr(offset + 4) = rem.head._5; res.arr(offset + 5) = rem.head._6; res.arr(offset + 7) = rem.head._7         
       count += 1
       rem = rem.tail
     }
     res
  }
}
