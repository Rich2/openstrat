/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

class ListExtensions[A](val thisList: List[A]) extends AnyVal
{   
  def ifAppendList[B >: A](b: Boolean, newElems: => List[B]): List[B] = if (b) thisList ::: newElems else thisList

  def removeFirst(f: A => Boolean): List[A] =
  { def loop(rem: List[A], acc: List[A]): List[A] = rem match
    { case ::(h, tail) if f(h) => acc.reverse ::: tail
      case ::(h, tail) => loop(tail, h :: acc)
      case Nil => acc.reverse
    }
    loop(thisList, Nil)
  }

  /** Reverses List[A] and puts it inside Good. */
  def goodReverse: Good[List[A]] = Good(thisList.reverse)
   
  /** Replaces all instances of the old value with the new value */
  def replace(oldValue: A, newValue: A): List[A] = thisList map { it => if (it == oldValue) newValue else it }
}