/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, collection.immutable._

/** This the base trait for all efficient sequence collections based on Array like classes, Arrays, ArrayBuffers etc. The final classes compile time
 *  wrap the platform Array and buffer classes. So currently there are just two classes for each type A, An ArrImut that wraps a standard immutable
 *  Array to produce an immutable array, and a ArrBuff that wraps an ArrayBuffer. Currently this just in a standard ArrayBuffer. Where A is a compound
 *  value types or an AnyVal type. */
trait SeqGen[+A] extends Any with DataGen[A @uncheckedVariance]
{ /** The final type of this object. */
  type ThisT <: SeqGen[A]

  /** Method for keeping the typer happy when returning this as an instance of ThisT. */
  @inline def returnThis: ThisT = this.asInstanceOf[ThisT]

  /** apply method accesses the individual elements of the sequence by 0 based index. */
  @inline def apply(index: Int): A = indexData(index)//: A = apply(index)

  /** The first element of this sequence. */
  @inline def head: A = apply(0)

  /** The last element of this sequence. */
  @inline def last: A = apply(elemsNum - 1)

  /** Is this sequence empty? */
  @inline def empty: Boolean = elemsNum <= 0

  /** Is this sequence non empty? */
  @inline def nonEmpty: Boolean = elemsNum > 0

  def ifEmpty[B](vEmpty: => B, vNonEmpty: => B): B = if (elemsNum == 0) vEmpty else vNonEmpty
  def fHeadElse[B](noHead: => B)(ifHead: A => B): B = ife(elemsNum >= 1, ifHead(head), noHead)
  def headToStringElse(ifEmptyString: String): String = ife(elemsNum >= 1, head.toString, ifEmptyString)

  /** Applies an index to this ArrayLike collection which cycles back to element 0, when it reaches the end of the collection. Accepts even negative
   * integers as an index value without throwing an exception. */
  @inline def cycleGet(index: Int): A = apply(index %% elemsNum)

  /** Performs a side effecting function on each element of this sequence in order. */
  def foreach[U](f: A => U): Unit =
  { var count = 0
    while(count < elemsNum)
    { f(apply(count))
      count = count + 1
    }
  }

  /** Performs a side effecting function on each element of this sequence with an index starting at 0. */
  def iForeach[U](f: (A, Int) => U): Unit =
  { var count = 0
    var i: Int = 0
    while(count < elemsNum )
    { f(apply(count), i)
      count+= 1
      i += 1
    }
  }

  /** Performs a side effecting function on each element of this sequence with an index starting at the given integer parameter. */
  def iForeach[U](startIndex: Int)(f: (A, Int) => U): Unit =
  { var count = 0
    var i: Int = startIndex
    while(count < elemsNum )
    { f(apply(count), i)
      count+= 1
      i += 1
    }
  }

  /** Specialised map to an immutable ArrBase of B. */
  def map[B, ArrB <: ArrBase[B]](f: A => B)(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val res = ev.newArr(elemsNum)
    iForeach((a, i) => ev.arrSet(res, i, f(a)))
    res
  }

  /** Specialised flatMap to an immutable Arr. */
  def flatMap[ArrB <: ArrBase[_]](f: A => ArrB)(implicit ev: ArrFlatBuilder[ArrB]): ArrB =
  {
    val buff: ev.BuffT = ev.newBuff()
    foreach{ a =>
      val newVals = f(a)
      ev.buffGrowArr(buff, newVals)
    }
    ev.buffToBB(buff)
  }

  /** Takes a second collection as a parameter and zips the elements of this collection and the operand collection and applies the specialised map
   * function from type A and type B to type C. */
  def zipMap[B, C, ArrC <: ArrBase[C]](operator: SeqGen[B])(f: (A, B) => C)(implicit ev: ArrBuilder[C, ArrC]): ArrC =
  { val newLen = elemsNum.min(operator.elemsNum)
    val res = ev.newArr(newLen)
    var count = 0
    while(count < newLen)
    { val newElem = f(apply(count), operator.apply(count))
      ev.arrSet(res, count, newElem)
      count += 1
    }
    res
  }

