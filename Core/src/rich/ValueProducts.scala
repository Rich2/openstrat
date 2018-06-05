/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

trait ValueProducts[A] extends Any// extends TraversableOnce[A]
{
   def productSize: Int
   def arrLen: Int
   final def length: Int = arrLen / productSize
   def apply(index: Int): A
   def setElem(index: Int, elem: A): Unit
   def head: A = apply(0)
   def setHead(value: A): Unit = setElem(0, value)
   def last: A = apply(length - 1)
   def setLast(value: A): Unit = setElem(length -1, value) 
   
   def foreach[U](f: A => U): Unit =
   {
      var count = 0
      while(count < length)
      {
         f(apply(count))
         count += 1
      }
   }
   
   def foreachTail[U](f: A => U): Unit =
   {
      var count = 1
      while(count < length)
      {
         f(apply(count))
         count += 1
      }
   }   
   
   def foreachInit[U](f: A => U): Unit =
   {
      var count = 0
      while(count < length - 1)
      {
         f(apply(count))
         count += 1
      }
   }   
   
   def foldLeft[B](initial: B)(f: (B, A) => B) =
   {
      var acc: B = initial
      foreach(a => acc = f(acc, a))
      acc
   }
   
   def foreachReverse[U](f: A => U): Unit =
   {
      var count = length
      while(count > 0)
      {
         count -= 1
         f(apply(count))         
      }
   }
   
   def iForeach[U](f: (A, Int) => U): Unit =
   {
      var count = 0
      while(count < length)
      {
         f(apply(count), count)
         count += 1
      }
   }
      
   def iForeachReverse[U](f: (A, Int) => U): Unit =
   {
      var count = length
      while(count > 0)
      {
         count -= 1
         f(apply(count), count)         
      }
   }
   
   def pMap[B , C <: ValueProducts[B]](f: A => B)(implicit factory: Int => C): C =
   {
      val res = factory(length)
      var count: Int = 0
      while (count < length)
      {
         val newValue: B = f(apply(count))
         res.setElem(count, newValue)
         count += 1         
      }
      res
   }
   
   def aMap[B <: AnyRef](f: A => B)(implicit ev: reflect.ClassTag[B]): Array[B] =
   {
      val res = new Array[B](length)
      iForeach((a, i) => res(i) = f(a))
      res
   }
   
   def lMap[B <: AnyRef](f: A => B): List[B] =
   {
      var res: List[B] = Nil
      foreachReverse(res ::= f(_))
      res
   }
   
    /** map 2 values of A to 1 element of B in List */
   def by2PMap[B , C <: ValueProducts[B]](f: (A, A) => B)(implicit factory: Int => C): C =
   {
      val res = factory(length / 2)
      var count: Int = 0
      while (count < length)
      {
         val newValue: B = f(apply(count), apply(count + 1))
         res.setElem(count, newValue)
         count += 2         
      }
      res
   }
   
   /** map 2 values of A to 1 element of B in List */
   def by2LMap[B](f: (A, A) => B): List[B] =
   {
      var count = 0
      var acc: List[B] = Nil
      while (count < length)
      {
         acc ::= f(apply(count), apply(count + 1))
         count += 2
      }
      acc.reverse
   }  
}