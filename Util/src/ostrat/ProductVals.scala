/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** This is the base trait for the ProductDoubles and ProductInts classes. */
trait ProductVals[A] extends Any
{ //def typeStr: String
  //override def toString: String = typeStr - MapList(_.toString).commaParenth
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
  { var count = 0
    while(count < length) { f(apply(count)); count += 1 }
  }
   
  def foreachTail[U](f: A => U): Unit =
  { var count = 1
    while(count < length) { f(apply(count)); count += 1 }
  }   
   
  def foreachInit[U](f: A => U): Unit =
  { var count = 0
    while(count < length - 1)
    { f(apply(count))
      count += 1
    }
  }   
   
  def foldLeft[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    foreach(a => acc = f(acc, a))
    acc
  }
   
  def foreachReverse[U](f: A => U): Unit =
  { var count = length
    while(count > 0) { count -= 1; f(apply(count)) }
  }
   
  def iForeach[U](f: (A, Int) => U): Unit =
  { var count = 0
    while(count < length) { f(apply(count), count); count += 1 }
  }
      
  def iForeachReverse[U](f: (A, Int) => U): Unit =
  { var count = length
    while(count > 0) { count -= 1; f(apply(count), count) }
  }
     
  def pMap[B , N <: ProductVals[B]](f: A => B)(implicit factory: Int => N): N =
  { val res = factory(length)
    var count: Int = 0
    while (count < length)
    { val newValue: B = f(apply(count))
      res.setElem(count, newValue)
      count += 1         
    }
    res
  }
  
  /** Maps to a standard Array of type B. */
  def Map[B <: AnyRef](f: A => B)(implicit ev: reflect.ClassTag[B]): Array[B] =
  { val res = new Array[B](length)
    iForeach((a, i) => res(i) = f(a))
     res
  }

  /** Maps to a standard Array of type B. */
  def MapS[B <: AnyRef](f: A => B)(implicit ev: reflect.ClassTag[B]): Arr[B] =
  { val res = new Array[B](length)
    iForeach((a, i) => res(i) = f(a))
    ArrWrap(res)
  }
   
  /** maps ValueProduct collection to List */
  def MapList[B <: AnyRef](f: A => B): List[B] =
  { var res: List[B] = Nil
    foreachReverse(res ::= f(_))
    res
  }
   
  /** map every 2 elements of type A from this ProductValue Collection A to 1 element of B Product Value collection N[B]. */
  def by2PMap[B , N <: ProductVals[B]](f: (A, A) => B)(implicit factory: Int => N): N =
  { val res = factory(length / 2)
    var count: Int = 0
    while (count < length)
    { val newValue: B = f(apply(count), apply(count + 1))
      res.setElem(count, newValue)
      count += 2         
    }
    res
  }
   
  /** map 2 values of A to 1 element of B in List. */
  def by2MapList[B](f: (A, A) => B): List[B] =
  { var count = 0
    var acc: List[B] = Nil
    while (count < length) { acc ::= f(apply(count), apply(count + 1)); count += 2 }
      acc.reverse
  }
  
  def contains[A1 >: A](elem: A1): Boolean =
  { var count = 0
    var res = false
    while (res == false & count < length){ if (elem == apply(count)) res = true; count += 1 }
    res
  }
  
  /** Appends ProductValue collection with the same type of Elements to a new ValueProduct collection. Note the operand collection can have a different
   *  type, although it shares the same element type. In such a case, the returned collection will have the type of the operand not this collection. */
  def ++ [N <: ProductVals[A]](operand: N)(implicit factory: Int => N): N =
  { val res = factory(length + operand.length)
    iForeach((elem, i) => res.setElem(i, elem))
    operand.iForeach((elem, i) => res.setElem(i + length, elem))
    res
  }
  
  /** Appends an element to a new ProductValue collection of type N with the same type of Elements. */
  def :+ [N <: ProductVals[A]](operand: A)(implicit factory: Int => N): N =
  { val res = factory(length + 1)
    iForeach((elem, i) => res.setElem(i, elem))
    res.setElem(length, operand)
    res
  }  
  
  /** Counts the number of elements that fulfil the condition A => Boolean */
  def filterCount(f: A => Boolean): Int =
  { var count = 0
    foreach(el => if (f(el)) count += 1)
    count
  }
  
  def foldWithPrevious[B](initPrevious: A, initAcc: B)(f: (B, A, A) => B): B =
  { var acc: B = initAcc
    var prev: A = initPrevious
    foreach { newA =>
      acc = f(acc, prev, newA)
      prev = newA
    }
    acc
  }
}