  /** Takes a second collection and third collections as parameters and zips the elements of this collection and the operand collections and applies
   *  the specialised map function from type A and type B and type C to type D. */
  def zipMap2[B, C, D, ArrD <: ArrBase[D]](operator1: SeqGen[B], operator2: SeqGen[C])(f: (A, B, C) => D)(implicit ev: ArrBuilder[D, ArrD]): ArrD =
  { val newLen = elemsNum.min(operator1.elemsNum).min(operator2.elemsNum)
    val res = ev.newArr(newLen)
    var count = 0
    while(count < newLen)
    { val newElem = f(apply(count), operator1.apply(count), operator2.apply(count))
      ev.arrSet(res, count, newElem)
      count += 1
    }
    res
  }

  /** Specialised map with index to an immutable ArrBase of B. This method should be overridden in sub classes. */
  def iMap[B, ArrB <: ArrBase[B]](f: (A, Int) => B)(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val res = ev.newArr(elemsNum)
    iForeach((a, i) => ev.arrSet(res, i, f(a, i)))
    res
  }

  /** Specialised flatMap with index to an immutable Arr. */
  def iFlatMap[ArrB <: ArrBase[_]](f: (A, Int) => ArrB)(implicit build: ArrFlatBuilder[ArrB]): ArrB =
  { val buff: build.BuffT = build.newBuff()
    var i: Int = 0
    while (i < elemsNum)
    { val newArr = f(apply(i), i);
      build.buffGrowArr(buff, newArr)
      i += 1
    }
    build.buffToBB(buff)
  }

  /** Specialised flatMap with index to an immutable Arr. */
  def iFlatMap[ArrB <: ArrBase[_]](iInit: Int = 0)(f: (A, Int) => ArrB)(implicit build: ArrFlatBuilder[ArrB]): ArrB =
  { val buff: build.BuffT = build.newBuff()
    var count: Int = 0
    while (count < elemsNum)
    { val newElems = f(apply(count), count + iInit)
      build.buffGrowArr(buff, newElems)
      count += 1
    }
    build.buffToBB(buff)
  }

  /* Maps from A to B like normal map,but has an additional accumulator of type C that is discarded once the traversal is completed */
  def mapWithAcc[B, ArrB <: ArrBase[B], C](initC: C)(f: (A, C) => (B, C))(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val res = ev.newArr(elemsNum)
    var accC: C = initC
    iForeach { (a, i) =>
      val (newB, newC) = f(a, accC)
      res.unsafeSetElem(i, newB)
      accC = newC
    }
    res
  }

  def eMap[B, ArrB <: ArrBase[B]](f: A => EMon[B])(implicit ev: ArrBuilder[B, ArrB]): EMon[ArrB] =
  { val acc = ev.newBuff()
    var continue = true
    var count = 0
    var errs: Strings = Strings()
    while(count < elemsNum & continue == true)
      f(apply(count)).foldErrs { g => ev.buffGrow(acc, g); count += 1 } { e => errs = e; continue = false }
    ife(continue, Good(ev.buffToBB(acc)), Bad(errs))
  }

  def eMapList[B](f: A => EMon[B]): EMon[List[B]] =
  { var acc: List[B] = Nil
    var continue = true
    var count = 0
    var errs: Strings = Strings()
    while(count < elemsNum & continue == true)
      f(apply(count)).foldErrs { g => acc ::= g; count += 1 } { e => errs = e; continue = false }
    ife(continue, Good(acc.reverse), Bad(errs))
  }

  /** map 2 elements of A to 1 element of B. Ignores the last element on a collection of odd numbered length. */
  def map2To1[B, ArrB <: ArrBase[B]](f: (A, A) => B)(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val res = ev.newArr(elemsNum)
    var count = 0
    while (count + 1  < elemsNum)
    {  ev.arrSet(res, count, f(apply(count), apply(count + 1)))
      count += 2
    }
    res
  }

