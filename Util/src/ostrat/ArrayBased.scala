package ostrat
import annotation.unchecked.uncheckedVariance

trait ArrayBased[+A] extends Any
{ type ThisT <: ArrayBased[A]

  def returnThis: ThisT = ???
  def length: Int
  def apply(index: Int): A

  def head: A = apply(0)
  def last: A = apply(length - 1)

  def foreach[U](f: A => U): Unit =
  { var count = 0
    while(count < length)
    { f(apply(count))
      count = count + 1
    }
  }

  /** foreach with index. The startIndex parameter is placed 2nd to allow it to have a default value of zero. */
  def iForeach[U](f: (A, Int) => U, startIndex: Int = 0): Unit =
  { val endIndex = length + startIndex
    var i: Int = startIndex
    while(i < endIndex ) { f(apply(i), i); i = i + 1 }
  }

  def map[B](f: A => B)(implicit ev: ArrBuilder[B]): ev.ImutT =
  { val res = ev.imutNew(length)
    iForeach((a, i) => ev.imutSet(res, i, f(a)))
    res
  }

  def seqMap[B](f: A => Iterable[B])(implicit ev: ArrBuilder[B]): ev.ImutT =
  { val buff = ev.buffNew(length)
    foreach(a => ev.buffAppendSeq(buff, f(a)))
    ev.buffImut(buff)
  }

  def foldLeft[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    foreach(a => acc = f(acc, a))
    acc
  }

  def indexOf(elem: A @uncheckedVariance): Int =
  { var result = -1
    var count  = 0
    while (count < length & result == -1)
    { if (elem == apply(count)) result = count
    else count += 1
    }
    result
  }

  /** Return the index of the first lemenet where predicate is true, or -1 if predicate not true forall. */
  def indexWhere(f: A => Boolean): Int =
  { var count = 0
    var result = -1
    while(count < length & result == -1)
    { if(f(apply(count))) result = count
      count += 1
    }
    result
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


  def foldTailLeft[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    foreachTail(a => acc = f(acc, a))
    acc
  }

  def foldHeadTail[B](initial: B)(fHead: (B, A) => B)(fTail: (B, A) => B) =
  { var acc: B = initial
    var start: Boolean = true
    foreach{a => if(start == true)
    {acc = fHead(acc, a); start = false}
    else acc = fTail(acc, a)
    }
    acc
  }

  /** Consider changing this name, as might not be appropriate to all sub classes. */
  def foreachReverse[U](f: A => U): Unit =
  { var count = length
    while(count > 0) { count -= 1; f(apply(count)) }
  }

  def iForeachReverse[U](f: (A, Int) => U): Unit =
  { var count = length
    while(count > 0) { count -= 1; f(apply(count), count) }
  }

  def contains[A1 >: A](elem: A1): Boolean =
  { var count = 0
    var res = false
    while (res == false & count < length){ if (elem == apply(count)) res = true; count += 1 }
    res
  }
}

object ArrayBased
{
  implicit class ArrBaseImplicit[A](ba: ArrayBased[A])
  { def bind[BB <: ArrImut[_]](f: A => BB)(implicit ev: Bind[BB]): BB = ev.bind[A](ba, f)
  }
}