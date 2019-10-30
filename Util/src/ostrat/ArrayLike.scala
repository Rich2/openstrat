package ostrat
import annotation.unchecked.uncheckedVariance
import collection.immutable._, reflect.ClassTag

/** Base trait for Array buffers. The compound deep-value types use the standard ArrayBuffers for underlying storage.  */
trait ArrBuff[A] extends Any with ArrayLike[A]

/** Base trait for Arr and  ArrBuff. */
trait ArrayLike[+A] extends Any
{ type ThisT <: ArrayLike[A]
  def returnThis: ThisT = ???
  def length: Int
  def lenStr: String = length.toString
  def apply(index: Int): A
  def head: A = apply(0)
  def last: A = apply(length - 1)
  def empty: Boolean = length <= 0
  def nonEmpty: Boolean = length > 0
  def ifEmpty[B](vEmpty: => B, vNonEmpty: => B): B = if (length == 0) vEmpty else vNonEmpty

  /** transitional method to be removed. */
  @deprecated def toArraySeq(implicit ct: ClassTag[A] @uncheckedVariance): ArraySeq[A] =
  { val newArray: Array[A] = new Array[A](length)
    iForeach((v, i) => newArray(i) = v)
    ArraySeq.unsafeWrapArray(newArray)
  }

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

  def bmap[B, BB <: ArrImut[B]](f: A => B)(implicit ev: BBuild[B, BB]): BB =
  { val res = ev.imutNew(length)
    //iForeach((a, i) => ev.imutSet(res, i, f(a)))
    res
  }

  def eMap[B](f: A => EMon[B])(implicit ev: ArrBuilder[B]): EMon[ev.ImutT] = ???

  /** map 2 elements of A to 1 element of B. Ignores the last element on a collection of odd numbered length. */
  def map2To1[B](f: (A, A) => B)(implicit ev: ArrBuilder[B]): ev.ImutT =
  { val res = ev.imutNew(length)
    var count = 0
    while (count + 1  < length)
    {  ev.imutSet(res, count, f(apply(count), apply(count + 1)))
      count += 2
    }
    res
  }

  /** FlatMaps over a function from A to any Iterable. */
  def iterFlatMap[B](f: A => Iterable[B])(implicit ev: ArrBuilder[B]): ev.ImutT =
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

  /** maps ValueProduct collection to List */
  def mapList[B <: AnyRef](f: A => B): List[B] =
  { var res: List[B] = Nil
    foreachReverse(res ::= f(_))
    res
  }

  def toStrsFold(seperator: String = "", f: A => String = _.toString): String =
  { var acc: String = ""
    var start = true
    foreach(a => ife(start == true, { acc = f(a); start = false }, acc += a))
    acc
  }

  /** Counts the number of elements that fulfil the condition A => Boolean */
  def existsCount(f: A => Boolean): Int =
  { var count = 0
    foreach(el => if (f(el)) count += 1)
    count
  }

  /** Not sure about this method. */
  def mkString(seperator: String): String = ife(length == 0, "",
    { var acc = head.toString
      var count = 1
      while(count < length)
      { acc += seperator + apply(count).toString
        count += 1
      }
      acc
    }
  )

  def toList: List[A] =
  { var acc: List[A] = Nil
    foreachReverse(acc ::= _)
    acc
  }
}

object ArrayLike
{
  implicit class ArrBaseImplicit[A](ba: ArrayLike[A])
  { def bind[BB <: ArrImut[_]](f: A => BB)(implicit ev: Bind[BB]): BB = ev.bind[A](ba, f)
  }
}