  def filter[ArrA <: ArrBase[A] @uncheckedVariance](f: A => Boolean)(implicit ev: ArrBuilder[A, ArrA] @uncheckedVariance): ArrA =
  { val buff = ev.newBuff()
    foreach(a => oif(f(a), ev.buffGrow(buff, a)))
    ev.buffToBB(buff)
  }

  def filterNot[ArrA <: ArrBase[A] @uncheckedVariance](f: A => Boolean)(implicit ev: ArrBuilder[A, ArrA] @uncheckedVariance): ArrA =
  { val buff = ev.newBuff()
    foreach(a => oif(!f(a), ev.buffGrow(buff, a)))
    ev.buffToBB(buff)
  }

  def filterToList(f: A => Boolean): List[A] =
  {
    var acc: List[A] = Nil
    foreach{ a => if (f(a)) acc ::= a }
    acc.reverse
  }

  /** FlatMaps over a function from A to any Iterable. */
  def iterFlatMap[B, ArrB <: ArrBase[B]](f: A => Iterable[B])(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val buff = ev.newBuff(elemsNum)
    foreach(a => ev.buffGrowIter(buff, f(a)))
    ev.buffToBB(buff)
  }

  def foldLeft[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    foreach(a => acc = f(acc, a))
    acc
  }

  def indexOf(elem: A @uncheckedVariance): Int =
  { var result = -1
    var count  = 0
    while (count < elemsNum & result == -1)
    { if (elem == apply(count)) result = count
    else count += 1
    }
    result
  }

  /** Return the index of the first element where predicate is true, or -1 if predicate not true forall. */
  def indexWhere(f: A => Boolean): Int =
  { var count = 0
    var result = -1
    while(count < elemsNum & result == -1)
    { if(f(apply(count))) result = count
      count += 1
    }
    result
  }

  /** Foreachs over the tail of this sequence. */
  def tailForeach[U](f: A => U): Unit =
  { var count = 1
    while(count < elemsNum) { f(apply(count)); count += 1 }
  }

  def foreachInit[U](f: A => U): Unit =
  { var count = 0
    while(count < elemsNum - 1)
    { f(apply(count))
      count += 1
    }
  }

  /** foldLeft over the tail of this sequence. */
  def tailfold[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    tailForeach(a => acc = f(acc, a))
    acc
  }

  def foldHeadTail[B](initial: B)(fHead: (B, A) => B)(fTail: (B, A) => B) =
  { var acc: B = initial
    var start: Boolean = true
    foreach { a =>
      if(start == true) { acc = fHead(acc, a); start = false }
      else acc = fTail(acc, a)
    }
    acc
  }

  /** Consider changing this name, as might not be appropriate to all sub classes. */
  def foreachReverse[U](f: A => U): Unit =
  { var count = elemsNum
    while(count > 0) { count -= 1; f(apply(count)) }
  }

  def iForeachReverse[U](f: (A, Int) => U): Unit =
  { var count = elemsNum
    while(count > 0) { count -= 1; f(apply(count), count) }
  }

  def forAll(p: (A) => Boolean): Boolean =
  { var acc: Boolean = true
    var count = 0
    while (acc & count < elemsNum) if (p(apply(count))) count += 1 else acc = false
    acc
  }

  def iForAll(p: (A, Int) => Boolean): Boolean =
  { var acc: Boolean = true
    var count = 0
    while (acc & count < elemsNum) if (p(apply(count), count)) count += 1 else acc = false
    acc
  }


  def contains[A1 >: A](elem: A1): Boolean =
  { var count = 0
    var res = false
    while (res == false & count < elemsNum){ if (elem == apply(count)) res = true; count += 1 }
    res
  }

  /** maps ValueProduct collection to List */
  def mapList[B <: AnyRef](f: A => B): List[B] =
  { var res: List[B] = Nil
    foreachReverse(res ::= f(_))
    res
  }

  def toStrsFold(seperator: String,  f: A => String): String =
  { var acc: String = ""
    var start = true
    foreach{ a => if(start == true) { acc = f(a); start = false } else acc = acc + seperator + f(a)}
    acc
  }

  /** Counts the number of elements that fulfil the condition A => Boolean */
  def existsCount(f: A => Boolean): Int =
  { var count = 0
    foreach(el => if (f(el)) count += 1)
    count
  }

  /** Not sure about this method. */
  def mkString(seperator: String): String = ife(elemsNum == 0, "",
    { var acc = head.toString
      var count = 1
      while(count < elemsNum)
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

  def sumBy(f: A => Int): Int =
  { var acc = 0
    foreach(acc += f(_))
    acc
  }

  /** Collects values of B by applying partial function to only those elements of A, for which the PartialFunction is defined. */
  def collect[B, BB <: ArrBase[B]](pf: PartialFunction[A, B])(implicit ev: ArrBuilder[B, BB]): BB =
  { val acc = ev.newBuff()
    foreach{a => if (pf.isDefinedAt(a)) ev.buffGrow(acc, pf(a)) }
    ev.buffToBB(acc)
  }

  /** Collects a List values of B by applying partial function to only those elements of A, for which the PartialFunction is defined. */
  def collectList[B](pf: PartialFunction[A, B]): List[B] =
  { var acc: List[B] = Nil
    foreach{a => if (pf.isDefinedAt(a)) acc ::= pf(a) }
    acc.reverse
  }

  /** maps from A to EMon[B], collects the good values. */
  def mapCollectGoods[B, BB <: ArrBase[B]](f: A => EMon[B])(implicit ev: ArrBuilder[B, BB]): BB =
  { val acc = ev.newBuff()
    foreach(f(_).forGood(ev.buffGrow(acc, _)))
    ev.buffToBB(acc)
  }

  def max[B >: A](implicit ord: math.Ordering[B]): A =
  { var acc = apply(0)
    tailForeach{ el => acc = ord.max(acc, el) }
    acc
  }

  def min[B >: A](implicit ord: math.Ordering[B]): A =
  { var acc = apply(0)
    tailForeach{ el => acc = ord.min(acc, el) }
    acc
  }

  /** Gives the maximum value of type B, produced by applying the function from A to B on each element of this collection, or the default value if the
   *  collection is empty. */
  def fMax[B](defaultValue: B)(f: (A) => B)(implicit cmp: math.Ordering[B]): B = if (empty) defaultValue else
  { var acc = f(head)
    tailForeach{ el => acc = cmp.max(acc, f(el)) }
    acc
  }

  /** Gives the minimum value of type B, produced by applying the function from A to B on each element of this collection, or the default value if the
   *  collection is empty. */
  def fMin[B](defaultValue: B)(f: (A) => B)(implicit cmp: math.Ordering[B]): B =
  { var acc = f(head)
    tailForeach{ el => acc = cmp.min(acc, f(el)) }
    acc
  }

  def toStrsCommaFold(fToStr: A => String): String = toStrsFold(", ", fToStr)
  def toStrsCommaNoSpaceFold(fToStr: A => String): String = toStrsFold(",", fToStr)
  def toStrsSemiFold(fToStr: A => String): String = toStrsFold("; ", fToStr)
  def toStrsCommaParenth(fToStr: A => String): String = toStrsCommaFold(fToStr).enParenth
  def toStrsSemiParenth(fToStr: A => String): String = toStrsSemiFold(fToStr).enParenth

  def sum(implicit ev: Sumable[A] @uncheckedVariance): A = foldLeft[A](ev.identity)(ev.sum(_, _))
}

case class ArrayLikeShow[A, R <: SeqGen[A]](evA: ShowT[A]) extends ShowTSeqLike[A, R]
{
  override def syntaxDepthT(obj: R): Int = obj.fMax(1)(evA.syntaxDepthT(_))
  override def showT(obj: R, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = ""
}